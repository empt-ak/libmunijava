/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package library.utils;

import javax.ejb.ApplicationException;

/**
 *
 * @author Nemko
 */
@ApplicationException(rollback = false)
public class LibraryIllegalArgumentException extends IllegalArgumentException {

    public LibraryIllegalArgumentException() {
        super();
    }

    public LibraryIllegalArgumentException(String message) {
        super(message);
    }

    public LibraryIllegalArgumentException(String message, Throwable cause) {
        super(message, cause);
    }

    public LibraryIllegalArgumentException(Throwable cause) {
        super(cause);
    }
}
