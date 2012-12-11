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

/**
 *
 * @author emptak
 */
@Aspect
public class AspectDAOTicketScanner 
{
    private static final org.apache.log4j.Logger logger = org.apache.log4j.Logger.getLogger(AspectDAOTicketScanner.class);
    
    @Before("execution (* library.dao.impl.TicketDAOImpl.createTicket(..)) && args(ticket)")
    public void scanForSave(Ticket ticket)
    {
        logger.debug("=Method call TicketDAOImpl.createTicket() with following parameter: "+ticket);
        LibraryValidator.validateDAO(ticket, Ticket.class, Operation.CREATE);        
    }
    
    @Before("execution (* library.dao.impl.TicketDAOImpl.updateTicket(..)) && args(ticket)")
    public void scanForUpdate(Ticket ticket)
    {
        logger.debug("=Method call TicketDAOImpl.update() with following parameter: "+ticket);
        LibraryValidator.validateDAO(ticket, Ticket.class, Operation.UPDATE);        
    }
    
    @Before("execution (* library.dao.impl.TicketDAOImpl.deleteTicket(..)) && args(ticket)")
    public void scanForDelete(Ticket ticket)
    {
        logger.debug("=Method call TicketDAOImpl.deleteTicket() with following parameter: "+ticket);
        LibraryValidator.validateDAO(ticket, Ticket.class, Operation.DELETE);        
    }
    
    @Before("execution (* library.dao.impl.TicketDAOImpl.getTicketByID(..)) && args(id)")
    public void scanForGetTicketByID(Long id)
    {
        logger.debug("=Method call TicketDAOImpl.getTicketByID() with following parameter: "+id);
        if(!LibraryValidator.isValidID(id))
        {
            throw new IllegalArgumentException("ERROR: given id is out of range");
        }
    }
    
    @Before("execution (* library.dao.impl.TicketDAOImpl.getLastTicketForUser(..)) && args(user)")
    public void scanForGetTicketByID(User user)
    {
        logger.debug("=Method call TicketDAOImpl.getLastTicketForUser() with following parameter: "+user);
        if(!LibraryValidator.isValidID(user.getUserID()))
        {
            throw new IllegalArgumentException("ERROR: given user id is out of range");
        }
    }    
    
    @Before("execution (* library.dao.impl.TicketDAOImpl.getAllTicketsForUser(..)) && args(user)")
    public void scanForGetAllTicketsForUser(User user)
    {
        logger.debug("=Method call TicketDAOImpl.getAllTicketsForUser() with following parameter: "+user);
        if(LibraryValidator.isNull(user) || !LibraryValidator.isValidID(user.getUserID()))
        {
            throw new IllegalArgumentException("ERROR: given user is null or does not have proper id");
        }
    }    
}
