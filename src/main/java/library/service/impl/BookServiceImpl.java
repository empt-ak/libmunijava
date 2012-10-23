/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package library.service.impl;

import java.util.List;
import library.dao.BookDAO;
import library.entity.Book;
import library.entity.Department;
import library.service.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * @author Szalai
 */
@Service
public class BookServiceImpl implements BookService {

    @Autowired
    private BookDAO bookDAO;

    @Override
    @Transactional
    public void createBook(Book book) throws IllegalArgumentException {
            bookDAO.createBook(book);        
    }

    @Override
    @Transactional
    public void updateBook(Book book) throws IllegalArgumentException {        
            bookDAO.updateBook(book);        
    }

    @Override
    @Transactional
    public void deleteBook(Book book) throws IllegalArgumentException {
        bookDAO.deleteBook(book);
    }

    @Override
    @Transactional(readOnly=true)
    public Book getBookByID(Long id) throws IllegalArgumentException {
        return bookDAO.getBookByID(id);
    }

    @Override
    @Transactional(readOnly=true)
    public List<Book> getAllBooks() {
        return bookDAO.getAllBooks();
    }

    @Override
    @Transactional(readOnly=true)
    public List<Book> getBooksByAuthor(String authorName) throws IllegalArgumentException {
        return bookDAO.getBooksByAuthor(authorName);
    }

    @Override
    @Transactional(readOnly=true)
    public List<Book> searchBooksByTitle(String title) throws IllegalArgumentException {
        return bookDAO.searchBooksByTitle(title);
    }

    @Override
    @Transactional(readOnly=true)
    public List<Book> getBooksByDepartment(Department department) throws IllegalArgumentException {
        return bookDAO.getBooksByDepartment(department);
    }
}
