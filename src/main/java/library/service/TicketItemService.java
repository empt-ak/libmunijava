/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package library.service;

import java.util.List;
import library.entity.dto.TicketDTO;
import library.entity.dto.TicketItemDTO;


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
    void createTicketItem(TicketItemDTO ticketItem) throws IllegalArgumentException;    
    
    /**
     * Method used for updating given TicketItemDTO. 
     * @param ticketItem ticketItem object for updating
     * @throws IllegalArgumentException if ticketItem is null
     */
    void updateTicketItem(TicketItemDTO ticketItem) throws IllegalArgumentException;
    
    /**
     * Method used for retrieving TicketItems from the database. 
     * @param ticketItem ticketItem object for deletion
     * @throws IllegalArgumentException if ticketItem is null or does not exist in the database
     */
    void deleteTicketItem(TicketItemDTO ticketItem) throws IllegalArgumentException;
    
    /**
     * Method used for retrieving TicketItemDTO by given id. 
     * @param id ID of an TicketItemDTO we would like to retrieve
     * @return List with books by given department or empty {@link java.util.ArrayList} if there are no books
     * @throws IllegalArgumentException if id is null or lower or equal to zero
     */
    TicketItemDTO getTicketItemByID(Long id) throws IllegalArgumentException;
    
    /**
     * Method used for retrieving TicketItems belonging to a given TicketItemDTO.
     * @param ticket ticket for which we would like to get its TicketItems
     * @return List with TicketItems by given ticket or empty {@link java.util.ArrayList} if there are no TicketItems
     * @throws IllegalArgumentException if ticket is null
     */
    List<TicketItemDTO> getTicketItemsByTicket(TicketDTO ticket) throws IllegalArgumentException;
}
  

