/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package library.test;

import library.service.TicketService;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import library.dao.TicketDAO;
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
import org.junit.After;
import org.junit.Test;
import org.kubek2k.springockito.annotations.SpringockitoContextLoader;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.AbstractJUnit4SpringContextTests;
import static org.mockito.Mockito.*;
import org.junit.Before;
import org.junit.runner.RunWith;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import static org.junit.Assert.*;

/**
 *
 * @author Rainbow
 */
@ContextConfiguration(loader = SpringockitoContextLoader.class,
locations = "file:src/main/resources/spring/applicationContext-mock.xml")
@RunWith(SpringJUnit4ClassRunner.class)
public class TicketsServiceTest extends AbstractJUnit4SpringContextTests {

    @Autowired
    private TicketService ticketService;
    @Autowired
    TicketDAO ticketDAO;
    private List<TicketItemDTO> correctTicketItems;
    private List<TicketItem> correctTicketItemDAOS;
    private List<UserDTO> correctUSERS;
    private List<User> correctUserDAOS;
    private List<TicketDTO> correctTickets;
    private List<Ticket> correctTicketDAOS;

    @Before
    public void init() {
        List<BookDTO> books = new ArrayList<>(3);
        books.add(createBookDTO(new Long(1), "pepazdepa", "ach jaj", Department.ADULT, BookStatus.AVAILABLE));
        books.add(createBookDTO(new Long(2), "pepazdepa", "xexe", Department.ADULT, BookStatus.AVAILABLE));
        books.add(createBookDTO(null, "pepazdepa", "hlavninadrazi", Department.ADULT, BookStatus.AVAILABLE));

        List<Book> booksDAO = new ArrayList<>(3);
        booksDAO.add(createBook(new Long(1), "pepazdepa", "ach jaj", Department.ADULT, BookStatus.AVAILABLE));
        booksDAO.add(createBook(new Long(2), "pepazdepa", "xexe", Department.ADULT, BookStatus.AVAILABLE));
        booksDAO.add(createBook(null, "pepazdepa", "hlavninadrazi", Department.ADULT, BookStatus.AVAILABLE));



        //now lets create some ticketitems
        correctTicketItems = new ArrayList<>(3);
        correctTicketItems.add(createTicketItemDTO(new Long(1), books.get(0), TicketItemStatus.BORROWED));
        correctTicketItems.add(createTicketItemDTO(new Long(2), books.get(1), TicketItemStatus.BORROWED));
        correctTicketItems.add(createTicketItemDTO(null, books.get(2), TicketItemStatus.RETURNED));

        correctTicketItemDAOS = new ArrayList<>(3);
        correctTicketItemDAOS.add(createTicketItem(new Long(1), booksDAO.get(0), TicketItemStatus.BORROWED));
        correctTicketItemDAOS.add(createTicketItem(new Long(2), booksDAO.get(1), TicketItemStatus.BORROWED));
        correctTicketItemDAOS.add(createTicketItem(null, booksDAO.get(2), TicketItemStatus.RETURNED));


        correctUSERS = new ArrayList<>(2);
        correctUSERS.add(createUserDTO(new Long(1), "heslo1", "realnemeno1", "USER1", "login1"));
        correctUSERS.add(createUserDTO(new Long(2), "heslo2", "realnemeno2", "USER2", "login2"));

        correctUserDAOS = new ArrayList<>(2);
        correctUserDAOS.add(createUser(new Long(1), "heslo1", "realnemeno1", "USER1", "login1"));
        correctUserDAOS.add(createUser(new Long(2), "heslo2", "realnemeno2", "USER2", "login2"));


        correctTickets = new ArrayList<>(3);
        correctTickets.add(createTicketDTO(new Long(1), correctUSERS.get(0), new DateTime(2012, 10, 7, 12, 00), correctTicketItems));
        correctTickets.add(createTicketDTO(new Long(2), correctUSERS.get(1), new DateTime(2012, 10, 8, 12, 30), correctTicketItems));
        correctTickets.add(createTicketDTO(null, correctUSERS.get(1), new DateTime(2012, 10, 11, 12, 30), correctTicketItems));


        correctTicketDAOS = new ArrayList<>(3);
        correctTicketDAOS.add(createTicket(new Long(1), correctUserDAOS.get(0), new DateTime(2012, 10, 7, 12, 00), correctTicketItemDAOS));
        correctTicketDAOS.add(createTicket(new Long(2), correctUserDAOS.get(1), new DateTime(2012, 10, 8, 12, 30), correctTicketItemDAOS));
        correctTicketDAOS.add(createTicket(null, correctUserDAOS.get(1), new DateTime(2012, 10, 11, 12, 30), correctTicketItemDAOS));




    }

