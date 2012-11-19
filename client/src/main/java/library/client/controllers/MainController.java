/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package library.client.controllers;

import library.client.RssLoader;
import library.entity.dto.UserDTO;
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
    
    
    @RequestMapping("/")
    public ModelAndView show() throws Exception 
    {
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
    
}
