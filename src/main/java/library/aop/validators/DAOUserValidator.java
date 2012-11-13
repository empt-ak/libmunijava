/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package library.aop.validators;

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
        
        // trochu prasacina :] mozno to ide urobit jednoduchsie
        boolean hasRole = false;
        
        if(!hasRole && user.getSystemRole() != null)
        {
            switch (user.getSystemRole()) 
            {                
                case "USER":
                    hasRole = true;
                    break;
                case "ADMINISTRATOR":
                    hasRole = true;
                    break;
            }            
        }
        
        if(!hasRole)
        {
            throw new IllegalArgumentException("ERROR: Given user does not have set its system role");
        }
    }
}
