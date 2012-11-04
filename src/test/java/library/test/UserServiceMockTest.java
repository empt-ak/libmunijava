/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package library.test;

import java.util.ArrayList;
import java.util.List;
import library.dao.BookDAO;
import library.dao.UserDAO;
import library.entity.User;
import library.entity.dto.UserDTO;
import library.service.UserService;
import library.service.impl.UserServiceImpl;
import org.dozer.Mapper;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.test.util.ReflectionTestUtils;
import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

/**
 *
 * @author Szalai
 */
@RunWith(MockitoJUnitRunner.class)
public class UserServiceMockTest 
{
    
    UserService userService;
    
    @Mock
    UserDAO userDAO;
    
    @Mock
    Mapper mapper;
    
    
    java.util.List<User> users = new java.util.ArrayList<>();
    java.util.List<UserDTO> userDTOs = new java.util.ArrayList<>();
    
    
    @Before
    public void init()
    {
        userService = new UserServiceImpl();
        ReflectionTestUtils.setField(userService, "userDAO", userDAO);
        ReflectionTestUtils.setField(userService, "mapper", mapper);
        for(int i =1;i<5;i++)
        {
            userDTOs.add(createUserDTO(new Long(i), "heslo"+i, "rname"+i, "USER", "username"+i));
            users.add(createUser(new Long(i), "heslo"+i, "rname"+i, "USER", "username"+i));            
        }
        userDTOs.add(createUserDTO(new Long(7), "hEsLo", "Andrejko", "USER", "rain"));
        users.add(createUser(new Long(7), "hEsLo", "Andrejko", "USER", "rain")); 
        
        //System.err.println(userDTOs);
    }
    
    
    @Test
    public void testCreateUser()
    {
        try
        {
            userService.createUser(null);
        }
        catch(IllegalArgumentException iae)
        {
            //kk
        }
        
        
        try
        {
            userService.createUser(userDTOs.get(0));
        }
        catch(Exception e)
        {   
            e.printStackTrace();
            fail(e.getMessage());
        }        
    }
    
    @Test
    public void testGetBookByID()
    {
       
        when(userDAO.getUserByID(new Long(1))).thenReturn(users.get(0));
        
        when(userService.getUserByID(new Long(1))).thenReturn(userDTOs.get(0));
        
        UserDTO returned = userService.getUserByID(new Long(1));
        
        assertEquals(returned, userDTOs.get(0));
    }
    
    @Test
    public void testUpdateUser()
    {
       try
       {
           userService.updateUser(null);
           fail();
       }
       catch(IllegalArgumentException e)
       {
           //ok
       }
       
       try
       {
           userService.updateUser(userDTOs.get(0));
       }
       catch(Exception e)
       {
           fail(e.getMessage());
       }
        
    }
    
    @Test
    public void testDeleteUser()
    {
        try
        {
            userService.deleteUser(userDTOs.get(0));
        }
        catch(Exception e)
        {
            fail(e.getMessage());
        }
        
        
    }
    
    @Test 
    public void testGetAllUsers()
    {
        when(userDAO.getUsers()).thenReturn(users);
        
        when(userService.getUsers()).thenReturn(userDTOs);
        
        List<UserDTO> resultList = userService.getUsers();
        
        
        // mocknuty dozer blbne miesto toho aby vratil spravny list
        // teda user1,...,usern vrati
        // null, ..., null, List<User>(user1, ..., usern) 
        // shit is magic, takze treba pretypovat posledny object a vlozit
        // to do listu
        resultList = (List<UserDTO>) resultList.get(resultList.size()-1);
        
        
        assertEquals(userDTOs.size(),resultList.size());
        for(UserDTO u : userDTOs)
        {
            if(!resultList.contains(u))
            {
                fail();
            }
        }
         
    }
    
    @Test
    public void testGetUserByUserName()
    {
        when(userDAO.getUserByUsername("rain")).thenReturn(users.get(4));
        
        when(userService.getUserByUsername("rain")).thenReturn(userDTOs.get(4));
        
        UserDTO result = userService.getUserByUsername("rain");
        
        assertEquals(userDTOs.get(4),result);
        
    }
    
    
    @Test
    public void testgetUserByRealName()
    {
        List<User> tempU = new ArrayList<>();
        tempU.add(users.get(4));
        
        when(userDAO.findUserByRealName("Andrejko")).thenReturn(tempU);
        
        List<UserDTO> temp = new ArrayList<>();
        temp.add(userDTOs.get(4));
        
        when(userService.findUserByRealName("Andrejko")).thenReturn(temp);
        
        List<UserDTO> resultList = userService.findUserByRealName("Andrejko");        
        
        assertEquals(1,resultList.size());
        
    }
    
    
    
    
    
    
    
    
    
    private UserDTO createUserDTO(Long id, String password,String realname,String systemRole,String username){
        UserDTO u = new UserDTO();
        u.setUserID(id);
        u.setPassword(password);
        u.setRealName(realname);
        u.setSystemRole(systemRole);
        u.setUsername(username);
        return u;
    }
    
    private User createUser(Long id, String password,String realname,String systemRole,String username){
        User u = new User();
        u.setUserID(id);
        u.setPassword(password);
        u.setRealName(realname);
        u.setSystemRole(systemRole);
        u.setUsername(username);
        return u;
    }
}
