/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package library.service.impl;

import java.util.ArrayList;
import java.util.List;
import library.dao.UserDAO;
import library.entity.UserDO;
import library.entity.dto.User;
import library.service.UserService;
import org.dozer.Mapper;
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
    private UserDAO userDAO; 
    
    @Autowired
    private Mapper mapper;

    @Override
    @Transactional
    public void createUser(User user) throws IllegalArgumentException{// 
            if(user == null){ throw new IllegalArgumentException(); } // mapper nesmie dostat ako source null
            UserDO userDO = mapper.map(user, UserDO.class);
            userDAO.createUser(userDO);
            user.setUserID(userDO.getUserID()); // treba nastavit ID pretoze DTO nie je manazovany factory
    }

    @Override
    @Transactional(readOnly=true)
    public User getUserByID(Long ID) throws IllegalArgumentException{//        
        return mapper.map(userDAO.getUserByID(ID),User.class);
    }

    @Override
    @Transactional(readOnly=true)
    public List<User> getUsers() {
        List<User> users = new ArrayList<>();
        List<UserDO> usersDO = userDAO.getUsers();
        for(UserDO uDO : usersDO)
        {
            users.add(mapper.map(uDO, User.class));
        }
        
        return users;
    }

    @Override
    @Transactional
    public void updateUser(User user) throws IllegalArgumentException {//
            if(user == null){ throw new IllegalArgumentException(); }
            userDAO.updateUser(mapper.map(user, UserDO.class)); 
    }

    @Override
    @Transactional
    public void deleteUser(User user) throws IllegalArgumentException {//
        if(user == null){ throw new IllegalArgumentException(); }
        UserDO userDO = mapper.map(user, UserDO.class);
        userDAO.deleteUser(userDO);
    }

    @Override
    @Transactional(readOnly=true)
    public User getUserByUsername(String username) throws IllegalArgumentException {
        return mapper.map(userDAO.getUserByUsername(username),User.class);
    }

    @Override
    @Transactional(readOnly=true)
    public List<User> findUserByRealName(String name) throws IllegalArgumentException {
        List<User> users = new ArrayList<>();
        List<UserDO> usersDO = userDAO.findUserByRealName(name);
        for(UserDO uDO : usersDO)
        {
            users.add(mapper.map(uDO, User.class));
        }
        return users;
    }
    
}
