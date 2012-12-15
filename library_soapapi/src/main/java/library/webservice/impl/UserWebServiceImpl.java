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
    
    @Override
    public void createUser(UserDTO user) throws IllegalArgumentException {
        userService.createUser(user);
    }

    @Override
    public void updateUser(UserDTO user) throws IllegalArgumentException {
        userService.updateUser(user);
    }

    @Override
    public void deleteUser(UserDTO user) throws IllegalArgumentException {
        userService.deleteUser(user);
    }

    @Override
    public UserDTO getUserByID(Long id) throws IllegalArgumentException {
        return userService.getUserByID(id);
    }

    @Override
    public UserDTO getUserByUsername(String username) throws IllegalArgumentException {
        return userService.getUserByUsername(username);
    }

    @Override
    public List<UserDTO> findUserByRealName(String name) throws IllegalArgumentException {
        return userService.findUserByRealName(name);
    }

    @Override
    public List<UserDTO> getUsers() {
        return userService.getUsers();
    }
    
}
