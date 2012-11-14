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
    @RequestMapping(value="/errors/404.html")
    public String handle404()
    {
        return "error/404";
    }
}
