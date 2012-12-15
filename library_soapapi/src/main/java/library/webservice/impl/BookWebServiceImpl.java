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

    @Override
    public void createBook(BookDTO book) throws IllegalArgumentException {
        bookService.createBook(book);
    }

    @Override
    public void updateBook(BookDTO book) throws IllegalArgumentException {
        bookService.updateBook(book);
    }

    @Override
    public void deleteBook(BookDTO book) throws IllegalArgumentException {
        bookService.deleteBook(book);
    }

    @Override
    public BookDTO getBookByID(Long id) throws IllegalArgumentException {
        return bookService.getBookByID(id);
    }

    @Override
    public List<BookDTO> getAllBooks() {
        return bookService.getAllBooks();
    }

    @Override
    public List<BookDTO> getBooksByAuthor(String authorName) throws IllegalArgumentException {
        return bookService.getBooksByAuthor(authorName);
    }

    @Override
    public List<BookDTO> searchBooksByTitle(String title) throws IllegalArgumentException {
        return bookService.searchBooksByTitle(title);
    }

    @Override
    public List<BookDTO> getBooksByDepartment(Department department) throws IllegalArgumentException {
        return bookService.getBooksByDepartment(department);
    }
    
}
