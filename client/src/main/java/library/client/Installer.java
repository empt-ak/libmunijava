/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package library.client;

import library.entity.dto.BookDTO;
import library.entity.dto.UserDTO;
import library.entity.enums.BookStatus;
import library.entity.enums.Department;
import library.service.BookService;
import library.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;

/**
 *
 * @author Emptak
 */
public class Installer
{
    @Autowired
    private BookService bookService;
    @Autowired
    private UserService userService;
    
    private java.util.List<BookDTO> books = new java.util.ArrayList<>();
    private java.util.List<UserDTO> users = new java.util.ArrayList<>();

    /**
     * the only callable method in this class prepares database for first run
     */
    public void firstRun()
    {
        System.err.println("====INSTALL STARTING");
        install();        
        System.out.println("=====INSTALL END");
    }
    
    /**
     * calls single methods used for installing
     */
    private void install()
    {
        installBooks();
        installUsers();
    }
    
    /**
     * installs books
     */
    private void installBooks()
    {
        setupBooks();
        for(BookDTO b : books)
        {
            bookService.createBook(b);
            System.out.println("==INSTALLING BOOK: "+b);
        }
    }
    
    /**
     * installs users
     */
    private void installUsers()
    {
        setupUsers();
        for(UserDTO u : users)
        {
            userService.createUser(u);
            System.out.println("==INSTALLING USER: "+u);
        }
    }
    
    /**
     * setups books
     */
    private void setupBooks()
    {
        books.add(createBook("Emma", "Jane Austen", Department.ADULT, BookStatus.AVAILABLE));
        books.add(createBook("Pride & Prejudice", "Jane Austen", Department.ADULT, BookStatus.AVAILABLE));
        books.add(createBook("Sense and Sensibility", "Jane Austen", Department.ADULT, BookStatus.AVAILABLE));
        books.add(createBook("Harry Potter and the Philosophers Stone", "J.K. Rowling", Department.ADULT, BookStatus.AVAILABLE));
        books.add(createBook("Harry Potter and the Chamber of Secrets", "J.K. Rowling", Department.ADULT, BookStatus.AVAILABLE));
        books.add(createBook("Harry Potter and the Prisoner of Azkaban", "J.K. Rowling", Department.ADULT, BookStatus.AVAILABLE));
        books.add(createBook("On the Generalized Theory of Gravitation", "Albert Einstein", Department.SCIENTIFIC, BookStatus.AVAILABLE));
        books.add(createBook("čerešnička", "ľščťžýáíéúäň", Department.ADULT, BookStatus.AVAILABLE));
        books.add(createBook("The Grand Design", "Stephen Hawking", Department.SCIENTIFIC, BookStatus.AVAILABLE));
        books.add(createBook("Spring Security 3", "Peter Mularien", Department.SCIENTIFIC, BookStatus.AVAILABLE));
        books.add(createBook("Tri gaštanové kone", "Margita Figuli", Department.ADULT, BookStatus.AVAILABLE));
        books.add(createBook("Spring Security", "Peter Mularien", Department.SCIENTIFIC, BookStatus.AVAILABLE));        
    }
    
    /**
     * setups users
     */
    private void setupUsers()
    {
        users.add(createUser("a949c2a6e2ca4bc2e237c07b332c9168c7497cfd", "Dominik Szalai","ADMINISTRATOR", "emptak"));        
    }
    
    private BookDTO createBook(String title, String author, Department department, BookStatus status) {
        BookDTO b = new BookDTO();
        b.setTitle(title);
        b.setAuthor(author);
        b.setDepartment(department);
        b.setBookStatus(status);

        return b;
    }
    
    public static UserDTO createUser(String password,String realname,String systemRole,String username){
        UserDTO u = new UserDTO();
        u.setPassword(password);
        u.setRealName(realname);
        u.setSystemRole(systemRole);
        u.setUsername(username);
        return u;
    }
    
    
}
