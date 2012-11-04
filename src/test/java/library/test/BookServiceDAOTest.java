/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package library.test;

import java.util.ArrayList;
import java.util.List;
import library.entity.dto.BookDTO;
import library.entity.enums.BookStatus;
import library.entity.enums.Department;
import library.service.BookService;
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
 * @author Nemcek
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"file:src/main/resources/spring/applicationContext-test.xml"})
@TestExecutionListeners({DirtiesContextTestExecutionListener.class, DependencyInjectionTestExecutionListener.class})
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
public class BookServiceDAOTest {

    private List<BookDTO> correctBooks = null;
    private List<BookDTO> wrongBooks = null;
    @Autowired
    private BookService bookService;

    @Before
    public void init() {
        correctBooks = new ArrayList<>();
        BookDTO cb1 = createBook("nazov1", "autor1", Department.SCIENTIFIC, BookStatus.AVAILABLE);
        BookDTO cb2 = createBook("nazov2", "autor2", Department.ADULT, BookStatus.AVAILABLE);
        BookDTO cb3 = createBook("nazov3", "autor3", Department.KIDS, BookStatus.AVAILABLE);

        correctBooks.add(cb1);
        correctBooks.add(cb2);
        correctBooks.add(cb3);



        wrongBooks = new ArrayList<>();
        BookDTO wb1 = createBook(null, "autor1", Department.SCIENTIFIC, BookStatus.AVAILABLE);
        BookDTO wb2 = createBook("nazov2", null, Department.ADULT, BookStatus.AVAILABLE);
        BookDTO wb3 = createBook("nazov3", "autor3", null, BookStatus.AVAILABLE);
        wrongBooks.add(wb1);
        wrongBooks.add(wb2);
        wrongBooks.add(wb3);

    }

    /**
     * Test of createBook method, of class BookService.
     */
    @Test
    public void testCreateAndGetBook() {
        //=================
        // TEST wrong books
        //=================
        try {
            // create null book
            bookService.createBook(null);
            fail("IllegalArgumentException should be thrown when creating null Book");
        } catch (IllegalArgumentException iae) {
            //ok
        }
        try {
            // create book without set title
            bookService.createBook(wrongBooks.get(0));
            fail("IllegalArgumentException should be thrown when creating user without title");
        } catch (IllegalArgumentException iae) {
            //ok
        }
        try {
            // create book without set author
            bookService.createBook(wrongBooks.get(1));
            fail("IllegalArgumentException should be thrown when creating user without author");
        } catch (IllegalArgumentException iae) {
            //ok
        }
        try {
            // create book without set department
            bookService.createBook(wrongBooks.get(2));
            fail("IllegalArgumentException should be thrown when creating user without department");
        } catch (IllegalArgumentException iae) {
            //ok
        }


        //=================
        // TEST correct book
        //================= 

        try {
            bookService.createBook(correctBooks.get(0));
            //ok
        } catch (Exception e) {
            fail("No exception should be thrown when creating correct book : " + e);
        }

        //=================
        // TEST get BookDTO
        //================= 
        try {
            bookService.getBookByID(null);
            fail("IllegalArgumentException should be thrown when getting book by null id");
        } catch (IllegalArgumentException iae) {
            //ok
        }
        try {
            bookService.getBookByID(new Long(0));
            fail("IllegalArgumentException should be thrown when getting book by his id that is out of range");
        } catch (IllegalArgumentException iae) {
            //ok
        }
        BookDTO saved = null;
        try {
            saved = bookService.getBookByID(correctBooks.get(0).getBookID()); // should be 5
        } catch (Exception e) {
        }
        assertEquals("Based on equals method books are not same, they dont have same ID", correctBooks.get(0), saved);
        assertDeepEquals(correctBooks.get(0), saved);
    }

    /**
     * Test of updateBook method, of class BookService.
     */
    @Test
    public void testUpdateBook() {
        BookDTO b = correctBooks.get(1);
        bookService.createBook(b);
        try {
            bookService.updateBook(null);
            fail("IllegalArgumentException should be thrown when updating null book");
        } catch (IllegalArgumentException iae) {
            //ok
        }
        b.setTitle(null);
        try {
            bookService.updateBook(b);
            fail("IllegalArgumentException should be thrown when updating book without title");
        } catch (IllegalArgumentException iae) {
            //ok
        }
        b.setTitle("dakyNazov");
        b.setAuthor(null);
        try {
            bookService.updateBook(b);
            fail("IllegalArgumentException should be thrown when updating book without author");
        } catch (IllegalArgumentException iae) {
            //ok
        }
        b.setAuthor("zmenenyAuthor");
        b.setDepartment(null);
        try {
            bookService.updateBook(b);
            fail("IllegalArgumentException should be thrown when updating book without department");
        } catch (IllegalArgumentException iae) {
            //ok
        }
        b.setDepartment(Department.ADULT);

        try {
            bookService.updateBook(b);
        } catch (IllegalArgumentException iae) {
            fail("No exception should be thrown when updating correct user");
        }


        BookDTO buuk = bookService.getBookByID(b.getBookID());

        assertDeepEquals(b, buuk);
    }

