/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package library.security;

/**
 *
 * @author Nemko
 */
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import library.service.UserService;
import org.apache.cxf.binding.soap.SoapMessage;
import org.apache.cxf.interceptor.Fault;
import org.apache.cxf.ws.security.wss4j.WSS4JInInterceptor;
import org.apache.ws.security.WSConstants;
import org.apache.ws.security.WSSecurityEngineResult;
import org.apache.ws.security.WSSecurityException;
import org.apache.ws.security.WSUsernameTokenPrincipal;
import org.apache.ws.security.handler.WSHandlerConstants;
import org.apache.ws.security.handler.WSHandlerResult;
import org.apache.ws.security.util.Base64;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

@Component
public class WSAuthenticationInterceptor extends WSS4JInInterceptor {

    AuthenticationManager authenticationManager;
    
    @Autowired
    private UserService userService;

    public WSAuthenticationInterceptor() {
        super();
    }

    public WSAuthenticationInterceptor(final Map<String, Object> properties) {
        super(properties);
    }

    public void setAuthenticationManager(final AuthenticationManager authenticationManager) {
        this.authenticationManager = authenticationManager;
    }

    @Override
    public void handleMessage(final SoapMessage message) throws Fault {
        try {
            final ArrayList<WSHandlerResult> result = (ArrayList<WSHandlerResult>) message.getContextualProperty(WSHandlerConstants.RECV_RESULTS);
            if ((result != null) && !result.isEmpty()) {
                for (final WSHandlerResult res : result) {
                    // loop through security engine results
                    for (final WSSecurityEngineResult securityResult : (ArrayList<WSSecurityEngineResult>) res
                            .getResults()) {
                        final int action = (Integer) securityResult
                                .get(WSSecurityEngineResult.TAG_ACTION);
                        // determine if the action was a username token
                        if ((action & WSConstants.UT) > 0) {
                            // get the principal object
                            final WSUsernameTokenPrincipal principal = (WSUsernameTokenPrincipal) securityResult
                                    .get(WSSecurityEngineResult.TAG_PRINCIPAL);
                            if (principal.getPassword() == null) {
                                principal.setPassword("");
                            }

                            Authentication authentication = null;
                            if (principal.isPasswordDigest()) {
                                // we recompute value received and compare it to value stored in
                                // our security context
                                if (checkCredentials(principal.getNonce(), principal.getCreatedTime(),
                                        userService.getUserByUsername(principal.getName()).getPassword(),
                                        principal.getPassword())) {
                                    System.out.println("User " + principal.getName() + " was successfully"
                                            + " authenticated from a SOAP request");
                                    authentication = new UsernamePasswordAuthenticationToken(principal.getName(),
                                            userService.getUserByUsername(principal.getName()).getPassword());
                                }
                            } else {
                                authentication = new UsernamePasswordAuthenticationToken(
                                        principal.getName(), principal.getPassword());
                            }

                            authentication = authenticationManager.authenticate(authentication);
                            if (!authentication.isAuthenticated()) {
                                System.out.println("This user is not authentic.");
                            }
                            SecurityContextHolder.getContext().setAuthentication(authentication);
                        }
                    }
                }
            }
        } catch (final RuntimeException ex) {
            ex.printStackTrace();
            throw ex;
        }
    }

    // Digested password is in form of:
    // Password_Digest = Base64 ( SHA-1 ( nonce + created + password ) )
    // http://docs.oasis-open.org/wss/2004/01/oasis-200401-wss-username-token-profile-1.0.pdf
    private boolean checkCredentials(String nonce, String createdTime, String plainPassword, String password) {

        MessageDigest sha;

        try {
            sha = MessageDigest.getInstance("SHA-1");
            sha.reset();
            byte[] nonceDecoded = null;
            try {
                // nonce is sent encoded in Base64, thus we decode it
                nonceDecoded = Base64.decode(nonce);
            } catch (WSSecurityException ex) {
                Logger.getLogger(WSAuthenticationInterceptor.class.getName()).log(Level.SEVERE, null, ex);
            }

            byte[] createdInBytes = createdTime.getBytes("UTF-8");
            byte[] secretInBytes = plainPassword.getBytes("UTF-8");
            ByteArrayOutputStream outputStream = new ByteArrayOutputStream();

            // we concatenate nonce + created + password
            try {
                outputStream.write(nonceDecoded);
                outputStream.write(createdInBytes);
                outputStream.write(secretInBytes);
            } catch (IOException ex) {
                Logger.getLogger(WSAuthenticationInterceptor.class.getName()).log(Level.SEVERE, null, ex);
            }

            // we set outputstream content into MessageDigestor
            sha.update(outputStream.toByteArray());

            // we test Base64 encoded sha digested array with password retrieved via soap message
            return Base64.encode(sha.digest()).equals(password);

        } catch (NoSuchAlgorithmException | UnsupportedEncodingException ex) {
            Logger.getLogger(WSAuthenticationInterceptor.class.getName()).log(Level.SEVERE, null, ex);
        }

        return false;
    }
}