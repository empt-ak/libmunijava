/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package library.test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import library.dao.TicketDAO;
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
import library.service.TicketItemService;
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
 * @author emptak
 */
@ContextConfiguration(loader = SpringockitoContextLoader.class,
locations = "file:src/main/resources/spring/applicationContext-mock.xml")
@RunWith(SpringJUnit4ClassRunner.class)
public class TicketItemServiceTest extends AbstractJUnit4SpringContextTests {

    @Autowired
    TicketItemService ticketItemService;
    @Autowired
    TicketItemDAO ticketItemDAO;
    //private List<BookDTO> books;
    private List<TicketItemDTO> correct;
    private List<TicketItem> correctDAOS;
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
        correct = new ArrayList<>(3);
        correct.add(createTicketItemDTO(new Long(1), books.get(0), TicketItemStatus.BORROWED));
        correct.add(createTicketItemDTO(new Long(2), books.get(1), TicketItemStatus.BORROWED));
        correct.add(createTicketItemDTO(null, books.get(2), TicketItemStatus.RETURNED));

        correctDAOS = new ArrayList<>(3);
        correctDAOS.add(createTicketItem(new Long(1), booksDAO.get(0), TicketItemStatus.BORROWED));
        correctDAOS.add(createTicketItem(new Long(2), booksDAO.get(1), TicketItemStatus.BORROWED));
        correctDAOS.add(createTicketItem(null, booksDAO.get(2), TicketItemStatus.RETURNED));


        correctUSERS = new ArrayList<>(2);
        correctUSERS.add(createUserDTO(new Long(1), "heslo1", "realnemeno1", "USER1", "login1"));
        correctUSERS.add(createUserDTO(new Long(2), "heslo2", "realnemeno2", "USER2", "login2"));

        correctUserDAOS = new ArrayList<>(2);
        correctUserDAOS.add(createUser(new Long(1), "heslo1", "realnemeno1", "USER1", "login1"));
        correctUserDAOS.add(createUser(new Long(2), "heslo2", "realnemeno2", "USER2", "login2"));

        correctTickets = new ArrayList<>(2);
        correctTickets.add(createTicketDTO(new Long(1), correctUSERS.get(0), new DateTime(2012, 10, 7, 12, 00), correct));
       
        
        correctTicketDAOS = new ArrayList<>(2);
        correctTicketDAOS.add(createTicket(new Long(1), correctUserDAOS.get(0), new DateTime(2012, 10, 7, 12, 00), correctDAOS));
       


    }

    //toto asi nejde
    @After
    public void clearMocks() {
        reset(ticketItemDAO);
    }

    @Test
    @DirtiesContext
    public void testCreateTicketItem() {


        doThrow(new IllegalArgumentException("")).when(ticketItemDAO).createTicketItem(null);

        // zavolame metodu
        ticketItemService.createTicketItem(correct.get(2));


        // overime ze bola zavolana
        verify(ticketItemDAO, times(1)).createTicketItem(correctDAOS.get(2));
    }

    @Test
    @DirtiesContext
    public void testGetTicketItemByID() {
        when(ticketItemDAO.getTicketItemByID(new Long(1))).thenReturn(correctDAOS.get(0));
        ticketItemService.createTicketItem(correct.get(0));


        TicketItemDTO result = ticketItemService.getTicketItemByID(new Long(1));

        System.out.println(result);

        assertEquals(new Long(1), result.getTicketItemID());
    }

    @Test
    @DirtiesContext
    public void testUpdateTicketItem() {

        // given
        TicketItemDTO tiDTO = correct.get(0);
        TicketItem ti = correctDAOS.get(0);
        ti.setTicketItemStatus(TicketItemStatus.RETURNED);
        when(ticketItemDAO.getTicketItemByID(new Long(1))).thenReturn(ti);

        //when
        ticketItemService.createTicketItem(tiDTO);
        tiDTO.setTicketItemStatus(TicketItemStatus.RETURNED);
        ticketItemService.updateTicketItem(tiDTO);
        TicketItemDTO result = ticketItemService.getTicketItemByID(new Long(1));

        //then        
        assertEquals(result.getTicketItemStatus(), ti.getTicketItemStatus());

    }

    @Test
    @DirtiesContext
    public void testDeleteTicketItem() {
        //given
        when(ticketItemDAO.getTicketItemByID(new Long(2))).thenReturn(null);


        //when
        ticketItemService.createTicketItem(correct.get(1));
        ticketItemService.deleteTicketItem(correct.get(1));
        TicketItemDTO result = ticketItemService.getTicketItemByID(new Long(2));

        //then
        assertNull(result);
    }

    @Test
    @DirtiesContext
    public void testGetTicketItemsByTicket() {
        
      TicketDTO tidto1 = createTicketDTO(new Long(1), correctUSERS.get(0), new DateTime(2012, 10, 7, 12, 00), (ArrayList<TicketItemDTO>) correct);
    
        
        
      Ticket t1 = createTicket(new Long(1), correctUserDAOS.get(0), new DateTime(2012, 10, 7, 12, 00), correctDAOS);
       
        
        when(ticketItemDAO.getTicketItemsByTicket(t1)).thenReturn(correctDAOS);
        
        ticketItemService.createTicketItem(correct.get(0));


        List<TicketItemDTO> result = ticketItemService.getTicketItemsByTicket(tidto1);

        System.out.println(result);

        
        for(int i = 0; i < 3; i++) {
        
        assertEquals(correct.get(i), result.get(i));
        
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
