/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package library.test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
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
import org.joda.time.DateTime;
import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestExecutionListeners;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.support.DependencyInjectionTestExecutionListener;
import org.springframework.test.context.support.DirtiesContextTestExecutionListener;

/**
 *
 * @author Gaspar
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"file:src/main/resources/spring/applicationContext-test.xml"})
@TestExecutionListeners({DirtiesContextTestExecutionListener.class, DependencyInjectionTestExecutionListener.class})
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
public class TicketsDAOTest {

    @Autowired
    private UserService userService;
    @Autowired
    private BookService bookService;
    @Autowired
    private TicketItemService ticketItemService;
    @Autowired
    private TicketService ticketService;
    private List<TicketDTO> correctTickets;
    private List<TicketDTO> wrongTickets;

    @Before
    public void init() {
        // we need one correct user for ticket, since one of fields is user
        UserDTO user1 = TestUtils.createUserDTONoID("heslo1", "realny1", "USER", "uziv1");
        UserDTO user2 = TestUtils.createUserDTONoID("heslo2", "realny2", "USER", "uziv2");
        userService.createUser(user1);
        userService.createUser(user2);

        List<BookDTO> books = new ArrayList<>(6);

        books.add(TestUtils.createBookDTONoID("nazov1", "autor1", Department.SCIENTIFIC, BookStatus.AVAILABLE));
        books.add(TestUtils.createBookDTONoID("nazov2", "autor2", Department.ADULT, BookStatus.AVAILABLE));
        books.add(TestUtils.createBookDTONoID("nazov3", "autor3", Department.KIDS, BookStatus.AVAILABLE));
        books.add(TestUtils.createBookDTONoID("nazov4", "autor4", Department.ADULT, BookStatus.AVAILABLE));
        books.add(TestUtils.createBookDTONoID("nazov5", "autor5", Department.ADULT, BookStatus.AVAILABLE));
        books.add(TestUtils.createBookDTONoID("nazov6", "autor6", Department.ADULT, BookStatus.AVAILABLE));

        // lets save those books
        for (BookDTO b : books) {
            bookService.createBook(b);
        }


        List<TicketItemDTO> ticketItems = new ArrayList<>(6);
        ticketItems.add(TestUtils.createTicketItemDTONoID(books.get(0), TicketItemStatus.BORROWED));
        ticketItems.add(TestUtils.createTicketItemDTONoID(books.get(1), TicketItemStatus.RESERVATION));
        ticketItems.add(TestUtils.createTicketItemDTONoID(books.get(2), TicketItemStatus.RETURNED));
        ticketItems.add(TestUtils.createTicketItemDTONoID(books.get(3), TicketItemStatus.BORROWED));
        ticketItems.add(TestUtils.createTicketItemDTONoID(books.get(4), TicketItemStatus.RESERVATION));
        ticketItems.add(TestUtils.createTicketItemDTONoID(books.get(5), TicketItemStatus.RETURNED));


        for (TicketItemDTO ti : ticketItems) {
            ticketItemService.createTicketItem(ti);
        }

        // 5 correct tickets
        correctTickets = new ArrayList<>(5);

        // first ticket has 2 ticketitems              
        correctTickets.add(TestUtils.createTicketDTONoID(user1, new DateTime(2012, 10, 5, 12, 30),Arrays.asList(ticketItems.get(0), ticketItems.get(1))));
        correctTickets.add(TestUtils.createTicketDTONoID(user1, new DateTime(2012, 10, 8, 12, 30), Arrays.asList(ticketItems.get(2))));
        correctTickets.add(TestUtils.createTicketDTONoID(user1, new DateTime(2012, 10, 11, 12, 30),Arrays.asList(ticketItems.get(3))));
        correctTickets.add(TestUtils.createTicketDTONoID(user2, new DateTime(2012, 10, 14, 12, 30), Arrays.asList(ticketItems.get(4))));
        correctTickets.add(TestUtils.createTicketDTONoID(user2, new DateTime(2012, 10, 17, 12, 30),Arrays.asList(ticketItems.get(5))));

        wrongTickets = new ArrayList<>(2);

        wrongTickets.add(TestUtils.createTicketDTONoID(null, new DateTime(),Arrays.asList(ticketItems.get(5))));//ticket without user
        wrongTickets.add(TestUtils.createTicketDTONoID(user1, new DateTime(),new ArrayList<TicketItemDTO>()));




    }

    /**
     * Test of createTicket method, of class TicketService.
     */
    @Test
    public void testCreateAndGetTicket() {
        //=================
        // TEST wrong tickets
        //=================
        try {
            // create null ticket
            ticketService.createTicket(null);
            fail("IllegalArgumentException should be thrown when creating null Ticket");
        } catch (IllegalArgumentException iae) {
            //ok
        }
        try {
            // create ticket without set user
            ticketService.createTicket(wrongTickets.get(0));
            fail("IllegalArgumentException should be thrown when creating ticket without user");
        } catch (IllegalArgumentException iae) {
            //ok
        }


        //=================
        // TEST correct ticket
        //================= 

        try {
            ticketService.createTicket(correctTickets.get(0));
            //ok
        } catch (Exception e) {
            fail("No exception should be thrown when creating correct Ticket : " + e);
        }

        //=================
        // TEST get user
        //================= 
        try {
            ticketService.getTicketByID(null);
            fail("IllegalArgumentException should be thrown when getting ticket by null id");
        } catch (IllegalArgumentException iae) {
            //ok
        }
        try {
            ticketService.getTicketByID(new Long(0));
            fail("IllegalArgumentException should be thrown when getting ticket by his id that is out of range");
        } catch (IllegalArgumentException iae) {
            //ok
        }
        TicketDTO saved = null;
        try {
            saved = ticketService.getTicketByID(correctTickets.get(0).getTicketID()); // should be 5
        } catch (Exception e) {
        }
        // when creating a new correct ticket (reservation) the book is automatically set as not available
        for (TicketItemDTO ti : saved.getTicketItems()) {
            ti.getBook().setBookStatus(BookStatus.NOT_AVAILABLE);
        }
        assertEquals("Based on equals method tickets are not same, they dont have same ID", correctTickets.get(0), saved);       
        assertDeepEquals(correctTickets.get(0), saved);
    }

    /**
     * Test of updateTicket method, of class TicketService.
     */
    @Test
    public void testUpdateTicket() {

        try
        {
            ticketService.updateTicket(null);
            fail("IllegalArgumentException should be thrown when updating a null ticket");
        }
        catch(IllegalArgumentException iae)
        {
            //ok
        }
        try
        {
            ticketService.updateTicket(new TicketDTO());
            fail("IllegalArgumentException should be thrown when updating a ticket without id");
        }
        catch(IllegalArgumentException iae)
        {
            //ok
        }
        ticketService.createTicket(correctTickets.get(1));
        
        TicketDTO ticket = ticketService.getLastTicketForUser(userService.getUserByID(new Long(1)));       
        ticket.setBorrowTime(null);
        try
        {
            ticketService.updateTicket(ticket);
            fail("IllegalArgumentException should be thrown when updating a ticket without borrowtime");
        }
        catch(IllegalArgumentException iae)
        {
            ticket.setBorrowTime(new DateTime());
            ticket.setDueTime(null);            
        }
        try
        {
            ticketService.updateTicket(ticket);
            fail("IllegalArgumentException should be thrown when updating a ticket without duetime");
        }
        catch(IllegalArgumentException iae)
        {
            ticket.setDueTime(ticket.getBorrowTime().plusDays(1));
            ticket.setUser(null);
        }
        
        try
        {
            ticketService.updateTicket(ticket);
            fail("IllegalArgumentException should be thrown when updating a ticket with a null user");
        }
        catch(IllegalArgumentException iae)
        {
            ticket.setUser(userService.getUserByUsername("uziv1"));
        }
        try
        {
            ticketService.updateTicket(ticket);
        }
        catch(Exception e)
        {
            fail(e.getMessage());
        }
        
        assertDeepEquals(ticket, ticketService.getLastTicketForUser(userService.getUserByUsername("uziv1")));   
    }

    /**
     * Test of deleteTicket method, of class TicketService.
     */
    @Test
    public void testDeleteTicket() {
        
        try {
            ticketService.deleteTicket(null);
            fail("IllegalArgumentException should be thrown when deleting a null Ticket");
        } catch (IllegalArgumentException iae) {
            //ok
        }
        ticketService.createTicket(correctTickets.get(3));
        TicketDTO ticket = ticketService.getTicketByID(1L);
        
        assertNotNull(ticket);
        ticketService.deleteTicket(ticket);
        assertNull(ticketService.getTicketByID(1L));
    }

    /**
     * Test of getLastTicketForUser method, of class TicketService.
     */
    @Test
    public void testGetLastTicketForUser() {
        TicketDTO last = null;
        for (TicketDTO t : correctTickets) {
            ticketService.createTicket(t);
            last = t;
        }

        TicketDTO ticket = ticketService.getLastTicketForUser(userService.getUserByID(2L));
        last.setTicketID(ticket.getTicketID());
        assertDeepEquals(ticket, last);
    }  

    /**
     * Test of getAllTicketsForUser method, of class TicketService.
     */
    @Test
    public void testGetAllTicketsForUser() {
        
        for(TicketDTO t :correctTickets)
        {
            ticketService.createTicket(t);
        }
        
        try {
            ticketService.getAllTicketsForUser(null);
            fail("IllegalArgumentException should be thrown when updating a ticket with empty TicketItems");
        } catch (IllegalArgumentException iae) {
            //ok
        }
        List<TicketDTO> list = ticketService.getAllTicketsForUser(userService.getUserByID(1L));
        
        correctTickets.get(0).setTicketID(list.get(0).getTicketID());
        correctTickets.get(1).setTicketID(list.get(1).getTicketID());
        correctTickets.get(2).setTicketID(list.get(2).getTicketID());
        
        assertDeepEquals(correctTickets.get(0), list.get(0));
        assertDeepEquals(correctTickets.get(1), list.get(1));
        assertDeepEquals(correctTickets.get(2), list.get(2));
    }
    
    private void assertDeepEquals(TicketDTO expected, TicketDTO actual) {
        assertEquals("Users are not the same", expected.getUser(), actual.getUser());
        assertEquals("Borrow times are not the same", expected.getBorrowTime(), actual.getBorrowTime());
        assertDeepEquals(expected.getTicketItems(), actual.getTicketItems());

    }

    private void assertDeepEquals(List<TicketItemDTO> expected, List<TicketItemDTO> actual) {
        for (int i = 0; i < expected.size(); i++) {
            assertEquals("Books are not the same", expected.get(i).getBook(), actual.get(i).getBook());
            assertEquals("TicketItemStstuses are not the same", expected.get(i).getTicketItemStatus(), actual.get(i).getTicketItemStatus());
        }
    }
}
