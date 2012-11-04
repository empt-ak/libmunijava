/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package library.test;

import java.util.ArrayList;
import java.util.List;
import library.dao.BookDAO;
import library.entity.Book;
import library.entity.dto.BookDTO;
import library.entity.enums.BookStatus;
import library.entity.enums.Department;
import library.service.BookService;
import library.service.impl.BookServiceImpl;
import org.dozer.Mapper;
import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import static org.mockito.Mockito.*;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.test.util.ReflectionTestUtils;

/**
 *
 * @author Nemko
 */
@RunWith(MockitoJUnitRunner.class)
public class BookServiceMockTest {

    BookService bookService;
    @Mock
    BookDAO bookDAO;
    @Mock
    Mapper mapper;

    @Before
    public void init() {
        bookService = new BookServiceImpl();
        ReflectionTestUtils.setField(bookService, "bookDAO", bookDAO);
        ReflectionTestUtils.setField(bookService, "mapper", mapper);
    }

    @Test
    public void testCreateBook() {

        try {
            bookService.createBook(null);
        } catch (IllegalArgumentException e) {
            // ok
        }
        
        BookDTO bdto1 = createBookDTO(new Long(1), "nazov1", "autor1", Department.SCIENTIFIC, BookStatus.AVAILABLE);
        
        try {
            bookService.createBook(bdto1);
        } catch (Exception e) {
            fail();
        }

    }
    
    @Test
    public void testGetBookByID() {
        Book b1 = createBook(new Long(1), "nazov1", "autor1", Department.SCIENTIFIC, BookStatus.AVAILABLE);
        when(bookDAO.getBookByID(new Long(1))).thenReturn(b1);

        BookDTO bdto1 = createBookDTO(new Long(1), "nazov1", "autor1", Department.SCIENTIFIC, BookStatus.AVAILABLE);
        when(bookService.getBookByID(new Long(1))).thenReturn(bdto1);

        BookDTO bookDTO = bookService.getBookByID(new Long(1));

        assertEquals(bookDTO.getBookID(), b1.getBookID());
    }
    
    @Test
    public void testGetAllBooks() {
        Book b1 = createBook(new Long(1), "nazov1", "autor1", Department.SCIENTIFIC, BookStatus.AVAILABLE);
        Book b2 = createBook(new Long(2), "nazov2", "autor2", Department.ADULT, BookStatus.AVAILABLE);
        
        BookDTO bdto1 = createBookDTO(new Long(1), "nazov1", "autor1", Department.SCIENTIFIC, BookStatus.AVAILABLE);
        BookDTO bdto2 = createBookDTO(new Long(2), "nazov2", "autor2", Department.ADULT, BookStatus.AVAILABLE);
        
        List<Book> booksList = new ArrayList<>();
        List<BookDTO> bookDTOs = new ArrayList<>();
        bookDTOs.add(bdto1);
        bookDTOs.add(bdto2);
        booksList.add(b1);
        booksList.add(b2);

        when(bookDAO.getAllBooks()).thenReturn(booksList);
        when(bookService.getAllBooks()).thenReturn(bookDTOs);

        List<BookDTO> books = bookService.getAllBooks();

        assertEquals(((List<Book>)books.get(1)).get(0), bookDTOs.get(0));
        assertEquals(((List<Book>)books.get(1)).get(1), bookDTOs.get(1));
    }

    @Test
    public void testUpdateBook() {

        try {
            bookService.updateBook(null);
        } catch (IllegalArgumentException e) {
            // ok
        }
        
        BookDTO bdto1 = createBookDTO(new Long(1), "nazov1", "autor1", Department.SCIENTIFIC, BookStatus.AVAILABLE);
        
        try {
            bookService.updateBook(bdto1);
        } catch (Exception e) {
            fail();
        }

//        final List<Book> books = new ArrayList<>();
//        Book b1 = createBook(new Long(1), "nazov1", "autor1", Department.SCIENTIFIC, BookStatus.AVAILABLE);
//        BookDTO bdto1 = createBookDTO(new Long(1), "nazov1", "autor1", Department.SCIENTIFIC, BookStatus.AVAILABLE);
//        doAnswer(new Answer() {
//            @Override
//            public Object answer(InvocationOnMock invocation) {
//                Object[] args = invocation.getArguments();
//                Book book = (Book) invocation.getMock();
//                books.add(book);
//                return null;
//            }
//        }).when(bookDAO).updateBook(b1);
//
//        bookDAO.updateBook(b1);
////
//        BookDTO bookDTO = bookService.getBookByID(new Long(1));
//
//        assertEquals(bookDTO.getBookID(), b1.getBookID());
//        BookDTO bdto1 = createBookDTO(new Long(1), "nazov1", "autor1", Department.SCIENTIFIC, BookStatus.AVAILABLE);
//        Book b1 = createBook(new Long(1), "nazov1", "autor1", Department.SCIENTIFIC, BookStatus.AVAILABLE);
//        bookService.updateBook(bdto1);
//
//        // then
//        ArgumentCaptor<Book> messageCaptor = ArgumentCaptor.forClass(Book.class);
//        verify(bookDAO).updateBook(messageCaptor.capture());
//
//        assertEquals(messageCaptor.getValue(), b1);

    }

