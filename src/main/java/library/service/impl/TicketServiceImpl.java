/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package library.service.impl;

import java.util.ArrayList;
import java.util.List;
import library.dao.TicketDAO;
import library.entity.TicketDO;
import library.entity.UserDO;
import library.entity.dto.Ticket;
import library.entity.dto.User;
import library.service.TicketService;
import org.dozer.Mapper;
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
    private TicketDAO ticketDAO;
    
    @Autowired
    Mapper mapper;

    @Override
    @Transactional
    public void createTicket(Ticket ticket) throws IllegalArgumentException {
        if(ticket == null){throw new IllegalArgumentException();}
        TicketDO ticketDO = mapper.map(ticket, TicketDO.class);
        ticketDAO.createTicket(ticketDO);
        ticket.setTicketID(ticketDO.getTicketID());
    }

    @Override
    @Transactional
    public void updateTicket(Ticket ticket) throws IllegalArgumentException {
        if(ticket == null){throw new IllegalArgumentException();}
        ticketDAO.updateTicket(mapper.map(ticket, TicketDO.class));
    }

    @Override
    @Transactional
    public void deleteTicket(Ticket ticket) throws IllegalArgumentException {
        if(ticket == null){throw new IllegalArgumentException();}
        ticketDAO.deleteTicket(mapper.map(ticket, TicketDO.class));
    }

    @Override
    @Transactional(readOnly=true)
    public Ticket getTicketByID(Long id) throws IllegalArgumentException {
        return mapper.map(ticketDAO.getTicketByID(id), Ticket.class);
    }

    @Override
    @Transactional(readOnly=true)
    public Ticket getLastTicketForUser(User user) throws IllegalArgumentException {
        if(user==null){throw new IllegalArgumentException(); }
        UserDO userDO = mapper.map(user, UserDO.class);
        return mapper.map(ticketDAO.getLastTicketForUser(userDO),Ticket.class);
    }
 
    @Override
    @Transactional(readOnly=true)
    public List<Ticket> getTicketsInPeriodForUser(DateTime from, DateTime to, User user) throws IllegalArgumentException {
        if(user==null){throw new IllegalArgumentException(); }
        UserDO userDO = mapper.map(user, UserDO.class);
        List<TicketDO> dOs = ticketDAO.getTicketsInPeriodForUser(from, to, userDO);
        List<Ticket> tickets = new ArrayList<>();
        for(TicketDO tDO :dOs)
        {
            tickets.add(mapper.map(tDO, Ticket.class));
        }
        return tickets;
    }

    @Override
    @Transactional(readOnly=true)
    public List<Ticket> getAllTicketsForUser(User user) throws IllegalArgumentException {
        if(user==null){throw new IllegalArgumentException(); }
        UserDO userDO = mapper.map(user, UserDO.class);
        List<TicketDO> dOs = ticketDAO.getAllTicketsForUser(userDO);
        List<Ticket> tickets = new ArrayList<>();
        for(TicketDO tDO :dOs)
        {
            tickets.add(mapper.map(tDO, Ticket.class));
        }
        return tickets;
    }
}
