/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package library.service;

import java.util.List;
import library.entity.dto.User;

/**
 *
 * @author Gaspar
 */
public interface UserService 
{
    
    void createUser(User user) throws IllegalArgumentException;
   
    void updateUser(User user) throws IllegalArgumentException;    
   
    void deleteUser(User user) throws IllegalArgumentException;    
    
    User getUserByID(Long id) throws IllegalArgumentException;  
    
    User getUserByUsername(String username) throws IllegalArgumentException;
     
    List<User> findUserByRealName(String name) throws IllegalArgumentException;
    
    List<User> getUsers();
    
}
