/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package library.dao;

import java.util.List;
import javax.annotation.Resource;
import javax.ejb.SessionContext;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.PersistenceContextType;
import library.entity.Book;
import library.utils.LibraryIllegalArgumentException;
import library.utils.ValidationUtils;

/**
 *
 * @author Szalai
 */
@Stateless
public class BookDao implements BookDaoLocal {
    
    @PersistenceContext(unitName="libraryPU", type = PersistenceContextType.TRANSACTION)
    private EntityManager entityManager;
    
    @Resource
    private SessionContext sessionContext;

    @Override
    public void createBook(Book book) throws LibraryIllegalArgumentException {
        if (book == null) {
            throw new LibraryIllegalArgumentException("Sent book is null");
        }

        if (ValidationUtils.testBookIsCorrect(book)) {
            entityManager.persist(book);
        } else {
            throw new LibraryIllegalArgumentException("Sent book is missing (or has incorrect) required values!");
        }

    }

    @Override
    public void updateBook(Book book) throws LibraryIllegalArgumentException {
        if (book == null) {
            throw new LibraryIllegalArgumentException("Sent book is null");
        }
        if (book.getId() == null || book.getId().compareTo(new Long(1)) < 0) {
            throw new LibraryIllegalArgumentException("Sent book does not have its ID set");
        }

        if (ValidationUtils.testBookIsCorrect(book)) {
            entityManager.merge(book);
        } else {
            throw new LibraryIllegalArgumentException("Sent book is missing (or has incorrect) required values!");
        }

    }

    @Override
    public void deleteBook(Book book) throws LibraryIllegalArgumentException {

        if (book == null) {
            throw new LibraryIllegalArgumentException("Sent book is null");
        }

        if (book.getId() == null || book.getId().compareTo(new Long(1)) < 0) {
            throw new LibraryIllegalArgumentException("Sent book does not have its ID set");
        }
        Book thisBook = entityManager.find(Book.class, book.getId());
        if (thisBook != null) {

            if (ValidationUtils.testBookIsCorrect(thisBook)) {
                entityManager.remove(thisBook);
            } else {
                throw new LibraryIllegalArgumentException("Sent book is missing (or has incorrect) required values!");
            }

        }
    }

    @Override
    public Book getBookByID(Long id) throws LibraryIllegalArgumentException {
        if (id == null || id.compareTo(new Long(1)) < 0) {
            throw new LibraryIllegalArgumentException("Sent ID is null or not out of valid ID range");
        }
        return entityManager.find(Book.class, id);
    }

    @Override
    public List<Book> getAllBooks() {
        return entityManager.createQuery("SELECT b FROM Book b").getResultList();
    }

    @Override
    public List<Book> searchBooksByTitle(String title) throws LibraryIllegalArgumentException {
        if (title == null || title.length() == 0) {
            throw new LibraryIllegalArgumentException("Sent title is empty");
        }

        return entityManager.createQuery("SELECT b FROM Book b WHERE b.title LIKE :title")
                .setParameter("title", "%" + title + "%").getResultList();
    }

    @Override
    public List<Book> getBooksByAuthor(String authorName) {
        if (authorName == null || authorName.length() == 0) {
            throw new LibraryIllegalArgumentException("Sent authorName is empty");
        }
        return entityManager.createQuery("SELECT b FROM Book b where b.author LIKE :authorName")
                .setParameter("authorName", "%" + authorName + "%").getResultList();
    }  
    
    @Override
    public void callSetRollbackOnly() {
        sessionContext.setRollbackOnly();
    }
}
