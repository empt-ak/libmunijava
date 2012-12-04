/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package library.service.impl;

import java.util.ArrayList;
import java.util.List;
import library.dao.TicketItemDAO;
import library.entity.Ticket;
import library.entity.TicketItem;
import library.entity.dto.TicketDTO;
import library.entity.dto.TicketItemDTO;
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
    private Mapper mapper;

    @Override
    @Transactional
    public void createTicketItem(TicketItemDTO ticketItemDTO) throws IllegalArgumentException {
        if(ticketItemDTO == null){throw new IllegalArgumentException("ERROR: Given ticketitem is null"); }
        TicketItem ticketItem = mapper.map(ticketItemDTO, TicketItem.class);
        ticketItemDAO.createTicketItem(ticketItem);
        ticketItemDTO.setTicketItemID(ticketItem.getTicketItemID());
    }

    @Override
    @Transactional
    public void updateTicketItem(TicketItemDTO ticketItemDTO) throws IllegalArgumentException {
        if(ticketItemDTO == null){throw new IllegalArgumentException("ERROR: Given ticketitem is null"); }
        ticketItemDAO.updateTicketItem(mapper.map(ticketItemDTO, TicketItem.class));
    }

    @Override
    @Transactional
    public void deleteTicketItem(TicketItemDTO ticketItemDTO) throws IllegalArgumentException {
        if(ticketItemDTO == null){throw new IllegalArgumentException("ERROR: Given ticketitem is null"); }
        ticketItemDAO.deleteTicketItem(mapper.map(ticketItemDTO, TicketItem.class));
    }

    @Override
    @Transactional(readOnly=true)
    public TicketItemDTO getTicketItemByID(Long id) throws IllegalArgumentException {
        TicketItem ticketItem = ticketItemDAO.getTicketItemByID(id);
        if(ticketItem != null){
            return mapper.map(ticketItem, TicketItemDTO.class);
        }else{
            return null;
        }
        
    }

    @Override
    @Transactional(readOnly=true)
    public List<TicketItemDTO> getTicketItemsByTicket(TicketDTO ticketDTO) throws IllegalArgumentException {
        if(ticketDTO == null){ throw new IllegalArgumentException("ERROR: Given ticket is null");}
        List<TicketItem> ticketItems = ticketItemDAO.getTicketItemsByTicket(mapper.map(ticketDTO, Ticket.class));
        List<TicketItemDTO> ticketItemDTOs = new ArrayList<>();
        for(TicketItem tio : ticketItems)
        {
            ticketItemDTOs.add(mapper.map(tio, TicketItemDTO.class));
        }
        return ticketItemDTOs;
    }
}
