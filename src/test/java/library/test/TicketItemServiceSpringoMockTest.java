/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package library.test;

import java.util.ArrayList;
import java.util.List;
import library.dao.TicketItemDAO;
import library.entity.Book;
import library.entity.TicketItem;
import library.entity.dto.BookDTO;
import library.entity.dto.TicketDTO;
import library.entity.dto.TicketItemDTO;
import library.entity.dto.UserDTO;
import library.entity.enums.BookStatus;
import library.entity.enums.Department;
import library.entity.enums.TicketItemStatus;
import library.service.TicketItemService;
import org.joda.time.DateTime;
import org.junit.After;
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
public class TicketItemServiceSpringoMockTest extends AbstractJUnit4SpringContextTests
{    
    
    @Autowired
    TicketItemService ticketItemService;
    
    @Autowired
    TicketItemDAO ticketItemDAO;
    
    //private List<BookDTO> books;
    private List<TicketItemDTO> correct;
    private List<TicketItem> correctDAOS;
    
    
    @Before
    public void init()
    {
        List<BookDTO> books = new ArrayList<>(3);
        books.add(createBookDTO("pepazdepa", "ach jaj", Department.ADULT, BookStatus.AVAILABLE));
        books.add(createBookDTO("pepazdepa", "xexe", Department.ADULT, BookStatus.AVAILABLE));
        books.add(createBookDTO("pepazdepa", "hlavninadrazi", Department.ADULT, BookStatus.AVAILABLE));
        
        List<Book> booksDAO = new ArrayList<>(3);
        booksDAO.add(createBook(new Long(1),"pepazdepa", "ach jaj", Department.ADULT, BookStatus.AVAILABLE));
        booksDAO.add(createBook(new Long(2),"pepazdepa", "xexe", Department.ADULT, BookStatus.AVAILABLE));
        booksDAO.add(createBook(null,"pepazdepa", "hlavninadrazi", Department.ADULT, BookStatus.AVAILABLE));
               
        
        
        //now lets create some ticketitems
        correct = new ArrayList<>(3);        
        correct.add(createTicketItemDTO(books.get(0), TicketItemStatus.BORROWED));
        correct.add(createTicketItemDTO(books.get(1), TicketItemStatus.BORROWED));
        correct.add(createTicketItemDTO(books.get(2), TicketItemStatus.RETURNED));
        
        correctDAOS = new ArrayList<>(3);        
        correctDAOS.add(createTicketItem(new Long(1),booksDAO.get(0), TicketItemStatus.BORROWED));
        correctDAOS.add(createTicketItem(new Long(2),booksDAO.get(1), TicketItemStatus.BORROWED));
        correctDAOS.add(createTicketItem(null,booksDAO.get(2), TicketItemStatus.RETURNED));    
        
    }
    
    
    //toto asi nejde
    @After
    public void clearMocks() {
       reset(ticketItemDAO);
    }
    
    @Test
    @DirtiesContext
    public void testCreateTicketItem(){
        
        // zavolame metodu
        ticketItemService.createTicketItem(correct.get(2));
        
        
        // overime ze bola zavolana
        verify(ticketItemDAO,times(1)).createTicketItem(correctDAOS.get(2));
    }
    
    @Test
    @DirtiesContext
    public void testGetTicketItemByID(){
        when(ticketItemDAO.getTicketItemByID(new Long(1))).thenReturn(correctDAOS.get(0));
        ticketItemService.createTicketItem(correct.get(0));
        
        
        TicketItemDTO result = ticketItemService.getTicketItemByID(new Long(1));
        
        System.out.println(result);
        
        assertEquals(new Long(1),result.getTicketItemID());
    }
    
    
    private TicketDTO createTicket(Long id,UserDTO user, DateTime borrowtime, ArrayList<TicketItemDTO> ticketItems)
    {
        TicketDTO t = new TicketDTO();
        t.setTicketID(id);
        t.setUser(user);
        t.setBorrowTime(borrowtime);
        t.setTicketItems(ticketItems);
        
        return t;
    }
    
    private UserDTO createUser(String password,String realname,String systemRole,String username)
    {
        UserDTO u = new UserDTO();
        u.setPassword(password);
        u.setRealName(realname);
        u.setSystemRole(systemRole);
        u.setUsername(username);
        return u;
    }
    
    private TicketItemDTO createTicketItemDTO(BookDTO b,TicketItemStatus status)
    {
        TicketItemDTO ti = new TicketItemDTO();        
        ti.setBook(b);
        ti.setTicketItemStatus(status);
        
        return ti;
    }
    private TicketItem createTicketItem(Long id,Book b,TicketItemStatus status)
    {
        TicketItem ti = new TicketItem();
        ti.setTicketItemID(id);
        ti.setBook(b);
        ti.setTicketItemStatus(status);
        
        return ti;
    }
    
    private BookDTO createBookDTO(String author, String title, Department department, BookStatus status)
    {
        BookDTO b = new BookDTO();
        b.setAuthor(author);
        b.setTitle(title);
        b.setDepartment(department);
        b.setBookStatus(status);
        
        return b;
    }
    
    private Book createBook(Long id,String author, String title, Department department, BookStatus status)
    {
        Book b = new Book();
        b.setBookID(id);
        b.setAuthor(author);
        b.setTitle(title);
        b.setDepartment(department);
        b.setBookStatus(status);
        
        return b;
    }
   
}
