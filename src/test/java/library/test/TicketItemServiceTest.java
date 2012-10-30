/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package library.test;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import library.entity.enums.BookStatus;
import library.entity.enums.Department;
import library.entity.enums.TicketItemStatus;
import library.entity.dto.Book;
import library.entity.dto.Ticket;
import library.entity.dto.TicketItem;
import library.entity.dto.User;
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
 * @author Szalai
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations ={"file:src/main/resources/spring/applicationContext-test.xml"})
@TestExecutionListeners({DirtiesContextTestExecutionListener.class, DependencyInjectionTestExecutionListener.class})
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
public class TicketItemServiceTest 
{
    @Autowired
    private UserService userService;
    @Autowired
    private BookService bookService;
    @Autowired
    private TicketItemService ticketItemService;
    @Autowired
    private TicketService ticketService;
    
    private List<Book> books;
    private List<TicketItem> correct;
    private List<TicketItem> incorrect;
    
    @Before
    public void init() 
    {
        // now we need some books for various testing
        books = new ArrayList<>(3);
        Book b1 = createBook("pepazdepa", "ach jaj", Department.ADULT, BookStatus.AVAILABLE);
        Book b2 = createBook("pepazdepa", "xexe", Department.ADULT, BookStatus.AVAILABLE);
        Book b3 = createBook("pepazdepa", "hlavninadrazi", Department.ADULT, BookStatus.AVAILABLE);
        books.add(b1);
        books.add(b2);
        books.add(b3);        
        bookService.createBook(b1);
        bookService.createBook(b2);
        bookService.createBook(b3);
        
        //now lets create some ticketitems
        correct = new ArrayList<>(3);
        TicketItem cti1 = createTicketItem(b1, TicketItemStatus.BORROWED);
        TicketItem cti2 = createTicketItem(b2, TicketItemStatus.BORROWED);
        TicketItem cti3 = createTicketItem(b3, TicketItemStatus.RETURNED);
        correct.add(cti1);
        correct.add(cti2);
        correct.add(cti3);
        
        incorrect = new ArrayList<>(2);
        TicketItem wti1 = createTicketItem(null, TicketItemStatus.BORROWED);
        TicketItem wti2 = createTicketItem(b3, null);        
        incorrect.add(wti1);
        incorrect.add(wti2);
    }
    

    /**
     * Test of createTicketItem method, of class TicketItemService.
     */
    @Test
    public void testCreateAndGetTicketItem() 
    {
        try
        {   //null ti
            ticketItemService.createTicketItem(null);
            fail("IllegalArgumentException should be thrown when creating null ticketitem");
        }
        catch(IllegalArgumentException iae)
        {
            // ok
        }
        try
        {   // ti without set book
            ticketItemService.createTicketItem(incorrect.get(0));
            fail("IllegalArgumentException should be thrown when creating ticketitem without book");
        }
        catch(IllegalArgumentException iae)
        {
            // ok
        }

        try
        {   // ti without status
            ticketItemService.createTicketItem(incorrect.get(1));
            fail("IllegalArgumentException should be thrown when creating ticketitem without status");
        }
        catch(IllegalArgumentException iae)
        {
            // ok
        }
        
        try
        {
           ticketItemService.createTicketItem(correct.get(0));
        }
        catch(Exception e)
        {   // no exception should ne thrown
            fail("No exception should be thrown when creating correct ticketitem. Following exception was thrown: "+e);
        }
        
        try
        {
            ticketItemService.getTicketItemByID(null);
            fail("IllegalArgumentException should be thrown when getting ticketitem by null id");
        }
        catch(IllegalArgumentException iae)
        {
            // ok
        }
        
        try
        {
            ticketItemService.getTicketItemByID(new Long(0));
            fail("IllegalArgumentException should be thrown when getting ticket item with id that is out of range");
        }
        catch(IllegalArgumentException iae)
        {
            // ok
        }
        
        TicketItem ti = null;
        try
        {
            ti = ticketItemService.getTicketItemByID(correct.get(0).getTicketItemID()); // should be 3
        }
        catch(Exception e)
        {   // should not occur
            fail("No exception should be thrown when getting ticket item by correct id. Following exception was thrown: "+e);
        }
        
        assertEquals("Returned ticket item is not as expected", correct.get(0),ti);
        assertDeepEquals(correct.get(0), ti);  
    }

    /**
     * Test of updateTicketItem method, of class TicketItemService.
     */
    @Test
    public void testUpdateTicketItem() 
    {
        ticketItemService.createTicketItem(correct.get(0));
        try
        {
            ticketItemService.updateTicketItem(null);
            fail("IllegalArgumentException should be thrown when updating null ticketitem");
        }
        catch(IllegalArgumentException iae)
        {
            //ok
        }
        try
        {   // update without set its id
            ticketItemService.createTicketItem(new TicketItem());
            fail("IllegalArgumentException should be thrown when updating ticketitem with unset id");
        }
        catch(IllegalArgumentException iae)
        {
            //ok
        }
        TicketItem tic = correct.get(0);
        tic.setBook(books.get(2));
        tic.setTicketItemStatus(TicketItemStatus.RESERVATION);
        try
        {
            ticketItemService.updateTicketItem(tic);
        }
        catch(Exception e)
        {
            fail("No exception should be thrown when updating correct ticketItem. Following exception was thrown: "+e);
        }
        
        TicketItem returned = ticketItemService.getTicketItemByID(tic.getTicketItemID());
        
        assertEquals(tic, returned);
        assertDeepEquals(tic, returned);
        
    }

