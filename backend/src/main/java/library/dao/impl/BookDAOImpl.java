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
import library.entity.enums.Department;
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
        entityManager.persist(book);
    }

    @Override
    public void updateBook(Book book) throws IllegalArgumentException {
        entityManager.merge(book);
    }

    @Override
    public void deleteBook(Book book) throws IllegalArgumentException {
        Book thisBook = entityManager.find(Book.class, book.getBookID());
        if (thisBook != null) {
            // TOTO je blbost nie ? ved ked je uz v db netreba cekovat ci je ok...
//            if (ValidationUtils.testBookIsCorrect(thisBook)) {
//                entityManager.remove(thisBook);
//            } else {
//                throw new IllegalArgumentException("Sent book is missing (or has incorrect) required values!");
//            }
            entityManager.remove(thisBook);
        }
    }

    @Override
    public Book getBookByID(Long id) throws IllegalArgumentException {        
        return entityManager.find(Book.class, id);
    }

    @Override
    public List<Book> getAllBooks() {
        return entityManager.createQuery("SELECT b FROM Book b",Book.class).getResultList();
    }

    @Override
    public List<Book> searchBooksByTitle(String title) throws IllegalArgumentException {
        return entityManager.createQuery("SELECT b FROM Book b WHERE b.title LIKE :title",Book.class)
                .setParameter("title", "%" + title + "%").getResultList();
    }

    @Override
    public List<Book> getBooksByDepartment(Department department) throws IllegalArgumentException {
        return entityManager.createQuery("SELECT b FROM Book b WHERE b.department = :department",Book.class)
                .setParameter("department", department).getResultList();
    }

    @Override
    public List<Book> getBooksByAuthor(String authorName) {        
        return entityManager.createQuery("SELECT b FROM Book b where b.author LIKE :authorName",Book.class)
                .setParameter("authorName", "%" + authorName + "%").getResultList();
    }
}