    //toto asi nejde
    @After
    public void clearMocks() {
        reset(ticketDAO);
    }

    @Test
    @DirtiesContext
    public void testCreateTicket() {

        doThrow(new IllegalArgumentException("")).when(ticketDAO).createTicket(null);

        // zavolame metodu
        ticketService.createTicket(correctTickets.get(2));


        // overime ze bola zavolana
        verify(ticketDAO, times(1)).createTicket(correctTicketDAOS.get(2));
    }

    @Test
    @DirtiesContext
    public void testGetTicketByID() {
        when(ticketDAO.getTicketByID(new Long(1))).thenReturn(correctTicketDAOS.get(0));
        ticketService.createTicket(correctTickets.get(0));


        TicketDTO result = ticketService.getTicketByID(new Long(1));

        System.out.println(result);

        assertEquals(new Long(1), result.getTicketID());
    }

    @Test
    @DirtiesContext
    public void testUpdateTicket() {
        // given
        TicketDTO tdto = correctTickets.get(0);
        Ticket t = correctTicketDAOS.get(0);
        t.setReturnTime(new DateTime(2012, 10, 9, 12, 00));

        when(ticketDAO.getTicketByID(new Long(1))).thenReturn(t);

        //when
        ticketService.createTicket(tdto);
        tdto.setReturnTime(new DateTime(2012, 10, 9, 12, 00));
        ticketService.updateTicket(tdto);
        TicketDTO result = ticketService.getTicketByID(new Long(1));

        //then        
        assertEquals(result.getReturnTime(), t.getReturnTime());


    }

    /**
     * Test of deleteTicket method, of class TicketService.
     */
    @Test
    public void testDeleteTicket() {

        //given
        when(ticketDAO.getTicketByID(new Long(2))).thenReturn(null);


        //when
        ticketService.createTicket(correctTickets.get(1));
        ticketService.deleteTicket(correctTickets.get(1));
        TicketDTO result = ticketService.getTicketByID(new Long(2));

        //then
        assertNull(result);
    }

    @Test
    @DirtiesContext
    public void testGetLastTicketForUser() {

        TicketDTO lastDTO = null;
        for (TicketDTO t : correctTickets) {
            ticketService.createTicket(t);
            lastDTO = t;
        }

        Ticket last = null;
        for (Ticket t : correctTicketDAOS) {
            ticketDAO.createTicket(t);
            last = t;
        }



        when(ticketDAO.getLastTicketForUser(correctUserDAOS.get(0))).thenReturn(last);

        ticketService.createTicket(correctTickets.get(0));
        ticketService.createTicket(correctTickets.get(1));
        ticketService.createTicket(correctTickets.get(2));



        TicketDTO result = ticketService.getLastTicketForUser(correctUSERS.get(0));


        System.out.println(result);



        assertEquals(result, lastDTO);

    }

