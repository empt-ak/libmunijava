/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package library.client.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 *
 * @author emptak
 */
@Controller
public class HTTPErrorController 
{
    /**
     * method used when error 404 occurs
     * @return 404 error page
     */
    @RequestMapping(value="/errors/404.html")
    public String handle404()
    {
        return "error/404";
    }
}
