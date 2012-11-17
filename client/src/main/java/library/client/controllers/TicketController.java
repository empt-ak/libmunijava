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
import javax.servlet.http.HttpSession;
import library.entity.Ticket;
import library.entity.TicketItem;
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
                TicketItemDTO ti = new TicketItemDTO();
                ti.setBook(bookService.getBookByID(new Long(15)));
                ti.setTicketItemStatus(TicketItemStatus.BORROWED);
                         
                TicketItemDTO ti1 = new TicketItemDTO();
                ti1.setBook(bookService.getBookByID(new Long(16)));
                ti1.setTicketItemStatus(TicketItemStatus.BORROWED);
                try
                {
                    ticketItemService.createTicketItem(ti);
                    ticketItemService.createTicketItem(ti1);                    
                }
                catch(IllegalArgumentException iae)
                {
                    System.out.println(iae.getMessage());
                    System.out.println(ti);
                    System.out.println(ti1);
                }
                
                List<TicketItemDTO> l = new ArrayList<>();
                l.add(ti);
                l.add(ti1);
                t.setTicketItems(l);

                ticketService.createTicket(t);                
            }           
        }
        //System.out.println(inSession);
        
//        TicketDTO t = ticketService.getLastTicketForUser(temp);
//        
//        if(t!= null)
//        {
//            boolean flag = true;
//            for(TicketItemDTO ti : t.getTicketItems())
//            {
//                if(ti.getTicketItemStatus().equals(TicketItemStatus.BORROWED))
//                {
//                    flag = false;
//                    break;
//                }
//            }
//        }
        // teraz vieme ze mame na starej pozicke daku knihu
        
        return new ModelAndView("redirect:/");
    }
    
    //                      /show/mytickets/user/1
    @RequestMapping(value="/show/mytickets/user/{userID}")
    public ModelAndView viewTicketsForUser(@PathVariable Long userID, HttpServletRequest request)
    {
        UserDTO inSession = (UserDTO) request.getSession().getAttribute("USER");
        if(inSession != null)
        {
            UserDTO temp = userService.getUserByID(userID);
            if(inSession.equals(temp))
            {
                List<TicketDTO> tickets = ticketService.getAllTicketsForUser(temp);
                java.util.Collections.sort(tickets,tComparator);
                return new ModelAndView("ticket_list","tickets",tickets);                                
            }
        }
        
        
        return new ModelAndView("");
    }
    
    @RequestMapping(value="add/book/{bookID}")
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
                    ti.setTicketItemStatus(TicketItemStatus.BORROWED);
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
                    ti.setTicketItemStatus(TicketItemStatus.BORROWED);
                    
                    b.setBookStatus(BookStatus.NOT_AVAILABLE);
                    bookService.updateBook(b);
                    
                    
                    ticketItemService.createTicketItem(ti);
                    list.add(ti);
                    
                    t.setTicketItems(list);
                    
                    ticketService.updateTicket(t);
                    
                    return new ModelAndView("redirect:/ticket/show/mytickets/user/"+inSession.getUserID().toString());
                }
            }            
        }       
        return new ModelAndView();
    }
    
    
    
    private static java.util.Comparator<TicketDTO> tComparator = new Comparator<TicketDTO>() 
    {
        @Override
        public int compare(TicketDTO o1, TicketDTO o2) {
            return o2.getTicketID().compareTo(o1.getTicketID());
        }
    };
}
