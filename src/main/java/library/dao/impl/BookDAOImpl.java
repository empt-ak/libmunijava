/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package library.dao.impl;

import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import library.dao.BookDAO;
import library.entity.BookDO;
import library.entity.enums.Department;
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
    public void createBook(BookDO book) throws IllegalArgumentException {
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
    public void updateBook(BookDO book) throws IllegalArgumentException {
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
    public void deleteBook(BookDO book) throws IllegalArgumentException {

        if (book == null) {
            throw new IllegalArgumentException("Sent book is null");
        }

        if (book.getBookID() == null || book.getBookID().compareTo(new Long(1)) < 0) {
            throw new IllegalArgumentException("Sent book does not have its ID set");
        }
        BookDO thisBook = entityManager.find(BookDO.class, book.getBookID());
        if (thisBook != null) {

            if (ValidationUtils.testBookIsCorrect(thisBook)) {
                entityManager.remove(thisBook);
            } else {
                throw new IllegalArgumentException("Sent book is missing (or has incorrect) required values!");
            }

        }
    }

    @Override
    public BookDO getBookByID(Long id) throws IllegalArgumentException {
        if (id == null || id.compareTo(new Long(1)) < 0) {
            throw new IllegalArgumentException("Sent ID is null or not out of valid ID range");
        }
        return entityManager.find(BookDO.class, id);
    }

    @Override
    public List<BookDO> getAllBooks() {
        return entityManager.createQuery("SELECT b FROM BookDO b").getResultList();
    }

    @Override
    public List<BookDO> searchBooksByTitle(String title) throws IllegalArgumentException {
        if (title == null || title.length() == 0) {
            throw new IllegalArgumentException("Sent title is empty");
        }

        return entityManager.createQuery("SELECT b FROM BookDO b WHERE b.title LIKE :title")
                .setParameter("title", "%" + title + "%").getResultList();
    }

    @Override
    public List<BookDO> getBooksByDepartment(Department department) throws IllegalArgumentException {
        if (department == null) {
            throw new IllegalArgumentException("Sent department is null");
        }
        return entityManager.createQuery("SELECT b FROM BookDO b WHERE b.department = :department")
                .setParameter("department", department).getResultList();
    }

    @Override
    public List<BookDO> getBooksByAuthor(String authorName) {
        if (authorName == null || authorName.length() == 0) {
            throw new IllegalArgumentException("Sent authorName is empty");
        }
        return entityManager.createQuery("SELECT b FROM BookDO b where b.author LIKE :authorName")
                .setParameter("authorName", "%" + authorName + "%").getResultList();
    }
}
