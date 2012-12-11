/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package library.test;

import java.util.ArrayList;
import java.util.List;
import library.dao.TicketItemDAO;
import library.entity.Book;
import library.entity.Ticket;
import library.entity.TicketItem;
import library.entity.User;
import library.entity.dto.BookDTO;
import library.entity.dto.TicketDTO;
import library.entity.dto.TicketItemDTO;
import library.entity.dto.UserDTO;
import library.entity.enums.BookStatus;
import library.entity.enums.Department;
import library.entity.enums.TicketItemStatus;
import library.service.TicketItemService;
import org.joda.time.DateTime;
import org.junit.Test;
import org.kubek2k.springockito.annotations.SpringockitoContextLoader;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.AbstractJUnit4SpringContextTests;
import static org.mockito.Mockito.*;
import org.junit.Before;
import org.junit.runner.RunWith;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import static org.junit.Assert.*;

/**
 *
 * @author emptak
 */
@ContextConfiguration(loader = SpringockitoContextLoader.class,
locations = "file:src/main/resources/spring/applicationContext-mock.xml")
@RunWith(SpringJUnit4ClassRunner.class)
public class TicketItemServiceTest extends AbstractJUnit4SpringContextTests {

    @Autowired
    private TicketItemService ticketItemService;
    @Autowired
    private TicketItemDAO ticketItemDAO;    
    private List<TicketItemDTO> ticketDTOS;
    private List<TicketItem> ticketsDAOs;

    @Before
    public void init() {
        List<BookDTO> books = new ArrayList<>(3);
        books.add(TestUtils.createBookDTO(new Long(1), "pepazdepa", "ach jaj", Department.ADULT, BookStatus.AVAILABLE));
        books.add(TestUtils.createBookDTO(new Long(2), "pepazdepa", "xexe", Department.ADULT, BookStatus.AVAILABLE));

        List<Book> booksDAO = new ArrayList<>(3);
        booksDAO.add(TestUtils.createBook(new Long(1), "pepazdepa", "ach jaj", Department.ADULT, BookStatus.AVAILABLE));
        booksDAO.add(TestUtils.createBook(new Long(2), "pepazdepa", "xexe", Department.ADULT, BookStatus.AVAILABLE));

        ticketDTOS = new ArrayList<>(3);
        ticketDTOS.add(TestUtils.createTicketItemDTO(new Long(1),books.get(0), TicketItemStatus.BORROWED));
        ticketDTOS.add(TestUtils.createTicketItemDTO(new Long(2),books.get(1), TicketItemStatus.BORROWED));
        ticketDTOS.add(TestUtils.createTicketItemDTO(null,new BookDTO(), TicketItemStatus.RETURNED));

        ticketsDAOs = new ArrayList<>(3);
        ticketsDAOs.add(TestUtils.createTicketItem(new Long(1), booksDAO.get(0), TicketItemStatus.BORROWED));
        ticketsDAOs.add(TestUtils.createTicketItem(new Long(2), booksDAO.get(1), TicketItemStatus.BORROWED));
        ticketsDAOs.add(TestUtils.createTicketItem(null, new Book(), TicketItemStatus.RETURNED));        
    }

    @Test
    @DirtiesContext
    public void testGetTicketItemByID() {
        // given
        when(ticketItemDAO.getTicketItemByID(new Long(1))).thenReturn(ticketsDAOs.get(0));
        ticketItemService.createTicketItem(ticketDTOS.get(0));

        //when        
        TicketItemDTO result = ticketItemService.getTicketItemByID(new Long(1));

        //then
        assertEquals(new Long(1), result.getTicketItemID());
    }

    @Test
    @DirtiesContext
    public void testUpdateTicketItem() {
        // given
        TicketItemDTO tiDTO = ticketDTOS.get(0);
        TicketItem ti = ticketsDAOs.get(0);
        ti.setTicketItemStatus(TicketItemStatus.RETURNED);
        when(ticketItemDAO.getTicketItemByID(new Long(1))).thenReturn(ti);

        //when
        ticketItemService.createTicketItem(tiDTO);
        tiDTO.setTicketItemStatus(TicketItemStatus.RETURNED);
        ticketItemService.updateTicketItem(tiDTO);
        TicketItemDTO result = ticketItemService.getTicketItemByID(new Long(1));

        //then        
        assertEquals(result.getTicketItemStatus(), ti.getTicketItemStatus());
    }

    @Test
    @DirtiesContext
    public void testDeleteTicketItem() {
        //given
        when(ticketItemDAO.getTicketItemByID(new Long(2))).thenReturn(null);


        //when
        ticketItemService.createTicketItem(ticketDTOS.get(1));
        ticketItemService.deleteTicketItem(ticketDTOS.get(1));
        TicketItemDTO result = ticketItemService.getTicketItemByID(new Long(2));

        //then
        assertNull(result);
    }

    @Test
    @DirtiesContext
    public void testGetTicketItemsByTicket() {
        //given
        TicketDTO tidto1 = TestUtils.createTicketDTO(new Long(1), new UserDTO(), new DateTime(2012, 10, 7, 12, 00), ticketDTOS.subList(0, 2));//treti ti nema id
        Ticket t1 = TestUtils.createTicket(new Long(1), new User(), new DateTime(2012, 10, 7, 12, 00), ticketsDAOs.subList(0, 2));
        when(ticketItemDAO.getTicketItemsByTicket(t1)).thenReturn(ticketsDAOs.subList(0, 2));
        
        //when        
        ticketItemService.createTicketItem(ticketDTOS.get(0));
        ticketItemService.createTicketItem(ticketDTOS.get(1));
        List<TicketItemDTO> result = ticketItemService.getTicketItemsByTicket(tidto1);
        
        //then        
        assertEquals(2,result.size());        
    }
}
