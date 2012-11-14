/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package library.service;

import java.util.List;
import library.entity.dto.TicketDTO;
import library.entity.dto.UserDTO;
import org.joda.time.DateTime;

/**
 *
 * @author Nemcek
 */
public interface TicketService {
   
    
    /**
     * Method creates ticket inside database;
     * @param ticket to be save with correct fields
     * @throws IllegalArgumentException if ticket is null or has no id
     */
    void createTicket(TicketDTO ticket) throws IllegalArgumentException;
    
    /**
     * Method updates ticket inside database
     * @param ticket to be update with correct fields
     * @throws IllegalArgumentException if ticket is null or has no id
     */
    void updateTicket(TicketDTO ticket) throws IllegalArgumentException;
    
    /**
     * method deletes ticked from database
     * @param ticket to be deleted. only id has to be set
     * @throws IllegalArgumentException if ticket is null
     */
    void deleteTicket(TicketDTO ticket) throws IllegalArgumentException;
    
    /**
     * method gets ticket from database on its id
     * @param id of ticket to be obtained
     * @return ticket with given id
     * @throws IllegalArgumentException  if id is null or out of range 
     */
    TicketDTO getTicketByID(Long id) throws IllegalArgumentException;
    
    /**
     * method returns last ticket for given user. this is useful when he returns books
     * for last ticket
     * @param user of whom ticket we want
     * @return last ticket for given user
     * @throws IllegalArgumentException if user is null
     */
    TicketDTO getLastTicketForUser(UserDTO user) throws IllegalArgumentException;
    
    /**
     * method return tickets between given period of time for given user
     * @param from start time since we want tickets
     * @param to end time until we want books
     * @param user user whom tickets we want
     * @return list from given period for given user
     * @throws IllegalArgumentException if any of paramters are null.
     */
    List<TicketDTO> getTicketsInPeriodForUser(DateTime from, DateTime to,UserDTO user) throws IllegalArgumentException;
    
    /**
     * method returns all tickets for given user
     * @param user of whom tickets we want
     * @return list with all tickets for given user
     * @throws IllegalArgumentException if user is null
     */
    List<TicketDTO> getAllTicketsForUser(UserDTO user) throws IllegalArgumentException;
}

