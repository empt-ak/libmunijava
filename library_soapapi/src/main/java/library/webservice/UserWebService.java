/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package library.webservice;

import java.util.List;
import javax.jws.WebService;
import library.entity.dto.UserDTO;
import org.apache.cxf.interceptor.InInterceptors;

/**
 *
 * @author Nemko
 */
@WebService
@InInterceptors(interceptors = "library.security.BasicAuthorizationInterceptor")
public interface UserWebService 
{
    
    void createUser(UserDTO user) throws IllegalArgumentException;
   
    void updateUser(UserDTO user) throws IllegalArgumentException;    
   
    void deleteUser(UserDTO user) throws IllegalArgumentException;    
    
    UserDTO getUserByID(Long id) throws IllegalArgumentException;  
    
    UserDTO getUserByUsername(String username) throws IllegalArgumentException;
     
    List<UserDTO> findUserByRealName(String name) throws IllegalArgumentException;
    
    List<UserDTO> getUsers();
    
}
