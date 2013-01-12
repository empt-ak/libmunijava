/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package library.gui;

import java.io.IOException;
import javax.security.auth.callback.Callback;
import javax.security.auth.callback.CallbackHandler;
import javax.security.auth.callback.UnsupportedCallbackException;
import org.apache.ws.security.WSPasswordCallback;

/**
 *
 * @author Nemko
 */
public class ClientPasswordCallback implements CallbackHandler {
    
    private static String password = "";

    @Override
    public void handle(Callback[] callbacks) throws IOException, 
        UnsupportedCallbackException {

        WSPasswordCallback pc = (WSPasswordCallback) callbacks[0];

        // set the password for our message.
        pc.setPassword(password);
    }
    
    public static void setPassword(String password) {
        ClientPasswordCallback.password = password;
    }

}
