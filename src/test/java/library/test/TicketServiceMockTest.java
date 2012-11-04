/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package library.test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import library.dao.BookDAO;
import library.dao.TicketDAO;
import library.dao.TicketItemDAO;
import library.dao.UserDAO;
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
import library.service.impl.BookServiceImpl;
import library.service.impl.TicketItemServiceImpl;
import library.service.impl.TicketServiceImpl;
import library.service.impl.UserServiceImpl;
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
 * @author Andrej Gajdoš
 */
@RunWith(MockitoJUnitRunner.class)
public class TicketServiceMockTest {
     
    @Mock
    TicketDAO ticketDAO;
    
    @Mock
    private UserDAO userDAO;
    
    @Mock
    private BookDAO bookDAO;
    
    @Mock
    private TicketItemDAO ticketItemDAO;
    
    @Mock
    Mapper mapper;
    
    private UserService userService;
    private BookService bookService;
    private TicketItemService ticketItemService;
    private TicketService ticketService;
    
    private User user1;
    private UserDTO user1DTO;
    private List<TicketItem> ticketItems;
    private List<TicketItemDTO> ticketItemsDTO;
    private List<Ticket> correctTickets;
    private List<Ticket> wrongTickets;
    private List<TicketDTO> correctTicketsDTO;
    private List<TicketDTO> wrongTicketsDTO;
    
    @Before
    public void init() {
        
        bookService = new BookServiceImpl();
        userService = new UserServiceImpl();
        ticketService = new TicketServiceImpl();
        ticketItemService = new TicketItemServiceImpl();
        
        ReflectionTestUtils.setField(bookService, "bookDAO", bookDAO);
        ReflectionTestUtils.setField(ticketItemService, "ticketItemDAO", ticketItemDAO);
        ReflectionTestUtils.setField(userService, "userDAO", userDAO);
        ReflectionTestUtils.setField(ticketService, "ticketDAO", ticketDAO);
        
        ReflectionTestUtils.setField(bookService, "mapper", mapper);
        ReflectionTestUtils.setField(ticketItemService, "mapper", mapper);
        ReflectionTestUtils.setField(userService, "mapper", mapper);
        ReflectionTestUtils.setField(ticketService, "mapper", mapper);
        
        user1 = createUser(new Long(1),"heslo1", "realny1", "USER", "uziv1");
         
         List<Book> books = new ArrayList<>(2);
         books.add(createBook(new Long(1),"nazov1", "autor1", Department.SCIENTIFIC, BookStatus.AVAILABLE));
         books.add(createBook(new Long(1),"nazov2", "autor2", Department.ADULT, BookStatus.AVAILABLE));
         
         ticketItems = new ArrayList<>(2);
         ticketItems.add(createTicketItem(new Long(1),books.get(0), TicketItemStatus.BORROWED));
         ticketItems.add(createTicketItem(new Long(1),books.get(1), TicketItemStatus.RESERVATION));
        
        user1DTO = createUserDTO(new Long(1),"heslo1", "realny1", "USER", "uziv1");
         
         List<BookDTO> booksDTO = new ArrayList<>(2);
         booksDTO.add(createBookDTO(new Long(1),"nazov1", "autor1", Department.SCIENTIFIC, BookStatus.AVAILABLE));
         booksDTO.add(createBookDTO(new Long(1),"nazov2", "autor2", Department.ADULT, BookStatus.AVAILABLE));
         
         ticketItemsDTO = new ArrayList<>(2);
         ticketItemsDTO.add(createTicketItemDTO(new Long(1),booksDTO.get(0), TicketItemStatus.BORROWED));
         ticketItemsDTO.add(createTicketItemDTO(new Long(1),booksDTO.get(1), TicketItemStatus.RESERVATION));
         
         // 3 correct tickets
        correctTickets = new ArrayList<>(3);

        // first ticket has 2 ticketitems              
        correctTickets.add(createTicket(new Long(1),user1, new DateTime(2012, 10, 5, 12, 30), new ArrayList<>(Arrays.asList(ticketItems.get(0), ticketItems.get(1)))));
        correctTickets.add(createTicket(new Long(1),user1, new DateTime(2012, 10, 8, 12, 30), new ArrayList<>(Arrays.asList(ticketItems.get(0)))));
        correctTickets.add(createTicket(new Long(1),user1, new DateTime(2012, 10, 11, 12, 30), new ArrayList<>(Arrays.asList(ticketItems.get(1)))));

        wrongTickets = new ArrayList<>(2);

        wrongTickets.add(createTicket(new Long(1),null, new DateTime(), new ArrayList<>(Arrays.asList(ticketItems.get(1)))));//ticket without user
        wrongTickets.add(createTicket(new Long(1),user1, new DateTime(), new ArrayList<TicketItem>()));
         
         // 3 correct tickets
        correctTicketsDTO = new ArrayList<>(3);

        // first ticket has 2 ticketitems              
        correctTicketsDTO.add(createTicketDTO(new Long(1),user1DTO, new DateTime(2012, 10, 5, 12, 30), new ArrayList<>(Arrays.asList(ticketItemsDTO.get(0), ticketItemsDTO.get(1)))));
        correctTicketsDTO.add(createTicketDTO(new Long(1),user1DTO, new DateTime(2012, 10, 8, 12, 30), new ArrayList<>(Arrays.asList(ticketItemsDTO.get(0)))));
        correctTicketsDTO.add(createTicketDTO(new Long(1),user1DTO, new DateTime(2012, 10, 11, 12, 30), new ArrayList<>(Arrays.asList(ticketItemsDTO.get(1)))));

        wrongTicketsDTO = new ArrayList<>(2);

        wrongTicketsDTO.add(createTicketDTO(new Long(1),null, new DateTime(), new ArrayList<>(Arrays.asList(ticketItemsDTO.get(1)))));//ticket without user
        wrongTicketsDTO.add(createTicketDTO(new Long(1),user1DTO, new DateTime(), new ArrayList<TicketItemDTO>()));
        
    }
    
