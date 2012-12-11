/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package library.utils.aop.validators;

import library.entity.Book;
import library.entity.Ticket;
import library.entity.TicketItem;
import library.entity.User;

/**
 *
 * @author emptak
 */
public class LibraryValidator 
{
    private LibraryValidator() { }
    
    public static void validateDAO(Object o, Class clazz, Operation operation)
    {
        if(o == null)
        {
            throw new IllegalArgumentException("ERROR: object is null");
        }
        
        if(clazz.equals(User.class))
        {
            User u = (User) o;
            switch(operation)
            {
                case CREATE: 
                    DAOUserValidator.validateOnSave(u); 
                    break;
                case UPDATE: 
                    DAOUserValidator.validateOnUpdate(u); 
                    break;
                case DELETE: 
                    DAOUserValidator.validateOnDelete(u); 
                    break;
                default: 
                    throw new IllegalArgumentException();
            }
        }
        else if(clazz.equals(Book.class))
        {
            Book b = (Book)o;
            switch(operation)
            {
                case CREATE: 
                    DAOBookValidator.validateOnSave(b); 
                    break;
                case UPDATE: 
                    DAOBookValidator.validateOnUpdate(b); 
                    break;
                case DELETE: 
                    DAOBookValidator.validateOnDelete(b); 
                    break;
                default: 
                    throw new IllegalArgumentException();
            }
        }
        else if(clazz.equals(TicketItem.class))
        {
            TicketItem ti = (TicketItem) o;
            switch(operation)
            {
                case CREATE: 
                    DAOTicketItemValidator.validateOnSave(ti); 
                    break;
                case UPDATE: 
                    DAOTicketItemValidator.validateOnUpdate(ti); 
                    break;
                case DELETE: 
                    DAOTicketItemValidator.validateOnDelete(ti); 
                    break;
                default: 
                    throw new IllegalArgumentException();
            }
        }
        else if(clazz.equals(Ticket.class))
        {
            Ticket t = (Ticket)o;
            switch(operation)
            {
                case CREATE: 
                    DAOTicketValidator.validateOnSave(t); 
                    break;
                case UPDATE: 
                    DAOTicketValidator.validateOnUpdate(t); 
                    break;
                case DELETE: 
                    DAOTicketValidator.validateOnDelete(t); 
                    break;
                default: 
                    throw new IllegalArgumentException();
            }
        }
        else
        {
            throw new IllegalArgumentException("ERROR: following class is not supported "+clazz);
        }
    }
    
    public static boolean isValidID(Long id)
    {
        if(id == null || id.compareTo(new Long(1)) < 0)
        {
            return false;
        }
        return true;
    }
    
    public static boolean isStringEmpty(String s)
    {
        if (s == null || s.length() == 0) {
            return true;
        }
        return false;
    }
    
    public static boolean isNull(Object o)
    {
        return o == null;        
    }
    
}
