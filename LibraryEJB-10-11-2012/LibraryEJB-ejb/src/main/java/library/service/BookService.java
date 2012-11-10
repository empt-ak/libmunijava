/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package library.service;

import java.util.List;
import javax.ejb.EJB;
import javax.ejb.Local;
import javax.ejb.Stateless;
import library.dao.BookDao;
import library.entity.Book;
import library.utils.LibraryIllegalArgumentException;

/**
 *
 * @author Gajdos
 */
@Stateless
@Local(value=BookServiceLocal.class)
public class BookService implements BookServiceLocal {
    
    @EJB
    private BookDao bookDAO;

    @Override
    public void createBook(Book book) throws LibraryIllegalArgumentException 
    {
            if(book == null)
            {
                throw new LibraryIllegalArgumentException("ERROR: method argument for method createBook cannot be null");            
            }
            bookDAO.createBook(book);
    }

    @Override
    public void updateBook(Book book) throws LibraryIllegalArgumentException 
    {      
            if(book == null)
            {
                throw new LibraryIllegalArgumentException("ERROR: method argument for method updateBook cannot be null"); 
            }
            
            bookDAO.updateBook(book);        
    }

    @Override
    public void deleteBook(Book book) throws LibraryIllegalArgumentException 
    {
        if(book == null)
        {
            throw new LibraryIllegalArgumentException("ERROR: method argument for method deleteBook cannot be null"); 
        }
        
        bookDAO.deleteBook(book);
    }

    @Override
    public Book getBookByID(Long id) throws LibraryIllegalArgumentException 
    {
        return bookDAO.getBookByID(id); // throws iae on wrong id
        
    }

    @Override
    public List<Book> getAllBooks() 
    {
        return bookDAO.getAllBooks();
    }

    @Override
    public List<Book> getBooksByAuthor(String authorName) throws LibraryIllegalArgumentException 
    {
        return bookDAO.getBooksByAuthor(authorName);
    }

    @Override
    public List<Book> searchBooksByTitle(String title) throws LibraryIllegalArgumentException 
    {
        return bookDAO.searchBooksByTitle(title);
    }
    
}
