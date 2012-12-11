/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package library.utils.aop;

import library.utils.aop.validators.LibraryValidator;
import library.utils.aop.validators.Operation;
import library.entity.User;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;

/**
 *
 * @author emptak
 */
@Aspect
public class AspectDAOUserScanner 
{
    private static final org.apache.log4j.Logger logger = org.apache.log4j.Logger.getLogger(AspectDAOUserScanner.class);
    
    @Before("execution (* library.dao.impl.UserDAOImpl.createUser(..)) && args(user)")
    public void scanForSave(User user)
    {    
        logger.debug("=Method call UserDAOImpl.createUser() with following parameter: "+user);
        LibraryValidator.validateDAO(user, User.class, Operation.CREATE);        
    }
    
    @Before("execution (* library.dao.impl.UserDAOImpl.updateUser(..)) && args(user)")
    public void scanForUpdate(User user)
    {
        logger.debug("=Method call UserDAOImpl.updateUser() with following parameter: "+user);
        LibraryValidator.validateDAO(user, User.class, Operation.UPDATE);        
    }
    
    @Before("execution (* library.dao.impl.UserDAOImpl.deleteUser(..)) && args(user)")
    public void scanForDelete(User user)
    {
        logger.debug("=Method call UserDAOImpl.deleteUser() with following parameter: "+user);
        LibraryValidator.validateDAO(user, User.class, Operation.DELETE);        
    }
    
    @Before("execution (* library.dao.impl.UserDAOImpl.getUserByID(..)) && args(id)")
    public void scanForGetByID(Long id)
    {
        logger.debug("=Method call UserDAOImpl.getUserByID() with following parameter: "+id);
        if(!LibraryValidator.isValidID(id))
        {
            throw new IllegalArgumentException("Sent ID is null or not out of valid ID range");
        }
    }
    
    @Before("execution (* library.dao.impl.UserDAOImpl.findUserByRealName(..)) && args(realName)")
    public void scanForFindUserByRealName(String realName)
    {
        logger.debug("=Method call UserDAOImpl.findUserByRealName() with following parameter: "+realName);
        if(LibraryValidator.isStringEmpty(realName))
        {
            throw new IllegalArgumentException("Sent realname is null or has zero length");
        }
    } 
    
    @Before("execution (* library.dao.impl.UserDAOImpl.getUserByUsername(..)) && args(username)")
    public void scanForgGetUserByUsername(String username)
    {
        logger.debug("=Method call UserDAOImpl.getUserByUsername() with following parameter: "+username);
        if(LibraryValidator.isStringEmpty(username))
        {
            throw new IllegalArgumentException("Sent username is null or has zero length");
        }
    }   
}
