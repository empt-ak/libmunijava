/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package library.test;

import java.util.ArrayList;
import java.util.List;
import library.dao.BookDAO;
import library.entity.Book;
import library.entity.dto.BookDTO;
import library.entity.enums.BookStatus;
import library.entity.enums.Department;
import library.service.BookService;
import org.junit.Test;
import org.kubek2k.springockito.annotations.SpringockitoContextLoader;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.AbstractJUnit4SpringContextTests;
import static org.mockito.Mockito.*;
import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.runner.RunWith;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

/**
 *
 * @author Rainbow
 */
@ContextConfiguration(loader = SpringockitoContextLoader.class,
locations = "file:src/main/resources/spring/applicationContext-mock.xml")
@RunWith(SpringJUnit4ClassRunner.class)
public class BookServiceTest extends AbstractJUnit4SpringContextTests {

    @Autowired
    BookService bookService;
    @Autowired
    BookDAO bookDAO;
    private List<BookDTO> listDTOS;
    private List<Book> listDAOS;

    @Before
    public void init() {
        listDTOS = new ArrayList<>(3);
        listDTOS.add(TestUtils.createBookDTO(new Long(1),"pepazdepa", "ach jaj", Department.ADULT, BookStatus.AVAILABLE));
        listDTOS.add(TestUtils.createBookDTO(new Long(2),"pepazdepa", "xexe", Department.KIDS, BookStatus.AVAILABLE));
        listDTOS.add(TestUtils.createBookDTO(null,"pepazdepa", "hlavninadrazi", Department.ADULT, BookStatus.AVAILABLE));

        listDAOS = new ArrayList<>(3);
        listDAOS.add(TestUtils.createBook(new Long(1), "pepazdepa", "ach jaj", Department.ADULT, BookStatus.AVAILABLE));
        listDAOS.add(TestUtils.createBook(new Long(2), "pepazdepa", "xexe", Department.KIDS, BookStatus.AVAILABLE));
        listDAOS.add(TestUtils.createBook(null, "pepazdepa", "hlavninadrazi", Department.ADULT, BookStatus.AVAILABLE));
    }


    @Test
    @DirtiesContext
    public void testGetBookByID() {
        //given
        when(bookDAO.getBookByID(new Long(1))).thenReturn(listDAOS.get(0));
        bookService.createBook(listDTOS.get(0));

        // when
        BookDTO result = bookService.getBookByID(new Long(1));

        //then
        assertEquals(new Long(1), result.getBookID());
    }

    @Test
    @DirtiesContext
    public void testUpdateBook() {
        // given
        BookDTO bdto = listDTOS.get(0);
        Book b = listDAOS.get(0);
        b.setTitle("Winnetou");
        when(bookDAO.getBookByID(new Long(1))).thenReturn(b);

        //when
        bookService.createBook(bdto);
        bdto.setTitle("Winnetou");
        bookService.updateBook(bdto);
        BookDTO result = bookService.getBookByID(new Long(1));

        //then        
        assertEquals(result.getTitle(), b.getTitle());

    }

    @Test
    @DirtiesContext
    public void testDeleteBook() {

        //given
        when(bookDAO.getBookByID(new Long(2))).thenReturn(null);


        //when
        bookService.createBook(listDTOS.get(1));
        bookService.deleteBook(listDTOS.get(1));
        BookDTO result = bookService.getBookByID(new Long(2));

        //then
        assertNull(result);

    }

    @Test
    @DirtiesContext
    public void testGetAllBooks() {

        // given
        List<Book> tempList = listDAOS.subList(0, 2); // su tam 3 knihy ale posledna nema ID, sluzi pre iny test
  
        when(bookDAO.getAllBooks()).thenReturn(tempList);

        //when
        for (int i = 0; i < 3; i++) {
            bookService.createBook(listDTOS.get(i));
        }
        List<BookDTO> resultList = bookService.getAllBooks();

        //then
        assertEquals(2, resultList.size());        
        
        for(int i =0;i<2;i++)
        {
            equalsWithoutID(resultList.get(i),listDTOS.get(i));
        }
    }

    @Test
    @DirtiesContext
    public void testGetBooksByAuthor() 
    {
        //given
        List<BookDTO> tempDTO = new ArrayList<>();
        tempDTO.add(listDTOS.get(0));
        tempDTO.add(listDTOS.get(1));
        tempDTO.add(TestUtils.createBookDTO(new Long(4),"stvrtya", "stvrtynazov", Department.ADULT, BookStatus.AVAILABLE));
        
        List<Book> tempDAOz = new ArrayList<>();
        tempDAOz.add(listDAOS.get(0));
        tempDAOz.add(listDAOS.get(1));       
        
        when(bookDAO.getBooksByAuthor("pepazdepa")).thenReturn(tempDAOz);
        
        
        //when
        for(BookDTO b : tempDTO)
        {
            bookService.createBook(b);
        }
        List<BookDTO> resulList = bookService.getBooksByAuthor("pepazdepa");
        
        
        //then
        assertEquals(2,resulList.size());
    }

    @Test
    @DirtiesContext
    public void testSearchBooksByTitle() 
    {
        // given
        List<Book> temp = new ArrayList<>();
        temp.add(listDAOS.get(0));
        when(bookDAO.searchBooksByTitle("ach")).thenReturn(temp);
        
        //when
        for(BookDTO b : listDTOS)
        {
            bookService.createBook(b);
        }
        List<BookDTO> resultList = bookService.searchBooksByTitle("ach");
        
        
        //then
        assertEquals(1,temp.size());
        assertEquals(listDAOS.get(0).getBookID(), resultList.get(0).getBookID());
    }

    @Test
    @DirtiesContext
    public void testGetBooksByDepartment() {

        // given
        List<Book> temp = new ArrayList<>();
        temp.add(listDAOS.get(1));
        when(bookDAO.getBooksByDepartment(Department.KIDS)).thenReturn(temp);
        
        //when
        for(BookDTO b : listDTOS)
        {
            bookService.createBook(b);
        }
        List<BookDTO> resultList = bookService.getBooksByDepartment(Department.KIDS);
        
        
        //then
        assertEquals(1, resultList.size());
        assertEquals(new Long(2), temp.get(0).getBookID());
    }
    
    private void equalsWithoutID(BookDTO b1,BookDTO b2)
    {
        assertEquals(b1.getAuthor(), b2.getAuthor());
        assertEquals(b1.getBookStatus(), b2.getBookStatus());
        assertEquals(b1.getDepartment(), b2.getDepartment());
        assertEquals(b1.getTitle(), b2.getTitle());
    }
}