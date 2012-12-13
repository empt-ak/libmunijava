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
 * @author Andrej Gašpar
 */

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"file:src/main/resources/spring/applicationContext-test.xml"})
@TestExecutionListeners({DirtiesContextTestExecutionListener.class, DependencyInjectionTestExecutionListener.class})
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
public class TicketFacadeTest {

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
        
        

    } 
//            
    

    @Test
    public void testBorrowTicket() {

//        BookDTO b1 = bookService.getAllBooks().get(0);
//        BookDTO b2 = bookService.getAllBooks().get(1);
        
             
//        b1.setBookStatus(BookStatus.NOT_AVAILABLE);
//        b2.setBookStatus(BookStatus.NOT_AVAILABLE);
//        
//        TicketItemDTO ti1 = ticketItemService.getTicketItemByID(1L);
//        TicketItemDTO ti2 = ticketItemService.getTicketItemByID(2L);
//        
//        ti1.setTicketItemStatus(TicketItemStatus.RESERVATION);
//        ti1.setBook(b1);
//        
//        ti2.setTicketItemStatus(TicketItemStatus.RESERVATION);
//        ti2.setBook(b2);
//        
//        List<TicketItemDTO> ticketItems = new ArrayList<>();
//        
//        ticketItems.add(ti1);
//        ticketItems.add(ti2);
         
        TicketDTO t = correctTickets.get(0);
        ticketService.createTicket(t);
        for(TicketItemDTO ti : t.getTicketItems()) {
            ti.getBook().setBookStatus(BookStatus.NOT_AVAILABLE);
            ti.setTicketItemStatus(TicketItemStatus.RESERVATION);
        }
        
              
        
        
        
        ticketFacade.borrowTicket(t.getTicketID());
        
        for(TicketItemDTO ti : ticketService.getTicketByID(1L).getTicketItems()) {
            assertTrue(ti.getTicketItemStatus().equals(TicketItemStatus.BORROWED));
        }
        
        
    }

    @Test
    public void testReturnTicket() {

    
    
        
    }
        
        

    @Test
    public void testReturnBookInTicketItem() {
        
       
            
            
        
        
    }

    @Test
    public void testDeleteTicket() {
        
        
        
        
        
        

    }
    

    @Test
     public void testCancelTicket() {
        
    }
    
    
       
}      