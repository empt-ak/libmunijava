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
import library.entity.BookDO;
import library.entity.enums.BookStatus;
import library.entity.TicketDO;
import library.entity.TicketItemDO;
import library.entity.UserDO;
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
    public void createTicket(TicketDO loanTicket) throws IllegalArgumentException {
        if (loanTicket == null) {
            throw new IllegalArgumentException("Error creating Ticket object: passed TicketItem is null");
        }

        if (loanTicket.getUser() != null) {
            ValidationUtils.checkUser(loanTicket.getUser());
            if (loanTicket.getTicketItems().isEmpty()) {
                throw new IllegalArgumentException("Error creating Ticket object: TicketItem list is empty");
            }
            for (Iterator<TicketItemDO> it = loanTicket.getTicketItems().iterator(); it.hasNext();) {
                TicketItemDO item = it.next();
                BookDO book = item.getBook();
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
    public TicketDO getTicketByID(Long id) throws IllegalArgumentException {
        if (id == null) {
            throw new IllegalArgumentException("Given Ticket can't be found "
                    + "in the database, it's ID is null");
        } else if (id <= new Long(0)) {
            throw new IllegalArgumentException("Can't get Ticket: Passed ID is not within a correct range");
        }

        return entityManager.find(TicketDO.class, id);
    }

    @Override
    public void updateTicket(TicketDO ticket) throws IllegalArgumentException {
        if (ticket == null) {
            throw new IllegalArgumentException("Given ticket is null");
        }
        if (ticket.getUser() != null) {
            ValidationUtils.checkUser(ticket.getUser());
            if (ticket.getTicketItems().isEmpty()) {
                throw new IllegalArgumentException("Error creating Ticket object: TicketItem list is empty");
            }
            for (Iterator<TicketItemDO> it = ticket.getTicketItems().iterator(); it.hasNext();) {
                TicketItemDO item = it.next();
                BookDO book = item.getBook();
                ValidationUtils.testBookIsCorrect(book);
            }
            entityManager.merge(ticket);
        } else {
            throw new IllegalArgumentException("Error updating Ticket object: " + ticket.toString());
        }
    }

    @Override
    public void deleteTicket(TicketDO ticket) throws IllegalArgumentException {
        if (ticket == null) {
            throw new IllegalArgumentException("Passed Ticket is null");
        }
        
        TicketDO thisTicket = entityManager.find(TicketDO.class, ticket.getTicketID());
        if (thisTicket != null) {
            entityManager.remove(thisTicket);
        } else {
            throw new IllegalArgumentException("Given Ticket can't be found "
                    + "in the database, it's ID does not exist or is null");
        }
    }

    @Override
    public TicketDO getLastTicketForUser(UserDO user) throws IllegalArgumentException {
        if (user == null) {
            throw new IllegalArgumentException("Given user is null");
        }

         return (TicketDO) entityManager.createQuery("SELECT t FROM TicketDO t where "
                 + "t.ticketID = (SELECT MAX(ticketID) FROM TicketDO t WHERE t.user = :user)")
                 .setParameter("user", user).getSingleResult();
        //return entityManager.createNativeQuery("SELECT * FROM Ticket t where t.userID = :userID AND t.ticketID = MAX(t.ticketID))",Ticket.class)
        //        .setParameter("userID", user.getUserID()).getSingleResult();
//        return (Ticket) entityManager.createNativeQuery("SELECT * FROM ticket t WHERE ticketID = (SELECT MAX(ticketID) FROM ticket WHERE userID = :userID)", Ticket.class)
//                .setParameter("userID", user.getUserID()).getSingleResult();
    }

    @Override
    public List<TicketDO> getTicketsInPeriodForUser(DateTime from, DateTime to, UserDO user) throws IllegalArgumentException {

        if (from == null) {
            throw new IllegalArgumentException("Given DateTime indicating start date is null");
        }
        if (to == null) {
            throw new IllegalArgumentException("Given DateTime indicating end date is null");
        }
        if (user == null) {
            throw new IllegalArgumentException("Given user is null");
        }

        return entityManager.createQuery("SELECT t FROM TicketDO t WHERE t.borrowTime BETWEEN :start AND :end AND t.user = :user")
                .setParameter("start", from).setParameter("end", to)
                .setParameter("user", user).getResultList();
    }

    @Override
    public List<TicketDO> getAllTicketsForUser(UserDO user) throws IllegalArgumentException {
        if (user == null) {
            throw new IllegalArgumentException("Given user is null");
        }

        return entityManager.createQuery("SELECT t FROM TicketDO t WHERE user = :user")
                .setParameter("user", user).getResultList();
    }
}
