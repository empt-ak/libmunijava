/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package library.utils.aop.validators;

import library.entity.Ticket;
import library.entity.TicketItem;
import library.entity.User;

/**
 *
 * @author emptak
 */
class DAOTicketValidator 
{
    private DAOTicketValidator(){}
    
    
    public static void validateOnSave(Ticket ticket)
    {
        validateCore(ticket);        
    }
    
    public static void validateOnUpdate(Ticket ticket)
    {
        if(!LibraryValidator.isValidID(ticket.getTicketID()))
        {
            throw new IllegalArgumentException("ERROR: Given ticket does not have proper ID");
        }
        validateCore(ticket);
        
    }
    
    public static void validateOnDelete(Ticket ticket)
    {
        if(!LibraryValidator.isValidID(ticket.getTicketID()))
        {
            throw new IllegalArgumentException("ERROR: Given ticket does not have proper ID");
        }        
    }
    
    private static void validateCore(Ticket ticket)
    {
        if(ticket.getBorrowTime() == null)
        {
            throw new IllegalArgumentException("ERROR: Given ticket does not have set its borrow time");
        }
        if(ticket.getDueTime() == null)
        {
            throw new IllegalArgumentException("ERROR: given ticket does not have due time");
        }
        
//        if(ticket.getReturnTime() == null)
//        {
//            throw new IllegalArgumentException("ERROR: Given ticket does not have return time");
//        }
        
        LibraryValidator.validateDAO(ticket.getUser(), User.class, Operation.UPDATE);
        for(TicketItem ti : ticket.getTicketItems())
        {
            LibraryValidator.validateDAO(ti, TicketItem.class, Operation.UPDATE);
        }
        
    }
}
