/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package library.service.impl;

import java.util.List;
import library.dao.UserDAO;
import library.entity.User;
import library.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * @author Gaspar
 */
@Service
public class UserServiceImpl implements UserService
{
    @Autowired
    UserDAO userDAO;    

    @Override
    @Transactional
    public void createUser(User user) throws IllegalArgumentException{//       
            userDAO.createUser(user);       
    }

    @Override
    @Transactional
    public User getUserByID(Long ID) throws IllegalArgumentException{//
        return userDAO.getUserByID(ID);
    }

    @Override
    @Transactional
    public List<User> getUsers() {//
        return userDAO.getUsers();
    }

    @Override
    @Transactional
    public void updateUser(User user) throws IllegalArgumentException {//        
            userDAO.updateUser(user); 
    }

    @Override
    @Transactional
    public void deleteUser(User user) throws IllegalArgumentException {//
        userDAO.deleteUser(user);
    }

    @Override
    @Transactional
    public User getUserByUsername(String username) throws IllegalArgumentException {
        return userDAO.getUserByUsername(username);
    }

    @Override
    @Transactional
    public List<User> findUserByRealName(String name) throws IllegalArgumentException {
        return userDAO.findUserByRealName(name);
    }
    
}
