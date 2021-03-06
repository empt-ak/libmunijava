/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package library.utils.aop.validators;

import library.entity.User;

/**
 *
 * @author emptak
 */
class DAOUserValidator 
{
    private DAOUserValidator(){}
    
    public static void validateOnSave(User user)
    {
        validateCore(user);        
    }
    
    public static void validateOnUpdate(User user)
    {
        if(!LibraryValidator.isValidID(user.getUserID()))
        {
            throw new IllegalArgumentException("ERROR: given user does not have proper id");
        }
        validateCore(user);        
    }
    
    public static void validateOnDelete(User user)
    {
        if(!LibraryValidator.isValidID(user.getUserID()))
        {
            throw new IllegalArgumentException("ERROR: given user does not have proper id");
        }        
    }
    
    private static void validateCore(User user)
    {
        if(LibraryValidator.isStringEmpty(user.getPassword()))
        {
            throw new IllegalArgumentException("ERROR: Given user does not have set its password");
        }
        if(LibraryValidator.isStringEmpty(user.getRealName()))
        {
            throw new IllegalArgumentException("ERROR: Given user does not have set its real name");
        }
        if(LibraryValidator.isStringEmpty(user.getUsername()))
        {
            throw new IllegalArgumentException("ERROR: Given user does not have set its username");
        }
        
        if(user.getSystemRole() == null || !(user.getSystemRole().equals("USER") ^ user.getSystemRole().equals("ADMINISTRATOR")))
        {
            throw new IllegalArgumentException("ERROR: Given user does not have set its system role");
        }
    }
}
