/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package library.service;

import library.entity.dto.UserDTO;

/**
 *
 * @author Emptak
 */
public interface TicketFascade 
{
    void addBookToTicket(Long bookID, UserDTO userDTO) throws IllegalArgumentException;
    void borrowTicket(Long ticketID) throws IllegalArgumentException;
    void returnTicket(Long ticketID) throws IllegalArgumentException;
    void returnBookInTicketItem(Long ticketItemID, Long ticketID) throws IllegalArgumentException;
    void deleteTicket(Long ticketID) throws IllegalArgumentException;
    void cancelTicket(Long ticketID) throws IllegalArgumentException;
    
}
