/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package library.client.controllers;

import java.util.ArrayList;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import library.entity.dto.BookDTO;
import library.entity.enums.BookStatus;
import library.entity.enums.Department;

import library.service.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
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
    BookService bookService;
    
    @Autowired
    Validator bookValidator;
    
    @RequestMapping("/")
    public ModelAndView listBooks()
    {
        return new ModelAndView("book_list");
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
            return new ModelAndView("book_list");  
        }           
    }
    
    @RequestMapping(value="/save",method=RequestMethod.GET)
    public ModelAndView saveBook(){ 
       return new ModelAndView("book_add","bookDTO",new BookDTO()); 
    }
    
    
    
    
    @RequestMapping("/show/{bookID}")
    public ModelAndView showBook(@PathVariable String bookID)
    {
        return new ModelAndView();
    } 
    
    @RequestMapping("/edit/{bookID}")
    public ModelAndView editBook(@PathVariable String bookID)
    {
        return new ModelAndView();
    }
    
    // db operation
    
    @RequestMapping("/delete/{bookID}")
    public ModelAndView deleteBook(@PathVariable Long bookID)
    {  
                
        
        return new ModelAndView();
    }
    
    
    @RequestMapping("/update")
    public void updateBook(HttpServletRequest request, HttpServletResponse response){
        
    }
    
    @RequestMapping(value="/insert",method= RequestMethod.POST)
    public ModelAndView insertBook(HttpServletRequest request, HttpServletResponse response){
      
        
        return new ModelAndView();
        
    }
    
    @RequestMapping(value="/getJSONList",method= RequestMethod.GET)
    public @ResponseBody String getJSONlist()
    {
        List<BookDTO> bookz = bookService.getAllBooks();
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
            sb.append(b.getDepartment());
            sb.append("\",\"");
            sb.append(b.getBookStatus());
            
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
}