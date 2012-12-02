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
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public void updateBook(BookDTO book) throws IllegalArgumentException {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public void deleteBook(BookDTO book) throws IllegalArgumentException {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public BookDTO getBookByID(Long id) throws IllegalArgumentException {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public List<BookDTO> getAllBooks() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public List<BookDTO> getBooksByAuthor(String authorName) throws IllegalArgumentException {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public List<BookDTO> searchBooksByTitle(String title) throws IllegalArgumentException {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public List<BookDTO> getBooksByDepartment(Department department) throws IllegalArgumentException {
        throw new UnsupportedOperationException("Not supported yet.");
    }
    
}
