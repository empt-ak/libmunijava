/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package library.client.controllers;

import javax.annotation.PostConstruct;
import library.client.Installer;
import library.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
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
}
