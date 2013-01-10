/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package library.service.impl;

import java.util.ArrayList;
import java.util.List;
import library.dao.BookDAO;
import library.entity.Book;
import library.entity.dto.BookDTO;
import library.entity.enums.Department;
import library.service.BookService;
import org.dozer.Mapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
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

    @PreAuthorize("ROLE_ADMINISTRATOR")
    @Override
    @Transactional
    public void createBook(BookDTO bookDTO) throws IllegalArgumentException 
    {
        if(bookDTO == null)
        {
            throw new IllegalArgumentException("ERROR: method argument for method createBook cannot be null");            
        }
        Book book = mapper.map(bookDTO, Book.class);
        bookDAO.createBook(book);
        
        bookDTO.setBookID(book.getBookID());        
    }

    @PreAuthorize("ROLE_ADMINISTRATOR")
    @Override
    @Transactional
    public void updateBook(BookDTO bookDTO) throws IllegalArgumentException 
    {      
        if(bookDTO == null)
        {
            throw new IllegalArgumentException("ERROR: method argument for method updateBook cannot be null"); 
        }
        Book book = mapper.map(bookDTO, Book.class);

        bookDAO.updateBook(book);        
    }

    @PreAuthorize("ROLE_ADMINISTRATOR")
    @Override
    @Transactional
    public void deleteBook(BookDTO bookDTO) throws IllegalArgumentException 
    {
        if(bookDTO == null)
        {
            throw new IllegalArgumentException("ERROR: method argument for method deleteBook cannot be null"); 
        }
        Book book = mapper.map(bookDTO, Book.class);
        
        bookDAO.deleteBook(book);
    }

    
    @Override
    @Transactional(readOnly=true)
    public BookDTO getBookByID(Long id) throws IllegalArgumentException 
    {
        Book book = bookDAO.getBookByID(id); // throws iae on wrong id
        if(book != null)
        {
            return mapper.map(bookDAO.getBookByID(id), BookDTO.class);
        }
        else
        {
            return null;
        }        
    }

    @Override
    @Transactional(readOnly=true)
    public List<BookDTO> getAllBooks() 
    {
        List<BookDTO> books = new ArrayList<>();
        List<Book> booksDO = bookDAO.getAllBooks();
        for(Book bDO : booksDO)
        {
            books.add(mapper.map(bDO, BookDTO.class));
        }
        return books;
    }

    @Override
    @Transactional(readOnly=true)
    public List<BookDTO> getBooksByAuthor(String authorName) throws IllegalArgumentException 
    {
        List<BookDTO> books = new ArrayList<>();
        List<Book> booksDO = bookDAO.getBooksByAuthor(authorName);
        for(Book bDO : booksDO)
        {
            books.add(mapper.map(bDO, BookDTO.class));
        }
        return books;
    }

    @Override
    @Transactional(readOnly=true)
    public List<BookDTO> searchBooksByTitle(String title) throws IllegalArgumentException 
    {
        List<BookDTO> books = new ArrayList<>();
        List<Book> booksDO = bookDAO.searchBooksByTitle(title);
        for(Book bDO : booksDO)
        {
            books.add(mapper.map(bDO, BookDTO.class));
        }
        return books;
    }

    @Override
    @Transactional(readOnly=true)
    public List<BookDTO> getBooksByDepartment(Department department) throws IllegalArgumentException 
    {
        if(department == null){throw new IllegalArgumentException("ERROR: Given department is null"); }
        List<BookDTO> books = new ArrayList<>();
        List<Book> booksDO = bookDAO.getBooksByDepartment(department);
        for(Book bDO : booksDO)
        {
            books.add(mapper.map(bDO, BookDTO.class));
        }
        return books;        
    }
}
