    /*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package library;

import java.util.ArrayList;
import java.util.List;
import java.util.Properties;
import javax.annotation.Resource;
import javax.ejb.EJB;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.RollbackException;
import javax.transaction.UserTransaction;
import junit.framework.TestCase;
import library.dao.BookDao;
import library.dao.BookDaoLocal;
import library.entity.Book;
import library.utils.LibraryIllegalArgumentException;
import org.apache.openejb.jee.EjbJar;
import org.apache.openejb.jee.StatelessBean;
import org.apache.openejb.jee.jpa.unit.PersistenceUnit;
import org.apache.openejb.junit.ApplicationComposer;
import org.apache.openejb.junit.Configuration;
import org.apache.openejb.junit.Module;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 *
 * @author Nemko
 */
@RunWith(ApplicationComposer.class)
public class BookTest extends TestCase {

    private List<Book> correctBooks = null;
    
    @EJB
    private BookDaoLocal bookDao;
    @Resource
    private UserTransaction userTransaction;
    @PersistenceContext
    private EntityManager entityManager;

    @Module
    public PersistenceUnit persistence() {
        PersistenceUnit unit = new PersistenceUnit("libraryPU");
        unit.setJtaDataSource("TestDB");
        unit.setNonJtaDataSource("TestDBUnmanaged");
        unit.getClazz().add(Book.class.getName());
        unit.setProperty("openjpa.jdbc.SynchronizeMappings", "buildSchema(ForeignKeys=true)");
        return unit;
    }

    @Module
    public EjbJar beans() {
        EjbJar ejbJar = new EjbJar("book-beans");
        ejbJar.addEnterpriseBean(new StatelessBean(BookDao.class));
        return ejbJar;
    }

    @Configuration
    public Properties config() throws Exception {
        Properties p = new Properties();
        p.put("bookDatabase", "new://Resource?type=DataSource");
        p.put("bookDatabase.JdbcDriver", "org.hsqldb.jdbcDriver");
        p.put("bookDatabase.JdbcUrl", "jdbc:hsqldb:mem:bookdb");
        return p;
    }

    @Before
    public void init() {

        correctBooks = new ArrayList<Book>();
        Book cb1 = createBook("nazov1", "autor1");
        Book cb2 = createBook("nazov2", "autor2");
        Book cb3 = createBook("nazov3", "autor3");

        correctBooks.add(cb1);
        correctBooks.add(cb2);
        correctBooks.add(cb3);

    }

    @Test
    public void test() throws Exception {

        userTransaction.begin();

        try {
            entityManager.persist(createBook("Quentin Tarantino", "Reservoir Dogs"));
            entityManager.persist(createBook("Joel Coen", "Fargo"));
            entityManager.persist(createBook("Joel Coen", "The Big Lebowski"));

            List<Book> list = bookDao.getAllBooks();
            assertEquals("List.size()", 3, list.size());

            for (Book book : list) {
                bookDao.deleteBook(book);
            }

            assertEquals("Movies.getMovies()", 0, bookDao.getAllBooks().size());

            bookDao.callSetRollbackOnly();
        } finally {
            try {
                userTransaction.commit();
                fail("A RollbackException should have been thrown");
            } catch (RollbackException e) {
                // Pass
            }
        }
    }

