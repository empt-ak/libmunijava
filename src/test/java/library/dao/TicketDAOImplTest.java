/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package library.dao;


import java.util.AbstractSet;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import javax.persistence.Persistence;
import javax.persistence.PersistenceException;
import library.entity.Ticket;
import library.entity.User;
import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.testng.annotations.Test;
import junit.framework.Assert;
import library.entity.Book;
import library.entity.Department;
import library.entity.TicketItem;
import library.entity.TicketItemStatus;
import org.testng.annotations.BeforeSuite;

/**
 *
 * @author Gaspar
 */

public class TicketDAOImplTest {
    
    
    private TicketDAOImpl tDAO;
    private UserDAOImpl userDAO;
    private TicketItemDAOImpl tidao;
    private BookDAOImpl bookDao;
    
    public TicketDAOImplTest() {
    }
     @BeforeSuite
    public void setUp() {
        tDAO = new TicketDAOImpl();
        userDAO = new UserDAOImpl();
        tidao = new TicketItemDAOImpl();
        tDAO.setEm(Persistence.createEntityManagerFactory("library").createEntityManager());
        userDAO.setEm(Persistence.createEntityManagerFactory("library").createEntityManager());
        tidao.setEm(Persistence.createEntityManagerFactory("library").createEntityManager());
        bookDao = new BookDAOImpl();
        bookDao.setEm(Persistence.createEntityManagerFactory("library").createEntityManager());
    }
//    @BeforeClass
//    public static void setUpClass() {
//    }
//    
//    @AfterClass
//    public static void tearDownClass() {
//    }
//    
//    @Before
//    public void setUp() {
//    }
//    
//    @After
//    public void tearDown() {
//    }

    /**
     * Test of createLoanTicket method, of class TicketDAOImpl.
     */
    @Test(groups="a")
    public void testCreateLoanTicket() {
//        User u = new User();
//        u.setUsername("user");
//        u.setPassword("pass");
//        u.setRealName("Userovic");
//        u.setSystemRole("USER");
//        
//        Ticket ticket = new Ticket();
//        ticket.setBorrowTime(DateTime.now());
//        ticket.setUser(null);
//        loanTicketService.createLoanTicket(u);
        
        
        try {
            tDAO.createTicket(null);
            Assert.fail("IllegalArgumentException should be thrown when trying to create null book");
        } catch (IllegalArgumentException iae) {
            // this is OK
        }

        Ticket t = new Ticket();
        User u = createUser("h1", "jozo fi", "USER", "jozo");
        
        userDAO.createUser(u);
      
        
        Book b = new Book();

        b.setAuthor("pepa");
        b.setTitle("iba 4 entity nie viac!");
        b.setDepartment(Department.ADULT);

        bookDao.createBook(b);
        TicketItem ti = new TicketItem();
        ti.setBook(b);
        ti.setTicketItemStatus(TicketItemStatus.BORROWED);
        tidao.createTicketItem(ti);
        
        DateTime date = new DateTime(2012, 10, 5, 12, 31);
        Set<TicketItem> ticketItems = new HashSet<TicketItem>();
        ticketItems.add(ti);
        
        
        

        t.setBorrowTime(date);
        t.setUser(u);
        t.setTicketItems(ticketItems);
        
        //System.out.println(t);

        try {
            tDAO.createTicket(t);
        } catch (Exception e) {
            Assert.fail("Exception was thrown when we are creating a correct object");
        }

        Ticket t1 = tDAO.getTicketByID(1L);
        // ID is assigned by the database
        t.setTicketID(t1.getTicketID());
        Assert.assertTrue(t1.equals(t));

        
    }

    /**
     * Test of getLoanTicketByID method, of class TicketDAOImpl.
     */
    @Test(groups="b",dependsOnGroups="a")
    public void testGetLoanTicketByID() {
        
        Ticket t = null;
        
        
        
        try {
            t = tDAO.getTicketByID(null);
            Assert.fail("IAE should be thrown");
        } catch (Exception e) {
        }       

        try {
            t = tDAO.getTicketByID(new Long(1));
        } catch (Exception e) {
            Assert.fail("exc should not be thrown on correct ID");
        }
        System.out.println(t);
        Assert.assertTrue(new Long(1).equals(t.getTicketID()));
        
        
    }

    /**
     * Test of updateLoanTicket method, of class TicketDAOImpl.
     */
    @Test(groups="c",dependsOnGroups="b")
    public void testUpdateLoanTicket() {
        
        Ticket t = null;
        try{
            t = tDAO.getTicketByID(new Long(1));
        }catch(Exception e){}
        
        System.out.println(t);
            
        t.setBorrowTime(new DateTime(2012, 11, 5, 12, 31));
        
        
        
        try{
            tDAO.updateTicket(t);
        }catch(Exception e){}
        
        Assert.assertTrue(new Long(1).equals(t.getTicketID()));
        
               
        
        Assert.assertEquals(new DateTime(2012, 11, 5, 12, 31), tDAO.getTicketByID(new Long(1)).getBorrowTime());
        
        
    }
    
    @Test(groups="d",dependsOnGroups="c")
    public void testGetAllTicketsForUser(){
        User u = userDAO.getUserByID(new Long(1));
        
        try{
            tDAO.getAllTicketsForUser(null);
            Assert.fail();
        }catch(IllegalArgumentException iae){
            
        }
        List<Ticket> list = null;
        try{
            list= tDAO.getAllTicketsForUser(u);
        }catch(Exception e){
            Assert.fail();
        }
        
        Assert.assertEquals(1, list.size());
    }
    
    //@Test(groups="e",dependsOnGroups="d")
    public void testPeriod(){
        DateTime from1 = new DateTime(2012, 11, 4, 12, 31);
        DateTime to = new DateTime(2012, 11, 6, 12, 31);
        User u = userDAO.getUserByID(new Long(1));
        List<Ticket> list =null;
        try{
            list = tDAO.getTicketsInPeriodForUser(from1, to, u);
        }catch(Exception e){}
        
        Assert.assertEquals(1, list.size());
    }

    /**
     * Test of deleteLoanTicket method, of class TicketDAOImpl.
     */
    //@Test(groups="d",dependsOnGroups="c")
    public void testDeleteLoanTicket() {
        
        Ticket t= null;
        try{
            tDAO.deleteTicket(null);
            Assert.fail();
        }catch(Exception e){
            
        }
        
        t = tDAO.getTicketByID(new Long(1));
        
        try{
            tDAO.deleteTicket(t);
        }catch(Exception e){
            Assert.fail();
        }
        
        Assert.assertEquals(null, tDAO.getTicketByID(new Long(1)));
    }
    
    private User createUser(String password,String realname,String systemRole,String username){
        User u = new User();
        u.setPassword(password);
        u.setRealName(realname);
        u.setSystemRole(systemRole);
        u.setUsername(username);
        return u;
    }
}
