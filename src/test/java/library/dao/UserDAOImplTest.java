///*
// * To change this template, choose Tools | Templates
// * and open the template in the editor.
// */
package library.dao;

import java.util.List;
import javax.persistence.Persistence;
import javax.persistence.PersistenceException;
import library.entity.User;
import org.hibernate.HibernateException;
import org.testng.Assert;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.Test;

/**
 *
 * @author Gajdos
 */

public class UserDAOImplTest{
    
    private UserDAOImpl userDAO;
    
    @BeforeSuite
    public void setUp() {
        userDAO = new UserDAOImpl();
        userDAO.setEm(Persistence.createEntityManagerFactory("library").createEntityManager());
    }

    /**
     * Test of createUser method, of class UserDAOImpl.
     */
    @Test(groups="a")
    public void testCreateUser() {
        try{
            userDAO.createUser(null);
            Assert.fail();
        }catch(IllegalArgumentException iae){}
        User u = createUser("h1", "jozo fi", "USER", "jozo");
        User u2 = createUser("h1", "jozo fu", "USER", "jozo2");
        User u3 = createUser("h2","jozo fe","USER","jozo3");
        User u4 = createUser(null, "jozo fa", "USER", "jozo4");
        try{
            userDAO.createUser(u4);
            Assert.fail("exp");
        }catch(PersistenceException pe){
            //ok nie je nastavene heslo
        }
        
        u4 = createUser("h4", null, "USER", "jozo4");
        try{
            userDAO.createUser(u4);
            Assert.fail("exp");
        }catch(PersistenceException pe){
            // ok nie je nastavene meno
        }
        u4 = createUser("h4", "jozo fa", null, "jozo4");
        try{
            userDAO.createUser(u4);
            Assert.fail("exp");
        }catch(PersistenceException pe){
            // ok nie je nastavena systemRole
        }
        
        u4 = createUser("h4", "jozo fa", "USER", null);
        try{
            userDAO.createUser(u4);
            Assert.fail("exp");
        }catch(PersistenceException pe){
            //ok nie je nastavene username
        }
        
        u4 = createUser("h4", "jozo fa", "USER", "jozo4");
        try{
            userDAO.createUser(u);
            userDAO.createUser(u2);
            userDAO.createUser(u3);
            userDAO.createUser(u4);
        }catch(Exception e){
            Assert.fail("vsetci su ok");
        }
    }
    
    @Test(groups="b",dependsOnGroups="a")
    public void testGetUserByID()
    {
        User u = null;
        try
        {
            u = userDAO.getUserByID(null);
            Assert.fail("IAE should be thrown");
        }catch(Exception e){ }
        
        try
        {
            u = userDAO.getUserByID(new Long(0));
            Assert.fail("IAE should be thrown");
        }catch(Exception e){ }
       
        try
        {
            u = userDAO.getUserByID(new Long(5));            
        }
        catch(Exception e){
            Assert.fail("exc should not be thrown on correct ID");
        }
        System.out.println(u);
        
        //createUser("h1", "jozo fi", "USER", "jozo");
        Assert.assertEquals(new Long(5), u.getUserID(),"Retrieved user does not have correct id");
        Assert.assertEquals("h1", u.getPassword(),"Retrieved user does not have correct id");
        Assert.assertEquals("jozo fi", u.getRealName(), "Retrieved user does not have correct real name");
        Assert.assertEquals("USER", u.getSystemRole(), "Retrieved user does not have correct system role");
        Assert.assertEquals("jozo", u.getUsername(), "Retrieved user does not have correct username");
    }


    /**
     * Test of findUserByName method, of class UserDAOImpl.
     */
    @Test(groups="c",dependsOnGroups="b")
    public void testFindUserByName() {
        try{
            userDAO.findUserByRealName(null);
            Assert.fail();
        }catch(IllegalArgumentException iae){
            //ok
        }
        
        try{
            userDAO.findUserByRealName("");
            Assert.fail();
        }catch(IllegalArgumentException iae){
            //ok
        }
        List<User> result = null;
        
        try{
            result =  userDAO.findUserByRealName("fi");            
        }catch(IllegalArgumentException iae){            
        }
       
        Assert.assertEquals(result.get(0).getUserID(), new Long(5));
        Assert.assertEquals(result.get(0).getPassword(), "h1");
        Assert.assertEquals(result.get(0).getRealName(), "jozo fi");
        
    }

    /**
     * Test of updateUser method, of class UserDAOImpl.
     */
    @Test(groups="d",dependsOnGroups="c")
    public void testUpdateUser() 
    {
        User u = null;
        try{
            u = userDAO.getUserByID(new Long(7));
        }catch(Exception e){
            Assert.fail();
        }
        
        u.setRealName("jozozmeneny");
        u.setPassword("heslozmenene");
        
        try{
            userDAO.updateUser(null);
            Assert.fail();
        }catch(IllegalArgumentException iae){
            
        }
        
        try{
            userDAO.updateUser(u);
        }catch(Exception e){
            Assert.fail();
        }
        
        User result = null;
        try{
            result = userDAO.getUserByID(new Long(7));
        }catch(HibernateException he){
            
        }
        
        Assert.assertEquals(result.getUserID(), new Long(7));
        Assert.assertEquals(result.getPassword(), "heslozmenene");
        Assert.assertEquals(result.getRealName(), "jozozmeneny");
        
    }

    /**
     * Test of getUsers method, of class UserDAOImpl.
     */
    @Test(groups="f",dependsOnGroups="a")
    public void testGetUsers() {
        Assert.assertTrue(userDAO.getUsers().size() == 4, "V db sa nenachadzaju  uzivatelia, ktory by tam mali byt");
    }

    /**
     * Test of deleteUser method, of class UserDAOImpl.
     */
    //@Test
    public void testDeleteUser() 
    {
        User u = null;
        try{
            u = userDAO.getUserByID(new Long(7));
        }catch(Exception e){
            
        }
        
        try{
            userDAO.deleteUser(null);
            Assert.fail();
        }catch(IllegalArgumentException iae){
            // ok
        }
        
        try{
            userDAO.deleteUser(u);
            Assert.fail();
        }catch(IllegalArgumentException iae){
            // ok
        }
        
        Assert.assertEquals(userDAO.getUserByID(new Long(7)), null);
        
    }
    
    
    private User createUser(String password,String realname,String systemRole,String username){
        User u = new User();
        u.setPassword(password);
        u.setRealName(realname);
        u.setSystemRole(systemRole);
        u.setUsername(username);
        return u;
    }
}