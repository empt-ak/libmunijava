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
import library.service.TicketItemService;
import org.joda.time.DateTime;
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
    private TicketItemService ticketItemService;
    @Autowired
    private TicketItemDAO ticketItemDAO;    
    private List<TicketItemDTO> ticketDTOS;
    private List<TicketItem> ticketsDAOs;

    @Before
    public void init() {
        List<BookDTO> books = new ArrayList<>(3);
        books.add(createBookDTO(new Long(1), "pepazdepa", "ach jaj", Department.ADULT, BookStatus.AVAILABLE));
        books.add(createBookDTO(new Long(2), "pepazdepa", "xexe", Department.ADULT, BookStatus.AVAILABLE));

        List<Book> booksDAO = new ArrayList<>(3);
        booksDAO.add(createBook(new Long(1), "pepazdepa", "ach jaj", Department.ADULT, BookStatus.AVAILABLE));
        booksDAO.add(createBook(new Long(2), "pepazdepa", "xexe", Department.ADULT, BookStatus.AVAILABLE));

        ticketDTOS = new ArrayList<>(3);
        ticketDTOS.add(createTicketItemDTO(books.get(0), TicketItemStatus.BORROWED));
        ticketDTOS.add(createTicketItemDTO(books.get(1), TicketItemStatus.BORROWED));
        ticketDTOS.add(createTicketItemDTO(new BookDTO(), TicketItemStatus.RETURNED));

        ticketsDAOs = new ArrayList<>(3);
        ticketsDAOs.add(createTicketItem(new Long(1), booksDAO.get(0), TicketItemStatus.BORROWED));
        ticketsDAOs.add(createTicketItem(new Long(2), booksDAO.get(1), TicketItemStatus.BORROWED));
        ticketsDAOs.add(createTicketItem(null, new Book(), TicketItemStatus.RETURNED));        
    }

    @Test
    @DirtiesContext
    public void testCreateTicketItem() {
        doThrow(new IllegalArgumentException("")).when(ticketItemDAO).createTicketItem(null);

        // zavolame metodu
        ticketItemService.createTicketItem(ticketDTOS.get(2));


        // overime ze bola zavolana
        verify(ticketItemDAO, times(1)).createTicketItem(ticketsDAOs.get(2));
    }

    @Test
    @DirtiesContext
    public void testGetTicketItemByID() {
        // given
        when(ticketItemDAO.getTicketItemByID(new Long(1))).thenReturn(ticketsDAOs.get(0));
        ticketItemService.createTicketItem(ticketDTOS.get(0));

        //when        
        TicketItemDTO result = ticketItemService.getTicketItemByID(new Long(1));

        //then
        assertEquals(new Long(1), result.getTicketItemID());
    }

    @Test
    @DirtiesContext
    public void testUpdateTicketItem() {
        // given
        TicketItemDTO tiDTO = ticketDTOS.get(0);
        TicketItem ti = ticketsDAOs.get(0);
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
        ticketItemService.createTicketItem(ticketDTOS.get(1));
        ticketItemService.deleteTicketItem(ticketDTOS.get(1));
        TicketItemDTO result = ticketItemService.getTicketItemByID(new Long(2));

        //then
        assertNull(result);
    }

    @Test
    @DirtiesContext
    public void testGetTicketItemsByTicket() {
        //given
        TicketDTO tidto1 = createTicketDTO(new Long(1), new UserDTO(), new DateTime(2012, 10, 7, 12, 00), ticketDTOS.subList(0, 2));//treti ti nema id
        Ticket t1 = createTicket(new Long(1), new User(), new DateTime(2012, 10, 7, 12, 00), ticketsDAOs.subList(0, 2));
        when(ticketItemDAO.getTicketItemsByTicket(t1)).thenReturn(ticketsDAOs.subList(0, 2));
        
        //when        
        ticketItemService.createTicketItem(ticketDTOS.get(0));
        ticketItemService.createTicketItem(ticketDTOS.get(1));
        List<TicketItemDTO> result = ticketItemService.getTicketItemsByTicket(tidto1);
        
        //then        
        assertEquals(2,result.size());        
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

    private TicketItemDTO createTicketItemDTO(BookDTO b, TicketItemStatus status) {
        TicketItemDTO ti = new TicketItemDTO();
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
