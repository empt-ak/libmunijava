/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package library.service.impl;

import java.util.ArrayList;
import java.util.List;
import library.dao.UserDAO;
import library.entity.User;
import library.entity.dto.UserDTO;
import library.service.UserService;
import org.dozer.Mapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * @author Gaspar
 */
@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserDAO userDAO;
    @Autowired
    private Mapper mapper;

    
    @Override
    @Transactional
    public void createUser(UserDTO userDTO) throws IllegalArgumentException {
        if (userDTO == null) {
            throw new IllegalArgumentException("ERROR: given user is null");
        } // mapper nesmie dostat ako source null
        User user = mapper.map(userDTO, User.class);
        userDAO.createUser(user);
        
        userDTO.setUserID(user.getUserID());
        
    }

    @PreAuthorize("hasRole('ROLE_USER')")
    @Override
    @Transactional(readOnly = true)
    public UserDTO getUserByID(Long ID) throws IllegalArgumentException {
        User user = userDAO.getUserByID(ID);
        if (user != null) {
            return mapper.map(userDAO.getUserByID(ID), UserDTO.class);
        } else {
            return null;
        }
    }

    @PreAuthorize("hasRole('ROLE_ADMINISTRATOR')")
    @Override
    @Transactional(readOnly = true)
    public List<UserDTO> getUsers() {
        List<UserDTO> userDTOs = new ArrayList<>();
        List<User> users = userDAO.getUsers();
        for (User u : users) {
            userDTOs.add(mapper.map(u, UserDTO.class));
        }

        return userDTOs;
    }

    @PreAuthorize("hasRole('ROLE_USER')")
    @Override
    @Transactional
    public void updateUser(UserDTO userDTO) throws IllegalArgumentException {
        if (userDTO == null) {
            throw new IllegalArgumentException("ERROR: Given user is null");
        }
        userDAO.updateUser(mapper.map(userDTO, User.class));
    }

    @PreAuthorize("hasRole('ROLE_ADMINISTRATOR')")
    @Override
    @Transactional
    public void deleteUser(UserDTO userDTO) throws IllegalArgumentException {
        if (userDTO == null) {
            throw new IllegalArgumentException("ERROR: Given user is null");
        }
        User user = mapper.map(userDTO, User.class);
        userDAO.deleteUser(user);
    }

    @Override
    @Transactional(readOnly = true)
    public UserDTO getUserByUsername(String username) throws IllegalArgumentException {
        return mapper.map(userDAO.getUserByUsername(username), UserDTO.class);
    }

    @PreAuthorize("hasRole('ROLE_ADMINISTRATOR')")
    @Override
    @Transactional(readOnly = true)
    public List<UserDTO> findUserByRealName(String name) throws IllegalArgumentException {
        List<UserDTO> userDTOs = new ArrayList<>();
        List<User> users = userDAO.findUserByRealName(name);
        for (User u : users) {
            userDTOs.add(mapper.map(u, UserDTO.class));
        }
        return userDTOs;
    }
    
}
