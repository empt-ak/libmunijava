/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package library.client.controllers;

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
    @RequestMapping("/")
    public ModelAndView show() throws Exception 
    {
        return new ModelAndView("index");
    }

}
