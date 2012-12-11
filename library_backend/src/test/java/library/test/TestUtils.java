/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package library.test;

import java.util.List;
import library.entity.Book;
import library.entity.Ticket;
import library.entity.TicketItem;
import library.entity.User;
import library.entity.dto.BookDTO;
import library.entity.dto.TicketDTO;
import library.entity.dto.TicketItemDTO;
import library.entity.dto.UserDTO;
import library.entity.enums.BookStatus;
import library.entity.enums.Department;
import library.entity.enums.TicketItemStatus;
import org.joda.time.DateTime;

/**
 *
 * @author Emptak
 */
public class TestUtils 
{
    public static UserDTO createUserDTO(Long id,String password,String realname,String systemRole,String username){
        UserDTO u = new UserDTO();
        u.setUserID(id);
        u.setPassword(password);
        u.setRealName(realname);
        u.setSystemRole(systemRole);
        u.setUsername(username);
        return u;
    }
    
    public static UserDTO createUserDTONoID(String password,String realname,String systemRole,String username){
        UserDTO u = new UserDTO();
        u.setPassword(password);
        u.setRealName(realname);
        u.setSystemRole(systemRole);
        u.setUsername(username);
        return u;
    }
    
    public static User createUser(Long id,String password,String realname,String systemRole,String username){
        User u = new User();
        u.setUserID(id);
        u.setPassword(password);
        u.setRealName(realname);
        u.setSystemRole(systemRole);
        u.setUsername(username);
        return u;
    }
    
    
    
    public static TicketItemDTO createTicketItemDTO(Long id, BookDTO b, TicketItemStatus status) {
        TicketItemDTO ti = new TicketItemDTO();
        ti.setTicketItemID(id);
        ti.setBook(b);
        ti.setTicketItemStatus(status);

        return ti;
    }
    
    public static TicketItemDTO createTicketItemDTONoID(BookDTO b, TicketItemStatus status) {
        TicketItemDTO ti = new TicketItemDTO();        
        ti.setBook(b);
        ti.setTicketItemStatus(status);

        return ti;
    }

    public static TicketItem createTicketItem(Long id, Book b, TicketItemStatus status) {
        TicketItem ti = new TicketItem();
        ti.setTicketItemID(id);
        ti.setBook(b);
        ti.setTicketItemStatus(status);

        return ti;
    }
    
    
    public static TicketDTO createTicketDTO(Long id, UserDTO user, DateTime borrowtime, List<TicketItemDTO> ticketItems) {
        TicketDTO t = new TicketDTO();
        t.setTicketID(id);
        t.setUser(user);
        t.setBorrowTime(borrowtime);
        t.setTicketItems(ticketItems);
        t.setDueTime(t.getBorrowTime().plusMonths(1));

        return t;
    }
    
    public static TicketDTO createTicketDTONoID(UserDTO user, DateTime borrowtime, List<TicketItemDTO> ticketItems) {
        TicketDTO t = new TicketDTO();        
        t.setUser(user);
        t.setBorrowTime(borrowtime);
        t.setTicketItems(ticketItems);
        t.setDueTime(t.getBorrowTime().plusMonths(1));

        return t;
    }

    public static Ticket createTicket(Long id, User user, DateTime borrowtime, List<TicketItem> ticketItems) {
        Ticket t = new Ticket();
        t.setTicketID(id);
        t.setUser(user);
        t.setBorrowTime(borrowtime);
        t.setTicketItems(ticketItems);
        t.setDueTime(t.getBorrowTime().plusMonths(1));
        
        return t;
    }

    public static BookDTO createBookDTO(Long id, String author, String title, Department department, BookStatus status) {
        BookDTO b = new BookDTO();
        b.setBookID(id);
        b.setAuthor(author);
        b.setTitle(title);
        b.setDepartment(department);
        b.setBookStatus(status);

        return b;
    }
    
    public static BookDTO createBookDTONoID(String author, String title, Department department, BookStatus status) {
        BookDTO b = new BookDTO();
        b.setAuthor(author);
        b.setTitle(title);
        b.setDepartment(department);
        b.setBookStatus(status);

        return b;
    }

    public static Book createBook(Long id, String author, String title, Department department, BookStatus status) {
        Book b = new Book();
        b.setBookID(id);
        b.setAuthor(author);
        b.setTitle(title);
        b.setDepartment(department);
        b.setBookStatus(status);

        return b;
    }
    
}
