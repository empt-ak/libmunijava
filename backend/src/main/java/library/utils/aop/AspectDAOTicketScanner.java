/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package library.utils.aop;

import library.utils.aop.validators.LibraryValidator;
import library.utils.aop.validators.Operation;
import library.entity.Ticket;
import library.entity.User;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.joda.time.DateTime;

/**
 *
 * @author emptak
 */
@Aspect
public class AspectDAOTicketScanner 
{
    @Before("execution (* library.dao.impl.TicketDAOImpl.createTicket(..)) && args(ticket)")
    public void scanForSave(Ticket ticket)
    {
        LibraryValidator.validateDAO(ticket, Ticket.class, Operation.CREATE);        
    }
    
    @Before("execution (* library.dao.impl.TicketDAOImpl.updateTicket(..)) && args(ticket)")
    public void scanForUpdate(Ticket ticket)
    {
        LibraryValidator.validateDAO(ticket, Ticket.class, Operation.UPDATE);        
    }
    
    @Before("execution (* library.dao.impl.TicketDAOImpl.deleteTicket(..)) && args(ticket)")
    public void scanForDelete(Ticket ticket)
    {
        LibraryValidator.validateDAO(ticket, Ticket.class, Operation.DELETE);        
    }
    
    @Before("execution (* library.dao.impl.TicketDAOImpl.getTicketByID(..)) && args(id)")
    public void scanForGetTicketByID(Long id)
    {
        if(!LibraryValidator.isValidID(id))
        {
            throw new IllegalArgumentException("ERROR: given id is out of range");
        }
    }
    
    @Before("execution (* library.dao.impl.TicketDAOImpl.getLastTicketForUser(..)) && args(user)")
    public void scanForGetTicketByID(User user)
    {
        if(!LibraryValidator.isValidID(user.getUserID()))
        {
            throw new IllegalArgumentException("ERROR: given user id is out of range");
        }
    }
    
    @Before("execution (* library.dao.impl.TicketDAOImpl.getTicketsInPeriodForUser(..)) && args(from,to,user)")
    public void scanForGetTicketByID(DateTime from,DateTime to,User user)
    {
        if(LibraryValidator.isNull(from))
        {
            throw new IllegalArgumentException("ERROR: given fromtime is null");
        }
        if(LibraryValidator.isNull(to))
        {
            throw new IllegalArgumentException("ERROR: given to time is null");
        }
        if(LibraryValidator.isNull(user) || !LibraryValidator.isValidID(user.getUserID()))
        {
            throw new IllegalArgumentException("ERROR: given user is null or does not have proper id");
        }
    }
    @Before("execution (* library.dao.impl.TicketDAOImpl.getAllTicketsForUser(..)) && args(user)")
    public void scanForGetAllTicketsForUser(User user)
    {
        if(LibraryValidator.isNull(user) || !LibraryValidator.isValidID(user.getUserID()))
        {
            throw new IllegalArgumentException("ERROR: given user is null or does not have proper id");
        }
    }    
}
