/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package library.test;

import library.service.TicketFacade;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestExecutionListeners;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.support.DependencyInjectionTestExecutionListener;
import org.springframework.test.context.support.DirtiesContextTestExecutionListener;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import javax.persistence.NoResultException;
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
import library.service.BookService;
import library.service.TicketItemService;
import library.service.TicketService;
import library.service.UserService;
import library.service.impl.TicketFacadeImpl;
import library.utils.aop.validators.LibraryValidator;
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
 * @author Rainbow
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"file:src/main/resources/spring/applicationContext-test.xml"})
@TestExecutionListeners({DirtiesContextTestExecutionListener.class, DependencyInjectionTestExecutionListener.class})
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
public class TicketFascadeTest {

    @Autowired
    TicketFacade ticketFacade;
    @Autowired
    private UserService userService;
    @Autowired
    private BookService bookService;
    @Autowired
    private TicketItemService ticketItemService;
    @Autowired
    private TicketDAO ticketDAO;
    @Autowired
    private TicketItemDAO ticketItemDAO;
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
        correctTickets.add(TestUtils.createTicketDTONoID(user1, new DateTime(2012, 10, 5, 12, 30), Arrays.asList(ticketItems.get(0), ticketItems.get(1))));
        correctTickets.add(TestUtils.createTicketDTONoID(user1, new DateTime(2012, 10, 8, 12, 30), Arrays.asList(ticketItems.get(2))));
        correctTickets.add(TestUtils.createTicketDTONoID(user1, new DateTime(2012, 10, 11, 12, 30), Arrays.asList(ticketItems.get(3))));
        correctTickets.add(TestUtils.createTicketDTONoID(user2, new DateTime(2012, 10, 14, 12, 30), Arrays.asList(ticketItems.get(4))));
        correctTickets.add(TestUtils.createTicketDTONoID(user2, new DateTime(2012, 10, 17, 12, 30), Arrays.asList(ticketItems.get(5))));

        wrongTickets = new ArrayList<>(2);

        wrongTickets.add(TestUtils.createTicketDTONoID(null, new DateTime(), Arrays.asList(ticketItems.get(5))));//ticket without user
        wrongTickets.add(TestUtils.createTicketDTONoID(user1, new DateTime(), new ArrayList<TicketItemDTO>()));

    }

    @Test
    public void addBookToTicket() {

        BookDTO b = bookService.getBookByID(1L);
        UserDTO user = userService.getUserByID(new Long(1));
        TicketDTO ticket = correctTickets.get(0);


        try {
            ticketFacade.addBookToTicket(null, null);

            fail("IllegalArgumentException should be thrown when bookID and User are null");
        } catch (IllegalArgumentException iae) {
        }

        try {

            ticketFacade.addBookToTicket(b.getBookID(), null);

            fail("IllegalArgumentException should be thrown when User is null");
        } catch (IllegalArgumentException iae) {
        }

        try {


            ticketFacade.addBookToTicket(null, user);

            fail("IllegalArgumentException should be thrown when bookID is null");
        } catch (IllegalArgumentException iae) {
        }

        try {
            ticketFacade.addBookToTicket(b.getBookID(), user);
        } catch (Exception e) {
            fail(e.getMessage());
        }


        assertEquals(ticket.getUser(), user);


    }

    @Test
    public void testBorrowTicket() {

        
        ticketService.createTicket(correctTickets.get(0));
        
        TicketDTO t = ticketService.getTicketByID(new Long(1));
        
        try {
            ticketFacade.borrowTicket(null);
            fail("IllegalArgumentException should be thrown borrowing ticket with null id");
        } catch (IllegalArgumentException iae) {
        }

        // TicketDTO ticket = ticketService.getTicketByID(new Long(1));
        try {

            ticketFacade.borrowTicket(t.getTicketID());
            
        } catch (IllegalArgumentException iae) {
            fail(iae.getMessage());
        }

        
        assertEquals(t, correctTickets.get(0));

    }

    @Test
    public void testReturnTicket() {


        try {
            ticketFacade.returnTicket(null);
            fail("IllegalArgumentException should be thrown when return ticket with null id");
        } catch (IllegalArgumentException iae) {
        }

        ticketService.createTicket(correctTickets.get(0));
        TicketDTO ticket = ticketService.getTicketByID(1L);

       try { ticketFacade.returnTicket(ticket.getTicketID());
       } catch (Exception e) {
           fail(e.getMessage());
       }
       assertEquals(ticket, correctTickets.get(0));



    }

    @Test
    public void testReturnBookInTicketItem() {
        
       ticketService.createTicket(correctTickets.get(0));
       TicketDTO t = ticketService.getTicketByID(new Long(1));
      
       
       try {
           ticketFacade.returnBookInTicketItem(null, null, true);
           fail("cannot return book in ticket Item with null id for ticket and ticket item");
       } catch(IllegalArgumentException iae) {
           
       }
       
        try {
           ticketFacade.returnBookInTicketItem(t.getTicketItems().get(0).getTicketItemID(), null, true);
           fail("cannot return book in given ticket Item with null ticket id");
       } catch(IllegalArgumentException iae) {
           
       }
        
         try {
           ticketFacade.returnBookInTicketItem(null, t.getTicketID(), true);
           
       } catch(IllegalArgumentException iae) {
         fail(iae.getMessage()); 
       }
       
        try {
            ticketFacade.returnBookInTicketItem(t.getTicketItems().get(0).getTicketItemID(), t.getTicketID(), true);
        } catch(Exception e) {
            fail(e.getMessage());
            
        }
         
        assertEquals(t, correctTickets.get(0));
    }

    @Test
    public void testDeleteTicket() {

        try {
            ticketFacade.deleteTicket(null);
            fail("IllegalArgumentException should be thrown when deleting null ticket");
        } catch (IllegalArgumentException iae) {
        }
        
         
             ticketService.createTicket(correctTickets.get(0));
             TicketDTO ticket = ticketService.getTicketByID(1L);
             assertNotNull(ticket);
         
         try {
             
              ticketFacade.deleteTicket(ticket.getTicketID());
         } catch(Exception e) {
             fail(e.getMessage());
         }
         
         assertEquals(ticket.getTicketItems(), correctTickets.get(0).getTicketItems());
//        try {
//
//            ticketService.createTicket(correctTickets.get(0));
//
//            TicketDTO ticket = ticketService.getTicketByID(1L);
//            assertNotNull(ticket);
//  
//
//            for (TicketItemDTO t : ticket.getTicketItems()) {
//
//                ticketItemService.deleteTicketItem(t);
//
//            }
//            ticketFacade.deleteTicket(ticket.getTicketID());
//            
//
//            fail("No exception should be thrown when deleting ticket with correct id");
//        } catch (IllegalArgumentException iae) {
//            
//        }
//            
//           assertNull(ticketService.getTicketByID(new Long(1))); 
//            
        
    }
    

    @Test
     public void testCancelTicket() {
        
        ticketService.createTicket(correctTickets.get(0));
        TicketDTO t = ticketService.getTicketByID(new Long(1));

        try {
                    ticketFacade.cancelTicket(null);
                    fail("IllegalArgumentException should be thrown when canceling ticket with null id");
                } catch (IllegalArgumentException iae) {
                }




                try {
                    ticketFacade.cancelTicket(t.getTicketID());

                } catch (Exception e) {
                     fail(e.getMessage());
                }

                assertEquals(correctTickets.get(0), t);



           }
        }