    /**
     * Test of deleteBook method, of class BookService.
     */
    @Test
    public void testDeleteBook() {
        bookService.createBook(correctBooks.get(0));
        bookService.createBook(correctBooks.get(1));
        bookService.createBook(correctBooks.get(2));


        BookDTO toDelete = correctBooks.get(2);

        try {
            bookService.deleteBook(null);
            fail("IllegalArgumentException should be thrown when deleting null book");
        } catch (IllegalArgumentException iae) {
            // ok
        }
        try {
            bookService.deleteBook(toDelete);
        } catch (IllegalArgumentException iae) {
            fail("No Exception should be thrown when deleting correct Book " + iae.getMessage());
        }
        List<BookDTO> list = bookService.getAllBooks();
        assertEquals("v db nie su 2 knihy", 2, list.size());
        assertNotSame("a", correctBooks.get(1), list.get(0));
        assertNotSame("b", correctBooks.get(2), list.get(1));

    }

    /**
     * Test of getBookByID method, of class BookService.
     */
    /**
     * Test of getAllBooks method, of class BookService.
     */
    @Test
    public void testGetAllBooks() {
        try {
            bookService.createBook(correctBooks.get(0));
            bookService.createBook(correctBooks.get(1));
            bookService.createBook(correctBooks.get(2));

        } catch (Exception e) {
            fail("No exception should be thrown");
        }

        List<BookDTO> buuks = bookService.getAllBooks();



        assertEquals("There should be 3 books inside database but they are not", 3, buuks.size());
        assertDeepEquals(correctBooks, buuks);
    }

    /**
     * Test of getBooksByAuthor method, of class BookService.
     */
    @Test
    public void testSearchBooksByTitle() {
        bookService.createBook(correctBooks.get(0));
        bookService.createBook(correctBooks.get(1));
        bookService.createBook(correctBooks.get(2));

        try {
            bookService.searchBooksByTitle(null);
            fail("IllegalArgumentException should be thrown when getting book by null title");
        } catch (IllegalArgumentException iae) {
            //ok
        }

        try {
            bookService.searchBooksByTitle("");
            fail("IllegalArgumentException should be thrown when getting book by zero length title");
        } catch (IllegalArgumentException iae) {
            //ok
        }

        List<BookDTO> list = null;

        try {
            list = bookService.searchBooksByTitle("zov");
        } catch (Exception e) {
            fail("No exception should be thrown when getting book by correct possible title that is created inside database");
        }

        assertEquals("Search did not return correct number of books", 3, list.size());
        assertTrue("This book has not been found", list.contains(correctBooks.get(0)));
        assertTrue("This book has not been found", list.contains(correctBooks.get(1)));
        assertTrue("This book has not been found", list.contains(correctBooks.get(2)));
    }

    @Test
    public void testGetBooksByAuthor() {
        bookService.createBook(correctBooks.get(0));
        bookService.createBook(correctBooks.get(1));
        bookService.createBook(correctBooks.get(2));

        try {
            bookService.getBooksByAuthor(null);
            fail("IllegalArgumentException should be thrown when getting book by null author");
        } catch (IllegalArgumentException iae) {
            //ok
        }

        try {
            bookService.getBooksByAuthor("");
            fail("IllegalArgumentException should be thrown when getting book by zero length author");
        } catch (IllegalArgumentException iae) {
            //ok
        }

        List<BookDTO> list = null;

        try {
            list = bookService.getBooksByAuthor("tor");
        } catch (Exception e) {
            fail("No exception should be thrown when getting book by correct possible author that is created inside database");
        }

        assertEquals("Search did not return correct number of books", 3, list.size());
        assertTrue("This book has not been found", list.contains(correctBooks.get(0)));
        assertTrue("This book has not been found", list.contains(correctBooks.get(1)));
        assertTrue("This book has not been found", list.contains(correctBooks.get(2)));
    }

    @Test
    public void testGetBooksByDepartment() {
        bookService.createBook(correctBooks.get(0));
        bookService.createBook(correctBooks.get(1));
        bookService.createBook(correctBooks.get(2));

        try {
            bookService.getBooksByDepartment(null);
            fail("IllegalArgumentException should be thrown when getting book by null department");
        } catch (IllegalArgumentException iae) {
            //ok
        }


        List<BookDTO> list = null;

        try {
            list = bookService.getBooksByDepartment(Department.SCIENTIFIC);

        } catch (Exception e) {
            fail("No exception should be thrown when getting book by correct possible department that is created inside database"+e.getMessage());
        }

        assertEquals("Search did not return correct number of books", 1, list.size());
        assertTrue("This book has not been found", list.contains(correctBooks.get(0)));

    }

    private BookDTO createBook(String title, String author, Department department, BookStatus status) {
        BookDTO b = new BookDTO();
        b.setTitle(title);
        b.setAuthor(author);
        b.setDepartment(department);
        b.setBookStatus(status);

        return b;
    }

    private void assertDeepEquals(BookDTO expected, BookDTO actual) {
        assertEquals("Titles are not same", expected.getTitle(), actual.getTitle());
        assertEquals("Authors are not same", expected.getAuthor(), actual.getAuthor());
        assertEquals("Departments are not same", expected.getDepartment(), actual.getDepartment());

    }

    private void assertDeepEquals(List<BookDTO> expected, List<BookDTO> actual) {
        for (int i = 0; i < expected.size(); i++) {
            assertEquals("Titles are not same", expected.get(i).getTitle(), actual.get(i).getTitle());
            assertEquals("Authors are not same", expected.get(i).getAuthor(), actual.get(i).getAuthor());
            assertEquals("Departments are not same", expected.get(i).getDepartment(), actual.get(i).getDepartment());

        }
    }
}
