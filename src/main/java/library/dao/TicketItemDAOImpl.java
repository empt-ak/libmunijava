/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package library.dao;

import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import library.entity.Ticket;
import library.entity.TicketItem;

/**
 *
 * @author Gaspar
 */
public class TicketItemDAOImpl implements TicketItemDAO {

    private EntityManager entityManager;

    public void setEm(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    @Override
    public void createTicketItem(TicketItem ticketItem) {
        if (ticketItem == null) {
            throw new IllegalArgumentException("Error creating TicketItem object: passed TicketItem is null");
        }

        if (entityManager.getTransaction().isActive()) {
            entityManager.getTransaction().rollback();
        }

        entityManager.getTransaction().begin();
        entityManager.persist(ticketItem);
        entityManager.getTransaction().commit();

    }

    @Override
    public TicketItem getTicketItemByID(Long id) throws IllegalArgumentException {
        if (id == null || id.compareTo(new Long(1)) < 0) {
            throw new IllegalArgumentException("Given ID is null or not within a valid range");
        }

        return entityManager.find(TicketItem.class, id);
    }

    @Override
    public void updateTicketItem(TicketItem ticketItem) throws IllegalArgumentException {
        if (ticketItem == null) {
            throw new IllegalArgumentException("Given TicketItem can't be updated " + ", its ID is null");
        }

        if (entityManager.getTransaction().isActive()) {
            entityManager.getTransaction().rollback();
        }

        entityManager.getTransaction().begin();
        entityManager.merge(ticketItem);
        entityManager.getTransaction().commit();
    }

    @Override
    public void deleteTicketItem(TicketItem ticketItem) throws IllegalArgumentException {
        if (ticketItem == null) {
            throw new IllegalArgumentException("Given TicketItem can't be found "
                    + "in the database, it is null");
        }

        TicketItem thisTicket = entityManager.find(TicketItem.class, ticketItem.getTicketItemID());
        if (thisTicket != null) {
            if (entityManager.getTransaction().isActive()) {
                entityManager.getTransaction().rollback();
            }

            entityManager.getTransaction().begin();
            entityManager.remove(ticketItem);
            entityManager.getTransaction().commit();
            
        } else {
            throw new IllegalArgumentException("Given TicketItem can't be found "
                    + "in the database");
        }
    }

    @Override
    public List<TicketItem> getTicketItemsByTicket(Ticket ticket) throws IllegalArgumentException {
        if (ticket == null) {
            throw new IllegalArgumentException("Passed ticket is null");
        }
        return entityManager.createQuery("SELECT ti FROM TicketItem ti WHERE ti.ticketItemID = :ticketID")
                .setParameter("ticketID", ticket.getTicketID()).getResultList();
    }
}