     @Test
    public void testCreateTicket() {
         
        TicketDTO tdto1 = createTicketDTO(new Long(1), user1DTO, new DateTime(2012, 10, 5, 12, 30), new ArrayList<>(Arrays.asList(ticketItemsDTO.get(0), ticketItemsDTO.get(1))));
        
        try {
            ticketService.createTicket(tdto1);
        } catch (Exception e) {
            fail();
        }

    }
     
    @Test
    public void testGetTicketByID() {
        
        Ticket t1 = createTicket(new Long(1), user1, new DateTime(2012, 10, 5, 12, 30), new ArrayList<>(Arrays.asList(ticketItems.get(0), ticketItems.get(1))));
        when(ticketDAO.getTicketByID(new Long(1))).thenReturn(t1);

        TicketDTO tdto1 = createTicketDTO(new Long(1), user1DTO, new DateTime(2012, 10, 5, 12, 30), new ArrayList<>(Arrays.asList(ticketItemsDTO.get(0), ticketItemsDTO.get(1))));
        when(ticketService.getTicketByID(new Long(1))).thenReturn(tdto1);

        TicketDTO ticketDTO = ticketService.getTicketByID(new Long(1));

        assertEquals(ticketDTO.getTicketID(), t1.getTicketID());

    }

    /**
     * Test of updateTicket method, of class TicketService.
     */
    @Test
    public void testUpdateTicket() {
         
        TicketDTO tdto1 = createTicketDTO(new Long(1), user1DTO, new DateTime(2012, 10, 5, 12, 30), new ArrayList<>(Arrays.asList(ticketItemsDTO.get(0), ticketItemsDTO.get(1))));
        
        try {
            ticketService.updateTicket(tdto1);
        } catch (Exception e) {
            fail();
        }

       
    }

    /**
     * Test of deleteTicket method, of class TicketService.
     */
    @Test
    public void testDeleteTicket() {
         
        TicketDTO tdto1 = createTicketDTO(new Long(1), user1DTO, new DateTime(2012, 10, 5, 12, 30), new ArrayList<>(Arrays.asList(ticketItemsDTO.get(0), ticketItemsDTO.get(1))));
        
        try {
            ticketService.deleteTicket(tdto1);
        } catch (Exception e) {
            fail();
        }
        
        
    }

    /**
     * Test of getLastTicketForUser method, of class TicketService.
     */
    @Test
    public void testGetLastTicketForUser() {
        
        TicketDTO lastDTO = null;
        for (TicketDTO t : correctTicketsDTO) {
            ticketService.createTicket(t);
            lastDTO = t;
        }
        
        Ticket last = null;
        for (Ticket t : correctTickets) {
            ticketDAO.createTicket(t);
            last = t;
        }

        when(ticketDAO.getLastTicketForUser(user1)).thenReturn(last);   
        when(ticketService.getLastTicketForUser(user1DTO)).thenReturn(lastDTO);
        
        TicketDTO testTicket = new TicketDTO(); 
        
        try {
            testTicket = ticketService.getLastTicketForUser(user1DTO);
        } catch (Exception e) {
            fail();
        }
        
        assertEquals(testTicket, lastDTO);
      
    }

