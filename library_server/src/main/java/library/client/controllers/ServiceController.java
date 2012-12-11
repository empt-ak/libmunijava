/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package library.client.controllers;

import library.webservice.BookWebService;
import library.webservice.UserWebService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

/**
 *
 * @author Nemko
 */
@Controller
@RequestMapping("/service")
public class ServiceController {
    
    @Autowired
    private BookWebService bookWebService;
    
    @Autowired
    private UserWebService userWebService;
    
    @RequestMapping("/")
    public ModelAndView index() {
        return new ModelAndView("service");     
    } 
    
}
