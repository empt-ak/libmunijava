/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package library.gui;

import java.net.ConnectException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import library.gui.tools.Tools;
import library.webservice.BookWebService;
import library.webservice.IllegalArgumentException_Exception;
import library.webservice.UserDTO;
import library.webservice.UserWebService;
import library.webservice.impl.BookWebServiceImplService;
import library.webservice.impl.UserWebServiceImplService;
import org.apache.cxf.endpoint.Client;
import org.apache.cxf.endpoint.Endpoint;
import org.apache.cxf.frontend.ClientProxy;
import org.apache.cxf.ws.security.wss4j.WSS4JOutInterceptor;
import org.apache.ws.security.WSConstants;
import org.apache.ws.security.handler.WSHandlerConstants;

/**
 *
 * @author Emptak,Nemko
 */
public class ConnectionHolder {

    private BookWebService bws = null;
    private UserWebService uws = null;
    private static final String ERROR = java.util.ResourceBundle.getBundle("Messages").getString("gui.connection.error");
    private static ConnectionHolder instance = new ConnectionHolder();
    private static Properties props = null;
    // default role is Anonymous
    private static Role role = Role.ANONYMOUS;
    private static List<String> roles = new ArrayList<>(Arrays.asList("ADMINISTRATOR", "ANONYMOUS", "USER"));

    public static ConnectionHolder getInstance() {
        initProps();
        return instance;
    }

    public static Role getRole() {
        return role;
    }
    
    

    public static void resetConnection() {
        instance = new ConnectionHolder();
        ClientPasswordCallback.setPassword("");
        role = Role.ANONYMOUS;
    }

    public ConnectionHolder() {
        initProps();
    }

    public void setServiceCredentials(String userName, String password) throws ConnectException, IllegalArgumentException_Exception {
        if (check()) {
            bws = new BookWebServiceImplService().getBookWebServiceImplPort();
            uws = new UserWebServiceImplService().getUserWebServiceImplPort();
            Client client1 = ClientProxy.getClient(bws);
            Endpoint endpoint1 = client1.getEndpoint();
            Client client2 = ClientProxy.getClient(uws);
            Endpoint endpoint2 = client2.getEndpoint();

            Map<String, Object> outProps = new HashMap<>();
            outProps.put(WSHandlerConstants.ACTION, WSHandlerConstants.USERNAME_TOKEN);
            // Specify our username
            outProps.put(WSHandlerConstants.USER, userName);
            ClientPasswordCallback.setPassword(password);
            // Password type : digest
            outProps.put(WSHandlerConstants.PASSWORD_TYPE, WSConstants.PW_DIGEST);
            // Callback used to retrieve password for given user.
            outProps.put(WSHandlerConstants.PW_CALLBACK_CLASS,
                    ClientPasswordCallback.class.getName());
            WSS4JOutInterceptor wssOut = new WSS4JOutInterceptor(outProps);
            endpoint1.getOutInterceptors().add(wssOut);
            endpoint2.getOutInterceptors().add(wssOut);
            setUserRole(userName);
        } else {
            error();
            throw new ConnectException(ERROR);
        }
    }

    public BookWebService getBws() throws ConnectException {
        if (!check()) {
            error();
            throw new ConnectException(ERROR);
        } else {
            if (bws == null) {
                bws = new BookWebServiceImplService().getBookWebServiceImplPort();
            }
            return bws;
        }
    }

    public UserWebService getUws() throws ConnectException {
        if (!check()) {
            error();
            throw new ConnectException(ERROR);
        } else {
            if (uws == null) {
                uws = new UserWebServiceImplService().getUserWebServiceImplPort();
            }
            return uws;
        }
    }

    private boolean check() {
        return checkIfURLExists(props.getProperty("service.general.url"));
    }

    /**
     * Method used for checking if web service on specified port and address is
     * running. taken from
     * http://singztechmusings.wordpress.com/2011/05/26/java-how-to-check-if-a-web-page-exists-and-is-available/
     *
     * @param targetUrl url to be checked
     * @return true if server is running and given url is one of ours, false
     * otherwise
     */
    private static boolean checkIfURLExists(String targetUrl) {
        HttpURLConnection httpUrlConn = null;
        boolean exists = false;
        try {
            httpUrlConn = (HttpURLConnection) new URL(targetUrl).openConnection();

            httpUrlConn.setRequestMethod("HEAD");

            // Set timeouts in milliseconds
            httpUrlConn.setConnectTimeout(30000);
            httpUrlConn.setReadTimeout(30000);

            // Print HTTP status code/message for your information.
            System.out.println("Response Code: "
                    + httpUrlConn.getResponseCode());
            System.out.println("Response Message: "
                    + httpUrlConn.getResponseMessage());
            System.out.println("WEB Service is running");

            exists = (httpUrlConn.getResponseCode() == HttpURLConnection.HTTP_OK);
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }

        return exists;
    }

    private void error() {
        Tools.createErrorDialog(ERROR);
    }

    private static void initProps() {
        if (props == null) {
            props = Tools.getProperties();
        }
    }

    public void setUserRole(String userName) throws IllegalArgumentException_Exception {
        try {
        UserDTO user = uws.getUserByUsername(userName);
        
        String roleString = user.getSystemRole();
        switch (roleString) {
            case "USER":
                role = Role.USER;
            case "ADMINISTRATOR":
                role = Role.ADMINISTRATOR;
            default:
                role = Role.ANONYMOUS;
        }
        } catch (Exception e) {
            System.out.println(e.getCause());
        }
    }
}
