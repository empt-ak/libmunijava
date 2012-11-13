/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package library.aop.validators;

import library.entity.Book;
import library.entity.TicketItem;

/**
 *
 * @author emptak
 */
class DAOTicketItemValidator 
{
    private DAOTicketItemValidator(){}
    
    
    public static void validateOnSave(TicketItem ti)
    {
        System.out.println(ti);
        validateCore(ti);        
    }
    
    public static void validateOnUpdate(TicketItem ti)
    {
        if(!LibraryValidator.isValidID(ti.getTicketItemID()))
        {
            throw new IllegalArgumentException("ERROR: given ti has no id");
        }
        validateCore(ti);
        
    }
    
    public static void validateOnDelete(TicketItem ti)
    {
        if(!LibraryValidator.isValidID(ti.getTicketItemID()))
        {
            throw new IllegalArgumentException("ERROR: given ti has no id");
        }        
    }
    
    private static void validateCore(TicketItem ti)
    {
        if(ti.getTicketItemStatus() == null)
        {
            throw new IllegalArgumentException("ERROR: given ti has null status");
        }
        // update overi vsetky polia + id + null
        LibraryValidator.validateDAO(ti.getBook(), Book.class, Operation.UPDATE);
    }
}