    /**
     * Test of getTicketsInPeriodForUser method, of class TicketService.
     */
    @Test
    public void testGetTicketsInPeriodForUser() {
        
        for(TicketDTO t :correctTicketsDTO)
        {
            ticketService.createTicket(t);
        }
        
        for(Ticket t :correctTickets)
        {
            ticketDAO.createTicket(t);
        }
        
        List<TicketDTO> expectedTicketsDTO = new ArrayList<>(2);
        List<Ticket> expectedTickets = new ArrayList<>(2);
        
        expectedTicketsDTO.add(correctTicketsDTO.get(0));
        expectedTicketsDTO.add(correctTicketsDTO.get(1));
        
        expectedTickets.add(correctTickets.get(0));
        expectedTickets.add(correctTickets.get(1));
        
        when(ticketService.getTicketsInPeriodForUser(new DateTime(2012, 10, 5, 12, 00), 
                                                     new DateTime(2012, 10, 9, 12, 30), user1DTO)).thenReturn(expectedTicketsDTO);
        
        when(ticketDAO.getTicketsInPeriodForUser(new DateTime(2012, 10, 5, 12, 00), 
                                                  new DateTime(2012, 10, 9, 12, 30), user1)).thenReturn(expectedTickets);
        
        /*
       
       List<TicketDTO> testExpectedTicketsDTO = new ArrayList<>(2);
         
        try {
            testExpectedTicketsDTO = ticketService.getTicketsInPeriodForUser(new DateTime(2012, 10, 5, 12, 00), 
                                                                             new DateTime(2012, 10, 9, 12, 30), user1DTO);
        } catch (Exception e) {
            fail();
        }
        
        assertEquals(testExpectedTicketsDTO.get(0), expectedTicketsDTO.get(0));
        */
        
    }

    /**
     * Test of getAllTicketsForUser method, of class TicketService.
     */
    @Test
    public void testGetAllTicketsForUser() {
        
        
        for(TicketDTO t :correctTicketsDTO)
        {
            ticketService.createTicket(t);
        }
        
        for(Ticket t :correctTickets)
        {
            ticketDAO.createTicket(t);
        } 
        
        when(ticketService.getAllTicketsForUser(user1DTO)).thenReturn(correctTicketsDTO);
        when(ticketDAO.getAllTicketsForUser(user1)).thenReturn(correctTickets);
        
        /*
        List<TicketDTO> testExpectedTicketsDTO = new ArrayList<>(2);
         
        try {
            testExpectedTicketsDTO = ticketService.getAllTicketsForUser(user1DTO);
        } catch (Exception e) {
            fail();
        }
        
        assertEquals(testExpectedTicketsDTO.get(0), correctTicketsDTO);
        */
        
    }
    
    private User createUser(Long id, String password, String realname, String systemRole, String username) {
        User u = new User();
        u.setUserID(id);
        u.setPassword(password);
        u.setRealName(realname);
        u.setSystemRole(systemRole);
        u.setUsername(username);
        return u;
    }
    
    private UserDTO createUserDTO(Long id, String password, String realname, String systemRole, String username) {
        UserDTO u = new UserDTO();
        u.setUserID(id);
        u.setPassword(password);
        u.setRealName(realname);
        u.setSystemRole(systemRole);
        u.setUsername(username);
        return u;
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
    
    private TicketItem createTicketItem(Long id, Book book, TicketItemStatus ticketItemStatus) {
        TicketItem ti = new TicketItem();
        ti.setTicketItemID(id);
        ti.setBook(book);
        ti.setTicketItemStatus(ticketItemStatus);
        return ti;
    }

    private TicketItemDTO createTicketItemDTO(Long id, BookDTO book, TicketItemStatus ticketItemStatus) {
        TicketItemDTO ti = new TicketItemDTO();
        ti.setTicketItemID(id);
        ti.setBook(book);
        ti.setTicketItemStatus(ticketItemStatus);
        return ti;
    }
    
    private Ticket createTicket(Long id, User user, DateTime dateTime, ArrayList<TicketItem> ticketItems) {
        Ticket t = new Ticket();
        t.setTicketID(id);
        t.setUser(user);
        t.setBorrowTime(dateTime);

        t.setTicketItems(ticketItems);

        return t;
    }

    private TicketDTO createTicketDTO(Long id, UserDTO user, DateTime dateTime, ArrayList<TicketItemDTO> ticketItems) {
        TicketDTO t = new TicketDTO();
        t.setTicketID(id);
        t.setUser(user);
        t.setBorrowTime(dateTime);

        t.setTicketItems(ticketItems);

        return t;
    }

}
