/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package library.service;

import javax.persistence.NoResultException;
import library.entity.dto.UserDTO;

/**
 *
 * @author Emptak
 */
public interface TicketFascade 
{
    /**
     * Method used for adding book to ticket. In first step we retrieve user and book from database. Book should have its status set to AVAILABLE
     * because we are trying to borrow book and book that aint available cannot be borrowed. We get last user ticket, because we are trying to make
     * whole process easier we always add books into latest ticket. If user does not have any tickets yet we create it, then we create ticketitem
     * for book we are trying to borrow with status RESERVATION. Book status is set to NOT_AVAILABLE so noone else can make reservation on this book.
     * If any error occures during process whole transaction is rolled back, so no changes are made.
     * 
     * @param bookID id of book we are trying to assign for current user
     * @param userDTO current user
     * @throws IllegalArgumentException if user is null
     * @throws NoResultException if user or book does not exist
     */
    void addBookToTicket(Long bookID, UserDTO userDTO) throws IllegalArgumentException, NoResultException;
    
    /**
     * Method used for borrowing ticket. In first step we obtain ticket from database and for each ticketitem we set its status to BORROWED.
     * Since books have been already reserved their status is NOT_AVAILABLE. Then we set borrow time to current date, and due time +1 month.
     * If any error occures during process whole transaction is rolled back, so no changes are made.
     * @param ticketID to be borrowed
     * @throws IllegalArgumentException if id is out of range
     * @throws NoResultException if ticket does not exist
     */
    void borrowTicket(Long ticketID) throws IllegalArgumentException, NoResultException;
    
    /**
     * Method used for returning ticket. In first step we obtain ticket from database and for each ticketitem we set its status to returned. Also
     * we have to set for each book in each ticket item to AVAILABLE. In last step we set return time to current date.
     * If any error occures during process whole transaction is rolled back, so no changes are made.
     * @param ticketID id of ticket to be returned
     * @throws IllegalArgumentException if ticketID is wrong
     * @throws NoResultException if ticket does not exist
     */
    void returnTicket(Long ticketID) throws IllegalArgumentException, NoResultException;
    
    /**
     * Method used for returning selected "book" for selected ticket. First we obtain ticket from database and then we iterate through
     * all ticket items until we find the one we need. If isDamaged is set to true set ticketitem status to RETURNED_DAMAGED and leave
     * book as NOT_AVAILABLE. Otherwise we set ticketitem status to RETURNED and book to AVAILABLE. Last step is to check if all books 
     * are reserved and if they are we set return time to current date.
     * If any error occures during process whole transaction is rolled back, so no changes are made.
     * 
     * @param ticketItemID id of ti to be returned
     * @param ticketID id of ticket where ti is stored
     * @param isDamaged flag whether book is damaged or not
     * @throws IllegalArgumentException if IDs are not valid
     * @throws NoResultException if ticket or ti does not exist
     */
    void returnBookInTicketItem(Long ticketItemID, Long ticketID,boolean isDamaged) throws IllegalArgumentException, NoResultException;
    
    /* TODO: co ak mazem staru pozicku, a knihu ma teraz niekto pozicanu tak ju nastavim na dostupnu a moze byt pozicana 2x */
    /**
     * 
     * Method used for deleting ticket from database. At first we obtain ticket from database, iterate through ticketitems and 
     * we delete them one by one. If ticketitem status is set as RETURNED_DAMAGED we leave book as it is (NOT_AVAILABLE) otherwise we set
     * it to AVAILABLE. In last step we delete ticket. 
     * If any error occures during process whole transaction is rolled back, so no changes are made.
     * @param ticketID id of ticket to be deleted
     * @throws IllegalArgumentException if id is out of range
     * @throws NoResultException if ticket with such id does not exist
     */
    void deleteTicket(Long ticketID) throws IllegalArgumentException, NoResultException;
    
    /**
     * Method used for canceling ticket. This comes handy when user makes reservation for specific book and later he decides not to borrow it.
     * simply he can cancel ticket if all ticketitems are set as RESERVATION, so no books have been borrowed yet. If condition is fullfilled
     * ticket with ticketitems are deleted.
     * If any error occures during process whole transaction is rolled back, so no changes are made.
     * @param ticketID id of ticket to be canceled
     * @throws IllegalArgumentException if id is out of valid range
     * @throws NoResultException if ticket does not exist
     */
    void cancelTicket(Long ticketID) throws IllegalArgumentException, NoResultException;
    
}