    @Test
    public void testDeleteBook() {

        BookDTO bdto1 = createBookDTO(new Long(1), "nazov1", "autor1", Department.SCIENTIFIC, BookStatus.AVAILABLE);

        try {
            bookService.deleteBook(bdto1);
        } catch (Exception e) {
            fail();
        }

    }
    
    @Test
    public void testGetBooksByAuthor() {
        
        BookDTO bdto1 = createBookDTO(new Long(1), "nazov1", "autor1", Department.SCIENTIFIC, BookStatus.AVAILABLE);
        BookDTO bdto2 = createBookDTO(new Long(2), "nazov2", "autor1", Department.ADULT, BookStatus.AVAILABLE);
        Book b1 = createBook(new Long(1), "nazov1", "autor1", Department.SCIENTIFIC, BookStatus.AVAILABLE);
        Book b2 = createBook(new Long(2), "nazov2", "autor1", Department.ADULT, BookStatus.AVAILABLE);
        List<BookDTO> bdtoList = new ArrayList<>();
        List<BookDTO> testList = new ArrayList<>();
        List<Book> booksList = new ArrayList<>();
        bdtoList.add(bdto1);
        bdtoList.add(bdto2);
        booksList.add(b1);
        booksList.add(b2);
        
        when(bookDAO.getBooksByAuthor("autor1")).thenReturn(booksList);
        when(bookService.getBooksByAuthor("autor1")).thenReturn(bdtoList);
        
        try {
            testList = bookService.getBooksByAuthor("autor1");
        } catch (Exception e) {
            fail();
        }
        
        assertEquals(((List<Book>)testList.get(1)).get(0), bdtoList.get(0));
        assertEquals(((List<Book>)testList.get(1)).get(1), bdtoList.get(1));
    }
    
    @Test
    public void testSearchBooksByTitle() {
        
        BookDTO bdto1 = createBookDTO(new Long(1), "nazov1", "autor1", Department.SCIENTIFIC, BookStatus.AVAILABLE);
        BookDTO bdto2 = createBookDTO(new Long(2), "nazov1", "autor2", Department.ADULT, BookStatus.AVAILABLE);
        Book b1 = createBook(new Long(1), "nazov1", "autor1", Department.SCIENTIFIC, BookStatus.AVAILABLE);
        Book b2 = createBook(new Long(2), "nazov1", "autor2", Department.ADULT, BookStatus.AVAILABLE);
        List<BookDTO> bdtoList = new ArrayList<>();
        List<BookDTO> testList = new ArrayList<>();
        List<Book> booksList = new ArrayList<>();
        bdtoList.add(bdto1);
        bdtoList.add(bdto2);
        booksList.add(b1);
        booksList.add(b2);
        
        when(bookDAO.searchBooksByTitle("nazov1")).thenReturn(booksList);
        when(bookService.searchBooksByTitle("nazov1")).thenReturn(bdtoList);
        
        try {
            testList = bookService.searchBooksByTitle("nazov1");
        } catch (Exception e) {
            fail();
        }
        
        assertEquals(((List<Book>)testList.get(1)).get(0), bdtoList.get(0));
        assertEquals(((List<Book>)testList.get(1)).get(1), bdtoList.get(1));
        
    }
    
    @Test
    public void testGetBooksByDepartment() {
        
        BookDTO bdto1 = createBookDTO(new Long(1), "nazov1", "autor1", Department.SCIENTIFIC, BookStatus.AVAILABLE);
        BookDTO bdto2 = createBookDTO(new Long(2), "nazov2", "autor2", Department.SCIENTIFIC, BookStatus.AVAILABLE);
        Book b1 = createBook(new Long(1), "nazov1", "autor1", Department.SCIENTIFIC, BookStatus.AVAILABLE);
        Book b2 = createBook(new Long(2), "nazov2", "autor2", Department.SCIENTIFIC, BookStatus.AVAILABLE);
        List<BookDTO> bdtoList = new ArrayList<>();
        List<BookDTO> testList = new ArrayList<>();
        List<Book> booksList = new ArrayList<>();
        bdtoList.add(bdto1);
        bdtoList.add(bdto2);
        booksList.add(b1);
        booksList.add(b2);
        
        when(bookDAO.getBooksByDepartment(Department.SCIENTIFIC)).thenReturn(booksList);
        when(bookService.getBooksByDepartment(Department.SCIENTIFIC)).thenReturn(bdtoList);
        
        try {
            testList = bookService.getBooksByDepartment(Department.SCIENTIFIC);
        } catch (Exception e) {
            fail();
        }
        
        assertEquals(((List<Book>)testList.get(1)).get(0), bdtoList.get(0));
        assertEquals(((List<Book>)testList.get(1)).get(1), bdtoList.get(1));
        
    }

    private Book createBook(Long id, String title, String author, Department department, BookStatus status) {
        Book b = new Book();
        b.setBookID(id);
        b.setTitle(title);
        b.setAuthor(author);
        b.setDepartment(department);
        b.setBookStatus(status);

        return b;
    }

    private BookDTO createBookDTO(Long id, String title, String author, Department department, BookStatus status) {
        BookDTO b = new BookDTO();
        b.setBookID(id);
        b.setTitle(title);
        b.setAuthor(author);
        b.setDepartment(department);
        b.setBookStatus(status);

        return b;
    }
}
