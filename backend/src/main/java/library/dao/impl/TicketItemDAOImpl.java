/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package library.dao.impl;

import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import library.dao.TicketItemDAO;
import library.entity.Ticket;
import library.entity.TicketItem;

/**
 *
 * @author Gaspar
 */
public class TicketItemDAOImpl implements TicketItemDAO {

    @PersistenceContext
    private EntityManager entityManager;    

    @Override
    public void createTicketItem(TicketItem ticketItem) {
        entityManager.persist(ticketItem);
    }

    @Override
    public TicketItem getTicketItemByID(Long id) throws IllegalArgumentException {
        return entityManager.find(TicketItem.class, id);
    }

    @Override
    public void updateTicketItem(TicketItem ticketItem) throws IllegalArgumentException {
        entityManager.merge(ticketItem);      
    }

    @Override
    public void deleteTicketItem(TicketItem ticketItem) throws IllegalArgumentException {
        TicketItem thisTicket = entityManager.find(TicketItem.class, ticketItem.getTicketItemID());
        if (thisTicket != null) {           
            entityManager.remove(thisTicket);
        }
    }

    @Override
    public List<TicketItem> getTicketItemsByTicket(Ticket ticket) throws IllegalArgumentException {
        Ticket t = entityManager.find(Ticket.class, ticket.getTicketID());
        
        return t.getTicketItems();        
    }
}
