/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package library.dao.impl;

import java.util.Iterator;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import library.dao.TicketDAO;
import library.entity.Book;
import library.entity.enums.BookStatus;
import library.entity.Ticket;
import library.entity.TicketItem;
import library.entity.User;
import library.utils.ValidationUtils;
import org.joda.time.DateTime;
import org.springframework.stereotype.Repository;

/**
 *
 * @author Nemcek
 */
@Repository
public class TicketDAOImpl implements TicketDAO {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public void createTicket(Ticket loanTicket) throws IllegalArgumentException {
        if (loanTicket == null) {
            throw new IllegalArgumentException("Error creating Ticket object: passed TicketItem is null");
        }

        if (loanTicket.getUser() != null) {
            ValidationUtils.checkUser(loanTicket.getUser());
            if (loanTicket.getTicketItems().isEmpty()) {
                throw new IllegalArgumentException("Error creating Ticket object: TicketItem list is empty");
            }
            for (Iterator<TicketItem> it = loanTicket.getTicketItems().iterator(); it.hasNext();) {
                TicketItem item = it.next();
                Book book = item.getBook();
                ValidationUtils.testBookIsCorrect(book);
                if (ValidationUtils.testIsBookAvailable(book)) {
                    book.setBookStatus(BookStatus.NOT_AVAILABLE);
                } else {
                    throw new IllegalArgumentException("Error creating Ticket object: One or more sent book(s) is (are) not available!");
                }
            }

            if (loanTicket.getBorrowTime() == null) {
                loanTicket.setBorrowTime(DateTime.now());
            }
            
            entityManager.persist(loanTicket);
        } else {
            throw new IllegalArgumentException("Error creating Ticket object: "
                    + "passed TicketItem's User is null or incorrect");
        }


    }

    @Override
    public Ticket getTicketByID(Long id) throws IllegalArgumentException {
        if (id == null) {
            throw new IllegalArgumentException("Given Ticket can't be found "
                    + "in the database, it's ID is null");
        } else if (id <= new Long(0)) {
            throw new IllegalArgumentException("Can't get Ticket: Passed ID is not within a correct range");
        }

        return entityManager.find(Ticket.class, id);
    }

    @Override
    public void updateTicket(Ticket ticket) throws IllegalArgumentException {
        if (ticket == null) {
            throw new IllegalArgumentException("Given ticket is null");
        }
        if (ticket.getUser() != null) {
            ValidationUtils.checkUser(ticket.getUser());
            if (ticket.getTicketItems().isEmpty()) {
                throw new IllegalArgumentException("Error creating Ticket object: TicketItem list is empty");
            }
            for (Iterator<TicketItem> it = ticket.getTicketItems().iterator(); it.hasNext();) {
                TicketItem item = it.next();
                Book book = item.getBook();
                ValidationUtils.testBookIsCorrect(book);
            }
            entityManager.merge(ticket);
        } else {
            throw new IllegalArgumentException("Error updating Ticket object: " + ticket.toString());
        }
    }

    @Override
    public void deleteTicket(Ticket ticket) throws IllegalArgumentException {
        if (ticket == null) {
            throw new IllegalArgumentException("Passed Ticket is null");
        }
        
        Ticket thisTicket = entityManager.find(Ticket.class, ticket.getTicketID());
        if (thisTicket != null) {
            entityManager.remove(thisTicket);
        } else {
            throw new IllegalArgumentException("Given Ticket can't be found "
                    + "in the database, it's ID does not exist or is null");
        }
    }

    @Override
    public Ticket getLastTicketForUser(User user) throws IllegalArgumentException {
        if (user == null) {
            throw new IllegalArgumentException("Given user is null");
        }

         return (Ticket) entityManager.createQuery("SELECT t FROM Ticket t where "
                 + "t.ticketID = (SELECT MAX(ticketID) FROM Ticket t WHERE t.user = :user)")
                 .setParameter("user", user).getSingleResult();
        //return entityManager.createNativeQuery("SELECT * FROM Ticket t where t.userID = :userID AND t.ticketID = MAX(t.ticketID))",Ticket.class)
        //        .setParameter("userID", user.getUserID()).getSingleResult();
//        return (Ticket) entityManager.createNativeQuery("SELECT * FROM ticket t WHERE ticketID = (SELECT MAX(ticketID) FROM ticket WHERE userID = :userID)", Ticket.class)
//                .setParameter("userID", user.getUserID()).getSingleResult();
    }

    @Override
    public List<Ticket> getTicketsInPeriodForUser(DateTime from, DateTime to, User user) throws IllegalArgumentException {

        if (from == null) {
            throw new IllegalArgumentException("Given DateTime indicating start date is null");
        }
        if (to == null) {
            throw new IllegalArgumentException("Given DateTime indicating end date is null");
        }
        if (user == null) {
            throw new IllegalArgumentException("Given user is null");
        }

        return entityManager.createQuery("SELECT t FROM Ticket t WHERE t.borrowTime BETWEEN :start AND :end AND t.user = :user")
                .setParameter("start", from).setParameter("end", to)
                .setParameter("user", user).getResultList();
    }

    @Override
    public List<Ticket> getAllTicketsForUser(User user) throws IllegalArgumentException {
        if (user == null) {
            throw new IllegalArgumentException("Given user is null");
        }

        return entityManager.createQuery("SELECT t FROM Ticket t WHERE user = :user")
                .setParameter("user", user).getResultList();
    }
}
