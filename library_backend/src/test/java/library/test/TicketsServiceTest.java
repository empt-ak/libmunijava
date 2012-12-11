/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package library.test;

import library.service.TicketService;
import java.util.ArrayList;
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
    private TicketDAO ticketDAO;
    private List<TicketItemDTO> correctTicketItems;
    private List<TicketItem> correctTicketItemDAOS;
    private List<UserDTO> correctUSERS;
    private List<User> correctUserDAOS;
    private List<TicketDTO> correctTickets;
    private List<Ticket> correctTicketDAOS;

    @Before
    public void init() {
        List<BookDTO> books = new ArrayList<>(3);
        books.add(TestUtils.createBookDTO(new Long(1), "pepazdepa", "ach jaj", Department.ADULT, BookStatus.AVAILABLE));
        books.add(TestUtils.createBookDTO(new Long(2), "pepazdepa", "xexe", Department.ADULT, BookStatus.AVAILABLE));
        books.add(TestUtils.createBookDTO(new Long(3), "pepazdepa", "hlavninadrazi", Department.ADULT, BookStatus.AVAILABLE));

        List<Book> booksDAO = new ArrayList<>(3);
        booksDAO.add(TestUtils.createBook(new Long(1), "pepazdepa", "ach jaj", Department.ADULT, BookStatus.AVAILABLE));
        booksDAO.add(TestUtils.createBook(new Long(2), "pepazdepa", "xexe", Department.ADULT, BookStatus.AVAILABLE));
        booksDAO.add(TestUtils.createBook(new Long(3), "pepazdepa", "hlavninadrazi", Department.ADULT, BookStatus.AVAILABLE));



        //now lets create some ticketitems
        correctTicketItems = new ArrayList<>(3);
        correctTicketItems.add(TestUtils.createTicketItemDTO(new Long(1), books.get(0), TicketItemStatus.BORROWED));
        correctTicketItems.add(TestUtils.createTicketItemDTO(new Long(2), books.get(1), TicketItemStatus.BORROWED));
        correctTicketItems.add(TestUtils.createTicketItemDTO(new Long(3), books.get(2), TicketItemStatus.RETURNED));

        correctTicketItemDAOS = new ArrayList<>(3);
        correctTicketItemDAOS.add(TestUtils.createTicketItem(new Long(1), booksDAO.get(0), TicketItemStatus.BORROWED));
        correctTicketItemDAOS.add(TestUtils.createTicketItem(new Long(2), booksDAO.get(1), TicketItemStatus.BORROWED));
        correctTicketItemDAOS.add(TestUtils.createTicketItem(new Long(3), booksDAO.get(2), TicketItemStatus.RETURNED));


        correctUSERS = new ArrayList<>(2);
        correctUSERS.add(TestUtils.createUserDTO(new Long(1), "heslo1", "realnemeno1", "USER", "login1"));
        correctUSERS.add(TestUtils.createUserDTO(new Long(2), "heslo2", "realnemeno2", "USER", "login2"));

        correctUserDAOS = new ArrayList<>(2);
        correctUserDAOS.add(TestUtils.createUser(new Long(1), "heslo1", "realnemeno1", "USER", "login1"));
        correctUserDAOS.add(TestUtils.createUser(new Long(2), "heslo2", "realnemeno2", "USER", "login2"));


        correctTickets = new ArrayList<>(3);
        correctTickets.add(TestUtils.createTicketDTO(new Long(1), correctUSERS.get(0), new DateTime(2012, 10, 7, 12, 00), correctTicketItems));
        correctTickets.add(TestUtils.createTicketDTO(new Long(2), correctUSERS.get(1), new DateTime(2012, 10, 8, 12, 30), correctTicketItems));
        correctTickets.add(TestUtils.createTicketDTO(new Long(3), correctUSERS.get(1), new DateTime(2012, 10, 11, 12, 30), correctTicketItems));


        correctTicketDAOS = new ArrayList<>(3);
        correctTicketDAOS.add(TestUtils.createTicket(new Long(1), correctUserDAOS.get(0), new DateTime(2012, 10, 7, 12, 00), correctTicketItemDAOS));
        correctTicketDAOS.add(TestUtils.createTicket(new Long(2), correctUserDAOS.get(1), new DateTime(2012, 10, 8, 12, 30), correctTicketItemDAOS));
        correctTicketDAOS.add(TestUtils.createTicket(new Long(3), correctUserDAOS.get(1), new DateTime(2012, 10, 11, 12, 30), correctTicketItemDAOS));
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

    
    @Test
    @DirtiesContext
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

        for (int i = 0; i < 3; i++) {
            assertEquals(testExpectedTicketsDTO.get(i), correctTickets.get(i));
        }
    }
}
