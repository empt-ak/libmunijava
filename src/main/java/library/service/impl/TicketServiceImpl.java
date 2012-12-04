/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package library.service.impl;

import java.util.List;
import library.dao.TicketDAO;
import library.entity.Ticket;
import library.entity.User;
import library.service.TicketService;
import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * @author Nemcek
 */
@Service
public class TicketServiceImpl implements TicketService {

    @Autowired
    TicketDAO ticketDAO;

    @Override
    @Transactional
    public void createTicket(Ticket ticket) throws IllegalArgumentException {
        ticketDAO.createTicket(ticket);
    }

    @Override
    @Transactional
    public void updateTicket(Ticket ticket) throws IllegalArgumentException {
        ticketDAO.updateTicket(ticket);
    }

    @Override
    @Transactional
    public void deleteTicket(Ticket ticket) throws IllegalArgumentException {
        ticketDAO.deleteTicket(ticket);
    }

    @Override
    @Transactional
    public Ticket getTicketByID(Long id) throws IllegalArgumentException {
        return ticketDAO.getTicketByID(id);
    }

    @Override
    @Transactional
    public Ticket getLastTicketForUser(User user) throws IllegalArgumentException {
        return ticketDAO.getLastTicketForUser(user);
    }

    @Override
    @Transactional
    public List<Ticket> getTicketsInPeriodForUser(DateTime from, DateTime to, User user) throws IllegalArgumentException {
        return ticketDAO.getTicketsInPeriodForUser(from, to, user);
    }

    @Override
    @Transactional
    public List<Ticket> getAllTicketsForUser(User user) throws IllegalArgumentException {
        return ticketDAO.getAllTicketsForUser(user);
    }
}
