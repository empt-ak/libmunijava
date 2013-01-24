/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package library.service.impl;

import java.util.ArrayList;
import java.util.List;
import library.dao.TicketDAO;
import library.entity.Ticket;
import library.entity.User;
import library.entity.dto.TicketDTO;
import library.entity.dto.UserDTO;
import library.service.TicketService;
import org.dozer.Mapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
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
    private Mapper mapper;

    @PreAuthorize("hasRole('ROLE_USER')")
    @Override
    @Transactional
    public void createTicket(TicketDTO ticketDTO) throws IllegalArgumentException {
        if(ticketDTO == null){throw new IllegalArgumentException("ERROR: Given ticket is null");}
        Ticket ticketDO = mapper.map(ticketDTO, Ticket.class);
        ticketDAO.createTicket(ticketDO);
        if (ticketDO != null) {
            ticketDTO.setTicketID(ticketDO.getTicketID());
        }
    }

    @PreAuthorize("hasRole('ROLE_USER')")
    @Override
    @Transactional
    public void updateTicket(TicketDTO ticketDTO) throws IllegalArgumentException {
        if(ticketDTO == null){throw new IllegalArgumentException("ERROR: Given ticket is null");}
        ticketDAO.updateTicket(mapper.map(ticketDTO, Ticket.class));
    }
    
    @PreAuthorize("hasRole('ROLE_ADMINISTRATOR')")
    @Override
    @Transactional
    public void deleteTicket(TicketDTO ticketDTO) throws IllegalArgumentException {
        if(ticketDTO == null){throw new IllegalArgumentException("ERROR: Given ticket is null");}
        ticketDAO.deleteTicket(mapper.map(ticketDTO, Ticket.class));
    }

    @PreAuthorize("hasRole('ROLE_USER')")
    @Override
    @Transactional(readOnly=true)
    public TicketDTO getTicketByID(Long id) throws IllegalArgumentException {
        Ticket ticket = ticketDAO.getTicketByID(id);
        if(ticket != null){
            return mapper.map(ticket, TicketDTO.class);
        }else{
            return null;
        }        
    }
    
    @PreAuthorize("hasRole('ROLE_USER')")
    @Override
    @Transactional(readOnly=true)
    public TicketDTO getLastTicketForUser(UserDTO userDTO) throws IllegalArgumentException {
        if(userDTO==null){throw new IllegalArgumentException("ERROR: Given user is null"); }
        User user = mapper.map(userDTO, User.class);
        return mapper.map(ticketDAO.getLastTicketForUser(user),TicketDTO.class);
    } 
    
    @PreAuthorize("hasRole('ROLE_USER')")
    @Override
    @Transactional(readOnly=true)
    public List<TicketDTO> getAllTicketsForUser(UserDTO userDTO) throws IllegalArgumentException {
        if(userDTO==null){throw new IllegalArgumentException("ERROR: Given user is null"); }
        User user = mapper.map(userDTO, User.class);
        List<Ticket> tickets = ticketDAO.getAllTicketsForUser(user);
        List<TicketDTO> ticketDTOs = new ArrayList<>();
        for(Ticket tDO : tickets)
        {
            ticketDTOs.add(mapper.map(tDO, TicketDTO.class));
        }
        return ticketDTOs;
    }
}
