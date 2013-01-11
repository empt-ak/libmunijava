/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package library.security;

/**
 *
 * @author Nemko
 */
import java.io.UnsupportedEncodingException;
import java.security.NoSuchAlgorithmException;
import java.util.Map;
import java.util.Vector;
import java.util.logging.Level;
import java.util.logging.Logger;
import library.utils.Tools;
import org.apache.cxf.binding.soap.SoapMessage;
import org.apache.cxf.interceptor.Fault;
import org.apache.cxf.ws.security.wss4j.WSS4JInInterceptor;
import org.apache.ws.security.WSConstants;
import org.apache.ws.security.WSSecurityEngineResult;
import org.apache.ws.security.WSUsernameTokenPrincipal;
import org.apache.ws.security.handler.WSHandlerConstants;
import org.apache.ws.security.handler.WSHandlerResult;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.security.core.Authentication;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.util.Assert;

public class WSAuthenticationInterceptor extends WSS4JInInterceptor {

    AuthenticationManager authenticationManager;

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
            final Vector<WSHandlerResult> result = (Vector<WSHandlerResult>) message
                    .getContextualProperty(WSHandlerConstants.RECV_RESULTS);
            if ((result != null) && !result.isEmpty()) {
                for (final WSHandlerResult res : result) {
                    // loop through security engine results
                    for (final WSSecurityEngineResult securityResult : (Vector<WSSecurityEngineResult>) res
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
                            Authentication authentication = new UsernamePasswordAuthenticationToken(
                                    principal.getName(), principal.getPassword());
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

}
//public class WSAuthenticationInterceptor extends AbstractPhaseInterceptor<SoapMessage> implements InitializingBean {
//
//    private AuthenticationManager authenticationManager;
//
//    public WSAuthenticationInterceptor() {
//
//        super(Phase.POST_STREAM);
//    }
//
//    /**
//     *
//     * @throws Exception
//     */
//    @Override
//    public void afterPropertiesSet() throws Exception {
//
//        // ensure the 2 objects we need are not null
//        Assert.notNull(authenticationManager, "Authentication Manager should not be null!");
//    }
//
//    /**
//     *
//     * @param message
//     * @throws Fault
//     */
//    @Override
//    public void handleMessage(SoapMessage message) throws Fault {
//
//        // get out the results from the message context
////        Vector<WSHandlerResult> result = (Vector<WSHandlerResult>) message.getContextualProperty( WSHandlerConstants.RECV_RESULTS );
////        for (WSHandlerResult res : result) {
////
////            // loop through security engine results
////            for (WSSecurityEngineResult securityResult : (Vector<WSSecurityEngineResult>) res.getResults()) {
////
////                String action = (String) securityResult.get(WSSecurityEngineResult.TAG_ACTION);
////
////                // determine if the action was a username token
////                if ( !action.isEmpty() && WSConstants.UT  > 0 ) {
////
////                    // get the principal object
////                    WSUsernameTokenPrincipal principal = (WSUsernameTokenPrincipal) securityResult.get(WSSecurityEngineResult.TAG_PRINCIPAL);
////
////                    Authentication authentication = new UsernamePasswordAuthenticationToken( principal.getName(), principal.getPassword() );
////                    authentication = authenticationManager.authenticate( authentication );
////
////                    if ( !authentication.isAuthenticated() ) {
////                        System.out.println( "This user is not authentic." );
////                        //throw new AuthenticationException( "This user is not authentic." );
////                    }
////
////                    SecurityContextHolder.getContext().setAuthentication( authentication );
////                }
////            }
////        }
//        
////        List<Header> headers = new Ar
//        
//        System.out.println(message.toString());
//        Authentication authentication = new UsernamePasswordAuthenticationToken("admin", "d033e22ae348aeb5660fc2140aec35850c4da997");
////        ("admin","d033e22ae348aeb5660fc2140aec35850c4da997");
//        authentication = authenticationManager.authenticate(authentication);
//
//        if (!authentication.isAuthenticated()) {
//            System.out.println("This user is not authentic.");
//            //throw new AuthenticationException( "This user is not authentic." );
//        }
//
//        SecurityContextHolder.getContext().setAuthentication(authentication);
//    }
//
//    /**
//     * @return the authenticationManager
//     */
//    public AuthenticationManager getAuthenticationManager() {
//
//        return authenticationManager;
//    }
//
//    /**
//     * @param authenticationManager the authenticationManager to set
//     */
//    public void setAuthenticationManager(AuthenticationManager authenticationManager) {
//
//        this.authenticationManager = authenticationManager;
//    }
//}
