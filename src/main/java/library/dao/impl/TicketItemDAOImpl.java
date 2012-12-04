/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package library.dao.impl;

import java.util.ArrayList;
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
    private static final org.apache.log4j.Logger logger = org.apache.log4j.Logger.getLogger(TicketItemDAOImpl.class);

    @Override
    public void createTicketItem(TicketItem ticketItem) {
        logger.debug("creating object:"+ticketItem);
        if (ticketItem == null) {
            throw new IllegalArgumentException("Error creating TicketItem object: passed TicketItem is null");
        }        
        checkTicketItem(ticketItem);
        
        entityManager.persist(ticketItem);
    }

    @Override
    public TicketItem getTicketItemByID(Long id) throws IllegalArgumentException {
        if (id == null || id.compareTo(new Long(1)) < 0) {
            throw new IllegalArgumentException("Given ID is null or not within a valid range");
        }

        return entityManager.find(TicketItem.class, id);
    }

    @Override
    public void updateTicketItem(TicketItem ticketItem) throws IllegalArgumentException {
        if (ticketItem == null) {
            throw new IllegalArgumentException("Given TicketItem can't be updated " + ", it is null");
        }
        if(ticketItem.getTicketItemID() == null || ticketItem.getTicketItemID().compareTo(new Long(1)) < 0){
            throw new IllegalArgumentException("Given ticketitem can't be updated, its ID is null");
        }
        
        checkTicketItem(ticketItem);

        entityManager.merge(ticketItem);
      
    }

    @Override
    public void deleteTicketItem(TicketItem ticketItem) throws IllegalArgumentException {
        if (ticketItem == null) {
            throw new IllegalArgumentException("Given TicketItem can't be found "
                    + "in the database, it is null");
        }
        if(ticketItem.getTicketItemID() == null || ticketItem.getTicketItemID().compareTo(new Long(1)) < 0){
            throw new IllegalArgumentException("Given ticketitem can't be deleted, its ID is null");
        }

        TicketItem thisTicket = entityManager.find(TicketItem.class, ticketItem.getTicketItemID());
        if (thisTicket != null) {
           
            //http://stackoverflow.com/a/4273927
            //entityManager.remove(ticketItem);
            entityManager.remove(thisTicket);
            
            
        } else {
            throw new IllegalArgumentException("Given TicketItem can't be found "
                    + "in the database");
        }
    }

    @Override
    public List<TicketItem> getTicketItemsByTicket(Ticket ticket) throws IllegalArgumentException {
        if (ticket == null) {
            throw new IllegalArgumentException("Passed ticket is null");
        }
        if (ticket.getTicketID() == null || ticket.getTicketID().compareTo(new Long(0)) < 0){
            throw new IllegalArgumentException("wrong id");
        }        
        // http://stackoverflow.com/a/4834009
//        return entityManager.createQuery("SELECT ti FROM TicketItem ti WHERE ti.ticket.ticketID = :ticketID")
//                .setParameter("ticketID", ticket.getTicketID()).getResultList();
        Ticket t = entityManager.find(Ticket.class, ticket.getTicketID());
        
        return t.getTicketItems();        
    }
    
    private void checkTicketItem(TicketItem ticketItem){
        if(ticketItem.getBook() == null || ticketItem.getBook().getBookID() == null || ticketItem.getBook().getBookID().compareTo(new Long(1)) <0){
            throw new IllegalArgumentException("ERROR: Obtained ticketItem does not have set book");
        }
        if(ticketItem.getTicketItemStatus() == null){ 
            throw new IllegalArgumentException("ERROR: Obtained ticketItem does not have set its status");
        }
    }
}
