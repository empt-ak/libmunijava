/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package library.client.controllers;

import java.util.Comparator;
import java.util.List;
import javax.persistence.NoResultException;
import javax.servlet.http.HttpServletRequest;
import library.entity.dto.TicketDTO;
import library.entity.dto.UserDTO;
import library.service.TicketFacade;
import library.service.TicketService;
import library.service.UserService;
import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

/**
 * 
 * @author emptak
 */
@Controller
@RequestMapping("/ticket")
public class TicketController 
{  
    
    @Autowired
    private TicketService ticketService;
    
    @Autowired
    private UserService userService;
    
    @Autowired
    private TicketFacade ticketFacade;
    
    
    /**
     * request resolver for creating ticket for specific user by librarian
     * @param userID user to who we want to create ticket
     * @return redirect to /ticket/show/user/{userID} if method flow is in order, redirect to / otherwise
     */
    @RequestMapping("/create/user/{userID}")
    public ModelAndView createTicketLib(@PathVariable Long userID)
    {
        UserDTO temp = null;
        try
        {
            temp = userService.getUserByID(userID);
        }
        catch(NoResultException nre)
        {

        }

        if(temp != null)
        {
            TicketDTO t = new TicketDTO();
            t.setBorrowTime(new DateTime());
            t.setDueTime(t.getBorrowTime().plusMonths(1));
            t.setUser(temp);
            ticketService.createTicket(t);                
        }
        return new ModelAndView("redirect:/ticket/show/user/"+userID.toString());    
    }
    
    /**
     * Mapping for creating ticket for signed in user.
     * @return redirect to /ticket/show/mytickets/
     */
    @RequestMapping("/create/")
    public ModelAndView createTicket()
    {          
        TicketDTO t = new TicketDTO();
        t.setBorrowTime(new DateTime());
        t.setDueTime(t.getBorrowTime().plusMonths(1));

        t.setUser(userService.getUserByUsername(SecurityContextHolder.getContext().getAuthentication().getName()));
        ticketService.createTicket(t);

        return new ModelAndView("redirect:/ticket/show/mytickets/");        
    }
    
    /**
     * Request mapper for showing tickets for signed user.
     * @return list of tickets or redirect to /
     */
    @RequestMapping(value="/show/mytickets/")
    public ModelAndView viewTicketsForUser()
    {
        UserDTO temp = userService.getUserByUsername(SecurityContextHolder.getContext().getAuthentication().getName());

        List<TicketDTO> tickets = ticketService.getAllTicketsForUser(temp);
        java.util.Collections.sort(tickets,tComparator);
        return new ModelAndView("ticket_list","tickets",tickets); 
    }
    
    /**
     * Request mapper for showing tickets for given user
     * @param userID of user whose tickets we want to show
     * @return list of tickets for given user or redirect to /
     */
    @RequestMapping(value="/show/user/{userID}")
    public ModelAndView showTickets(@PathVariable Long userID)
    {
        UserDTO targetU = null;
        try
        {
            targetU = userService.getUserByID(userID);
        }
        catch(NoResultException nre)
        {                
        }

        if(targetU != null)
        {
            List<TicketDTO> tickets = ticketService.getAllTicketsForUser(targetU);
            java.util.Collections.sort(tickets,tComparator);
            return new ModelAndView("ticket_list","tickets",tickets);
        }      
        return new ModelAndView("redirect:/");
    }
    
    /**
     * Request resolver for adding ticket for last ticket.
     * @param bookID to be inserted inside ticket
     * @return redirect to list of books /book/
     */
    @RequestMapping(value="/add/book/{bookID}")
    public ModelAndView addBookToTicket(@PathVariable Long bookID)
    { 
        UserDTO temp = userService.getUserByUsername(SecurityContextHolder.getContext().getAuthentication().getName());
        ticketFacade.addBookToTicket(bookID, temp);                       
       
        return new ModelAndView("redirect:/book/");
    }
    
