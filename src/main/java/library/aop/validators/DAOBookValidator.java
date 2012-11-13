/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package library.aop.validators;

import library.entity.Book;

/**
 *
 * @author emptak
 * 
 * no modifier so it cannot be accessed outside package
 */
class DAOBookValidator 
{
    private DAOBookValidator() {}
    
    public static void validateOnSave(Book book)
    {
        coreValidation(book);        
    }
    
    public static void validateOnUpdate(Book book)
    {
        if(!LibraryValidator.isValidID(book.getBookID()))
        {
            throw new IllegalArgumentException("ERROR: Given book does not have set its ID");
        }
        coreValidation(book);
    }
    
    public static void validateOnDelete(Book book)
    {
        if(!LibraryValidator.isValidID(book.getBookID()))
        {
            throw new IllegalArgumentException("ERROR: Given book does not have set its ID");
        }        
    }
    
    
    private static void coreValidation(Book book)
    {
        if(LibraryValidator.isStringEmpty(book.getAuthor()))
        {
            throw new IllegalArgumentException("ERROR: Given book does not have set its author");
        }
        
        if(book.getBookStatus() == null)
        {
            throw new IllegalArgumentException("ERROR: Given book does not have set its status");
        }
        
        if(book.getDepartment() == null)
        {
            throw new IllegalArgumentException("ERROR: Given book does not have set its department");
        }
        
        if(LibraryValidator.isStringEmpty(book.getTitle()))
        {
            throw new IllegalArgumentException("ERROR: Given book does not have set its title");
        }
    }
}
