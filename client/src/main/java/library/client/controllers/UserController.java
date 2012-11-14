/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package library.client.controllers;

import java.io.UnsupportedEncodingException;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import library.client.Tools;
import library.client.validators.UserValidator;
import library.entity.dto.UserDTO;
import library.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpRequest;

import org.springframework.stereotype.Controller;
import org.springframework.validation.BindException;
import org.springframework.validation.BindingResult;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.servlet.ModelAndView;

/**
 *
 * @author emptak
 */
@Controller
@RequestMapping("/user")
@SessionAttributes({"USER"})
public class UserController extends SessionController
{
    @Autowired
    Validator userValidator;
    
    @Autowired
    UserService userService;
    
    @RequestMapping(value="/register",method= RequestMethod.POST)
    public ModelAndView addUser(@ModelAttribute("userDTO") UserDTO userDTO,BindingResult result, Errors errors)
    {       
        System.out.println(userDTO);
        userValidator.validate(userDTO, errors);
        if(result.hasErrors())
        {
            System.out.println("chyba");
            return new ModelAndView("user_add","userDTO",userDTO); 
        }
        else
        {
            userDTO.setSystemRole("USER");
            try {
                userDTO.setPassword(Tools.SHA1(userDTO.getPassword()));
            } catch (NoSuchAlgorithmException | UnsupportedEncodingException ex) {
                Logger.getLogger(UserController.class.getName()).log(Level.SEVERE, null, ex);
            }
            
            userService.createUser(userDTO);
            return new ModelAndView("index");  
        }           
    }
    
    
    @RequestMapping(value="/register",method=RequestMethod.GET)
    public ModelAndView addUser(){ 
       return new ModelAndView("user_add","userDTO",new UserDTO()); 
    }
    
    @RequestMapping(value="/edit",method= RequestMethod.POST)
    public ModelAndView editUser(@ModelAttribute("userDTO") UserDTO userDTO,BindingResult result, Errors errors)
    {       
        System.out.println(userDTO);
        userValidator.validate(userDTO, errors);
        if(result.hasErrors())
        {            
            return new ModelAndView("user_edit","userDTO",userDTO); 
        }
        else
        {           
            userService.updateUser(userDTO);
            return new ModelAndView("index");  
        }           
    }
    
    @RequestMapping(value="/delete/{userID}",method= RequestMethod.GET)
    public ModelAndView deleteUser(@PathVariable Long userID)
    {
        UserDTO u = new UserDTO();
        u.setUserID(userID);
        userService.deleteUser(u);
        
        return showUsers();
    }
    
    @RequestMapping(value="/edit/{userID}",method= RequestMethod.GET)
    public ModelAndView editUser(@PathVariable Long userID)
    {
        System.out.println(userID);
        return new ModelAndView("user_edit","userDTO",userService.getUserByID(userID));
    }
    
    
    @RequestMapping("/")
    public ModelAndView showUsers(){
        
        return new ModelAndView("user_list","USERS",userService.getUsers());
    }
    
    
    @RequestMapping("/getUsersJSON")
    @ResponseBody String getUsersJSON()
    {
        List<UserDTO> users = userService.getUsers();
        StringBuilder sb = new StringBuilder("{ \"aaData\": [");
        for(int i =0;i<users.size();i++)
        {
            UserDTO u = users.get(i);
            sb.append("\r\n").append("[\"");
            sb.append(u.getUserID());
            sb.append("\",\"");
            sb.append(u.getUsername());
            sb.append("\",\"");
            sb.append(u.getRealName());
            sb.append("\",\"");
            sb.append(u.getPassword());
            sb.append("\",\"");
            sb.append(u.getSystemRole());
            
            if(i < users.size()-1)
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
    
    @RequestMapping(value="/login/",method= RequestMethod.GET)
    public ModelAndView login()
    {
        return new ModelAndView("loginpage");        
    }
    
    
    @RequestMapping(value="/login/",method= RequestMethod.POST)
    public ModelAndView login(HttpSession session,HttpServletRequest request)
    {
        String username = (String) request.getParameter("username");
        String password = (String) request.getParameter("password");
        System.out.println(username);
        System.out.println(password);
        try {
            password = Tools.SHA1(password);
        } catch (NoSuchAlgorithmException | UnsupportedEncodingException ex) {
            Logger.getLogger(UserController.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        UserDTO user = userService.getUserByUsername(username);
        if(user != null && user.getPassword().equals(password))
        {
            session.setAttribute("USER", user);
        }
        //session.setAttribute("USER", this);
        
        return new ModelAndView("redirect:/");
    }
    
    @RequestMapping("/logout/")
    public ModelAndView logout(HttpSession session)
    {
        session.setAttribute("USER", new UserDTO());
        session.invalidate();
        
        return new ModelAndView("redirect:/");
    }
    
    
    
    
//    @RequestMapping("/login")
//    public ModelAndView login(HttpServletRequest request, HttpServletResponse response){
//        
//        
//        return new ModelAndView("index");        
//    }
//    
//    @RequestMapping("/logout")
//    public ModelAndView logout(HttpServletRequest request, HttpServletResponse response){
//        
////        String name = request.getParameter("username");
////        String password = request.getParameter("password");
////        for(User u :users){
////            if(u.getUsername().equals(name) && u.getPassword().equals(password))
////            {
////                request.getSession().setAttribute("USER", u);
////            }
////        }
//////        
////        request.getSession().setAttribute("USER", null);
////        request.getSession(true);
//        
//        
//        HttpSession session = request.getSession(false);
//        session.setAttribute("USER", null);
//        session.invalidate();
//        
//        //request.getSession().invalidate();
//        return new ModelAndView("index");        
//    }
    
}