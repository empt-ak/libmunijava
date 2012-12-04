/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package library.dao;

import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import library.entity.User;
import org.springframework.stereotype.Repository;

/**
 *
 * @author Szalai
 */
@Repository
public class UserDAOImpl implements UserDAO {

    private EntityManager entityManager;

    public void setEm(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    @Override
    public void createUser(User user) throws IllegalArgumentException {
        if (user == null) {
            throw new IllegalArgumentException("Sent user is null");
        }

        if (entityManager.getTransaction().isActive()) {
            entityManager.getTransaction().rollback();
        }

        entityManager.getTransaction().begin();
        entityManager.persist(user);
        entityManager.getTransaction().commit();

    }

    @Override
    public User getUserByID(Long id) throws IllegalArgumentException {
        if (id == null || id.compareTo(new Long(1)) < 0) {
            throw new IllegalArgumentException("Parsed id is null or not out of a valid range");
        }

        return entityManager.find(User.class, id);
    }

    @Override
    public List<User> findUserByRealName(String name) throws IllegalArgumentException {
        if (name == null || name.length() == 0) {
            throw new IllegalArgumentException("Parsed name is empty");
        }

        return entityManager.createQuery("SELECT u FROM User u WHERE u.realName LIKE :realName").setParameter("realName","%"+ name+"%").getResultList();
    }

    @Override
    public void updateUser(User user) throws IllegalArgumentException {
        if (user == null) {
            throw new IllegalArgumentException("Sent user is null");
        }
        if (user.getUserID() == null || user.getUserID().compareTo(new Long(1)) < 0) {
            throw new IllegalArgumentException("Sent user does not have his ID set");
        }

        if (entityManager.getTransaction().isActive()) {
            entityManager.getTransaction().rollback();
        }

        entityManager.getTransaction().begin();
        entityManager.merge(user);
        entityManager.getTransaction().commit();
    }

    @Override
    public List<User> getUsers() {
        return entityManager.createQuery("SELECT u FROM User u").getResultList();
    }

    @Override
    public void deleteUser(User user) throws IllegalArgumentException {
        if (user == null) {
            throw new IllegalArgumentException("npe");
        }
        if (user.getUserID() == null || user.getUserID().compareTo(new Long(1)) < 0) {
            throw new IllegalArgumentException("Sent user does not have his ID set");
        }

        User thisUser = entityManager.find(User.class, user.getUserID());

        if (thisUser != null) {
            if (entityManager.getTransaction().isActive()) {
                entityManager.getTransaction().rollback();
            }

            entityManager.getTransaction().begin();
            entityManager.remove(user);
            entityManager.getTransaction().commit();
        }
    }

    @Override
    public User getUserByUsername(String username) throws IllegalArgumentException {
        if (username == null || username.length() == 0) {
            throw new IllegalArgumentException("ERROR: Given username is null or has zero length");
        }

        return (User) entityManager.createQuery("SELECT u FROM User u WHERE u.username = :username").setParameter("username", username).getSingleResult();

    }
}
