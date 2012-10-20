/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package library.service;

import java.util.List;
import library.entity.Ticket;
import library.entity.TicketItem;

/**
 *
 * @author Gajdos
 */
public interface TicketItemService {
  
    
    /**
     * Method serving for creating TicketItems inside database. 
     * @param ticketItem
     * @throws IllegalArgumentException 
     */
    void createTicketItem(TicketItem ticketItem) throws IllegalArgumentException;    
    
    /**
     * Method used for updating given TicketItem. 
     * @param ticketItem ticketItem object for updating
     * @throws IllegalArgumentException if ticketItem is null
     */
    void updateTicketItem(TicketItem ticketItem) throws IllegalArgumentException;
    
    /**
     * Method used for retrieving TicketItems from the database. 
     * @param ticketItem ticketItem object for deletion
     * @throws IllegalArgumentException if ticketItem is null or does not exist in the database
     */
    void deleteTicketItem(TicketItem ticketItem) throws IllegalArgumentException;
    
    /**
     * Method used for retrieving TicketItem by given id. 
     * @param id ID of an TicketItem we would like to retrieve
     * @return List with books by given department or empty {@link java.util.ArrayList} if there are no books
     * @throws IllegalArgumentException if id is null or lower or equal to zero
     */
    TicketItem getTicketItemByID(Long id) throws IllegalArgumentException;
    
    /**
     * Method used for retrieving TicketItems belonging to a given TicketItem.
     * @param ticket ticket for which we would like to get its TicketItems
     * @return List with TicketItems by given ticket or empty {@link java.util.ArrayList} if there are no TicketItems
     * @throws IllegalArgumentException if ticket is null
     */
    List<TicketItem> getTicketItemsByTicket(Ticket ticket) throws IllegalArgumentException;
}
  

