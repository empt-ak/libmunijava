/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package library.dao;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import javax.persistence.Persistence;
import javax.persistence.PersistenceException;
import library.entity.Book;
import library.entity.Department;
import library.entity.Ticket;
import library.entity.TicketItem;
import library.entity.TicketItemStatus;
import library.entity.User;
import org.joda.time.DateTime;
import org.testng.Assert;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.Test;

/**
 *
 * @author emptak
 */
public class TicketItemDAOImplTest{

    private TicketItemDAOImpl tdao;
    private BookDAOImpl bookDao;
    private UserDAOImpl userDao;
    private TicketDAOImpl ticketDao;
    public TicketItemDAOImplTest() {
        
    }
    
    @BeforeSuite
    public void setUp() {
        tdao = new TicketItemDAOImpl();
        bookDao = new BookDAOImpl();
        userDao = new UserDAOImpl();
        ticketDao = new TicketDAOImpl();
        tdao.setEm(Persistence.createEntityManagerFactory("library").createEntityManager());
        bookDao.setEm(Persistence.createEntityManagerFactory("library").createEntityManager());
        userDao.setEm(Persistence.createEntityManagerFactory("library").createEntityManager());
        ticketDao.setEm(Persistence.createEntityManagerFactory("library").createEntityManager());
    }
    
   /**
     * Test of createTicketItem method, of class TicketItemDAOImpl.
     */
    @Test
    public void testCreateTicketItem() {
        
        try {
            tdao.createTicketItem(null);
            Assert.fail("IllegalArgumentException should be thrown when trying to create null book");
        } catch (IllegalArgumentException iae) {
            // this is OK
        }

        TicketItem t = new TicketItem();
        Book b = new Book();

        b.setAuthor("pepa");
        b.setTitle("iba 4 entity nie viac!");
        b.setDepartment(Department.ADULT);

        bookDao.createBook(b);
        b = bookDao.getBookByID(1L);
        
        t.setBook(b);
        t.setTicketItemStatus(TicketItemStatus.BORROWED);

        try {
            tdao.createTicketItem(t);
        } catch (Exception e) {
            Assert.fail("Exception was thrown when we are creating a correct object");
        }

        TicketItem t1 = tdao.getTicketItemByID(1L);
        // ID is assigned by the database
        t.setTicketItemID(t1.getTicketItemID());
        Assert.assertTrue(t1.equals(t));

        TicketItem t2 = new TicketItem();
        t2.setBook(null);
        t2.setTicketItemStatus(TicketItemStatus.RESERVATION);
        try {
            tdao.createTicketItem(t2);
        } catch (PersistenceException pe) {
            t2.setBook(b);
            tdao.createTicketItem(t2);
        }

        t2.setTicketItemID(tdao.getTicketItemByID(2L).getTicketItemID());
        Assert.assertTrue(t2.equals(tdao.getTicketItemByID(2L)));

        TicketItem t3 = new TicketItem();
        t3.setBook(b);
        t3.setTicketItemStatus(null);
        try {
            tdao.createTicketItem(t3);
        } catch (PersistenceException pe) {
            // this is OK
        }
        
        TicketItem t4 = new TicketItem();
        t4.setBook(b);
        t4.setTicketItemStatus(TicketItemStatus.RETURNED);
        try {
            tdao.createTicketItem(t4);
        } catch (Exception e) {
            Assert.fail("Error while creating a valid book");
        }
        
    }

    /**
     * Test of getTicketItemByID method, of class TicketItemDAOImpl.
     */
    @Test
    public void testGetTicketItemByID() {
          
        TicketItem t = null;
        Book b = null;
        
        try {
            t = tdao.getTicketItemByID(null);
            Assert.fail("IAE should be thrown");
        } catch (Exception e) {
        }
        try {
            t = tdao.getTicketItemByID(new Long(0));
            Assert.fail("IAE should be thrown");
        } catch (Exception e) {
        }

        try {
            t = tdao.getTicketItemByID(new Long(4));
        } catch (Exception e) {
            Assert.fail("exc should not be thrown on correct ID");
        }
        b = bookDao.getBookByID(1L);

        Assert.assertTrue(new Long(4).equals(t.getTicketItemID()));
        Assert.assertTrue(b.equals(t.getBook()));
        Assert.assertTrue(TicketItemStatus.RETURNED.equals(t.getTicketItemStatus()));
    }
    

    /**
     * Test of updateTicketItem method, of class TicketItemDAOImpl.
     */
    @Test
    public void testUpdateTicketItem() {
        
        TicketItem t = null;
        Book b = null;
        
        try {
            TicketItem tmp = tdao.getTicketItemByID(new Long(1));
            tmp.setBook(b);
            tmp.setTicketItemStatus(TicketItemStatus.RETURNED);
            tdao.updateTicketItem(tmp);
        } catch (Exception e) {
//            Assert.fail("exc should not be thrown on correct ID");
        }
        
        t = tdao.getTicketItemByID(1L);
        
//        Assert.assertTrue(new Long(1).equals(t.getTicketItemID()));
//        Assert.assertTrue(b.equals(t.getBook()));
//        Assert.assertTrue(TicketItemStatus.RETURNED.equals(t.getTicketItemStatus()));
    }

    /**
     * Test of deleteTicketItem method, of class TicketItemDAOImpl.
     */
    @Test
    public void testDeleteTicketItem() {
        /*
        System.out.println("deleteTicketItem");
        TicketItem ticketItem = null;
        TicketItemDAOImpl instance = new TicketItemDAOImpl();
        instance.deleteTicketItem(ticketItem);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
        * */
        
        TicketItem t = tdao.getTicketItemByID(1L);
        System.out.println("Deleting ticketItem: " + t.getBook() + " by an status: "
                + t.getTicketItemStatus());
        Assert.assertNotNull(t);
        tdao.deleteTicketItem(t);
        Assert.assertNull(tdao.getTicketItemByID(6L));
    }

    /**
     * Test of getTicketItemsByTicket method, of class TicketItemDAOImpl.
     */
    @Test
    public void testGetTicketItemsByTicket() {
        /*
        System.out.println("getTicketItemsByTicket");
        Ticket ticket = null;
        TicketItemDAOImpl instance = new TicketItemDAOImpl();
        List expResult = null;
        List result = instance.getTicketItemsByTicket(ticket);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
        * */
        
//        Ticket t = new Ticket();
//        t.setBorrowTime(DateTime.now());
//        Set<TicketItem> list = new HashSet<>();
//        list.add(tdao.getTicketItemByID(4L));
//        User u3 = new User();
//        u3.setPassword("h2");
//        u3.setRealName("jozo fe");
//        u3.setSystemRole("user");
//        u3.setUsername("jozi");
//        userDao.createUser(u3);
//        t.setUser(userDao.getUserByID(1L));
//        t.setTicketItems(list);
//        ticketDao.createTicket(t);
//        
//        List<TicketItem> ticketItems = (List<TicketItem>) tdao.getTicketItemsByTicket(ticketDao.getTicketByID(1L));
//        Assert.assertTrue(ticketItems.size() > 0);
        
    }
   
}
