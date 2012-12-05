/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package library.service.impl;

import java.util.ArrayList;
import java.util.List;
import javax.persistence.NoResultException;
import library.dao.BookDAO;
import library.dao.TicketDAO;
import library.dao.TicketItemDAO;
import library.dao.UserDAO;
import library.entity.Book;
import library.entity.Ticket;
import library.entity.TicketItem;
import library.entity.User;
import library.entity.dto.UserDTO;
import library.entity.enums.BookStatus;
import library.entity.enums.TicketItemStatus;
import library.service.TicketFacade;
import library.utils.aop.validators.LibraryValidator;
import org.dozer.Mapper;
import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * @author Emptak
 */
@Service
public class TicketFacadeImpl implements TicketFacade
{    
    @Autowired
    private BookDAO bookDAO;
    
    @Autowired
    private TicketDAO ticketDAO;
    
    @Autowired
    private TicketItemDAO ticketItemDAO;
    
    @Autowired
    private Mapper mapper;
    
    @Override
    @Transactional
    public void addBookToTicket(Long bookID, UserDTO userDTO) throws IllegalArgumentException, NoResultException
    {
        if(LibraryValidator.isNull(userDTO))
        {
            throw new IllegalArgumentException("ERROR: user is null");
        }
        User u = mapper.map(userDTO, User.class);
        Book b = bookDAO.getBookByID(bookID);            
        if(b.getBookStatus().equals(BookStatus.AVAILABLE))
        {
            Ticket t =null;
            try
            {
                t = ticketDAO.getLastTicketForUser(u);
            }
            catch(NoResultException nre)
            {

            }
            if(t == null)
            {// este sme si nikdy nic nepozicali
                t = new Ticket();
                t.setBorrowTime(new DateTime());
                t.setDueTime(t.getBorrowTime().plusMonths(1));
                t.setUser(u);

                TicketItem ti = new TicketItem();
                ti.setBook(b);
                ti.setTicketItemStatus(TicketItemStatus.RESERVATION);
                List<TicketItem> list = new ArrayList<>();
                list.add(ti);                    
                t.setTicketItems(list);

                b.setBookStatus(BookStatus.NOT_AVAILABLE);
                bookDAO.updateBook(b);
                ticketItemDAO.createTicketItem(ti);

                ticketDAO.createTicket(t);
            }
            else
            {
                List<TicketItem> list = t.getTicketItems();
                TicketItem ti = new TicketItem();
                ti.setBook(b);
                ti.setTicketItemStatus(TicketItemStatus.RESERVATION);

                b.setBookStatus(BookStatus.NOT_AVAILABLE);
                bookDAO.updateBook(b);


                ticketItemDAO.createTicketItem(ti);
                list.add(ti);

                t.setTicketItems(list);

                ticketDAO.updateTicket(t);                    
            }
        } 
    }

    @Override
    @Transactional
    public void borrowTicket(Long ticketID) throws IllegalArgumentException, NoResultException
    {
        Ticket t = ticketDAO.getTicketByID(ticketID);
             
        for(TicketItem ti :t.getTicketItems())
        {
            if (ti.getBook().getBookStatus().equals(BookStatus.AVAILABLE)) {
                ti.setTicketItemStatus(TicketItemStatus.BORROWED);
                ticketItemDAO.updateTicketItem(ti);
            }
        }

        t.setBorrowTime(new DateTime());
        t.setDueTime(t.getBorrowTime().plusMonths(1));
        ticketDAO.updateTicket(t);           
    }

    @Override
    @Transactional
    public void returnTicket(Long ticketID) throws IllegalArgumentException, NoResultException
    {
        Ticket t = ticketDAO.getTicketByID(ticketID);
       
        
        if(t != null)
        {
            for(TicketItem ti:t.getTicketItems())
            {
                if (!ti.getTicketItemStatus().equals(TicketItemStatus.RETURNED_DAMAGED)) {
                    ti.setTicketItemStatus(TicketItemStatus.RETURNED);
                    ticketItemDAO.updateTicketItem(ti);

                    Book b = ti.getBook();
                    b.setBookStatus(BookStatus.AVAILABLE);
                    bookDAO.updateBook(b);
                }
            }
            
            t.setReturnTime(new DateTime());
            ticketDAO.updateTicket(t);
        }
    }

    @Override
    @Transactional
    public void returnBookInTicketItem(Long ticketItemID, Long ticketID,boolean isDamaged) throws IllegalArgumentException, NoResultException 
    {
        Ticket t = ticketDAO.getTicketByID(ticketID);
        boolean allBooksReturned = true;
        for(TicketItem ticketItemDTO : t.getTicketItems())
        {                    
            if(ticketItemDTO.getTicketItemID().equals(ticketItemID))
            {
                if(isDamaged)
                {
                    ticketItemDTO.setTicketItemStatus(TicketItemStatus.RETURNED_DAMAGED);
                    Book b = ticketItemDTO.getBook();
                    b.setBookStatus(BookStatus.NOT_AVAILABLE);

                    bookDAO.updateBook(b);  
                }
                else
                {
                    ticketItemDTO.setTicketItemStatus(TicketItemStatus.RETURNED);
                    Book b = ticketItemDTO.getBook();
                    b.setBookStatus(BookStatus.AVAILABLE);

                    bookDAO.updateBook(b);                    
                }               
                       
                ticketItemDAO.updateTicketItem(ticketItemDTO);                        
            }

            if(!ticketItemDTO.getTicketItemStatus().equals(TicketItemStatus.RETURNED) || !ticketItemDTO.getTicketItemStatus().equals(TicketItemStatus.RETURNED_DAMAGED))
            {// kniha nie je vratena ok, alebo vratena poskodena tym padom nie je vratena
                allBooksReturned = false;
            }
        }

        // ak su vsetky knihy vratene oznacime si cas vratenia poslednej knihy
        if(allBooksReturned)
        {
            t.setReturnTime(new DateTime());
            ticketDAO.updateTicket(t);
        }    
    }

    @Override
    @Transactional
    public void deleteTicket(Long ticketID) throws IllegalArgumentException, NoResultException
    {
        Ticket t = ticketDAO.getTicketByID(ticketID);
        
        if(t != null)
        {
            ticketDAO.deleteTicket(t);
            for(TicketItem ti : t.getTicketItems())
            {
                ticketItemDAO.deleteTicketItem(ti);
                Book b = ti.getBook();
                // poskodena kniha ostane poskodena preto ju neupravime, upravime tie ktore su v poriadku tj nie DAMAGED
                if(!ti.getTicketItemStatus().equals(TicketItemStatus.RETURNED_DAMAGED))
                {
                    b.setBookStatus(BookStatus.AVAILABLE);
                    bookDAO.updateBook(b);                    
                }                
            }            
        }
    }

    @Override
    @Transactional
    public void cancelTicket(Long ticketID) throws IllegalArgumentException, NoResultException
    {
        Ticket t = ticketDAO.getTicketByID(ticketID);
        boolean flag = true; // all books are reservation
        for(TicketItem ti : t.getTicketItems())
        {
            if(!ti.getTicketItemStatus().equals(TicketItemStatus.RESERVATION))
            {
                flag = false;
                break;
            }
        }

        if(flag)
        {
            ticketDAO.deleteTicket(t);
            for(TicketItem ti : t.getTicketItems())
            {
                Book b = ti.getBook();
                b.setBookStatus(BookStatus.AVAILABLE);

                bookDAO.updateBook(b);
                ticketItemDAO.deleteTicketItem(ti);
            }
        }
    }
    
}
