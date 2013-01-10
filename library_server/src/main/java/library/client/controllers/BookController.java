/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package library.client.controllers;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Locale;
import javax.persistence.NoResultException;
import javax.servlet.http.HttpServletRequest;
import library.entity.dto.BookDTO;
import library.entity.dto.UserDTO;
import library.entity.enums.BookStatus;
import library.entity.enums.Department;
import library.service.BookService;
import library.utils.aop.validators.LibraryValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

/**
 *
 * @author emptak
 */
@Controller
@RequestMapping("/book")
public class BookController 
{

    @Autowired
    private BookService bookService;
    @Autowired
    private Validator bookValidator;

    @RequestMapping("/")
    public ModelAndView listBooks() 
    {
        return new ModelAndView("book_list", "jsonURL", "/getJSONList");
    }

    /**     
     * @param bookDTO book containing values from form
     * @param result
     * @param errors
     * @return redirect to form on any error redirect to /book/ otherwise
     */
    @RequestMapping(value = "/save", method = RequestMethod.POST)
    public ModelAndView saveBook(@ModelAttribute("bookDTO") BookDTO bookDTO, BindingResult result, Errors errors) 
    {
//        UserDTO inSession = (UserDTO) request.getSession().getAttribute("USER");
//        if (inSession != null && inSession.getSystemRole().equals("ADMINISTRATOR")) 
//        {
            bookValidator.validate(bookDTO, errors);
            if (result.hasErrors()) 
            {
                return new ModelAndView("book_add", "bookDTO", bookDTO);
            } 
            else 
            {
                bookDTO.setBookStatus(BookStatus.AVAILABLE);
                bookService.createBook(bookDTO);
                return new ModelAndView("redirect:/book/");
            }
//        }
//        return new ModelAndView("redirect:/");
    }

    /**
     * request mapper for saving book. method can be called by anyone since we
     * have check in jsp page and if user is not admin access denied is shown
     *
     * @return M&V book_add
     */
    @RequestMapping(value = "/save", method = RequestMethod.GET)
    public ModelAndView saveBook() 
    {
        return new ModelAndView("book_add", "bookDTO", new BookDTO());
    }

    /**
     * request mapper for book editing. method can be called by anyone since we
     * have check in jsp page and if user is not admin access denied is shown
     *
     * @param bookID id of book that we want to edit
     * @return M&V book_edit
     */
    @RequestMapping(value = "/edit/{bookID}", method = RequestMethod.GET)
    public ModelAndView editBook(@PathVariable Long bookID) 
    {
        return new ModelAndView("book_edit", "bookDTO", bookService.getBookByID(bookID));
    }

    /**
     * book so we check his rights as first then rest of method can proceed
     * @param bookDTO book containing form values
     * @param result
     * @param errors
     * @return redirect to M&V with set values from previous attempt (if any
     * error ocures), redirect to /book/ otherwise
     */
    @RequestMapping(value = "/edit/", method = RequestMethod.POST)
    public ModelAndView editBook(@ModelAttribute("bookDTO") BookDTO bookDTO, BindingResult result, Errors errors) 
    {
//        UserDTO inSession = (UserDTO) request.getSession().getAttribute("USER");
//        if (inSession != null && inSession.getSystemRole().equals("ADMINISTRATOR")) 
//        {
            bookValidator.validate(bookDTO, errors);
            if (!LibraryValidator.isValidID(bookDTO.getBookID())) 
            {
                //errors.
            }

            if (result.hasErrors()) 
            {
                return new ModelAndView("book_edit", "bookDTO", bookDTO);
            } 
            else 
            {
                bookService.updateBook(bookDTO);
                return new ModelAndView("redirect:/book/");
            }
//        }
//
//        return new ModelAndView("redirect:/");
    }

    /**
     * Request mapper for showing book details. Book can be showed by anyone
     * therefore no check is needed
     *
     * @param bookID
     * @return
     */
    @RequestMapping("/show/{bookID}")
    public ModelAndView showBook(@PathVariable Long bookID) 
    {
        BookDTO b = null;
        try 
        {
            b = bookService.getBookByID(bookID);
        } 
        catch (NoResultException nre) 
        {
        }
        if (b != null) 
        {
            return new ModelAndView("book_show", "book", b);
        } 
        else 
        {
            return new ModelAndView("book_show", "error", "notfound");
        }
    }

