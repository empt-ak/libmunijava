/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package library.test;

import java.util.List;
import library.dao.UserDAO;
import library.entity.User;
import library.entity.dto.UserDTO;
import library.service.UserService;
import org.junit.Test;
import org.kubek2k.springockito.annotations.SpringockitoContextLoader;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.AbstractJUnit4SpringContextTests;
import static org.mockito.Mockito.*;
import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.runner.RunWith;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

/**
 *
 * @author emptak
 */

@ContextConfiguration(loader = SpringockitoContextLoader.class,
locations = "file:src/main/resources/spring/applicationContext-mock.xml")
@RunWith(SpringJUnit4ClassRunner.class)
public class UserServiceTest extends AbstractJUnit4SpringContextTests
{    
    
    @Autowired
    private UserService userService;
    
    @Autowired
    private UserDAO userDAO;
    
    private List<User> users = new java.util.ArrayList<>();
    private List<UserDTO> userDTOs = new java.util.ArrayList<>();
    
    @Before
    public void init()
    {
        for(int i =1;i<5;i++)
        {
            userDTOs.add(TestUtils.createUserDTO(new Long(i),"heslo"+i, "rname"+i, "USER", "username"+i));
            users.add(TestUtils.createUser(new Long(i), "heslo"+i, "rname"+i, "USER", "username"+i));            
        }
        //specialny na create
        userDTOs.add(TestUtils.createUserDTO(null,"npas", "n_name", "USER", "nlogin"));
        users.add(TestUtils.createUser(null, "npas", "n_name", "USER", "nlogin")); 
        
        // specialny na vyhladavanie
        userDTOs.add(TestUtils.createUserDTO(new Long(7),"hEsLo", "Andrejko", "USER", "pinkrain"));
        users.add(TestUtils.createUser(new Long(7), "hEsLo", "Andrejko", "USER", "pinkrain")); 
    }
    
    @Test
    @DirtiesContext
    public void testGetUser()
    {
        // given
        when(userDAO.getUserByID(new Long(1))).thenReturn(users.get(0));
        userService.createUser(userDTOs.get(0));
        
        // when
        UserDTO result = userService.getUserByID(new Long(1));
        
        //then -- 
        assertEquals(new Long(1),result.getUserID());
        //verify(userDAO,times(1)).getUserByID(new Long(1));        
    }
    
    @Test
    @DirtiesContext
    public void testUpdateUser()
    {
        // given
        UserDTO uDTO = userDTOs.get(0);
        User u = users.get(0);
        u.setRealName("zmenene"); 
        when(userDAO.getUserByID(new Long(1))).thenReturn(u);
        
        //when
        userService.createUser(uDTO);
        uDTO.setRealName("zmenene");
        userService.updateUser(uDTO);
        UserDTO result = userService.getUserByID(new Long(1));
        
        //then        
        assertEquals(result.getRealName(),u.getRealName());        
    }
    
    @Test
    @DirtiesContext
    public void testDeleteUser()
    {
        //given
        when(userDAO.getUserByID(new Long(2))).thenReturn(null);
        
        
        //when
        userService.createUser(userDTOs.get(1));
        userService.deleteUser(userDTOs.get(1));
        UserDTO result = userService.getUserByID(new Long(2));
        
        //then
        assertNull(result);        
    }
    
    
    @Test
    @DirtiesContext
    public void testGetAll()
    {
        // given
        List<User> temp = users.subList(0, 3);
        when(userDAO.getUsers()).thenReturn(temp);
        
        //when
        for(int i =0;i<3;i++)
        {
            userService.createUser(userDTOs.get(i));
        }
        List<UserDTO> resultList = userService.getUsers();       
        
        assertEquals(resultList.size(),temp.size());        
    }
    
    @Test
    @DirtiesContext
    public void testGetUserByUserName()
    {
        //given
        when(userDAO.getUserByUsername("pinkrain")).thenReturn(users.get(users.size()-1));
        
        //when
        userService.createUser(userDTOs.get(userDTOs.size()-1));
        UserDTO result = userService.getUserByUsername("pinkrain");
        
        //then
        assertEquals("pinkrain",result.getUsername());
    }
    
    @Test
    @DirtiesContext
    public void testgetUserByRealName()
    {
        //given
        List<User> temp = users.subList(0, 3);
        when(userDAO.findUserByRealName("rname")).thenReturn(temp);
        
        //when
        for(int i =0;i<5;i++)
        {
            userService.createUser(userDTOs.get(i));
        }
        List<UserDTO> resultList = userService.findUserByRealName("rname");
        
        //then
        for(UserDTO u :resultList)
        {
            assertTrue(hasInName("rname", u.getRealName()));            
        }
    }
    
    private boolean hasInName(String value, String input)
    {
        return input.contains(value.subSequence(0, value.length()));
    }
}
