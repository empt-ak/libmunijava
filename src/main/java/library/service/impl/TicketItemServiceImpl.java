/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package library.service.impl;

import java.util.List;
import library.dao.TicketItemDAO;
import library.entity.Ticket;
import library.entity.TicketItem;
import library.service.TicketItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * @author Gajdos
 */
@Service
public class TicketItemServiceImpl implements TicketItemService {

    @Autowired
    private TicketItemDAO ticketItemDAO;

    @Override
    @Transactional
    public void createTicketItem(TicketItem ticketItem) throws IllegalArgumentException {
        ticketItemDAO.createTicketItem(ticketItem);
    }

    @Override
    @Transactional
    public void updateTicketItem(TicketItem ticketItem) throws IllegalArgumentException {
        ticketItemDAO.updateTicketItem(ticketItem);
    }

    @Override
    @Transactional
    public void deleteTicketItem(TicketItem ticketItem) throws IllegalArgumentException {
        ticketItemDAO.deleteTicketItem(ticketItem);
    }

    @Override
    @Transactional(readOnly=true)
    public TicketItem getTicketItemByID(Long id) throws IllegalArgumentException {
        return ticketItemDAO.getTicketItemByID(id);
    }

    @Override
    @Transactional(readOnly=true)
    public List<TicketItem> getTicketItemsByTicket(Ticket ticket) throws IllegalArgumentException {
        return ticketItemDAO.getTicketItemsByTicket(ticket);
    }
}