    /**
     * Request mapping for book deleting. only administrator can delete book so
     * we need check if logged user is administrator.
     *
     * @param bookID
     * @param request
     * @return redirect to /book/ or redirect to / if admin is not logged in
     */
    @RequestMapping("/delete/{bookID}")
    public ModelAndView deleteBook(@PathVariable Long bookID, HttpServletRequest request) 
    {

//        UserDTO inSession = (UserDTO) request.getSession().getAttribute("USER");
//        if (inSession != null && inSession.getSystemRole().equals("ADMINISTRATOR")) 
//        {
            BookDTO book = new BookDTO();
            book.setBookID(bookID);


            bookService.deleteBook(bookService.getBookByID(book.getBookID()));

            return new ModelAndView("redirect:/book/");
//        }
//
//        return new ModelAndView("redirect:/");
    }

    /**
     * metoda vlozi url pre JSON ktoru si stranka zavola
     *
     * @param categoryFilter category
     * @param value hodnota pre danu kategoriu
     * @return m&v pre dany input
     */
    @RequestMapping("/category/{categoryFilter}/{value}")
    public ModelAndView getCategoryFilter(@PathVariable String categoryFilter, @PathVariable String value) 
    {
        switch (categoryFilter) 
        {
            case "department":
                return new ModelAndView("book_list", "jsonURL", "/getJSONDepartment/" + value);
            case "author":
                return new ModelAndView("book_list", "jsonURL", "/getJSONAuthor/" + value);
            case "title":
                return new ModelAndView("book_list", "jsonURL", "/getJSONTitle/" + value);
        }

        return new ModelAndView("redirect:/");
    }

    /**
     * Request mapper for getting all books from database in form of json.
     *
     * @param locale current session locale for translating enums inside class
     * {status, department}
     * @return json containing all books
     */
    @RequestMapping(value = "/getJSONList", method = RequestMethod.GET)
    public @ResponseBody
    String getJSONlist(Locale locale) 
    {
        List<BookDTO> bookz = bookService.getAllBooks();

        return generateJSONfromList(bookz, locale,false);
    }

    /**
     * Request mapper for obtaining all books based on department from database
     *
     * @param departmentValue department from which we want books
     * @param locale current locale session for enums
     * @return json containing all books from given department
     */
    @RequestMapping(value = "/getJSONDepartment/{departmentValue}", method = RequestMethod.GET)
    public @ResponseBody
    String getJSONByDepartment(@PathVariable String departmentValue, Locale locale) 
    {
        Department d = null;
        try 
        {
            d = Department.valueOf(departmentValue.toUpperCase());
        } 
        catch (IllegalArgumentException iae) 
        {
        }

        if (d != null) 
        {
            return generateJSONfromList(bookService.getBooksByDepartment(d), locale,false);
        } 
        else 
        {
            return generateJSONfromList(new ArrayList<BookDTO>(), locale,false);
        }
    }

    /**
     * Request mapper for obtaining all books written by specific author from
     * database.
     *
     * @param authorValue name of author whose books we want to get
     * @param locale current locale session for enums
     * @return json containing all books writen by specific author
     */
    @RequestMapping(value = "/getJSONAuthor/{authorValue}", method = RequestMethod.GET)
    public @ResponseBody
    String getJSONByAuthor(@PathVariable String authorValue, Locale locale) 
    {
        List<BookDTO> bookz = new ArrayList<>();

        if (authorValue != null) 
        {
            bookz = bookService.getBooksByAuthor(authorValue);
        }

        return generateJSONfromList(bookz, locale,false);
    }

    /**
     * Request mapper for obtaining all books from database based on book title.
     *
     * @param titleValue title of book
     * @param locale current locale for session
     * @return json containing all books with given title
     */
    @RequestMapping(value = "/getJSONTitle/{titleValue}", method = RequestMethod.GET)
    public @ResponseBody String getJSONByTitle(@PathVariable String titleValue, Locale locale) 
    {
        List<BookDTO> bookz = new ArrayList<>();

        if (titleValue != null) 
        {
            bookz = bookService.searchBooksByTitle(titleValue);
        }

        return generateJSONfromList(bookz, locale,false);
    }
    
