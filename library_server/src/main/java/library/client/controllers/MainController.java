/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package library.client.controllers;

import javax.annotation.PostConstruct;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import library.client.Installer;
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
    private Installer installer;
    
    @RequestMapping("/")
    public ModelAndView show() throws Exception 
    {
        return new ModelAndView("index");
    }

    /**
     * Method used for setting up database. It uses @PostConstruct annotation which is registered to spring
     * and after initializing entire bean all methods with this annotation are called. This comes handy when we
     * run application for the first run since we use in-memory database that is cleaned with each start of 
     * application server.
     */
    @PostConstruct
    public void install(){
        installer.firstRun();
    }
    
    /**
     * Method makes admin from user with specified username
     * @param username username of user from who we want to make admin
     * @return redirect to :/ whether user exists or not
     */
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
}
