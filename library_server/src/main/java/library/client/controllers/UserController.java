/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package library.client.controllers;

import java.io.UnsupportedEncodingException;
import java.security.NoSuchAlgorithmException;
import java.util.List;
import javax.persistence.NoResultException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import library.entity.dto.TicketDTO;
import library.entity.dto.UserDTO;
import library.service.TicketService;
import library.service.UserService;
import library.utils.Tools;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.Errors;
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
     * mapping for user registering. user is registered if he has filled his
     * username realname and password. if user does not have filled any of those
     * fields. his request for registering is redirected back to form. If all
     * fields are valid, we set his role to default role (USER) and save him
     * inside database. if there is already such a user with desired username,
     * request is redirected back to register form with proper error.
     *
     * @param userDTO user saved in form
     * @param result
     * @param errors
     * @return if fields are nonvalid or username already exists redirect back
     * to form, redirect to / otherwise
     */
    @RequestMapping(value = "/register", method = RequestMethod.POST)
    public ModelAndView addUser(@ModelAttribute("userDTO") UserDTO userDTO, BindingResult result, Errors errors) 
    {
        userValidator.validate(userDTO, errors);
        if (result.hasErrors()) 
        {
            return new ModelAndView("user_add", "userDTO", userDTO);
        } 
        else 
        {
            UserDTO temp = null;
            try 
            {
                temp = userService.getUserByUsername(userDTO.getUsername());
            } 
            catch (NoResultException nre) 
            {
            }

            if (temp == null) 
            {
                userDTO.setSystemRole("USER");
                try 
                {
                    userDTO.setPassword(Tools.SHA1(userDTO.getPassword()));
                } catch (NoSuchAlgorithmException | UnsupportedEncodingException ex) 
                {
                }

                userService.createUser(userDTO);
                return new ModelAndView("redirect:/");
            } 
            else 
            {// uzivatel s takym menom uz existuje
                errors.rejectValue("username", "error.field.username.duplicate");
                return new ModelAndView("user_add", "userDTO", userDTO);
            }
        }
    }

    /**
     * requestmapper for registering.
     *
     * @return M&V for form with registration
     */
    @RequestMapping(value = "/register", method = RequestMethod.GET)
    public ModelAndView addUser() 
    {
        return new ModelAndView("user_add", "userDTO", new UserDTO());
    }

    /**
     * edits his profile via editprofile his systemrole is not shown. in this
     * case its shown since administrator (librarian) can make from any user new
     * librarian, therefore he has to see user role
     * @param userDTO user with values from form
     * @param result
     * @param errors
     * @return redirect to /user/ (since admin can see all users), or redirect
     * back to form, if any of details are not valid
     */
    @RequestMapping(value = "/edit", method = RequestMethod.POST)
    public ModelAndView editUser(@ModelAttribute("userDTO") UserDTO userDTO, BindingResult result, Errors errors, HttpServletRequest request) 
    {
//        UserDTO inSession = (UserDTO) request.getSession().getAttribute("USER");
//        if (inSession != null && inSession.getSystemRole().equals("ADMINISTRATOR")) 
//        {
            userValidator.validate(userDTO, errors);
            if (userDTO.getSystemRole() == null || !(!userDTO.getSystemRole().equals("USER") ^ !userDTO.getSystemRole().equals("ADMINISTRATOR"))) 
            {
                errors.rejectValue("systemRole", "error.field.systemrole.empty");
            }
            if (result.hasErrors()) 
            {
                return new ModelAndView("user_edit", "userDTO", userDTO);
            } 
            else 
            {
                try 
                {
                    userDTO.setPassword(Tools.SHA1(userDTO.getPassword()));
                } 
                catch (NoSuchAlgorithmException | UnsupportedEncodingException ex) 
                {                    
                }
                
                userService.updateUser(userDTO);
                return new ModelAndView("redirect:/user/");
            }
//        }
//        return new ModelAndView("redirect:/");        
    }

    /**
     * Request mapper for user editing form.
     *
     * @param userID of user we would like to edit
     * @return M&V user_edit with values of desired user based on his id
     */
    @RequestMapping(value = "/edit/{userID}", method = RequestMethod.GET)
    public ModelAndView editUser(@PathVariable Long userID) 
    {
        return new ModelAndView("user_edit", "userDTO", userService.getUserByID(userID));
    }

    /**
     * RequestMapper for user profile editing. User can edit only his profile
     * therefore three is a check for session. If any of his edited field are
     * not valid, he is redirected back to profile form with appropiate error
     * message. If success he is redirected back to /
     *
     * @param userDTO user with values from form
     * @param result
     * @param errors
     * @param request servlet request holding session attribute
     * @return redirect to /, or back to form if any of fields are not valid
     */
    @RequestMapping(value = "/editprofile/", method = RequestMethod.POST)
    public ModelAndView editUserProfile(@ModelAttribute("userDTO") UserDTO userDTO, BindingResult result, Errors errors, HttpServletRequest request) {
        UserDTO inSession = (UserDTO) request.getSession().getAttribute("USER");
        if (inSession != null) 
        {
            if (inSession.equals(userDTO)) 
            {
                userValidator.validate(userDTO, errors);
                if (result.hasErrors()) 
                {
                    return new ModelAndView("user_profile", "userDTO", userDTO);
                } 
                else 
                {
                    try 
                    {
                        userDTO.setPassword(Tools.SHA1(userDTO.getPassword()));
                    } 
                    catch (NoSuchAlgorithmException | UnsupportedEncodingException ex) 
                    {                        
                    }
                    
                    try 
                    {
                        userService.updateUser(userDTO);
                    } 
                    catch (IllegalArgumentException iae) 
                    {
                        return new ModelAndView("error/fatal", "ERROR_MESSAGE", iae.getCause());
                    }

                    return new ModelAndView("redirect:/");
                }
            }
        }
        
        return new ModelAndView("redirect:/");
    }

    /**
     * Request mapper for editing user profile. if we are logged in we pass our session attribute to service layer which will return 
     * user from database (we may changed something and it has not been reflected in session) and show form with logged in user values.
     * if we are not logged in redirect to / is performed
     * @param request servlet request holding session 
     * @return M&V user_profile if user is logged in with userDTO object, redirect to / otherwise
     */
    @RequestMapping(value = "/editprofile/", method = RequestMethod.GET)
    public ModelAndView editUserProfile(HttpServletRequest request) 
    {
//        UserDTO inSession = (UserDTO) request.getSession().getAttribute("USER");
//        if(inSession != null)
//        {
        UserDTO temp = userService.getUserByUsername(SecurityContextHolder.getContext().getAuthentication().getName());
            return new ModelAndView("user_profile", "userDTO", userService.getUserByUsername(temp.getUsername()));
//        }
//        else
//        {
//            return new ModelAndView("redirect:/");
//        }        
    }

    /**
     * Requestmapper for deleting user from database. Only administrator can delete user from database
     * @param userID to be deleted
     * @param request holding session
     * @return redirect to /user/ or / if administrator is not logged in
     */
    @RequestMapping(value = "/delete/{userID}", method = RequestMethod.GET)
    public ModelAndView deleteUser(@PathVariable Long userID, HttpServletRequest request) 
    {
//        UserDTO inSession = (UserDTO) request.getSession().getAttribute("USER");
//        if (inSession != null && inSession.getSystemRole().equals("ADMINISTRATOR")) 
//        {
            UserDTO u = new UserDTO();
            u.setUserID(userID);
            List<TicketDTO> tickets = ticketService.getAllTicketsForUser(u);
            for (TicketDTO t : tickets) 
            {
                ticketService.deleteTicket(t);
            }

            userService.deleteUser(u);

            return new ModelAndView("redirect:/user/");
//        }
//
//        return new ModelAndView("redirect:/");
    }

    /**
     * Request mapper for root /user/ call. 
     * @param request holding session
     * @return M&V user_list or redirect to / if we are not logged in as administrator
     */
    @RequestMapping("/")
    public ModelAndView showUsers(HttpServletRequest request) 
    {      
        UserDTO inSession = (UserDTO) request.getSession().getAttribute("USER");
        if (inSession != null && inSession.getSystemRole().equals("ADMINISTRATOR")) 
        {        
            return new ModelAndView("user_list");
        }
        
        return new ModelAndView("redirect:/");
    }

    /**
     * Request mapper for obtaiing users in JSON format. if we are logged in and we are administrator
     * we retrieve all users and format them into following JSON format:
     * <pre>
     * { "aaData" :[
     * ["userID","username","hashedpassword","userRole",""],
     * ["userID1","username1","hashedpassword1","userRole1",""],
     * ["userID2","username2","hashedpassword2","userRole2",""],
     * ...
     * ["userID"n,"usernamen","hashedpasswordn","userRolen",""]
     * ]}
     * </pre>
     * 
     * we have to generate one more colum (last) so we can create extra column for actions if we are
     * not an administrator we generate empty string
     * 
     * @param request servlet request holding session
     * @return all users in JSON format or empty string
     */
    @RequestMapping("/getUsersJSON")
    @ResponseBody
    String getUsersJSON(HttpServletRequest request) 
    {
        UserDTO inSession = (UserDTO) request.getSession().getAttribute("USER");
        if (inSession != null && inSession.getSystemRole().equals("ADMINISTRATOR")) 
        {        
            List<UserDTO> users = userService.getUsers();
            StringBuilder sb = new StringBuilder("{ \"aaData\": [");
            for (int i = 0; i < users.size(); i++) 
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
                if (i < users.size() - 1) 
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
        
        return "";
    }

    /**
     * Mapping used for user login.
     *
     * @return M&V with loginpage
     */
    @RequestMapping(value = "/login/", method = RequestMethod.GET)
    public ModelAndView login() 
    {
        return new ModelAndView("loginpage");
    }

    /**
     * RequestMapper for loging in. User is logged in if his password matches with password
     * stored in database. If they match this user is stored inside session
     * @param session session
     * @param request servletrequest holding form attributes
     * @return redirect to index page
     */
    @RequestMapping(value = "/login/", method = RequestMethod.POST)
    public ModelAndView login(HttpSession session, HttpServletRequest request)
    {
        String username = (String) request.getParameter("username");
        String password = (String) request.getParameter("password");

        try 
        {
            password = Tools.SHA1(password);
        } 
        catch (NoSuchAlgorithmException | UnsupportedEncodingException ex) 
        {
        }

        UserDTO user = userService.getUserByUsername(username);
        if (user != null && user.getPassword().equals(password)) 
        {
            session.setAttribute("USER", user);
        }

        return new ModelAndView("redirect:/");
    }

    /**
     * mapping used for logout
     *
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
