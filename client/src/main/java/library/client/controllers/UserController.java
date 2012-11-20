/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package library.client.controllers;

import java.io.UnsupportedEncodingException;
import java.security.NoSuchAlgorithmException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.persistence.NoResultException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import library.client.Tools;
import library.entity.dto.TicketDTO;
import library.entity.dto.TicketItemDTO;
import library.entity.dto.UserDTO;
import library.service.BookService;
import library.service.TicketService;
import library.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

/**
 *
 * @author emptak
 */
@Controller
@RequestMapping("/user")
public class UserController
{
    @Autowired
    private Validator userValidator;
    
    @Autowired
    private UserService userService;
    
    @Autowired
    private TicketService ticketService;
    
    
    /**
     * mapping for user registering. user is registered if he has filled his username realname and password. if user does not have filled any of those fields. his request for registering
     * is redirected back to form. If all fields are valid, we set his role to default role (USER) and save him inside database. if there is already such a user with desired username, request is redirected
     * back to register form with proper error.
     * @param userDTO user saved in form
     * @param result
     * @param errors
     * @return if fields are nonvalid or username already exists redirect back to form, redirect to / otherwise
     */
    @RequestMapping(value="/register",method= RequestMethod.POST)
    public ModelAndView addUser(@ModelAttribute("userDTO") UserDTO userDTO,BindingResult result, Errors errors)
    {       
        userValidator.validate(userDTO, errors);
        if(result.hasErrors())
        {
            //System.out.println("chyba");
            return new ModelAndView("user_add","userDTO",userDTO); 
        }
        else
        {
            UserDTO temp = null;
            try
            {
                temp = userService.getUserByUsername(userDTO.getUsername());
            }catch(NoResultException nre)
            {
                
            }
            
            if(temp == null)
            {
                userDTO.setSystemRole("USER");
                try 
                {
                    userDTO.setPassword(Tools.SHA1(userDTO.getPassword()));
                } 
                catch (NoSuchAlgorithmException | UnsupportedEncodingException ex) 
                {
                    Logger.getLogger(UserController.class.getName()).log(Level.SEVERE, null, ex);
                }

                userService.createUser(userDTO);
                return new ModelAndView("redirect:/");                
            }
            else
            {// uzivatel s takym menom uz existuje
                errors.rejectValue("username", "error.field.username.duplicate");
                return new ModelAndView("user_add","userDTO",userDTO);
            }
              
        }           
    }
    
    /**
     * requestmapper for registering. 
     * @return M&V for form with registration
     */
    @RequestMapping(value="/register",method=RequestMethod.GET)
    public ModelAndView addUser(){ 
       return new ModelAndView("user_add","userDTO",new UserDTO()); 
    }
    
    
    /**
     * @TODO admin only
     * request mapper for user editing user details. if user edits his profile via editprofile his systemrole is not shown. in this case its shown since administrator
     * (librarian) can make from any user new librarian, therefore he has to see user role
     * @param userDTO user with values from form
     * @param result
     * @param errors
     * @return redirect to /user/ (since admin can see all users), or redirect back to form, if any of details are not valid
     */
    @RequestMapping(value="/edit",method= RequestMethod.POST)
    public ModelAndView editUser(@ModelAttribute("userDTO") UserDTO userDTO,BindingResult result, Errors errors)
    {       
        //System.out.println(userDTO);
        userValidator.validate(userDTO, errors);
        if(userDTO.getSystemRole() == null || !userDTO.getSystemRole().equals("USER") || !userDTO.getSystemRole().equals("ADMINISTRATOR"))
        {
            errors.rejectValue("systemRole", "error.field.systemrole.empty");
        }
        if(result.hasErrors())
        {            
            return new ModelAndView("user_edit","userDTO",userDTO); 
        }
        else
        {           
            userService.updateUser(userDTO);
            return new ModelAndView("redirect:/user/");  
        }           
    }
    
    /**
     * Request mapper for user editing form.
     * @param userID of user we would like to edit
     * @return M&V user_edit with values of desired user based on his id
     */
    @RequestMapping(value="/edit/{userID}",method= RequestMethod.GET)
    public ModelAndView editUser(@PathVariable Long userID)
    {
        //System.out.println(userID);
        return new ModelAndView("user_edit","userDTO",userService.getUserByID(userID));
    }
    
