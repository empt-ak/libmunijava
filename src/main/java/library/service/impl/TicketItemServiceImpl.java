/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package library.service.impl;

import java.util.ArrayList;
import java.util.List;
import library.dao.TicketItemDAO;
import library.entity.TicketDO;
import library.entity.TicketItemDO;
import library.entity.dto.Ticket;
import library.entity.dto.TicketItem;
import library.service.TicketItemService;
import org.dozer.Mapper;
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
    
    @Autowired
    Mapper mapper;

    @Override
    @Transactional
    public void createTicketItem(TicketItem ticketItem) throws IllegalArgumentException {
        if(ticketItem == null){throw new IllegalArgumentException(); }
        TicketItemDO ticketItemDO = mapper.map(ticketItem, TicketItemDO.class);
        ticketItemDAO.createTicketItem(ticketItemDO);
        ticketItem.setTicketItemID(ticketItemDO.getTicketItemID());
    }

    @Override
    @Transactional
    public void updateTicketItem(TicketItem ticketItem) throws IllegalArgumentException {
        if(ticketItem == null){throw new IllegalArgumentException(); }
        ticketItemDAO.updateTicketItem(mapper.map(ticketItem, TicketItemDO.class));
    }

    @Override
    @Transactional
    public void deleteTicketItem(TicketItem ticketItem) throws IllegalArgumentException {
        if(ticketItem == null){throw new IllegalArgumentException(); }
        ticketItemDAO.deleteTicketItem(mapper.map(ticketItem, TicketItemDO.class));
    }

    @Override
    @Transactional(readOnly=true)
    public TicketItem getTicketItemByID(Long id) throws IllegalArgumentException {
        return mapper.map(ticketItemDAO.getTicketItemByID(id), TicketItem.class);
    }

    @Override
    @Transactional(readOnly=true)
    public List<TicketItem> getTicketItemsByTicket(Ticket ticket) throws IllegalArgumentException {
        if(ticket== null){ throw new IllegalArgumentException();}
        List<TicketItemDO> ticketItemDOs = ticketItemDAO.getTicketItemsByTicket(mapper.map(ticket, TicketDO.class));
        List<TicketItem> ticketItems = new ArrayList<>();
        for(TicketItemDO tio : ticketItemDOs)
        {
            ticketItems.add(mapper.map(tio, TicketItem.class));
        }
        return ticketItems;
    }
}
