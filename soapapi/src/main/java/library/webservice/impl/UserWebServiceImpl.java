/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package library.webservice.impl;

import java.util.List;
import javax.jws.WebService;
import library.entity.dto.UserDTO;
import library.service.UserService;
import library.webservice.UserWebService;
import org.springframework.beans.factory.annotation.Autowired;

/**
 *
 * @author Nemko
 */
@WebService(endpointInterface = "library.webservice.UserWebService")
public class UserWebServiceImpl implements UserWebService {

    @Autowired
    private UserService userService;
    
    public void createUser(UserDTO user) throws IllegalArgumentException {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public void updateUser(UserDTO user) throws IllegalArgumentException {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public void deleteUser(UserDTO user) throws IllegalArgumentException {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public UserDTO getUserByID(Long id) throws IllegalArgumentException {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public UserDTO getUserByUsername(String username) throws IllegalArgumentException {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public List<UserDTO> findUserByRealName(String name) throws IllegalArgumentException {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public List<UserDTO> getUsers() {
        throw new UnsupportedOperationException("Not supported yet.");
    }
    
}
