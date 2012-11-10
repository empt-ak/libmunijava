/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package library.utils;

import library.entity.Book;

/**
 *
 * @author Nemko
 */
public class ValidationUtils {
    
    public static boolean testBookIsCorrect(Book book) {
        
        if (book == null) {
            return false;
        }

        if (book.getAuthor() == null || book.getTitle() == null) {
            return false;
        } else if (book.getAuthor().isEmpty() || book.getTitle().isEmpty()) {
            return false;
        } else {
            return true;
        }

    }

}