    @Test
    public void testCreateAndGetBook() throws Exception {

        userTransaction.begin();

        try {
            //=================
            // TEST wrong books
            //=================
            try {
                // create null book
                bookDao.createBook(null);
                fail("LibraryIllegalArgumentException should be thrown when creating null Book");
            } catch (LibraryIllegalArgumentException iae) {
                //ok
            }

            //=================
            // TEST correct book
            //================= 

            try {
                bookDao.createBook(correctBooks.get(0));
                //ok
            } catch (Exception e) {
                fail("No exception should be thrown when creating correct book : " + e);
            }

            //=================
            // TEST get BookDTO
            //================= 
            try {
                bookDao.getBookByID(null);
                fail("LibraryIllegalArgumentException should be thrown when getting book by null id");
            } catch (LibraryIllegalArgumentException iae) {
                //ok
            }
            try {
                bookDao.getBookByID(new Long(0));
                fail("LibraryIllegalArgumentException should be thrown when getting book by his id that is out of range");
            } catch (LibraryIllegalArgumentException iae) {
                //ok
            }
            Book saved = null;
            try {
                saved = bookDao.getBookByID(correctBooks.get(0).getId()); // should be 5
            } catch (Exception e) {
            }
            assertEquals("Based on equals method books are not same, they dont have same ID", correctBooks.get(0), saved);
            assertDeepEquals(correctBooks.get(0), saved);

            bookDao.callSetRollbackOnly();
        } finally {
            try {
                userTransaction.commit();
                fail("A RollbackException should have been thrown");
            } catch (RollbackException e) {
                // Pass
            }
        }

    }

    /**
     * Test of updateBook method, of class BookService.
     */
    @Test
    public void testUpdateBook() throws Exception {

        userTransaction.begin();

        try {

            Book b = correctBooks.get(1);
            bookDao.createBook(b);
            try {
                bookDao.updateBook(null);
                fail("LibraryIllegalArgumentException should be thrown when updating null book");
            } catch (LibraryIllegalArgumentException iae) {
                //ok
            }
            b.setTitle(null);
            try {
                bookDao.updateBook(b);
                fail("LibraryIllegalArgumentException should be thrown when updating book without title");
            } catch (LibraryIllegalArgumentException iae) {
                //ok
            }
            b.setTitle("dakyNazov");
            b.setAuthor(null);
            try {
                bookDao.updateBook(b);
                fail("LibraryIllegalArgumentException should be thrown when updating book without author");
            } catch (LibraryIllegalArgumentException iae) {
                //ok
            }
            b.setAuthor("zmenenyAuthor");

            try {
                bookDao.updateBook(b);
            } catch (LibraryIllegalArgumentException iae) {
                fail("No exception should be thrown when updating correct user");
            }


            Book buuk = bookDao.getBookByID(b.getId());

            assertDeepEquals(b, buuk);

            bookDao.callSetRollbackOnly();
        } finally {
            try {
                userTransaction.commit();
                fail("A RollbackException should have been thrown");
            } catch (RollbackException e) {
                // Pass
            }
        }
    }

    /**
     * Test of deleteBook method, of class BookService.
     */
    @Test
    public void testDeleteBook() throws Exception {

        userTransaction.begin();

        try {

            bookDao.createBook(correctBooks.get(0));
            bookDao.createBook(correctBooks.get(1));
            bookDao.createBook(correctBooks.get(2));


            Book toDelete = correctBooks.get(2);

            try {
                bookDao.deleteBook(null);
                fail("LibraryIllegalArgumentException should be thrown when deleting null book");
            } catch (LibraryIllegalArgumentException iae) {
                // ok
            }
            try {
                bookDao.deleteBook(toDelete);
            } catch (LibraryIllegalArgumentException iae) {
                fail("No Exception should be thrown when deleting correct Book " + iae.getMessage());
            }
            List<Book> list = bookDao.getAllBooks();
            assertEquals("v db nie su 2 knihy", 2, list.size());
            assertNotSame("a", correctBooks.get(1), list.get(0));
            assertNotSame("b", correctBooks.get(2), list.get(1));

            bookDao.callSetRollbackOnly();
        } finally {
            try {
                userTransaction.commit();
                fail("A RollbackException should have been thrown");
            } catch (RollbackException e) {
                // Pass
            }
        }

    }

    /**
     * Test of getBookByID method, of class BookService.
     */
    /**
     * Test of getAllBooks method, of class BookService.
     */
    @Test
    public void testGetAllBooks() throws Exception {

        userTransaction.begin();

        try {

            try {
                bookDao.createBook(correctBooks.get(0));
                bookDao.createBook(correctBooks.get(1));
                bookDao.createBook(correctBooks.get(2));

            } catch (Exception e) {
                fail("No exception should be thrown");
            }

            List<Book> buuks = bookDao.getAllBooks();



            assertEquals("There should be 3 books inside database but they are not", 3, buuks.size());
            assertDeepEquals(correctBooks, buuks);

            bookDao.callSetRollbackOnly();
        } finally {
            try {
                userTransaction.commit();
                fail("A RollbackException should have been thrown");
            } catch (RollbackException e) {
                // Pass
            }
        }
    }

