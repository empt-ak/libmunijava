/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package library.test;

import java.util.ArrayList;
import java.util.List;
import library.dao.TicketItemDAO;
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
import library.service.BookService;
import library.service.TicketItemService;
import library.service.TicketService;
import library.service.UserService;
import library.service.impl.TicketItemServiceImpl;
import org.dozer.Mapper;
import org.joda.time.DateTime;
import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import static org.mockito.Mockito.*;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.test.util.ReflectionTestUtils;

/**
 *
 * @author Andrej Gašpar
 */
@RunWith(MockitoJUnitRunner.class)
public class TicketItemServiceMockTest {

   
    TicketItemService ticketItemService;
   
    @Mock
    TicketItemDAO ticketItemDao;
    @Mock
    Mapper mapper;

    @Before
    public void init() {
        ticketItemService = new TicketItemServiceImpl();
        ReflectionTestUtils.setField(ticketItemService, "ticketItemDAO", ticketItemDao);
        ReflectionTestUtils.setField(ticketItemService, "mapper", mapper);
    }

    @Test
    public void testCreateTicketItem() {

        try {
            ticketItemService.createTicketItem(null);
        } catch (IllegalArgumentException e) {
           
        }
        BookDTO bDTO1 = createBookDTO(new Long(1), "nazov1", "autor1", Department.SCIENTIFIC, BookStatus.AVAILABLE);
        TicketItemDTO tiDTO1 = createTicketItem(new Long(1), bDTO1, TicketItemStatus.BORROWED);

        try {
            ticketItemService.createTicketItem(tiDTO1);
            
        } catch (Exception e) {
            fail();
        }

    }

    @Test
    public void testGetTicketItemByID() {

        Book b1 = createBook(new Long(1), "nazov1", "autor1", Department.SCIENTIFIC, BookStatus.AVAILABLE);
        TicketItem ti1 = createTicketItem(new Long(1), b1, TicketItemStatus.BORROWED);
        when(ticketItemDao.getTicketItemByID(new Long(1))).thenReturn(ti1);;

        BookDTO bdto1 = createBookDTO(new Long(1), "nazov1", "autor1", Department.SCIENTIFIC, BookStatus.AVAILABLE);
        TicketItemDTO tiDTO1 = createTicketItem(new Long(1), bdto1, TicketItemStatus.BORROWED);

        when(ticketItemService.getTicketItemByID(new Long(1))).thenReturn(tiDTO1);

        TicketItemDTO tiDTO = ticketItemService.getTicketItemByID(new Long(1));

        assertEquals(tiDTO.getTicketItemID(), ti1.getTicketItemID());
    }

    @Test
    public void testUpdateTicketItem() {

        try {
            ticketItemService.updateTicketItem(null);
        } catch (IllegalArgumentException e) {
            
        }
        BookDTO bDTO1 = createBookDTO(new Long(1), "nazov1", "autor1", Department.SCIENTIFIC, BookStatus.AVAILABLE);
        TicketItemDTO tiDTO1 = createTicketItem(new Long(1), bDTO1, TicketItemStatus.BORROWED);

        try {
            ticketItemService.updateTicketItem(tiDTO1);

        } catch (Exception e) {
            fail();
        }


    }

    @Test
    public void testDeleteTicketItem() {

        try {
            ticketItemService.deleteTicketItem(null);
        } catch (IllegalArgumentException e) {
            
        }


        BookDTO bDTO1 = createBookDTO(new Long(1), "nazov1", "autor1", Department.SCIENTIFIC, BookStatus.AVAILABLE);
        TicketItemDTO tiDTO1 = createTicketItem(new Long(1), bDTO1, TicketItemStatus.BORROWED);

        try {
            ticketItemService.deleteTicketItem(tiDTO1);

        } catch (Exception e) {
            fail();
        }

    }