    /**
     * Test of deleteTicketItem method, of class TicketItemService.
     */
    @Test
    public void testDeleteTicketItem() 
    {
        ticketItemService.createTicketItem(correct.get(0));
        ticketItemService.createTicketItem(correct.get(1));
        ticketItemService.createTicketItem(correct.get(2));
        
        try
        {
            ticketItemService.deleteTicketItem(null);
            fail("IllegalArgumentException should be thrown when deleting null ticketitem");
        }
        catch(IllegalArgumentException iae)
        {
            //ok
        }        
        try
        {   // we try to delete ticketitem without id
            ticketItemService.deleteTicketItem(new TicketItem());
            fail("IllegalArgumentException should be thrown when deleting ticketitem without set id");
        }
        catch(IllegalArgumentException iae)
        {
            //ok
        }
        
        try
        {
            ticketItemService.deleteTicketItem(correct.get(1));
        }
        catch(Exception e)
        {
            fail("No exception should be thrown when deleting correct ticket. Following exception was thrown: " +e);
        }
        
        TicketItem toBeFound = null;
        try
        {
            toBeFound = ticketItemService.getTicketItemByID(new Long(2));
        }
        catch(Exception e)
        {
            fail("No exception should be thrown. Following one was: "+e);
        }
        
        assertNull("Deleted book is still inside database ",toBeFound);
    }
    

    /**
     * Test of getTicketItemsByTicket method, of class TicketItemService.
     */
    @Test
    public void testGetTicketItemsByTicket() 
    {       
        ticketItemService.createTicketItem(correct.get(0));
        ticketItemService.createTicketItem(correct.get(1));
        ticketItemService.createTicketItem(correct.get(2));
        
        User cUser = createUser("heslo", "realnemeno", "USER", "login");
        userService.createUser(cUser);
        
        
        Ticket ticket = createTicket(cUser, new DateTime(2012, 10, 7, 12, 00), new ArrayList<>(correct));
        ticketService.createTicket(ticket);
        
        List<TicketItem> returned = null;
        try
        {   // test null
            ticketItemService.getTicketItemsByTicket(null);
            fail("IllegalArgumentException should be thrown when getting ticketitems by null ticket");
        }
        catch(IllegalArgumentException iae)
        {
            // ok
        }
        try
        {   // get by null id
            ticketItemService.getTicketItemsByTicket(new Ticket());
            fail("IllegalArgumentException should be thrown when getting ticketitems by ticket that has set no id");
        }
        catch(IllegalArgumentException iae)
        {
            //ok
        }
        try
        {
            returned = ticketItemService.getTicketItemsByTicket(ticket);
        }
        catch(Exception e)
        {
            fail("No exception should be thrown when getting ticketitems by correct ticket. Following one was thrown : "+e);
        }
        // set has elements inside randomly ordered, therefore we have to sort both collection for further testing
        java.util.Collections.sort(returned, tIComparator);
        java.util.Collections.sort(correct, tIComparator);
        
        assertEquals("There should be 3 ticketitems",3,returned.size());
        assertDeepEquals(correct, returned);
        
    }
    
    private Ticket createTicket(User user, DateTime borrowtime, ArrayList<TicketItem> ticketItems)
    {
        Ticket t = new Ticket();
        t.setUser(user);
        t.setBorrowTime(borrowtime);
        t.setTicketItems(ticketItems);
        
        return t;
    }
    
    private User createUser(String password,String realname,String systemRole,String username)
    {
        User u = new User();
        u.setPassword(password);
        u.setRealName(realname);
        u.setSystemRole(systemRole);
        u.setUsername(username);
        return u;
    }
    
    private TicketItem createTicketItem(Book b,TicketItemStatus status)
    {
        TicketItem ti = new TicketItem();
        ti.setBook(b);
        ti.setTicketItemStatus(status);
        
        return ti;
    }
    
    private Book createBook(String author, String title, Department department, BookStatus status)
    {
        Book b = new Book();
        b.setAuthor(author);
        b.setTitle(title);
        b.setDepartment(department);
        b.setBookStatus(status);
        
        return b;
    }
    
    private void assertDeepEquals(TicketItem expected, TicketItem actual)
    {
        assertEquals("Books for given ticketitem are not same",expected.getBook(),actual.getBook());
        assertEquals("Statuses for given ticketitem are not same", expected.getTicketItemStatus(),actual.getTicketItemStatus());
    }
    
    private void assertDeepEquals(List<TicketItem> expected, List<TicketItem> actual)
    {
        for(int i =0;i<expected.size();i++)
        {
            assertEquals("Books for given ticketitem are not same",expected.get(i).getBook(),actual.get(i).getBook());
            assertEquals("Statuses for given ticketitem are not same", expected.get(i).getTicketItemStatus(),actual.get(i).getTicketItemStatus());            
        }        
    }
    
    
    private static java.util.Comparator<TicketItem> tIComparator = new Comparator<TicketItem>()
    {
        @Override
        public int compare(TicketItem ti1,TicketItem ti2)
        {
            return Long.valueOf(ti1.getTicketItemID()).compareTo(Long.valueOf(ti2.getTicketItemID()));
        }      
    };
}
