/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package library.client.controllers;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import javax.persistence.NoResultException;
import javax.servlet.http.HttpServletRequest;
import library.entity.dto.BookDTO;
import library.entity.dto.TicketDTO;
import library.entity.dto.TicketItemDTO;
import library.entity.dto.UserDTO;
import library.entity.enums.BookStatus;
import library.entity.enums.TicketItemStatus;
import library.service.BookService;
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
    private static final Logger logger = Logger.getLogger(TicketController.class);    
    
    @Autowired
    TicketService ticketService;
    
    @Autowired
    UserService userService;
    
    @Autowired
    TicketItemService ticketItemService;
    
    @Autowired
    BookService bookService;
    
    @RequestMapping("/create/user/{userID}")
    public ModelAndView createTicket(@PathVariable Long userID,HttpServletRequest request)
    {
        
        UserDTO inSession = (UserDTO) request.getSession().getAttribute("USER");
        if(inSession != null)
        {// sme prihlaseny 
            UserDTO temp = userService.getUserByID(userID);
            if(inSession.equals(temp))
            {
                TicketDTO t = new TicketDTO();
                t.setBorrowTime(new DateTime());
                t.setDueTime(t.getBorrowTime().plusMonths(1));
                t.setUser(temp);
                ticketService.createTicket(t);                
            }           
        }

        
        return new ModelAndView("redirect:/ticket/show/mytickets/");
    }
    
    //                      /show/mytickets/user/1
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
        
        
        return new ModelAndView("index");
    }
    
    @RequestMapping(value="/add/book/{bookID}")
    public ModelAndView addBookToTicket(@PathVariable Long bookID,HttpServletRequest request)
    {
        UserDTO inSession = (UserDTO) request.getSession().getAttribute("USER");
        if(inSession != null)
        {            
            BookDTO b = bookService.getBookByID(bookID);            
            if(b!= null && b.getBookStatus().equals(BookStatus.AVAILABLE))
            {
                TicketDTO t = null;
                try
                {
                    t = ticketService.getLastTicketForUser(inSession);                    
                }
                catch(NoResultException nre)
                {
                    
                }
                if(t == null)
                {// este sme si nikdy nic nepozicali
                    t = new TicketDTO();
                    t.setBorrowTime(new DateTime());
                    t.setDueTime(t.getBorrowTime().plusMonths(1));
                    t.setUser(inSession);
                    
                    TicketItemDTO ti = new TicketItemDTO();
                    ti.setBook(b);
                    ti.setTicketItemStatus(TicketItemStatus.RESERVATION);
                    List<TicketItemDTO> list = new ArrayList<>();
                    list.add(ti);                    
                    t.setTicketItems(list);
                    
                    b.setBookStatus(BookStatus.NOT_AVAILABLE);
                    bookService.updateBook(b);
                    ticketItemService.createTicketItem(ti);
                    
                    ticketService.createTicket(t);
                }
                else
                {
                    List<TicketItemDTO> list = t.getTicketItems();
                    TicketItemDTO ti = new TicketItemDTO();
                    ti.setBook(b);
                    ti.setTicketItemStatus(TicketItemStatus.RESERVATION);
                    
                    b.setBookStatus(BookStatus.NOT_AVAILABLE);
                    bookService.updateBook(b);
                    
                    
                    ticketItemService.createTicketItem(ti);
                    list.add(ti);
                    
                    t.setTicketItems(list);
                    
                    ticketService.updateTicket(t);                    
                    
                }
            }            
        }       
        return new ModelAndView("redirect:/book/");
    }
    
    
    @RequestMapping("/borrow/{ticketID}")
    public ModelAndView borrowTicket(@PathVariable Long ticketID,HttpServletRequest request)
    {
        UserDTO inSession = (UserDTO) request.getSession().getAttribute("USER");
        if(inSession != null)
        {
            TicketDTO ticketDTO = null;
            try
            {
                ticketDTO = ticketService.getTicketByID(ticketID);
            }
            catch(NoResultException nre)
            {
            } 
            
            if(ticketDTO != null && inSession.equals(ticketDTO.getUser()))
            { 
                for(TicketItemDTO t :ticketDTO.getTicketItems())
                {
                    t.setTicketItemStatus(TicketItemStatus.BORROWED);
                    ticketItemService.updateTicketItem(t);
                }
                
                ticketDTO.setBorrowTime(new DateTime());
                ticketDTO.setDueTime(ticketDTO.getBorrowTime().plusMonths(1));
                ticketService.updateTicket(ticketDTO);
                
                return new ModelAndView("redirect:/ticket/show/mytickets/");
            }
        }
        
        return new ModelAndView("index");
    }
    
    @RequestMapping("/return/{ticketID}")
    public ModelAndView returnTicket(@PathVariable Long ticketID,HttpServletRequest request)
    {
        UserDTO inSession = (UserDTO) request.getSession().getAttribute("USER");
        if(inSession != null)
        {
            TicketDTO ticketDTO = null;
            try
            {
                ticketDTO = ticketService.getTicketByID(ticketID);
            }
            catch(NoResultException nre)
            {
            } 
            
            if(ticketDTO != null && inSession.equals(ticketDTO.getUser()))
            {
                for(TicketItemDTO t : ticketDTO.getTicketItems())
                {
                    t.setTicketItemStatus(TicketItemStatus.RETURNED);
                    ticketItemService.updateTicketItem(t);
                    
                    BookDTO b = bookService.getBookByID(t.getBook().getBookID());
                    b.setBookStatus(BookStatus.AVAILABLE);
                    
                    bookService.updateBook(b);
                }
                
                ticketDTO.setReturnTime(new DateTime());
                
                ticketService.updateTicket(ticketDTO); 
                
                return new ModelAndView("redirect:/ticket/show/mytickets/");
            }
        }
        
        return new ModelAndView("index");       
    }
    
    
    @RequestMapping("/return/{ticketID}/ticketitem/{ticketItemID}")
    public ModelAndView returnBookForTicket(@PathVariable Long ticketID,@PathVariable Long ticketItemID,HttpServletRequest request)
    {
        UserDTO inSession = (UserDTO) request.getSession().getAttribute("USER");
        
        if(inSession != null)
        {            
            TicketDTO ticket = null;
            try
            {
                ticket = ticketService.getTicketByID(ticketID);
            }
            catch(NoResultException nre)
            {
            }           
            
            if(ticket != null && ticket.getUser().equals(inSession))
            {// ticket je prihlaseneho uzivatela                
                boolean allBooksReturned = true;
                for(TicketItemDTO ticketItemDTO : ticket.getTicketItems())
                {                    
                    if(ticketItemDTO.getTicketItemID().equals(ticketItemID))
                    {                        
                        ticketItemDTO.setTicketItemStatus(TicketItemStatus.RETURNED);
                        BookDTO b = ticketItemDTO.getBook();
                        b.setBookStatus(BookStatus.AVAILABLE);
                        
                        bookService.updateBook(b);                        
                        ticketItemService.updateTicketItem(ticketItemDTO);                        
                    }
                    
                    if(!ticketItemDTO.getTicketItemStatus().equals(TicketItemStatus.RETURNED) || !ticketItemDTO.getTicketItemStatus().equals(TicketItemStatus.RETURNED_DAMAGED))
                    {// kniha nie je vratena ok, alebo vratena poskodena tym padom nie je vratena
                        allBooksReturned = false;
                    }
                }
                
                // ak su vsetky knihy vratene oznacime si cas vratenia poslednej knihy
                if(allBooksReturned)
                {
                    ticket.setReturnTime(new DateTime());
                    ticketService.updateTicket(ticket);
                }               
            }
        }      
        
        return new ModelAndView("redirect:/ticket/show/mytickets/");
    }
    
    @RequestMapping("/delete/{ticketID}")
    public ModelAndView deleteTicket(@PathVariable Long ticketID,HttpServletRequest request)
    {
        UserDTO inSession = (UserDTO) request.getSession().getAttribute("USER");
        if(inSession != null)
        {
            TicketDTO t = ticketService.getTicketByID(ticketID);
            if(t.getUser().equals(inSession))
            {
                ticketService.deleteTicket(t);
            }            
        }
        
        return new ModelAndView("redirect:/ticket/show/mytickets/");
    }  
    
    
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
            
            if(t != null)
            {
                boolean flag = true; // all books are reservation
                for(TicketItemDTO ti : t.getTicketItems())
                {
                    if(!ti.getTicketItemStatus().equals(TicketItemStatus.RESERVATION))
                    {
                        flag = false;
                    }
                }
                
                if(flag)
                {
                    ticketService.deleteTicket(t);
                    for(TicketItemDTO ti : t.getTicketItems())
                    {
                        BookDTO b = ti.getBook();
                        b.setBookStatus(BookStatus.AVAILABLE);
                        
                        bookService.updateBook(b);
                    }
                }
            }
            
            return new ModelAndView("redirect:/ticket/show/mytickets/");
        }
        
        return new ModelAndView("index");
        
    }
    
    
    private static java.util.Comparator<TicketDTO> tComparator = new Comparator<TicketDTO>() 
    {
        @Override
        public int compare(TicketDTO o1, TicketDTO o2) {
            return o2.getTicketID().compareTo(o1.getTicketID());
        }
    };
}
