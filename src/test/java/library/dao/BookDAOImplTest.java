/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package library.dao;

import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.Persistence;
import javax.persistence.PersistenceException;
import library.entity.Book;
import library.entity.Department;
import org.springframework.beans.factory.annotation.Autowired;
import org.testng.Assert;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.Test;

/**
 *
 * @author Nemcek
 */
//@RunWith(SpringJUnit4ClassRunner.class)
//@TransactionConfiguration(defaultRollback=false)
//@ContextConfiguration(locations = {"file:src/main/resources/root-context.xml"})
public class BookDAOImplTest {

    private BookDAOImpl bookDao;
    
//    private EntityManager entityManager;

    public BookDAOImplTest() {
    }

//    @BeforeClass
//    public static void setUpClass() {
//    }
//
//    @AfterClass
//    public static void tearDownClass() {
//    }
//
    @BeforeSuite
    public void setUp() {
        bookDao = new BookDAOImpl();
        bookDao.setEm(Persistence.createEntityManagerFactory("library").createEntityManager());
    }
//
//    @After
//    public void tearDown() {
//    }

    /**
     * Test of createBook method, of class BookDAOImpl.
     */
    @Test(groups = "a")
    public void testCreateAndGetBook() {
        try {
            bookDao.createBook(null);
            Assert.fail("IllegalArgumentException should be thrown when trying to create null book");
        } catch (IllegalArgumentException iae) {
            // this is OK
        }

        Book b = new Book();

        b.setAuthor("pepa");
        b.setTitle("iba 4 entity nie viac!");
        b.setDepartment(Department.ADULT);

        try {
            bookDao.createBook(b);
        } catch (Exception e) {
            Assert.fail("Exception was thrown when we are creating a correct object");
        }

        Book b1 = bookDao.getBookByID(1L);
        // ID is assigned by the database
        b.setBookID(b1.getBookID());
        Assert.assertTrue(b1.equals(b));

        Book b2 = new Book();
        b2.setAuthor(null);
        b2.setDepartment(Department.ADULT);
        b2.setTitle("Title");
        try {
            bookDao.createBook(b2);
        } catch (PersistenceException pe) {
            b2.setAuthor("Author");
            bookDao.createBook(b2);
        }

        b2.setBookID(bookDao.getBookByID(3L).getBookID());
        Assert.assertTrue(b2.equals(bookDao.getBookByID(3L)));

        Book b3 = new Book();
        b3.setAuthor("Author");
        b3.setDepartment(Department.KIDS);
        b3.setTitle(null);
        try {
            bookDao.createBook(b3);
        } catch (PersistenceException pe) {
            // this is OK
        }

        Book b4 = new Book();
        b4.setAuthor("Author");
        b4.setDepartment(null);
        b4.setTitle("Title");
        try {
            bookDao.createBook(b4);
        } catch (PersistenceException pe) {
            // this is OK
        }
        
        Book b5 = new Book();
        b5.setAuthor("GRSc. Alexander Jaray");
        b5.setDepartment(Department.KIDS);
        b5.setTitle("Alexander JARAY, burac axiom!");
        try {
            bookDao.createBook(b5);
        } catch (Exception e) {
            Assert.fail("Error while creating a valid book");
        }

    }

    /**
     * Test of getBookByID method, of class BookDAOImpl.
     */
    @Test(groups = "b",dependsOnGroups="a")
    public void testGetBookByID() {
        Book b = null;
        try {
            b = bookDao.getBookByID(null);
            Assert.fail("IAE should be thrown");
        } catch (Exception e) {
        }
        try {
            b = bookDao.getBookByID(new Long(0));
            Assert.fail("IAE should be thrown");
        } catch (Exception e) {
        }

        try {
            b = bookDao.getBookByID(new Long(1));
        } catch (Exception e) {
            Assert.fail("exc should not be thrown on correct ID");
        }

        Assert.assertTrue(new Long(1).equals(b.getBookID()));
        Assert.assertTrue("iba 4 entity nie viac!".equals(b.getTitle()));
        Assert.assertTrue("pepa".equals(b.getAuthor()));
        Assert.assertTrue(Department.ADULT.equals(b.getDepartment()));
    }

    /**
     * Test of updateBook method, of class BookDAOImpl.
     */
    @Test(groups = "c",dependsOnGroups="b")
    public void testUpdateBook() {
        
        Book b = null;
        
        try {
            Book tmp = bookDao.getBookByID(new Long(1));
            tmp.setTitle("Male vevericie pribehy");
            tmp.setAuthor("Chip & Dale");
            tmp.setDepartment(Department.KIDS);
            bookDao.updateBook(tmp);
        } catch (Exception e) {
            Assert.fail("exc should not be thrown on correct ID");
        }
        
        b = bookDao.getBookByID(1L);
        
        Assert.assertTrue(new Long(1).equals(b.getBookID()));
        Assert.assertTrue("Male vevericie pribehy".equals(b.getTitle()));
        Assert.assertTrue("Chip & Dale".equals(b.getAuthor()));
        Assert.assertTrue(Department.KIDS.equals(b.getDepartment()));
    }

    /**
     * Test of deleteBook method, of class BookDAOImpl.
     */
    @Test(groups = "d",dependsOnGroups="c")
    public void testDeleteBook() {
        Book book = bookDao.getBookByID(6L);
        System.out.println("Deleting book: " + book.getTitle() + " by an author: "
                + book.getAuthor());
        Assert.assertNotNull(book);
        bookDao.deleteBook(book);
        Assert.assertNull(bookDao.getBookByID(6L));
    }

    /**
     * Test of getAllBooks method, of class BookDAOImpl.
     */
    @Test(groups = "e",dependsOnGroups="d")
    public void testGetBooks() {
        
        Assert.assertTrue(bookDao.getAllBooks().size() > 0);

        System.out.println("All books in the database: ");

        for (Book book : bookDao.getAllBooks()) {
            System.out.println(book.toString());
        }
        
        List<Book> books = (List<Book>) bookDao.searchBooksByTitle("Male vevericie pribehy");
        Assert.assertTrue(books.size() > 0);
        
        books = (List<Book>) bookDao.getBooksByDepartment(Department.ADULT);
        Assert.assertTrue(books.size() > 0);
        
        books = (List<Book>) bookDao.getBooksByAuthor("Chip & Dale");
        Assert.assertTrue(books.size() > 0);

    }

}