    @Test
    @DirtiesContext
    public void testGetAllTicketsForUser() {


        for (TicketDTO t : correctTickets) {
            ticketService.createTicket(t);
        }

        for (Ticket t : correctTicketDAOS) {
            ticketDAO.createTicket(t);
        }



        //    when(ticketService.getAllTicketsForUser(correctUSERS.get(0))).thenReturn(correctTickets);
        when(ticketDAO.getAllTicketsForUser(correctUserDAOS.get(0))).thenReturn(correctTicketDAOS);

        List<TicketDTO> testExpectedTicketsDTO = ticketService.getAllTicketsForUser(correctUSERS.get(0));

        System.out.println(testExpectedTicketsDTO);

        
        for(int i = 0; i < 3; i++) {
        assertEquals(testExpectedTicketsDTO.get(i), correctTickets.get(i));
        }
    }

    @Test
    @DirtiesContext
    public void testGetTicketsInPeriodForUser() {

        for (TicketDTO t : correctTickets) {
            ticketService.createTicket(t);
        }

        for (Ticket t : correctTicketDAOS) {
            ticketDAO.createTicket(t);
        }

        List<TicketDTO> expectedTicketsDTO = new ArrayList<>(2);
        List<Ticket> expectedTickets = new ArrayList<>(2);

        expectedTicketsDTO.add(correctTickets.get(0));
        expectedTicketsDTO.add(correctTickets.get(1));

        expectedTickets.add(correctTicketDAOS.get(0));
        expectedTickets.add(correctTicketDAOS.get(1));



        when(ticketDAO.getTicketsInPeriodForUser(new DateTime(2012, 10, 5, 12, 00),
                new DateTime(2012, 10, 9, 12, 30), correctUserDAOS.get(0))).thenReturn(expectedTickets);



        List<TicketDTO> testExpectedTicketsDTO = new ArrayList<>(2);


        testExpectedTicketsDTO = ticketService.getTicketsInPeriodForUser(new DateTime(2012, 10, 5, 12, 00), 
                                                     new DateTime(2012, 10, 9, 12, 30), correctUSERS.get(0));
        
          for(int i = 0; i < 2; i++) {
         assertEquals(testExpectedTicketsDTO.get(i),expectedTicketsDTO.get(i));      
         
          }
                
                

       






    }

    private TicketDTO createTicketDTO(Long id, UserDTO user, DateTime borrowtime, List<TicketItemDTO> ticketItems) {
        TicketDTO t = new TicketDTO();
        t.setTicketID(id);
        t.setUser(user);
        t.setBorrowTime(borrowtime);
        t.setTicketItems(ticketItems);

        return t;
    }

    private Ticket createTicket(Long id, User user, DateTime borrowtime, List<TicketItem> ticketItems) {
        Ticket t = new Ticket();
        t.setTicketID(id);
        t.setUser(user);
        t.setBorrowTime(borrowtime);
        t.setTicketItems(ticketItems);

        return t;
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

    private User createUser(Long id, String password, String realname, String systemRole, String username) {
        User u = new User();
        u.setUserID(id);
        u.setPassword(password);
        u.setRealName(realname);
        u.setSystemRole(systemRole);
        u.setUsername(username);
        return u;
    }

    private TicketItemDTO createTicketItemDTO(Long id, BookDTO b, TicketItemStatus status) {
        TicketItemDTO ti = new TicketItemDTO();
        ti.setTicketItemID(id);
        ti.setBook(b);
        ti.setTicketItemStatus(status);

        return ti;
    }

    private TicketItem createTicketItem(Long id, Book b, TicketItemStatus status) {
        TicketItem ti = new TicketItem();
        ti.setTicketItemID(id);
        ti.setBook(b);
        ti.setTicketItemStatus(status);

        return ti;
    }

    private BookDTO createBookDTO(Long id, String author, String title, Department department, BookStatus status) {
        BookDTO b = new BookDTO();
        b.setBookID(id);
        b.setAuthor(author);
        b.setTitle(title);
        b.setDepartment(department);
        b.setBookStatus(status);

        return b;
    }

    private Book createBook(Long id, String author, String title, Department department, BookStatus status) {
        Book b = new Book();
        b.setBookID(id);
        b.setAuthor(author);
        b.setTitle(title);
        b.setDepartment(department);
        b.setBookStatus(status);

        return b;
    }
}
