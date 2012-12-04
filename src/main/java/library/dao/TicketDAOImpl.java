/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package library.dao;

import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import library.entity.Ticket;
import library.entity.User;
import org.joda.time.DateTime;
import org.springframework.stereotype.Repository;

/**
 *
 * @author Nemcek
 */
@Repository
public class TicketDAOImpl implements TicketDAO {

    private EntityManager entityManager;

    public void setEm(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    @Override
    public void createTicket(Ticket loanTicket) throws IllegalArgumentException {
        if (loanTicket == null) {
            throw new IllegalArgumentException("Error creating Ticket object: passed TicketItem is null");
        }

        if (entityManager.getTransaction().isActive()) {
            entityManager.getTransaction().rollback();
        }

        entityManager.getTransaction().begin();
        entityManager.persist(loanTicket);
        entityManager.getTransaction().commit();
    }

    @Override
    public Ticket getTicketByID(Long id) throws IllegalArgumentException {
        if (id == null) {
            throw new IllegalArgumentException("Given Ticket can't be found "
                    + "in the database, it's ID is null");
        }

        return entityManager.find(Ticket.class, id);
    }

    @Override
    public void updateTicket(Ticket ticket) throws IllegalArgumentException {
        if(ticket == null){
            throw new IllegalArgumentException("Given ticket is null");
        }
        try {
            if (entityManager.getTransaction().isActive()) {
                entityManager.getTransaction().rollback();
            }

            entityManager.getTransaction().begin();
            entityManager.merge(ticket);
            entityManager.getTransaction().commit();
        } catch (Exception e) {
            throw new IllegalArgumentException("Error updating Ticket object: " + e.toString());
        }
    }

    @Override
    public void deleteTicket(Ticket ticket) throws IllegalArgumentException {
        Ticket thisTicket = entityManager.find(Ticket.class, ticket.getTicketID());
        if (thisTicket != null) {
            if (entityManager.getTransaction().isActive()) {
                entityManager.getTransaction().rollback();
            }

            entityManager.getTransaction().begin();
            entityManager.remove(ticket);
            entityManager.getTransaction().commit(); 
        } else {
            throw new IllegalArgumentException("Given Ticket can't be found "
                    + "in the database, it's ID does not exist or is null");
        }
    }

    @Override
    public Ticket getLastTicketForUser(User user) throws IllegalArgumentException {
        if (user == null) {
            throw new IllegalArgumentException("Given user is null");
        }

        return (Ticket) entityManager.createQuery("SELECT MAX(t.ticketID) AS ticketID,t.borrowTime,t.userID FROM Ticket t WHERE t.userID = :userID")
                .setParameter("userID", user.getUserID()).getSingleResult();
    }

    @Override
    public List<Ticket> getTicketsInPeriodForUser(DateTime from, DateTime to, User user) throws IllegalArgumentException {
        // http://dev.mysql.com/doc/refman/5.5/en/date-and-time-functions.html#function_unix-timestamp
        // unix_tstamp vracia pocet sekund
        if (from == null) {
            throw new IllegalArgumentException("Given DateTime indicating start date is null");
        }
        if (to == null) {
            throw new IllegalArgumentException("Given DateTime indicating end date is null");
        }
        if (user == null) {
            throw new IllegalArgumentException("Given user is null");
        }

        return entityManager.createQuery("SELECT t FROM Ticket t WHERE UNIX_TIMESTAMP(t.borrowTime) > :st AND UNIX_TIMESTAMP(t.borrowTime) < :end AND userID =: userID")
                .setParameter("st", from.getMillis() / 1000).setParameter("end", to.getMillis() / 1000)
                .setParameter("userID", user.getUserID()).getResultList();
    }

    @Override
    public List<Ticket> getAllTicketsForUser(User user) throws IllegalArgumentException {
        if (user == null) {
            throw new IllegalArgumentException("Given user is null");
        }

        return entityManager.createQuery("SELECT t FROM Ticket t WHERE userID = :userID")
                .setParameter("userID", user.getUserID()).getResultList();
    }
}
