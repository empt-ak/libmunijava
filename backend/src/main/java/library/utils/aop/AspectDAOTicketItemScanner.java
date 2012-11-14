/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package library.utils.aop;

import library.utils.aop.validators.LibraryValidator;
import library.utils.aop.validators.Operation;
import library.entity.Ticket;
import library.entity.TicketItem;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;

/**
 *
 * @author emptak
 */
@Aspect
public class AspectDAOTicketItemScanner 
{
    @Before("execution (* library.dao.impl.TicketItemDAOImpl.createTicketItem(..)) && args(ticketItem)")
    public void scanForSave(TicketItem ticketItem)
    {
        LibraryValidator.validateDAO(ticketItem, TicketItem.class, Operation.CREATE);        
    }
    
    @Before("execution (* library.dao.impl.TicketItemDAOImpl.getTicketItemByID(..)) && args(id)")
    public void scanForGetTicketItemByID(Long id)
    {
        if(!LibraryValidator.isValidID(id))
        {
            throw new IllegalArgumentException("");
        }
    }
    
    @Before("execution (* library.dao.impl.TicketItemDAOImpl.updateTicketItem(..)) && args(ticketItem)")
    public void scanForUpdateTicketItem(TicketItem ticketItem)
    {
        LibraryValidator.validateDAO(ticketItem, TicketItem.class, Operation.UPDATE);
    }
    
    @Before("execution (* library.dao.impl.TicketItemDAOImpl.deleteTicketItem(..)) && args(ticketItem)")
    public void scanForDeleteTicketItem(TicketItem ticketItem)
    {
        LibraryValidator.validateDAO(ticketItem, TicketItem.class, Operation.DELETE);
    }
    
    @Before("execution (* library.dao.impl.TicketItemDAOImpl.getTicketItemsByTicket(..)) && args(ticket)")
    public void scanForGetTicketItemsByTicket(Ticket ticket)
    {
        if(!LibraryValidator.isValidID(ticket.getTicketID()))
        {
            throw new IllegalArgumentException();
        }
    }
}