    /**
     * Method used for borrowing all books inside ticket. See {@link library.service.TicketFacade#borrowTicket(java.lang.Long) } for more info.
     * @param ticketID to be borrowed
     * @return redirect to view where user tickets are shown, redirect to / when logged user is not administrator
     */
    @RequestMapping("/borrow/{ticketID}")
    public ModelAndView borrowTicket(@PathVariable Long ticketID)
    {
        ticketFacade.borrowTicket(ticketID);            
        TicketDTO t = ticketService.getTicketByID(ticketID);

        return new ModelAndView("redirect:/ticket/show/user/"+t.getUser().getUserID().toString());   
    }
    
    /**
     * RequestMapper for returning ticket. It returns all books inside ticket. See {@link library.service.TicketFacade#returnTicket(java.lang.Long) } for more information.
     * @param ticketID to be returned
     * @return  redirect to users ticket list, or to / otherwise
     */
    @RequestMapping("/return/{ticketID}")
    public ModelAndView returnTicket(@PathVariable Long ticketID)
    {
        ticketFacade.returnTicket(ticketID);                
        TicketDTO t = ticketService.getTicketByID(ticketID);

        return new ModelAndView("redirect:/ticket/show/user/"+t.getUser().getUserID().toString());        
    }
    
    /**
     * Requestmapper for returning books for given ticket. See {@link library.service.TicketFacade#returnBookInTicketItem(java.lang.Long, java.lang.Long, boolean) } for more information.
     * @param ticketID to be returned
     * @param ticketItemID ticketitem holding book
     * @param isDamaged flag if returned book is damaged or not
     * @return redirect to list of users ticket or to mytickets otherwise
     */
    @RequestMapping("/return/{ticketID}/ticketitem/{ticketItemID}/damaged/{isDamaged}")
    public ModelAndView returnBookForTicket(@PathVariable Long ticketID,@PathVariable Long ticketItemID,
                                            @PathVariable Boolean isDamaged)
    {          
        ticketFacade.returnBookInTicketItem(ticketItemID, ticketID,isDamaged);
        TicketDTO t = ticketService.getTicketByID(ticketID);
        return new ModelAndView("redirect:/ticket/show/user/"+t.getUser().getUserID().toString());
    }
    
    /**
     * mapping for deleting ticket. only if we are administrator then we can delete ticket
     * @param ticketID to be deleted
     * @return  redirect to /ticket/show/mytickets/
     */
    @RequestMapping("/delete/{ticketID}")
    public ModelAndView deleteTicket(@PathVariable Long ticketID)
    {
        Long temp = ticketService.getTicketByID(ticketID).getUser().getUserID();
        ticketFacade.deleteTicket(ticketID);

        return new ModelAndView("redirect:/ticket/show/user/"+String.valueOf(temp));
    }  
    
    /**
     * mapper for canceling ticket. Ticket can be canceled only if it contains only reserved 
     * otherwise it aint doin nothing,
     * @param ticketID id of ticket we want to cancel
     * @return redirect to /ticket/show/mytickets/
     */
    @RequestMapping("/cancel/{ticketID}")
    public ModelAndView cancelTicket(@PathVariable Long ticketID)
    {
        UserDTO temp = userService.getUserByUsername(SecurityContextHolder.getContext().getAuthentication().getName());
        TicketDTO t = null;
        try
        {
            t = ticketService.getTicketByID(ticketID);
        }
        catch(NoResultException nre)
        {
        }

        // only if we are owner of ticket or we are administrator, then we can make som changes
        if(t != null && (t.getUser().equals(temp) || temp.getSystemRole().equals("ADMINISTRATOR")))
        {
            ticketFacade.cancelTicket(ticketID);
        } 
        
        return new ModelAndView("redirect:/ticket/show/mytickets/");        
    }    
    
    private static java.util.Comparator<TicketDTO> tComparator = new Comparator<TicketDTO>() 
    {
        @Override
        public int compare(TicketDTO o1, TicketDTO o2) {
            return o2.getTicketID().compareTo(o1.getTicketID());
        }
    };
}
