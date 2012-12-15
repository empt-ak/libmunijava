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
import library.entity.dto.BookDTO;
import library.entity.dto.TicketDTO;
import library.entity.dto.TicketItemDTO;
import library.entity.dto.UserDTO;
import library.entity.enums.BookStatus;
import library.entity.enums.Department;
import library.entity.enums.TicketItemStatus;
import library.service.BookService;
import library.service.TicketFacade;
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
    private TicketService ticketService;

    @Before
    public void init() {
        // we need one correct user for ticket, since one of fields is user
        UserDTO user1 = TestUtils.createUserDTONoID("heslo1", "realny1", "USER", "uziv1");
        userService.createUser(user1);

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
    }

    @Test
    public void addBookToTicket() {
        UserDTO user = userService.getUserByID(1L);
        BookDTO b = bookService.getAllBooks().get(0);

        ticketFacade.addBookToTicket(bookService.getBookByID(b.getBookID()).getBookID(), userService.getUserByID(user.getUserID()));

        TicketDTO tidto = ticketService.getLastTicketForUser(user);
        assertEquals(BookStatus.NOT_AVAILABLE, bookService.getAllBooks().get(0).getBookStatus());
        for (TicketItemDTO ti : tidto.getTicketItems()) {
            if (ti.getBook().equals(b)) {
                assertEquals(TicketItemStatus.RESERVATION, ti.getTicketItemStatus());
            }
        }
    }

    @Test
    public void testBorrowTicket() {
        ticketFacade.addBookToTicket(bookService.getBookByID(new Long(1)).getBookID(), userService.getUserByID(new Long(1)));
        ticketFacade.addBookToTicket(bookService.getBookByID(new Long(2)).getBookID(), userService.getUserByID(new Long(1)));

        ticketFacade.borrowTicket(ticketService.getLastTicketForUser(userService.getUserByID(new Long(1))).getTicketID());

        TicketDTO tdto = ticketService.getLastTicketForUser(userService.getUserByID(new Long(1)));

        for (TicketItemDTO ti : tdto.getTicketItems()) {
            assertEquals(TicketItemStatus.BORROWED, ti.getTicketItemStatus());
        }
    }

    @Test
    public void testReturnTicket() {
        ticketFacade.addBookToTicket(bookService.getBookByID(new Long(1)).getBookID(), userService.getUserByID(new Long(1)));
        ticketFacade.addBookToTicket(bookService.getBookByID(new Long(2)).getBookID(), userService.getUserByID(new Long(1)));
        ticketFacade.borrowTicket(ticketService.getLastTicketForUser(userService.getUserByID(new Long(1))).getTicketID());

        ticketFacade.returnTicket(ticketService.getLastTicketForUser(userService.getUserByID(new Long(1))).getTicketID());

        TicketDTO tdto = ticketService.getLastTicketForUser(userService.getUserByID(new Long(1)));

        for (TicketItemDTO ti : tdto.getTicketItems()) {
            assertTrue(ti.getTicketItemStatus().equals(TicketItemStatus.RETURNED));
            assertTrue(ti.getBook().getBookStatus().equals(BookStatus.AVAILABLE));
        }
    }

    @Test
    public void testReturnBookInTicketItem() {


        ticketFacade.addBookToTicket(bookService.getBookByID(new Long(1)).getBookID(), userService.getUserByID(new Long(1)));
        ticketFacade.addBookToTicket(bookService.getBookByID(new Long(2)).getBookID(), userService.getUserByID(new Long(1)));

        ticketFacade.borrowTicket(ticketService.getLastTicketForUser(userService.getUserByID(new Long(1))).getTicketID());


        ticketFacade.returnBookInTicketItem(ticketService.getLastTicketForUser(userService.getUserByID(new Long(1))).getTicketItems().get(0).getTicketItemID(), ticketService.getLastTicketForUser(userService.getUserByID(new Long(1))).getTicketID(), true);



        assertEquals(ticketService.getLastTicketForUser(userService.getUserByID(new Long(1))).getTicketItems().get(0).getTicketItemStatus(), TicketItemStatus.RETURNED_DAMAGED);
        assertEquals(ticketService.getLastTicketForUser(userService.getUserByID(new Long(1))).getTicketItems().get(0).getBook().getBookStatus(), BookStatus.NOT_AVAILABLE);


        ticketFacade.returnBookInTicketItem(ticketService.getLastTicketForUser(userService.getUserByID(new Long(1))).getTicketItems().get(1).getTicketItemID(), ticketService.getLastTicketForUser(userService.getUserByID(new Long(1))).getTicketID(), false);



        assertEquals(ticketService.getLastTicketForUser(userService.getUserByID(new Long(1))).getTicketItems().get(1).getTicketItemStatus(), TicketItemStatus.RETURNED);
        assertEquals(ticketService.getLastTicketForUser(userService.getUserByID(new Long(1))).getTicketItems().get(1).getBook().getBookStatus(), BookStatus.AVAILABLE);
    }

    @Test
    public void testDeleteTicket() {

        ticketFacade.addBookToTicket(bookService.getBookByID(new Long(1)).getBookID(), userService.getUserByID(new Long(1)));
        ticketFacade.addBookToTicket(bookService.getBookByID(new Long(2)).getBookID(), userService.getUserByID(new Long(1)));

        ticketFacade.deleteTicket(ticketService.getLastTicketForUser(userService.getUserByID(new Long(1))).getTicketID());



        for (int i = 0; i < bookService.getAllBooks().size(); i++) {
            assertTrue(bookService.getAllBooks().get(i).getBookStatus().equals(BookStatus.AVAILABLE));
        }





    }

    @Test
    public void testCancelTicket() {
        ticketFacade.addBookToTicket(bookService.getBookByID(new Long(1)).getBookID(), userService.getUserByID(new Long(1)));
        ticketFacade.addBookToTicket(bookService.getBookByID(new Long(2)).getBookID(), userService.getUserByID(new Long(1)));

        TicketDTO tdto = ticketService.getLastTicketForUser(userService.getUserByID(new Long(1))); // local copy
        List<BookDTO> books = new ArrayList<>();
        for (TicketItemDTO ti : tdto.getTicketItems()) {
            books.add(ti.getBook());
        }
        ticketFacade.cancelTicket(tdto.getTicketID());

        for (BookDTO b : books) {
            assertEquals(BookStatus.AVAILABLE, bookService.getBookByID(b.getBookID()).getBookStatus());
        }
    }
}