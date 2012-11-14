package library.utils.aop;

import library.utils.aop.validators.LibraryValidator;
import library.utils.aop.validators.Operation;
import library.entity.Book;
import library.entity.enums.Department;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author emptak
 */
@Aspect
public class AspectDAOBookScanner 
{
    @Before("execution (* library.dao.impl.BookDAOImpl.createBook(..)) && args(book)")
    public void scanForSave(Book book)
    {
        LibraryValidator.validateDAO(book, Book.class, Operation.CREATE);        
    }
    
    @Before("execution (* library.dao.impl.BookDAOImpl.updateBook(..)) && args(book)")
    public void scanForUpdate(Book book)
    {
        LibraryValidator.validateDAO(book, Book.class, Operation.UPDATE);        
    }
    
    @Before("execution (* library.dao.impl.BookDAOImpl.deleteBook(..)) && args(book)")
    public void scanForDelete(Book book)
    {
        LibraryValidator.validateDAO(book, Book.class, Operation.DELETE);        
    }
    
    @Before("execution (* library.dao.impl.BookDAOImpl.getBookByID(..)) && args(id)")
    public void scanForGetByID(Long id)
    {
        if(!LibraryValidator.isValidID(id))
        {
            throw new IllegalArgumentException("Sent ID is null or not out of valid ID range");
        }
    }
    
    @Before("execution (* library.dao.impl.BookDAOImpl.searchBooksByTitle(..)) && args(bookTitle)")
    public void scanForSearchBooksByTitle(String bookTitle)
    {
        if(LibraryValidator.isStringEmpty(bookTitle))
        {
            throw new IllegalArgumentException("Sent title is null");
        }
    }
    
    @Before("execution (* library.dao.impl.BookDAOImpl.getBooksByDepartment(..)) && args(department)")
    public void scanForGetBooksByDepartment(Department department)
    {
        if(LibraryValidator.isNull(department))
        {
            throw new IllegalArgumentException("Sent department is null");
        }
    }
    
    @Before("execution (* library.dao.impl.BookDAOImpl.getBooksByAuthor(..)) && args(author)")
    public void scanForGetBooksByAuthor(String author)
    {
        if(LibraryValidator.isStringEmpty(author))
        {
            throw new IllegalArgumentException("Sent author is null or has zero length");
        }
    } 
}
