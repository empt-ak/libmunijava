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
    private static final org.apache.log4j.Logger logger = org.apache.log4j.Logger.getLogger(AspectDAOTicketItemScanner.class);
    
    @Before("execution (* library.dao.impl.TicketItemDAOImpl.createTicketItem(..)) && args(ticketItem)")
    public void scanForSave(TicketItem ticketItem)
    {
        logger.debug("=Method call TicketItemDAOImpl.createTicketItem() with following parameter: "+ticketItem);
        LibraryValidator.validateDAO(ticketItem, TicketItem.class, Operation.CREATE);        
    }
    
    @Before("execution (* library.dao.impl.TicketItemDAOImpl.getTicketItemByID(..)) && args(id)")
    public void scanForGetTicketItemByID(Long id)
    {
        logger.debug("=Method call TicketItemDAOImpl.getTicketItemByID() with following parameter: "+id);
        if(!LibraryValidator.isValidID(id))
        {
            throw new IllegalArgumentException("");
        }
    }
    
    @Before("execution (* library.dao.impl.TicketItemDAOImpl.updateTicketItem(..)) && args(ticketItem)")
    public void scanForUpdateTicketItem(TicketItem ticketItem)
    {
        logger.debug("=Method call TicketItemDAOImpl.updateTicketItem() with following parameter: "+ticketItem);
        LibraryValidator.validateDAO(ticketItem, TicketItem.class, Operation.UPDATE);
    }
    
    @Before("execution (* library.dao.impl.TicketItemDAOImpl.deleteTicketItem(..)) && args(ticketItem)")
    public void scanForDeleteTicketItem(TicketItem ticketItem)
    {
        logger.debug("=Method call TicketItemDAOImpl.deleteTicketItem() with following parameter: "+ticketItem);
        LibraryValidator.validateDAO(ticketItem, TicketItem.class, Operation.DELETE);
    }
    
    @Before("execution (* library.dao.impl.TicketItemDAOImpl.getTicketItemsByTicket(..)) && args(ticket)")
    public void scanForGetTicketItemsByTicket(Ticket ticket)
    {
        logger.debug("=Method call TicketItemDAOImpl.getTicketItemsByTicket() with following parameter: "+ticket);
        if(!LibraryValidator.isValidID(ticket.getTicketID()))
        {
            throw new IllegalArgumentException();
        }
    }
}
