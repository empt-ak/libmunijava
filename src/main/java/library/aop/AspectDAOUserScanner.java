/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package library.aop;

import library.aop.validators.LibraryValidator;
import library.aop.validators.Operation;
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
    @Before("execution (* library.dao.impl.UserDAOImpl.createUser(..)) && args(user)")
    public void scanForSave(User user)
    {        
        LibraryValidator.validateDAO(user, User.class, Operation.CREATE);        
    }
    
    @Before("execution (* library.dao.impl.UserDAOImpl.updateUser(..)) && args(user)")
    public void scanForUpdate(User user)
    {
        LibraryValidator.validateDAO(user, User.class, Operation.UPDATE);        
    }
    
    @Before("execution (* library.dao.impl.UserDAOImpl.deleteUser(..)) && args(user)")
    public void scanForDelete(User user)
    {
        LibraryValidator.validateDAO(user, User.class, Operation.DELETE);        
    }
    
    @Before("execution (* library.dao.impl.UserDAOImpl.getUserByID(..)) && args(id)")
    public void scanForGetByID(Long id)
    {
        if(!LibraryValidator.isValidID(id))
        {
            throw new IllegalArgumentException("Sent ID is null or not out of valid ID range");
        }
    }
    
    @Before("execution (* library.dao.impl.UserDAOImpl.findUserByRealName(..)) && args(realName)")
    public void scanForFindUserByRealName(String realName)
    {
        if(LibraryValidator.isStringEmpty(realName))
        {
            throw new IllegalArgumentException("Sent realname is null or has zero length");
        }
    } 
    
    @Before("execution (* library.dao.impl.UserDAOImpl.getUserByUsername(..)) && args(username)")
    public void scanForgGetUserByUsername(String username)
    {
        if(LibraryValidator.isStringEmpty(username))
        {
            throw new IllegalArgumentException("Sent username is null or has zero length");
        }
    }   
}
