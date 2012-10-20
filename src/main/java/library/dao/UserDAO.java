/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package library.dao;

import java.util.List;
import library.entity.User;

/**
 *
 * @author Gaspar
 */
public interface UserDAO {
    
    //void setEm(EntityManager entityManager);
    
    /**
    * Creates new object of type User.
    * 
    * @param user the object to be created.
    * @throws IllegalArgumentException if user is null
    */ 
    void createUser(User user) throws IllegalArgumentException;
    /**
    * Modifies data about some existing object of type User. 
    * 
    * @param user object to be modified.
    * @throws IllegalArgumentException if user does not set his id or user is null.
    */
    void updateUser(User user) throws IllegalArgumentException;    
    /**
    * Removes some existing object of type User.
    * 
    * @param user the object to be removed.
    * @throws IllegalArgumentException if user does not have set his ID or user is null.
    */
    void deleteUser(User user) throws IllegalArgumentException;    
    /**
    * Finds user with some ID.
    * 
    * @param id identifies object to be found.
    * @return object of type User with inscribed id, what has been found.
    * @throws IllegalArgumentException if parsed id is null or out of range. 
    */ 
    User getUserByID(Long id) throws IllegalArgumentException;  
    /**
     * Finds user based on his username
     * @param username identifies object based on his username
     * @return object of type User with inscribed username, what has been found
     * @throws IllegalArgumentException if parsed username is empty
     */
    User getUserByUsername(String username) throws IllegalArgumentException;
    /**
    * Finds user in the database by his name.
    * 
    * @param name represents concrete data of the user to be found.
    * @return object of type user with precise name.
    * @throws IllegalArgumentException if parsed name is empty.
    */ 
    List<User> findUserByRealName(String name) throws IllegalArgumentException;
    /**
    * Returns all users in the database.
    * 
    * @return list of the objects of type User.
    */
    List<User> getUsers();
}
