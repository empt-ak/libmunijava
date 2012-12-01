/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package library.dao.impl;

import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import library.dao.TicketDAO;
import library.entity.Ticket;
import library.entity.User;
import org.springframework.stereotype.Repository;

/**
 *
 * @author Nemcek
 */
@Repository
public class TicketDAOImpl implements TicketDAO {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public void createTicket(Ticket loanTicket) throws IllegalArgumentException { 
            entityManager.persist(loanTicket);
    }

    @Override
    public Ticket getTicketByID(Long id) throws IllegalArgumentException {
        return entityManager.find(Ticket.class, id);
    }

    @Override
    public void updateTicket(Ticket ticket) throws IllegalArgumentException {
            entityManager.merge(ticket);
    }

    @Override
    public void deleteTicket(Ticket ticket) throws IllegalArgumentException {    
        Ticket thisTicket = entityManager.find(Ticket.class, ticket.getTicketID());
        if (thisTicket != null) {
            entityManager.remove(thisTicket);
        }
    }

    @Override
    public Ticket getLastTicketForUser(User user) throws IllegalArgumentException {
         return entityManager.createQuery("SELECT t FROM Ticket t where "
                 + "t.ticketID = (SELECT MAX(ticketID) FROM Ticket t WHERE t.user = :user)",Ticket.class)
                 .setParameter("user", user).getSingleResult();
    }
    
    @Override
    public List<Ticket> getAllTicketsForUser(User user) throws IllegalArgumentException {        
        return entityManager.createQuery("SELECT t FROM Ticket t WHERE user = :user",Ticket.class)
                .setParameter("user", user).getResultList();
    }
}