     /**
     * request mapper for obtaining last 5 books from database. this is used by main
     * (index) page
     *
     * @param locale locale holding current locale that is used for enums
     * @return string value of last 5 books in json format
     */
    @RequestMapping(value = "/getJSONlastbooks", method = RequestMethod.GET)
      public @ResponseBody String getlastBooks(Locale locale) 
    {
        List<BookDTO> b = bookService.getAllBooks();
        Collections.sort(b,bComparator);
        b = b.subList(0, Math.min(b.size(), 5));
        return generateJSONfromList(b, locale,true);
    }

    /**
     * Request mapper for reseting all books, all books are set to AVAILABLE.
     * just in case we want to reset all books
     *
     * @return redirect to /book/
     */
    @RequestMapping("/reset/")
    public ModelAndView resetBooks() 
    {
        List<BookDTO> bookz = bookService.getAllBooks();
        for (BookDTO b : bookz) 
        {
            b.setBookStatus(BookStatus.AVAILABLE);
            bookService.updateBook(b);
        }

        return new ModelAndView("redirect:/book/");
    }
    

    /**
     * Method builds json String from given list of books. Datatables requeres
     * following patter
     * <pre>
     * { "aaData" :[
     * ["bookID1" ,"bookTitle1", "bookAuthor1","bookDepartment1","bookAvailability1",""],
     * ["bookID2" ,"bookTitle2", "bookAuthor2","bookDepartment2","bookAvailability2",""],
     * ...
     * ["bookIDn" ,"bookTitlen", "bookAuthorn","bookDepartmentn","bookAvailability2",""]
     * ]}
     * </pre>
     * last _column_ is empty since we have in table one more column for actions like delete, show details, edit or add to ticket
     *
     * if reduced is true first and last column is omitted
     * 
     * @param bookz list of books from which we want to generate json format
     * @param locale locale for enums
     * @param reduced if true first and last column is omitted
     * @return json String build from list of books
     */
    private String generateJSONfromList(List<BookDTO> bookz, Locale locale,boolean reduced) 
    {
        java.util.ResourceBundle bundle = java.util.ResourceBundle.getBundle("bundle/messages", locale);
        StringBuilder sb = new StringBuilder("{ \"aaData\": [");
        for (int i = 0; i < bookz.size(); i++) {
            BookDTO b = bookz.get(i);

            sb.append("\r\n").append("[\"");
            if(!reduced)
            {
                sb.append(b.getBookID());
                sb.append("\",\"");                
            }            
            sb.append(b.getTitle());
            sb.append("\",\"");
            sb.append(b.getAuthor());
            sb.append("\",\"");
            switch (b.getDepartment()) 
            {
                case ADULT:
                    sb.append(bundle.getString("book.department.adult"));
                    break;
                case KIDS:
                    sb.append(bundle.getString("book.department.kids"));
                    break;
                case SCIENTIFIC:
                    sb.append(bundle.getString("book.department.scientific"));
                    break;
            }
            sb.append("\",\"");
            switch (b.getBookStatus())
            {
                case AVAILABLE:
                    sb.append(bundle.getString("book.bookstatus.available"));
                    break;
                case NOT_AVAILABLE:
                    sb.append(bundle.getString("book.bookstatus.unavailable"));
                    break;
            }
            if(!reduced)
            {
                sb.append("\",\"");
            }            

            if (i < bookz.size() - 1) 
            {
                sb.append("\"],");
            } 
            else 
            {
                sb.append("\"]");
            }
        }
        sb.append("\r\n] }");

        return sb.toString();
    }   
    
    private static java.util.Comparator<BookDTO> bComparator = new Comparator<BookDTO>() 
    {
        @Override
        public int compare(BookDTO o1, BookDTO o2) 
        {
            return o2.getBookID().compareTo(o1.getBookID());
        }
    };
}
