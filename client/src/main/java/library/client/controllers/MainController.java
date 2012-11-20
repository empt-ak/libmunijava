/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package library.client.controllers;

import library.client.RssLoader;
import library.entity.dto.BookDTO;
import library.entity.dto.UserDTO;
import library.entity.enums.BookStatus;
import library.entity.enums.Department;
import library.service.BookService;
import library.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

/**
 *
 * @author emptak
 */
@Controller
@RequestMapping("/")
public class MainController
{
    @Autowired
    private UserService userService;
    
    @Autowired
    private BookService bookService;
    
    
    @RequestMapping("/")
    public ModelAndView show() throws Exception 
    {
        if(bookService.getAllBooks().isEmpty())
        {
            install();            
        }
        return new ModelAndView("index","svnentries",RssLoader.getRSSItems(0, 10));
    } 
    
    @RequestMapping("makeadmin/{username}")
    public ModelAndView makeAdmin(@PathVariable String username)
    {
        UserDTO u = userService.getUserByUsername(username);
        if(u != null)
        {
            u.setSystemRole("ADMINISTRATOR");
            userService.updateUser(u);
        }
        
        return new ModelAndView("redirect:/");
    }
    
    
    
    
    
    private void install()
    {
        bookService.createBook(createBook("Emma", "Jane Austen", Department.ADULT, BookStatus.AVAILABLE));
        bookService.createBook(createBook("Pride & Prejudice", "Jane Austen", Department.ADULT, BookStatus.AVAILABLE));
        bookService.createBook(createBook("Sense and Sensibility", "Jane Austen", Department.ADULT, BookStatus.AVAILABLE));
        bookService.createBook(createBook("Harry Potter and the Philosophers Stone", "J.K. Rowling", Department.ADULT, BookStatus.AVAILABLE));
        bookService.createBook(createBook("Harry Potter and the Chamber of Secrets", "J.K. Rowling", Department.ADULT, BookStatus.AVAILABLE));
        bookService.createBook(createBook("Harry Potter and the Prisoner of Azkaban", "J.K. Rowling", Department.ADULT, BookStatus.AVAILABLE));
        bookService.createBook(createBook("On the Generalized Theory of Gravitation", "Albert Einstein", Department.SCIENTIFIC, BookStatus.AVAILABLE));
        bookService.createBook(createBook("čerešnička", "ľščťžýáíéúäň", Department.ADULT, BookStatus.AVAILABLE));
        bookService.createBook(createBook("The Grand Design", "Stephen Hawking", Department.SCIENTIFIC, BookStatus.AVAILABLE));
        bookService.createBook(createBook("Spring Security 3", "Peter Mularien", Department.SCIENTIFIC, BookStatus.AVAILABLE));
        bookService.createBook(createBook("Tri gaštanové kone", "Margita Figuli", Department.ADULT, BookStatus.AVAILABLE));
        bookService.createBook(createBook("Spring Security", "Peter Mularien", Department.SCIENTIFIC, BookStatus.AVAILABLE));
        
    }
    
    private BookDTO createBook(String title, String author, Department department, BookStatus status) {
        BookDTO b = new BookDTO();
        b.setTitle(title);
        b.setAuthor(author);
        b.setDepartment(department);
        b.setBookStatus(status);

        return b;
    }
    
}
