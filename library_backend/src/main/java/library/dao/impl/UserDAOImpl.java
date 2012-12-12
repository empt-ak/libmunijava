/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package library.dao.impl;

import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import library.dao.UserDAO;
import library.entity.User;
import org.springframework.stereotype.Repository;

/**
 *
 * @author Szalai
 */
@Repository
public class UserDAOImpl implements UserDAO 
{
    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public void createUser(User user) throws IllegalArgumentException 
    {  
        entityManager.persist(user);
    }

    @Override
    public User getUserByID(Long id) throws IllegalArgumentException 
    {        
        return entityManager.find(User.class, id);
    }

    @Override
    public List<User> findUserByRealName(String name) throws IllegalArgumentException 
    {        
        return entityManager.createQuery("SELECT u FROM User u WHERE u.realName LIKE :realName", User.class)
                .setParameter("realName", "%" + name + "%").getResultList();
    }

    @Override
    public void updateUser(User user) throws IllegalArgumentException 
    {        
        entityManager.merge(user);
    }

    @Override
    public List<User> getUsers() 
    {        
        return entityManager.createQuery("SELECT u FROM User u", User.class).getResultList();
    }

    @Override
    public void deleteUser(User user) throws IllegalArgumentException 
    {
        User thisUser = entityManager.find(User.class, user.getUserID());

        if (thisUser != null) 
        {            
            entityManager.remove(thisUser);
        }
    }

    @Override
    public User getUserByUsername(String username) throws IllegalArgumentException 
    {        
        return entityManager.createQuery("SELECT u FROM User u WHERE u.username = :username", User.class)
                .setParameter("username", username).getSingleResult();
    }

    @Override
    public boolean authenticate(String userName, String password) {
        try {
            entityManager.createQuery("SELECT u FROM User u WHERE u.username = :username "
                    + "AND u.password = :password", User.class).setParameter("username", userName)
                    .setParameter("password",password).getSingleResult();
            return true;
        } catch (NoResultException e) {
            return false;
        }   
    }

}
