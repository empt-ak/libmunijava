/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package library.service;

import java.util.List;
import library.entity.dto.UserDTO;

/**
 *
 * @author Gaspar
 */
public interface UserService 
{
    
    void createUser(UserDTO user) throws IllegalArgumentException;
   
    void updateUser(UserDTO user) throws IllegalArgumentException;    
   
    void deleteUser(UserDTO user) throws IllegalArgumentException;    
    
    UserDTO getUserByID(Long id) throws IllegalArgumentException;  
    
    UserDTO getUserByUsername(String username) throws IllegalArgumentException;
     
    List<UserDTO> findUserByRealName(String name) throws IllegalArgumentException;
    
    List<UserDTO> getUsers();

    public boolean authenticate(String userName, String password);
    
}
