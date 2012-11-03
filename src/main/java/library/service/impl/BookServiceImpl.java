/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package library.service.impl;

import java.util.ArrayList;
import java.util.List;
import library.dao.BookDAO;
import library.entity.BookDO;
import library.entity.dto.Book;
import library.entity.enums.Department;
import library.service.BookService;
import org.dozer.Mapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * @author Szalai
 */
@Service
public class BookServiceImpl implements BookService 
{
    private static final org.apache.log4j.Logger logger = org.apache.log4j.Logger.getLogger(BookServiceImpl.class);

    @Autowired
    private BookDAO bookDAO;
    
    @Autowired
    private Mapper mapper;

    @Override
    @Transactional
    public void createBook(Book book) throws IllegalArgumentException 
    {
            if(book == null)
            {
                throw new IllegalArgumentException("ERROR: method argument for method createBook cannot be null");            
            }
            BookDO bookDO = mapper.map(book, BookDO.class);
            bookDAO.createBook(bookDO);
            book.setBookID(bookDO.getBookID());
    }

    @Override
    @Transactional
    public void updateBook(Book book) throws IllegalArgumentException 
    {      
            if(book == null)
            {
                throw new IllegalArgumentException("ERROR: method argument for method updateBook cannot be null"); 
            }
            BookDO bookDO = mapper.map(book, BookDO.class);
            
            bookDAO.updateBook(bookDO);        
    }

    @Override
    @Transactional
    public void deleteBook(Book book) throws IllegalArgumentException 
    {
        if(book == null)
        {
            throw new IllegalArgumentException("ERROR: method argument for method deleteBook cannot be null"); 
        }
        BookDO bookDO = mapper.map(book, BookDO.class);
        
        bookDAO.deleteBook(bookDO);
    }

    @Override
    @Transactional(readOnly=true)
    public Book getBookByID(Long id) throws IllegalArgumentException 
    {
        BookDO bookDO = bookDAO.getBookByID(id); // throws iae on wrong id
        if(bookDO != null)
        {
            return mapper.map(bookDAO.getBookByID(id), Book.class);
        }
        else
        {
            return null;
        }
        
    }

    @Override
    @Transactional(readOnly=true)
    public List<Book> getAllBooks() 
    {
        List<Book> books = new ArrayList<>();
        List<BookDO> booksDO = bookDAO.getAllBooks();
        for(BookDO bDO : booksDO)
        {
            books.add(mapper.map(bDO, Book.class));
        }
        return books;
    }

    @Override
    @Transactional(readOnly=true)
    public List<Book> getBooksByAuthor(String authorName) throws IllegalArgumentException 
    {
        List<Book> books = new ArrayList<>();
        List<BookDO> booksDO = bookDAO.getBooksByAuthor(authorName);
        for(BookDO bDO : booksDO)
        {
            books.add(mapper.map(bDO, Book.class));
        }
        return books;
    }

    @Override
    @Transactional(readOnly=true)
    public List<Book> searchBooksByTitle(String title) throws IllegalArgumentException 
    {
        List<Book> books = new ArrayList<>();
        List<BookDO> booksDO = bookDAO.searchBooksByTitle(title);
        for(BookDO bDO : booksDO)
        {
            books.add(mapper.map(bDO, Book.class));
        }
        return books;
    }

    @Override
    @Transactional(readOnly=true)
    public List<Book> getBooksByDepartment(Department department) throws IllegalArgumentException 
    {
        if(department == null){throw new IllegalArgumentException(); }
        List<Book> books = new ArrayList<>();
        List<BookDO> booksDO = bookDAO.getBooksByDepartment(department);
        for(BookDO bDO : booksDO)
        {
            books.add(mapper.map(bDO, Book.class));
        }
        return books;        
    }
}
