/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package library.dao.impl;

import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import library.dao.BookDAO;
import library.entity.Book;
import library.entity.Department;
import library.utils.ValidationUtils;
import org.springframework.stereotype.Repository;

/**
 *
 * @author Gajdos
 */
@Repository
public class BookDAOImpl implements BookDAO {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public void createBook(Book book) throws IllegalArgumentException {
        if (book == null) {
            throw new IllegalArgumentException("Sent book is null");
        }

        if (ValidationUtils.testBookIsCorrect(book)) {
            entityManager.persist(book);
        } else {
            throw new IllegalArgumentException("Sent book is missing (or has incorrect) required values!");
        }

    }

    @Override
    public void updateBook(Book book) throws IllegalArgumentException {
        if (book == null) {
            throw new IllegalArgumentException("Sent book is null");
        }
        if (book.getBookID() == null || book.getBookID().compareTo(new Long(1)) < 0) {
            throw new IllegalArgumentException("Sent book does not have its ID set");
        }

        if (ValidationUtils.testBookIsCorrect(book)) {
            entityManager.merge(book);
        } else {
            throw new IllegalArgumentException("Sent book is missing (or has incorrect) required values!");
        }

    }

    @Override
    public void deleteBook(Book book) throws IllegalArgumentException {

        if (book == null) {
            throw new IllegalArgumentException("Sent book is null");
        }

        if (book.getBookID() == null || book.getBookID().compareTo(new Long(1)) < 0) {
            throw new IllegalArgumentException("Sent book does not have its ID set");
        }
        Book thisBook = entityManager.find(Book.class, book.getBookID());
        if (thisBook != null) {

            if (ValidationUtils.testBookIsCorrect(thisBook)) {
                entityManager.remove(thisBook);
            } else {
                throw new IllegalArgumentException("Sent book is missing (or has incorrect) required values!");
            }

        }
    }

    @Override
    public Book getBookByID(Long id) throws IllegalArgumentException {
        if (id == null || id.compareTo(new Long(1)) < 0) {
            throw new IllegalArgumentException("Sent ID is null or not out of valid ID range");
        }
        return entityManager.find(Book.class, id);
    }

    @Override
    public List<Book> getAllBooks() {
        return entityManager.createQuery("SELECT b FROM Book b").getResultList();
    }

    @Override
    public List<Book> searchBooksByTitle(String title) throws IllegalArgumentException {
        if (title == null || title.length() == 0) {
            throw new IllegalArgumentException("Sent title is empty");
        }

        return entityManager.createQuery("SELECT b FROM Book b WHERE b.title LIKE :title")
                .setParameter("title", "%" + title + "%").getResultList();
    }

    @Override
    public List<Book> getBooksByDepartment(Department department) throws IllegalArgumentException {
        if (department == null) {
            throw new IllegalArgumentException("Sent department is null");
        }
        return entityManager.createQuery("SELECT b FROM Book b WHERE b.department = :department")
                .setParameter("department", department).getResultList();
    }

    @Override
    public List<Book> getBooksByAuthor(String authorName) {
        if (authorName == null || authorName.length() == 0) {
            throw new IllegalArgumentException("Sent authorName is empty");
        }
        return entityManager.createQuery("SELECT b FROM Book b where b.author LIKE :authorName")
                .setParameter("authorName", "%" + authorName + "%").getResultList();
    }
}
