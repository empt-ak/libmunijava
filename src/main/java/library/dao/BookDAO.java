/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package library.dao;

import java.util.List;
import javax.persistence.EntityManager;
import library.entity.Book;
import library.entity.Department;

/**
 *
 * @author Szalai
 */
public interface BookDAO 
{
    void setEm(EntityManager entityManager);
    
    /**
     * Method used for creating book in DAO layer. Since book object we are trying to save is parsed from service layer
     * and should be already checked and validated via {@link  org.springframework.validation.Validator} no Exception 
     * should be thrown when trying to create inappropriate object inside database.
     * @param book with all valid attributes to be saved
     * @throws IllegalArgumentException if book is null or does not have set its ID
     */
    void createBook(Book book) throws IllegalArgumentException;
    
    /**
     * Method used for updating book in DAO layer. Since book object we are trying to update is parsed from service layer
     * and should be already checked and validated via {@link  org.springframework.validation.Validator} no Exception should
     * be thrown when trying to update inappropriate object inside database.
     * @param book with all valid attributes to be update
     * @throws IllegalArgumentException if book is null or does not have set its ID
     */
    void updateBook(Book book) throws IllegalArgumentException;
    
    /**
     * Method used for deleting book in DAO layer. The only required field to be set for book is ID, because {@link Book#equals(java.lang.Object) }
     * compares books based on their IDs, therefore when deleting object ID has to be set in order to successful deletion.
     * @param book with proper ID to be deleted
     * @throws IllegalArgumentException if book is null or does not have proper id
     */
    void deleteBook(Book book) throws IllegalArgumentException;
    
    /**
     * Method used for obtaining book in DAO layer. Since book IDs are from range 1 to {@link java.lang.Long#MAX_VALUE}, we check only
     * range or if ID is not null in order to retrieve book by given id.
     * @param id from range 1 to {@link java.lang.Long#MAX_VALUE} or not null
     * @return Book by given id as parameter
     * @throws IllegalArgumentException if id is null or out of range
     */
    Book getBookByID(Long id) throws IllegalArgumentException;
    
    /**
     * Method used for retrieving all books from database. If there are currently no books stored inside db empty {@link java.util.ArrayList} is
     * returned
     * @return List with all books or empty {@link java.util.ArrayList}
     */
    List<Book> getAllBooks();
    
    /**
     * Method used for retrieving books written by given author from database. Name should be longer than 1 character and should not be null,
     * otherwise proper exception is thrown. If there is no match empty {@link java.util.ArrayList} is returned.
     * @param authorName String longer than 1 and not null
     * @return List with all books written by given authors name or empty {@link java.util.ArrayList}
     * @throws IllegalArgumentException if authorName is null or has zero length
     */
    List<Book> getBooksByAuthor(String authorName) throws IllegalArgumentException;
    
    /**
     * Method used for retrieving books by given title. Title should be longer than 1 character and should not be null,
     * otherwise proper exception is thrown. If there is no match empty {@link java.util.ArrayList} is returned.
     * @param title book title String longer than 1 and not null
     * @return List of books by given title, or empty {@link java.util.ArrayList}
     * @throws IllegalArgumentException if title is null or has zero length
     */
    List<Book> searchBooksByTitle(String title) throws IllegalArgumentException;
    
    /**
     * Method used for retrieving books by given department. 
     * @param department library department of books we would like to retrieve
     * @return List with books by given department or empty {@link java.util.ArrayList} if there are no books
     * @throws IllegalArgumentException if department is null
     */
    List<Book> getBooksByDepartment(Department department) throws IllegalArgumentException;   
    
}
