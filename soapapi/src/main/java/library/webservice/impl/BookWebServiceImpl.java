/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package library.webservice.impl;

import java.util.List;
import javax.jws.WebService;
import library.entity.dto.BookDTO;
import library.entity.enums.Department;
import library.service.BookService;
import library.webservice.BookWebService;
import org.springframework.beans.factory.annotation.Autowired;

/**
 *
 * @author Nemko
 */
@WebService(endpointInterface = "library.webservice.BookWebService")
public class BookWebServiceImpl implements BookWebService {
    
    @Autowired
    private BookService bookService;

    public void createBook(BookDTO book) throws IllegalArgumentException {
        bookService.createBook(book);
    }

    public void updateBook(BookDTO book) throws IllegalArgumentException {
        bookService.updateBook(book);
    }

    public void deleteBook(BookDTO book) throws IllegalArgumentException {
        bookService.deleteBook(book);
    }

    public BookDTO getBookByID(Long id) throws IllegalArgumentException {
        return bookService.getBookByID(id);
    }

    public List<BookDTO> getAllBooks() {
        return bookService.getAllBooks();
    }

    public List<BookDTO> getBooksByAuthor(String authorName) throws IllegalArgumentException {
        return bookService.getBooksByAuthor(authorName);
    }

    public List<BookDTO> searchBooksByTitle(String title) throws IllegalArgumentException {
        return bookService.searchBooksByTitle(title);
    }

    public List<BookDTO> getBooksByDepartment(Department department) throws IllegalArgumentException {
        return bookService.getBooksByDepartment(department);
    }
    
}
