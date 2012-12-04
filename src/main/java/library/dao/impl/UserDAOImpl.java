/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package library.dao.impl;

import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import library.dao.UserDAO;
import library.entity.User;
import library.utils.ValidationUtils;
import org.springframework.stereotype.Repository;

/**
 *
 * @author Szalai
 */
@Repository
public class UserDAOImpl implements UserDAO {

    @PersistenceContext
    private EntityManager entityManager;
    private static final org.apache.log4j.Logger logger = org.apache.log4j.Logger.getLogger(UserDAOImpl.class);

    @Override
    public void createUser(User user) throws IllegalArgumentException {
        logger.debug("==Creating user : " + user);
        if (user == null) {
            throw new IllegalArgumentException("ERROR when creating user: obtained user is null");
        }
        ValidationUtils.checkUser(user);

        entityManager.persist(user);
    }

    @Override
    public User getUserByID(Long id) throws IllegalArgumentException {
        logger.debug("==Getting user by id : " + id);
        if (id == null || id.compareTo(new Long(1)) < 0) {
            throw new IllegalArgumentException("ERROR when getting user: obtained id is null or out of a valid range");
        }

        return entityManager.find(User.class, id);
    }

    @Override
    public List<User> findUserByRealName(String name) throws IllegalArgumentException {
        logger.debug("==Searching user by his real name : " + name);
        if (name == null || name.length() == 0) {
            throw new IllegalArgumentException("ERROR when searching for user: obtained name is null or has zero length");
        }

        return entityManager.createQuery("SELECT u FROM User u WHERE u.realName LIKE :realName", User.class).setParameter("realName", "%" + name + "%").getResultList();
    }

    @Override
    public void updateUser(User user) throws IllegalArgumentException {
        logger.debug("==Updating user : " + user);
        if (user == null) {
            throw new IllegalArgumentException("ERROR when updating user: obtained user is null");
        }
        if (user.getUserID() == null || user.getUserID().compareTo(new Long(1)) < 0) {
            throw new IllegalArgumentException("ERROR when updating user: obtained user does not have set its id");
        }
        ValidationUtils.checkUser(user);

        entityManager.merge(user);


    }

    @Override
    public List<User> getUsers() {
        logger.debug("==Getting all users");
        return entityManager.createQuery("SELECT u FROM User u", User.class).getResultList();
    }

    @Override
    public void deleteUser(User user) throws IllegalArgumentException {
        logger.debug("==Deleting user : " + user);
        if (user == null) {
            throw new IllegalArgumentException("ERROR when deleting user: obtained user is null");
        }
        if (user.getUserID() == null || user.getUserID().compareTo(new Long(1)) < 0) {
            throw new IllegalArgumentException("ERROR when deleting user: obtained user does not have his ID set");
        }

        User thisUser = entityManager.find(User.class, user.getUserID());

        if (thisUser != null) {
            //http://stackoverflow.com/a/4273927
            //entityManager.remove(user);
            entityManager.remove(thisUser);
        }
    }

    @Override
    public User getUserByUsername(String username) throws IllegalArgumentException {
        logger.debug("==Getting user by his username : " + username);
        if (username == null || username.length() == 0) {
            throw new IllegalArgumentException("ERROR: Given username is null or has zero length");
        }

        return entityManager.createQuery("SELECT u FROM User u WHERE u.username = :username", User.class).setParameter("username", username).getSingleResult();
    }

}