    /**
     * Test of getBooksByAuthor method, of class BookService.
     */
    @Test
    public void testSearchBooksByTitle() throws Exception {

        userTransaction.begin();

        try {

            bookDao.createBook(correctBooks.get(0));
            bookDao.createBook(correctBooks.get(1));
            bookDao.createBook(correctBooks.get(2));

            try {
                bookDao.searchBooksByTitle(null);
                fail("LibraryIllegalArgumentException should be thrown when getting book by null title");
            } catch (LibraryIllegalArgumentException iae) {
                //ok
            }

            try {
                bookDao.searchBooksByTitle("");
                fail("LibraryIllegalArgumentException should be thrown when getting book by zero length title");
            } catch (LibraryIllegalArgumentException iae) {
                //ok
            }

            List<Book> list = null;

            try {
                list = bookDao.searchBooksByTitle("zov");
            } catch (Exception e) {
                fail("No exception should be thrown when getting book by correct possible title that is created inside database");
            }

            assertEquals("Search did not return correct number of books", 3, list.size());
            assertTrue("This book has not been found", list.contains(correctBooks.get(0)));
            assertTrue("This book has not been found", list.contains(correctBooks.get(1)));
            assertTrue("This book has not been found", list.contains(correctBooks.get(2)));

            bookDao.callSetRollbackOnly();
        } finally {
            try {
                userTransaction.commit();
                fail("A RollbackException should have been thrown");
            } catch (RollbackException e) {
                // Pass
            }
        }
    }

    @Test
    public void testGetBooksByAuthor() throws Exception {

        userTransaction.begin();

        try {

            bookDao.createBook(correctBooks.get(0));
            bookDao.createBook(correctBooks.get(1));
            bookDao.createBook(correctBooks.get(2));

            try {
                bookDao.getBooksByAuthor(null);
                fail("LibraryIllegalArgumentException should be thrown when getting book by null author");
            } catch (LibraryIllegalArgumentException iae) {
                //ok
            }

            try {
                bookDao.getBooksByAuthor("");
                fail("LibraryIllegalArgumentException should be thrown when getting book by zero length author");
            } catch (LibraryIllegalArgumentException iae) {
                //ok
            }

            List<Book> list = null;

            try {
                list = bookDao.getBooksByAuthor("tor");
            } catch (Exception e) {
                fail("No exception should be thrown when getting book by correct possible author that is created inside database");
            }

            assertEquals("Search did not return correct number of books", 3, list.size());
            assertTrue("This book has not been found", list.contains(correctBooks.get(0)));
            assertTrue("This book has not been found", list.contains(correctBooks.get(1)));
            assertTrue("This book has not been found", list.contains(correctBooks.get(2)));

            bookDao.callSetRollbackOnly();
        } finally {
            try {
                userTransaction.commit();
                fail("A RollbackException should have been thrown");
            } catch (RollbackException e) {
                // Pass
            }
        }
    }

    private Book createBook(String title, String author) {
        Book b = new Book();
        b.setTitle(title);
        b.setAuthor(author);

        return b;
    }

    private void assertDeepEquals(Book expected, Book actual) {
        assertEquals("Titles are not same", expected.getTitle(), actual.getTitle());
        assertEquals("Authors are not same", expected.getAuthor(), actual.getAuthor());

    }

    private void assertDeepEquals(List<Book> expected, List<Book> actual) {
        for (int i = 0; i < expected.size(); i++) {
            assertEquals("Titles are not same", expected.get(i).getTitle(), actual.get(i).getTitle());
            assertEquals("Authors are not same", expected.get(i).getAuthor(), actual.get(i).getAuthor());
        }
    }
}