    /**
     * RequestMapper for user profile editing. User can edit only his profile therefore three is a check for session. If any of his edited field are not valid, he is redirected back to profile form with
     * appropiate error message. If success he is redirected back to /
     * @param userDTO user with values from form
     * @param result
     * @param errors
     * @param request servlet request holding session attribute
     * @return redirect to /, or back to form if any of fields are not valid
     */
    @RequestMapping(value="/editprofile/",method= RequestMethod.POST)
    public ModelAndView editUserProfile(@ModelAttribute("userDTO") UserDTO userDTO,BindingResult result, Errors errors,HttpServletRequest request)
    {       
        UserDTO inSession = (UserDTO) request.getSession().getAttribute("USER");
        if(inSession != null)
        {
            if(inSession.equals(userDTO))
            {
                userValidator.validate(userDTO, errors);
                if(result.hasErrors())
                {            
                    return new ModelAndView("user_profile","userDTO",userDTO); 
                }
                else
                {   
                   try {
                        userDTO.setPassword(Tools.SHA1(userDTO.getPassword()));
                    } catch (NoSuchAlgorithmException | UnsupportedEncodingException ex) {
                        Logger.getLogger(UserController.class.getName()).log(Level.SEVERE, null, ex);
                    }
                    try
                    {
                        userService.updateUser(userDTO);
                    }
                    catch(IllegalArgumentException iae)
                    {
                        return new ModelAndView("error/fatal","ERROR_MESSAGE",iae.getCause());
                    }

                    return new ModelAndView("redirect:/");  
                }                
            }
        }
        
        
        return new ModelAndView("redirect:/");
    }
    
    /**
     * @TODO add get profile id/username etc from session & remove this mapping only to /editprofile/ without any
     * arguments since argument will be passed by session
     * request mapper for user profile editing. 
     * @param username
     * @return 
     */
    @RequestMapping(value="/editprofile/{username}",method= RequestMethod.GET)
    public ModelAndView editUserProfile(@PathVariable String username)
    {
        return new ModelAndView("user_profile","userDTO",userService.getUserByUsername(username));
    }
    
    
    /**
     * @TODO delete ticketitems too (there are associations
     * RequestMapper for deleting user from database. User can be deleted only if he does not have any associations with tickets, therefore we need to delete all his tickets.
     * Only administrator can delete user from database
     * @param userID
     * @param request
     * @return 
     */
    @RequestMapping(value="/delete/{userID}",method= RequestMethod.GET)
    public ModelAndView deleteUser(@PathVariable Long userID,HttpServletRequest request)
    {
        UserDTO inSession = (UserDTO) request.getSession().getAttribute("USER");
        if(inSession != null && inSession.getSystemRole().equals("ADMINISTRATOR"))
        {
            UserDTO u = new UserDTO();
            u.setUserID(userID);
            List<TicketDTO> tickets = ticketService.getAllTicketsForUser(u);
            for(TicketDTO t : tickets)
            {
                ticketService.deleteTicket(t);
            }
            
            userService.deleteUser(u);
            
            return new ModelAndView("redirect:/user/");
            
        }
        
        return new ModelAndView("redirect:/");       
    }
    
    
    
    /**
     * @TODO kontrola admin
     * @return 
     */
    @RequestMapping("/")
    public ModelAndView showUsers()
    {        
        return new ModelAndView("user_list","USERS",userService.getUsers());
    }
    
    
    /**
     * @TODO kontrla admin
     * requestmapper for obtaining users in JSON format used for datatabels
     * @return 
     */
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
            sb.append("\",\"");
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
    
    /**
     * Mapping used for user login. 
     * @return M&V with loginpage
     */
    @RequestMapping(value="/login/",method= RequestMethod.GET)
    public ModelAndView login()
    {
        return new ModelAndView("loginpage");        
    }
    
    /**
     * @TODO wrong attributes
     * mapping used for user login, his password verification. User is logged in if his password matches with password stored in database. If they match this user
     * is stored inside session
     * @param session session
     * @param request servletrequest holding form attributes
     * @return redirect to index page
     */
    @RequestMapping(value="/login/",method= RequestMethod.POST)
    public ModelAndView login(HttpSession session,HttpServletRequest request)
    {
        String username = (String) request.getParameter("username");
        String password = (String) request.getParameter("password");
        
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
    
    /**
     * mapping used for logout
     * @param request containing session
     * @return redirect to /
     */
    @RequestMapping("/logout/")
    public ModelAndView logout(HttpServletRequest request)
    {
        request.getSession().invalidate();
        
        return new ModelAndView("redirect:/");
    }
}
