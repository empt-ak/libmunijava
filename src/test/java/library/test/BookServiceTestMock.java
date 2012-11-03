/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package library.test;

import library.dao.BookDAO;
import library.entity.Book;
import library.entity.dto.BookDTO;
import library.entity.enums.BookStatus;
import library.entity.enums.Department;
import library.service.BookService;
import library.service.impl.BookServiceImpl;
import org.dozer.Mapper;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import static org.junit.Assert.*;
import static org.mockito.Mockito.*;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.test.util.ReflectionTestUtils;

/**
 *
 * @author Nemko
 */
@RunWith(MockitoJUnitRunner.class)
public class BookServiceTestMock {
    
    BookService bookService;
    
    @Mock
    private BookDAO bookDAO;
    
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
 
        Book b1 = createBook(new Long(1), "nazov1", "autor1", Department.SCIENTIFIC, BookStatus.AVAILABLE);
        when(bookDAO.getBookByID(new Long(1))).thenReturn(b1);
        
        BookDTO bdto1 = createBookDTO(new Long(1), "nazov1", "autor1", Department.SCIENTIFIC, BookStatus.AVAILABLE);
        when(bookService.getBookByID(new Long(1))).thenReturn(bdto1);
        
        BookDTO bookDTO = bookService.getBookByID(new Long(1));
        
        assertEquals(bookDTO.getBookID(), b1.getBookID());
        
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
