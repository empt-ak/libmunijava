/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package library.test;

import java.util.ArrayList;
import java.util.List;
import library.entity.dto.UserDTO;
import library.service.UserService;
import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.annotation.DirtiesContext.ClassMode;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestExecutionListeners;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.support.DependencyInjectionTestExecutionListener;
import org.springframework.test.context.support.DirtiesContextTestExecutionListener;

/**
 *
 * @author Gajdos
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations ={"file:src/main/resources/spring/applicationContext-test.xml"})
@TestExecutionListeners({DirtiesContextTestExecutionListener.class, DependencyInjectionTestExecutionListener.class})
@DirtiesContext(classMode = ClassMode.AFTER_EACH_TEST_METHOD)
public class UserDAOTest
{
    private List<UserDTO> correctUsers = null;
    private List<UserDTO> wrongUsers = null;
    
    @Autowired
    private UserService userService;
    
    @Before
    public void init(){
        correctUsers = new ArrayList<>();
        UserDTO cu = createUser("heslo1", "realny1", "USER", "uziv1");
        UserDTO cu2 = createUser("heslo2", "realny2", "USER", "uziv2");
        UserDTO cu3 = createUser("heslo3", "realny3", "ADMINISTRATOR", "uziv3");
        UserDTO cu4 = createUser("heslo4", "realny4", "USER", "uziv4");
        correctUsers.add(cu);
        correctUsers.add(cu2);
        correctUsers.add(cu3);
        correctUsers.add(cu4);
        
        wrongUsers = new ArrayList<>();
        UserDTO wu = createUser(null, "r_zly1", "USER", "uz_zly1");
        UserDTO wu2 = createUser("h_zly2", null, "USER", "uz_zly2");
        UserDTO wu3 = createUser("h_zly3", "r_zly3", null, "uz_zly3");
        UserDTO wu4 = createUser("h_zly4", "r_zly4", "ADMINISTRATOR", null);
        wrongUsers.add(wu);
        wrongUsers.add(wu2);
        wrongUsers.add(wu3);
        wrongUsers.add(wu4);       
    } 

    /**
     * Test of createUser method, of class UserService.
     */
    @Test
    public void testCreateAndGetUser() 
    {
        //=================
        // TEST wrong users
        //=================
        try{
            // create null user
            userService.createUser(null);
            fail("IllegalArgumentException should be thrown when creating null User");            
        }catch(IllegalArgumentException iae){      
            //ok
        }
        try{
            // create user without set password
            userService.createUser(wrongUsers.get(0));
            fail("IllegalArgumentException should be thrown when creating user without password");
        }catch(IllegalArgumentException iae){
            //ok
        }
        try{
            // create user without set realname
            userService.createUser(wrongUsers.get(1));
            fail("IllegalArgumentException should be thrown when creating user without realname");
        }catch(IllegalArgumentException iae){
            //ok
        }
        try{
            // create user without set role
            userService.createUser(wrongUsers.get(2));
            fail("IllegalArgumentException should be thrown when creating user without role");
        }catch(IllegalArgumentException iae){
            //ok
        }
        try{
            // create user without set username
            userService.createUser(wrongUsers.get(3));
            fail("IllegalArgumentException should be thrown when creating user without username");
        }catch(IllegalArgumentException iae){
            //ok
        }
        
        //=================
        // TEST correct user
        //================= 
        
        try{
            userService.createUser(correctUsers.get(0));
            //ok
        }catch(Exception e){
            fail("No exception should be thrown when creating correct user : "+e);
        }
        
        //=================
        // TEST get user
        //================= 
        try{
            userService.getUserByID(null);
            fail("IllegalArgumentException should be thrown when getting user by null id");
        }catch(IllegalArgumentException iae){
            //ok
        }
        try{
            userService.getUserByID(new Long(0));
            fail("IllegalArgumentException should be thrown when getting user by his id that is out of range");
        }catch(IllegalArgumentException iae){
            //ok
        }
        UserDTO saved = null;
        try{
            saved = userService.getUserByID(correctUsers.get(0).getUserID()); // should be 5
        }catch(Exception e){
            
        }
        assertEquals("Based on equals method users are not same, they dont have same ID", correctUsers.get(0),saved);
        assertDeepEquals(correctUsers.get(0), saved);        
    }
    
    @Test
    public void testUpdateUser(){
        UserDTO u = correctUsers.get(1);
        userService.createUser(u);
        try{
            userService.updateUser(null);
            fail("IllegalArgumentException should be thrown when updating null user");
        }catch(IllegalArgumentException iae){
            //ok
        }
        u.setPassword(null);
        try{
            userService.updateUser(u);
            fail("IllegalArgumentException should be thrown when updating user without password");
        }catch(IllegalArgumentException iae){
            //ok
        } 
        u.setPassword("nejakeheslo");
        u.setRealName(null);
        try{
            userService.updateUser(u);
            fail("IllegalArgumentException should be thrown when updating user without realname");
        }catch(IllegalArgumentException iae){
            //ok
        }
        u.setRealName("zmenenerealname");
        u.setSystemRole(null);
        try{
            userService.updateUser(u);
            fail("IllegalArgumentException should be thrown when updating user without systemrole");
        }catch(IllegalArgumentException iae){
            //ok
        }
        u.setSystemRole("USER");
        u.setUsername(null);
        try{
            userService.updateUser(u);
            fail("IllegalArgumentException should be thrown when updating user without systemrole");
        }catch(IllegalArgumentException iae){
            // ok
        }
        u.setUsername("zusername");
        try{
            userService.updateUser(u);
        }catch(Exception e){
            fail("No exception should be thrown when updating correct user" + u+e);
        }
        
        UserDTO uzer = userService.getUserByID(u.getUserID());
        
        assertDeepEquals(u, uzer);
    }
    
    @Test
    public void testDeleteUser(){
        userService.createUser(correctUsers.get(0));
        userService.createUser(correctUsers.get(1));
        userService.createUser(correctUsers.get(2));
        userService.createUser(correctUsers.get(3));
        
        UserDTO toDelete =correctUsers.get(2);
        
        try{
            userService.deleteUser(null);
            fail("IllegalArgumentException should be thrown when deleting null user");
        }catch(IllegalArgumentException iae){
            // ok
        }
        try{
            userService.deleteUser(toDelete);
        }catch(IllegalArgumentException iae){
            fail("No Exception should be thrown when deleting correct user "+iae.getMessage());
        }
        List<UserDTO> list = userService.getUsers();
        assertEquals("v db nie su 3 uzivatelia",3,list.size());
        assertNotSame("a", correctUsers.get(2), list.get(0));
        assertNotSame("b",correctUsers.get(2),list.get(1));
        assertNotSame("c",correctUsers.get(2),list.get(2));
    }
    
    
    @Test
    public void testGetAllUsers(){        
        userService.createUser(correctUsers.get(0));
        userService.createUser(correctUsers.get(1));
        userService.createUser(correctUsers.get(2));
        userService.createUser(correctUsers.get(3)); 
        
        List<UserDTO> uzers = userService.getUsers();
        
        assertEquals("There should be 4 users inside database but they are not",4,uzers.size());
        assertDeepEquals(correctUsers, uzers);
    }
    
    @Test
    public void testGetUserByUserName()
    {
        userService.createUser(correctUsers.get(0));
        
        try{
            userService.getUserByUsername(null);
            fail("IllegalArgumentException should be thrown when getting user by null");
        }catch(IllegalArgumentException iae){
            //ok
        }
        try{
            userService.getUserByUsername("");
            fail("IllegalArgumentException when getting user by zero length username");
        }catch(IllegalArgumentException iae){
            //ok
        }
        UserDTO u = null;
        try{
            u = userService.getUserByUsername("uziv1");
        }catch(Exception e){
            fail("No exception should be thrown when getting user y his username that has to be inside database");
        }
        
        assertEquals(correctUsers.get(0),u);
        assertDeepEquals(correctUsers.get(0), u);
    }
    
    @Test
    public void testGetUserByRealName(){
        userService.createUser(correctUsers.get(0));
        userService.createUser(correctUsers.get(1));
        userService.createUser(correctUsers.get(2));
        
        try{
            userService.findUserByRealName(null);
            fail("IllegalArgumentException should be thrown when getting user by null realname");
        }catch(IllegalArgumentException iae){
            //ok
        }
        
        try{
            userService.findUserByRealName("");
            fail("IllegalArgumentException should be thrown when getting user by zero length real name");
        }catch(IllegalArgumentException iae){
            //ok
        }
        
        List<UserDTO> list = null;
        
        try{
            list = userService.findUserByRealName("alny");
        }catch(Exception e){
            fail("No exception should be thrown when getting user by correct possible real name that is created inside databse");
        }
        
        assertEquals("Search did not return correct number of users",3, list.size());
        assertTrue("This user has not been found",list.contains(correctUsers.get(0)));
        assertTrue("This user has not been found",list.contains(correctUsers.get(1)));
        assertTrue("This user has not been found",list.contains(correctUsers.get(2)));
    }

    
    
    private UserDTO createUser(String password,String realname,String systemRole,String username){
        UserDTO u = new UserDTO();
        u.setPassword(password);
        u.setRealName(realname);
        u.setSystemRole(systemRole);
        u.setUsername(username);
        return u;
    }
    
    private void assertDeepEquals(UserDTO expected, UserDTO actual){
        assertEquals("Passwords are not same",expected.getPassword(),actual.getPassword());
        assertEquals("Real names are not same",expected.getRealName(),actual.getRealName());
        assertEquals("Usernames are not same",expected.getUsername(),actual.getUsername());
        assertEquals("System roles are not same",expected.getSystemRole(),actual.getSystemRole());
    }
    
    private void assertDeepEquals(List<UserDTO> expected, List<UserDTO> actual){
        for(int i =0;i<expected.size();i++){
            assertEquals("Passwords are not same",expected.get(i).getPassword(),actual.get(i).getPassword());
            assertEquals("Real names are not same",expected.get(i).getRealName(),actual.get(i).getRealName());
            assertEquals("Usernames are not same",expected.get(i).getUsername(),actual.get(i).getUsername());
            assertEquals("System roles are not same",expected.get(i).getSystemRole(),actual.get(i).getSystemRole());
        }
        
    }

}