    @Test
    public void testGetTicketItemsByTicket() {

        try {
            ticketItemService.getTicketItemsByTicket(null);
        }
        catch (Exception e) {
            
        }
        
        
        BookDTO bdto1 = createBookDTO(new Long(1), "nazov1", "autor1", Department.SCIENTIFIC, BookStatus.AVAILABLE);
        BookDTO bdto2 = createBookDTO(new Long(2), "nazov2", "autor2", Department.KIDS, BookStatus.NOT_AVAILABLE);
        
        Book b1 = createBook(new Long(1), "nazov1", "autor1", Department.SCIENTIFIC, BookStatus.AVAILABLE);
        Book b2 = createBook(new Long(2), "nazov2", "autor2", Department.KIDS, BookStatus.NOT_AVAILABLE);

        TicketItemDTO tiDTO1 = createTicketItem(new Long(1), bdto1, TicketItemStatus.BORROWED);
        TicketItemDTO tiDTO2 = createTicketItem(new Long(2), bdto2, TicketItemStatus.RETURNED);

        TicketItem ti1 = createTicketItem(new Long(1), b1, TicketItemStatus.BORROWED);
        TicketItem ti2 = createTicketItem(new Long(2), b2, TicketItemStatus.RETURNED);


        UserDTO udto1 = createUserDTO(new Long(1), "heslo1", "realnemeno1", "USER1", "login1");
        UserDTO udto2 = createUserDTO(new Long(2), "heslo2", "realnemeno2", "USER2", "login2");

        User u1 = createUser(new Long(1), "heslo1", "realnemeno1", "USER1", "login1");
        User u2 = createUser(new Long(2), "heslo2", "realnemeno2", "USER2", "login2");


       List<TicketItemDTO> tidtoList = new ArrayList<>();
       List<TicketItemDTO> testList = new ArrayList<>();
       List<TicketItem> ticketItemsList = new ArrayList<>();


        tidtoList.add(tiDTO1);
        tidtoList.add(tiDTO2);
        ticketItemsList.add(ti1);
        ticketItemsList.add(ti2);
        
        
        
        
        TicketDTO tdto1 = createTicket(new Long(1), udto1, new DateTime(2012, 10, 7, 12, 00), (ArrayList<TicketItemDTO>) tidtoList);
        TicketDTO tdto2 = createTicket(new Long(2), udto2, new DateTime(2012, 10, 8, 12, 00), (ArrayList<TicketItemDTO>) tidtoList);

        Ticket t1 = createTicket(new Long(1), u1, new DateTime(2012, 10, 7, 12, 00), (ArrayList<TicketItem>) ticketItemsList);
        Ticket t2 = createTicket(new Long(2), u2, new DateTime(2012, 10, 8, 12, 00), (ArrayList<TicketItem>) ticketItemsList);


        

        when(ticketItemDao.getTicketItemsByTicket(t2)).thenReturn(ticketItemsList);
        when(ticketItemService.getTicketItemsByTicket(tdto2)).thenReturn(tidtoList);

//        try{
//            ticketItemService.createTicketItem(tiDTO1);
//            ticketItemService.createTicketItem(tiDTO2);
//        } catch (Exception e) {
//            fail();
//        }
            
        
        
       try {
          testList =  ticketItemService.getTicketItemsByTicket(tdto2);
          
        } catch (Exception e) {
          fail();  
        }
      
             
        
            
          assertEquals(((List<TicketItem>) testList.get(1)).get(0), tidtoList.get(1));
          assertEquals(((List<TicketItem>) testList.get(1)).get(1), tidtoList.get(1));


    }

    private TicketDTO createTicket(Long id, UserDTO user, DateTime borrowtime, ArrayList<TicketItemDTO> ticketItems) {
        TicketDTO t = new TicketDTO();
        t.setUser(user);
        t.setBorrowTime(borrowtime);
        t.setTicketItems(ticketItems);
        t.setTicketID(id);

        return t;
    }

    private Ticket createTicket(Long id, User user, DateTime borrowtime, ArrayList<TicketItem> ticketItems) {
        Ticket t = new Ticket();
        t.setUser(user);
        t.setBorrowTime(borrowtime);
        t.setTicketItems(ticketItems);
        t.setTicketID(id);

        return t;
    }

    private User createUser(Long id, String password, String realname, String systemRole, String username) {

        User u = new User();
        u.setPassword(password);
        u.setRealName(realname);
        u.setSystemRole(systemRole);
        u.setUsername(username);
        u.setUserID(id);

        return u;


    }

    private UserDTO createUserDTO(Long id, String password, String realname, String systemRole, String username) {
        UserDTO u = new UserDTO();
        u.setPassword(password);
        u.setRealName(realname);
        u.setSystemRole(systemRole);
        u.setUsername(username);
        u.setUserID(id);
        return u;
    }

    private TicketItemDTO createTicketItem(Long id, BookDTO b, TicketItemStatus status) {
        TicketItemDTO ti = new TicketItemDTO();
        ti.setBook(b);
        ti.setTicketItemStatus(status);
        ti.setTicketItemID(id);
        return ti;
    }

    private TicketItem createTicketItem(Long id, Book b, TicketItemStatus status) {
        TicketItem ti = new TicketItem();
        ti.setBook(b);
        ti.setTicketItemStatus(status);
        ti.setTicketItemID(id);
        return ti;
    }

    private Book createBook(Long id, String title, String author, Department department, BookStatus status) {
        Book b = new Book();
        b.setBookID(id);
        b.setTitle(title);
        b.setAuthor(author);
        b.setDepartment(department);
        b.setBookStatus(status);

        return b;
    }

    private BookDTO createBookDTO(Long id, String title, String author, Department department, BookStatus status) {
        BookDTO b = new BookDTO();
        b.setBookID(id);
        b.setTitle(title);
        b.setAuthor(author);
        b.setDepartment(department);
        b.setBookStatus(status);

        return b;
    }
   
    
    
}