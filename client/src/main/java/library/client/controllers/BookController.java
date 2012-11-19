/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package library.client.controllers;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import javax.persistence.NoResultException;
import library.entity.dto.BookDTO;
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
public class BookController {
  
    @Autowired
    private BookService bookService;
    
    @Autowired
    private Validator bookValidator;
    
    @RequestMapping("/")
    public ModelAndView listBooks()
    {
        return new ModelAndView("book_list","jsonURL","/getJSONList");
    }
    
    @RequestMapping(value="/save",method= RequestMethod.POST)
    public ModelAndView saveBook(@ModelAttribute("bookDTO") BookDTO bookDTO,BindingResult result, Errors errors)
    {       
        bookValidator.validate(bookDTO, errors);
        if(result.hasErrors())
        {
            return new ModelAndView("book_add","bookDTO",bookDTO); 
        }
        else
        {
            bookDTO.setBookStatus(BookStatus.AVAILABLE);
            bookService.createBook(bookDTO);
            return new ModelAndView("redirect:/book/");  
        }           
    }
    
    @RequestMapping(value="/save",method=RequestMethod.GET)
    public ModelAndView saveBook(){ 
       return new ModelAndView("book_add","bookDTO",new BookDTO()); 
    }
    
    @RequestMapping(value="/edit/{bookID}",method= RequestMethod.GET)
    public ModelAndView editBook(@PathVariable Long bookID)
    {
        return new ModelAndView("book_edit","bookDTO",bookService.getBookByID(bookID));
    }
    
    @RequestMapping(value="/edit/",method = RequestMethod.POST)
    public ModelAndView editBook(@ModelAttribute("bookDTO") BookDTO bookDTO,BindingResult result, Errors errors)
    {
        bookValidator.validate(bookDTO, errors);
        if(!LibraryValidator.isValidID(bookDTO.getBookID()))
        {
            //errors.
        }
        
        if(result.hasErrors())
        {
            return new ModelAndView("book_edit","bookDTO",bookDTO);
        }
        else
        {
            bookService.updateBook(bookDTO);
            return new ModelAndView("redirect:/book/");
        }
    }
    
    
    @RequestMapping("/show/{bookID}")
    public ModelAndView showBook(@PathVariable Long bookID)
    {
        BookDTO b = null;
        try
        {
            b = bookService.getBookByID(bookID);
        }
        catch(NoResultException nre)
        {            
        }
        if(b != null)
        {
            
            return new ModelAndView("book_show","book",b);
        }
        else
        {
            return new ModelAndView("book_show","error","notfound");
        }
    }    
   
    // db operation
    
    @RequestMapping("/delete/{bookID}")
    public ModelAndView deleteBook(@PathVariable Long bookID)
    {
        //BookDTO b = bookService.getBookByID(bookID);
        bookService.deleteBook(bookService.getBookByID(bookID));
        return new ModelAndView("redirect:/book/");
    }
    
    @RequestMapping(value="/getJSONList",method= RequestMethod.GET)
    public @ResponseBody String getJSONlist(Locale locale)
    {
        List<BookDTO> bookz = bookService.getAllBooks();
       
        return generateJSONfromList(bookz, locale);
    
    }
    
    
    private String generateJSONfromList(List <BookDTO> bookz, Locale locale) {
        
        java.util.ResourceBundle bundle = java.util.ResourceBundle.getBundle("bundle/messages",locale);
        StringBuilder sb = new StringBuilder("{ \"aaData\": [");
        for(int i =0;i<bookz.size();i++)
        {
            BookDTO b = bookz.get(i);
            
            sb.append("\r\n").append("[\"");
            sb.append(b.getBookID());
            sb.append("\",\"");
            sb.append(b.getTitle());
            sb.append("\",\"");
            sb.append(b.getAuthor());
            sb.append("\",\"");
            switch(b.getDepartment())
            {
                case ADULT: sb.append(bundle.getString("book.department.adult")); break;
                case KIDS: sb.append(bundle.getString("book.department.kids")); break;
                case SCIENTIFIC: sb.append(bundle.getString("book.department.scientific")); break;
            }
            sb.append("\",\"");
            switch(b.getBookStatus())
            {
                case AVAILABLE: sb.append(bundle.getString("book.bookstatus.available")); break;
                case NOT_AVAILABLE: sb.append(bundle.getString("book.bookstatus.unavailable")); break;
            }
            sb.append("\",\"");
            
            if(i < bookz.size()-1)
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
    
    /**
     * metoda vlozi url pre JSON ktoru si stranka zavola
     * @param categoryFilter category
     * @param value hodnota pre danu kategoriu
     * @return m&v pre dany input
     */
    @RequestMapping("/category/{categoryFilter}/{value}")
    public ModelAndView getCategoryFilter(@PathVariable String categoryFilter,@PathVariable String value)
    {
        switch(categoryFilter)
        {
            case "department" : return new ModelAndView("book_list","jsonURL","/getJSONDepartment/"+value);
            case "author" : return new ModelAndView("book_list","jsonURL","/getJSONAuthor/"+value);
            case "title" : return new ModelAndView("book_list","jsonURL","/getJSONTitle/"+value);                
        }
        
        return new ModelAndView("redirect:/");
    }
    
    @RequestMapping(value="/getJSONDepartment/{departmentValue}", method= RequestMethod.GET)    
    public @ResponseBody String getJSONByDepartment(@PathVariable String departmentValue,Locale locale)
    {
        Department d = null;
        try
        {
            d = Department.valueOf(departmentValue.toUpperCase());
        }
        catch(IllegalArgumentException iae)
        {
            
        }
        
        if(d != null)
        {
            return generateJSONfromList(bookService.getBooksByDepartment(d), locale);
        }
        else
        {
            return generateJSONfromList(new ArrayList<BookDTO>(), locale);
        }
        
    }
    
    @RequestMapping(value="/getJSONAuthor/{authorValue}", method= RequestMethod.GET)
    
    public @ResponseBody String getJSONByAuthor(@PathVariable String authorValue,Locale locale)
    {
        List<BookDTO> bookz = new ArrayList<>();
        
        if(authorValue != null)
        {
            bookz = bookService.getBooksByAuthor(authorValue);
        }
        
        return generateJSONfromList(bookz, locale);
    }
    
    @RequestMapping(value="/getJSONTitle/{titleValue}", method= RequestMethod.GET)
    
    public @ResponseBody String getJSONByTitle(@PathVariable String titleValue,Locale locale)
    {
        List<BookDTO> bookz = new ArrayList<>();

        if(titleValue != null)
        {
            bookz = bookService.searchBooksByTitle(titleValue);
        }

        return generateJSONfromList(bookz, locale);
    }
    
    /**
     * just in case we want to reset all books
     * @return 
     */
    @RequestMapping("/reset/")
    public ModelAndView resetBooks()
    {
        List<BookDTO> bookz = bookService.getAllBooks();
        for(BookDTO b : bookz)
        {
            b.setBookStatus(BookStatus.AVAILABLE);
            bookService.updateBook(b);
        }
        
        return new ModelAndView("redirect:/book/");
    }
    
    @RequestMapping("/install")
    public ModelAndView install()
    {
       return new ModelAndView(); 
    }
}
