/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package library.utils;

import library.entity.Book;
import library.entity.BookStatus;
import library.entity.Department;
import library.entity.User;

/**
 *
 * @author Nemko
 */
public class ValidationUtils {

    public static void checkUser(User user) {
        if (user.getPassword() == null) {
            throw new IllegalArgumentException("ERROR: obtained user does not have his password set");
        }
        if (user.getPassword().length() == 0) {
            throw new IllegalArgumentException("ERROR: obtained user does not have his password set");
        }
        if (user.getRealName() == null) {
            throw new IllegalArgumentException("ERROR: Obtained user does not have his real name set");
        }
        if (user.getPassword().length() == 0) {
            throw new IllegalArgumentException("ERROR: Obtained user does not have his real name set");
        }
        if (user.getSystemRole() == null) {
            throw new IllegalArgumentException("ERROR: Obtained user does not have his system role set");
        }
        boolean flag = false;
        switch (user.getSystemRole()) {
            case "USER":
                flag = true;
                break;
            case "ADMINISTRATOR":
                flag = true;
                break;
        }
        if (!flag) {
            throw new IllegalArgumentException("ERROR: Obtained user does not have set system role");
        }
        if (user.getUsername() == null || user.getUsername().length() == 0) {
            throw new IllegalArgumentException("ERROR: Obtained user does not have set its username");
        }
    }
    
    public static boolean testBookIsCorrect(Book book) {
        
        if (book == null) {
            return false;
        }
        
        Department[] values = Department.values();
        boolean contains = false;
        if (book.getDepartment() != null) {
            for (Department d : values) {
                if (book.getDepartment().compareTo(d) == 0) {
                    contains = true;
                }
            }
        }
        
        BookStatus[] bookStatuses = BookStatus.values();
        boolean bookStatus = false;
        if (book.getBookStatus() != null) {
            for (BookStatus bs : bookStatuses) {
                if (book.getBookStatus().compareTo(bs) == 0) {
                    bookStatus = true;
                }
            }
        }

        if (book.getAuthor() == null || !contains || !bookStatus || book.getTitle() == null) {
            return false;
        } else if (book.getAuthor().isEmpty() || book.getTitle().isEmpty()) {
            return false;
        } else {
            return true;
        }

    }
    
    public static boolean testIsBookAvailable(Book book) {
        return book.getBookStatus().equals(BookStatus.AVAILABLE);
    }
}
