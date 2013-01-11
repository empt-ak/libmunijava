/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package library.security;

/**
 *
 * @author Nemko
 */

import java.io.IOException;
import javax.security.auth.callback.Callback;
import javax.security.auth.callback.CallbackHandler;
import javax.security.auth.callback.UnsupportedCallbackException;
import library.dao.UserDAO;
import org.apache.ws.security.WSPasswordCallback;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class ServerPasswordCallback implements CallbackHandler {
    
    @Autowired
    private UserDAO userDAO;

    @Override
    public void handle( Callback[] callbacks ) throws IOException, UnsupportedCallbackException {

        WSPasswordCallback pc = (WSPasswordCallback) callbacks[0];

        pc.setPassword(userDAO.getUserByUsername(pc.getIdentifier()).getPassword());
    }

}
