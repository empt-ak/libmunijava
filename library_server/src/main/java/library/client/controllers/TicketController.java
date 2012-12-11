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
import library.service.BookService;
import library.service.TicketFacade;
import library.service.TicketItemService;
import library.service.TicketService;
import library.service.UserService;
import org.apache.log4j.Logger;
import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Autowired;
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
     * @param request servlet holding request
     * @return redirect to /ticket/show/user/{userID} if method flow is in order, redirect to / otherwise
     */
    @RequestMapping("/create/user/{userID}")
    public ModelAndView createTicketLib(@PathVariable Long userID,HttpServletRequest request)
    {        
        UserDTO inSession = (UserDTO) request.getSession().getAttribute("USER");
        if(inSession != null && inSession.getSystemRole().equals("ADMINISTRATOR"))
        {// sme prihlaseny 
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

        return new ModelAndView("redirect:/");        
    }
    
    /**
     * Mapping for creating ticket for signed in user.
     * @param request servlet request containing session
     * @return redirect to /ticket/show/mytickets/
     */
    @RequestMapping("/create/")
    public ModelAndView createTicket(HttpServletRequest request)
    {        
        UserDTO inSession = (UserDTO) request.getSession().getAttribute("USER");
        if(inSession != null)
        {// sme prihlaseny 
            
                TicketDTO t = new TicketDTO();
                t.setBorrowTime(new DateTime());
                t.setDueTime(t.getBorrowTime().plusMonths(1));
                t.setUser(inSession);
                ticketService.createTicket(t);                
            
        }

        return new ModelAndView("redirect:/ticket/show/mytickets/");        
    }
    
    /**
     * Request mapper for showing tickets for signed user.
     * @param request holding session
     * @return list of tickets or redirect to /
     */
    @RequestMapping(value="/show/mytickets/")
    public ModelAndView viewTicketsForUser(HttpServletRequest request)
    {
        UserDTO inSession = (UserDTO) request.getSession().getAttribute("USER");
        if(inSession != null)
        {
            UserDTO temp = userService.getUserByID(inSession.getUserID());
            if(inSession.equals(temp))
            {
                List<TicketDTO> tickets = ticketService.getAllTicketsForUser(temp);
                java.util.Collections.sort(tickets,tComparator);
                return new ModelAndView("ticket_list","tickets",tickets);                                
            }
        }
        
        
        return new ModelAndView("redirect:/");
    }
    
    /**
     * Request mapper for showing tickets for given user
     * @param userID of user whose tickets we want to show
     * @param request request holding session
     * @return list of tickets for given user or redirect to /
     */
    @RequestMapping(value="/show/user/{userID}")
    public ModelAndView showTickets(@PathVariable Long userID, HttpServletRequest request)
    {
        UserDTO inSession = (UserDTO) request.getSession().getAttribute("USER");
        if(inSession != null && inSession.getSystemRole().equals("ADMINISTRATOR"))
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
        }
        
        return new ModelAndView("redirect:/");
    }
    
    /**
     * Request resolver for adding ticket for last ticket.
     * @param bookID to be inserted inside ticket
     * @param request holding session
     * @return redirect to list of books /book/
     */
    @RequestMapping(value="/add/book/{bookID}")
    public ModelAndView addBookToTicket(@PathVariable Long bookID,HttpServletRequest request)
    {
        UserDTO inSession = (UserDTO) request.getSession().getAttribute("USER");
        if(inSession != null)
        {            
            ticketFacade.addBookToTicket(bookID, inSession);                       
        }       
        return new ModelAndView("redirect:/book/");
    }
    
    /**
     * Method used for borrowing all books inside ticket. See {@link library.service.TicketFacade#borrowTicket(java.lang.Long) } for more info.
     * @param ticketID to be borrowed
     * @param request holding session
     * @return redirect to view where user tickets are shown, redirect to / when logged user is not administrator
     */
    @RequestMapping("/borrow/{ticketID}")
    public ModelAndView borrowTicket(@PathVariable Long ticketID,HttpServletRequest request)
    {
        UserDTO inSession = (UserDTO) request.getSession().getAttribute("USER");
        if(inSession != null && inSession.getSystemRole().equals("ADMINISTRATOR"))
        {
            ticketFacade.borrowTicket(ticketID);
            
            TicketDTO t = ticketService.getTicketByID(ticketID);
           
            return new ModelAndView("redirect:/ticket/show/user/"+t.getUser().getUserID().toString());
            
        }
        
        return new ModelAndView("redirect:/");
    }
    
    /**
     * RequestMapper for returning ticket. It returns all books inside ticket. See {@link library.service.TicketFacade#returnTicket(java.lang.Long) } for more information.
     * @param ticketID to be returned
     * @param request holding session
     * @return  redirect to users ticket list, or to / otherwise
     */
    @RequestMapping("/return/{ticketID}")
    public ModelAndView returnTicket(@PathVariable Long ticketID,HttpServletRequest request)
    {
        UserDTO inSession = (UserDTO) request.getSession().getAttribute("USER");
        if(inSession != null  && inSession.getSystemRole().equals("ADMINISTRATOR"))
        {
            ticketFacade.returnTicket(ticketID);
                
            TicketDTO t = ticketService.getTicketByID(ticketID);
            
            return new ModelAndView("redirect:/ticket/show/user/"+t.getUser().getUserID().toString());
            
        }
        
        return new ModelAndView("index");       
    }
    
    /**
     * Requestmapper for returning books for given ticket. See {@link library.service.TicketFacade#returnBookInTicketItem(java.lang.Long, java.lang.Long, boolean) } for more information.
     * @param ticketID to be returned
     * @param ticketItemID ticketitem holding book
     * @param isDamaged flag if returned book is damaged or not
     * @param request holding session
     * @return redirect to list of users ticket or to mytickets otherwise
     */
    @RequestMapping("/return/{ticketID}/ticketitem/{ticketItemID}/damaged/{isDamaged}")
    public ModelAndView returnBookForTicket(@PathVariable Long ticketID,@PathVariable Long ticketItemID,
                                            @PathVariable Boolean isDamaged, HttpServletRequest request)
    {
        UserDTO inSession = (UserDTO) request.getSession().getAttribute("USER");
        
        if(inSession != null && inSession.getSystemRole().equals("ADMINISTRATOR"))
        {            
            ticketFacade.returnBookInTicketItem(ticketItemID, ticketID,isDamaged);
            TicketDTO t = ticketService.getTicketByID(ticketID);
            return new ModelAndView("redirect:/ticket/show/user/"+t.getUser().getUserID().toString());
        }      
        
        return new ModelAndView("redirect:/ticket/show/mytickets/");
    }
    
    /**
     * mapping for deleting ticket. only if we are administrator then we can delete ticket
     * @param ticketID to be deleted
     * @param request servlet request holding session
     * @return  redirect to /ticket/show/mytickets/
     */
    @RequestMapping("/delete/{ticketID}")
    public ModelAndView deleteTicket(@PathVariable Long ticketID,HttpServletRequest request)
    {
        UserDTO inSession = (UserDTO) request.getSession().getAttribute("USER");
        
        Long temp = null;
        if(inSession != null && inSession.getSystemRole().equals("ADMINISTRATOR"))
        {
            temp = ticketService.getTicketByID(ticketID).getUser().getUserID();
            ticketFacade.deleteTicket(ticketID);
            
            return new ModelAndView("redirect:/ticket/show/user/"+String.valueOf(temp));
        }
        
        return new ModelAndView("redirect:/");
    }  
    
    /**
     * mapper for canceling ticket. Ticket can be canceled only if it contains only reserved 
     * otherwise it aint doin nothing,
     * @param ticketID id of ticket we want to cancel
     * @param request servlet request holding session
     * @return redirect to /ticket/show/mytickets/
     */
    @RequestMapping("/cancel/{ticketID}")
    public ModelAndView cancelTicket(@PathVariable Long ticketID,HttpServletRequest request)
    {
        UserDTO inSession = (UserDTO) request.getSession().getAttribute("USER");
        if(inSession != null)
        {            
            TicketDTO t = null;
            try
            {
                t = ticketService.getTicketByID(ticketID);
            }
            catch(NoResultException nre)
            {
            }
            
            // only if we are owner of ticket or we are administrator, then we can make som changes
            if(t != null && (t.getUser().equals(inSession) || inSession.getSystemRole().equals("ADMINISTRATOR")))
            {
                ticketFacade.deleteTicket(ticketID);
            }          
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
