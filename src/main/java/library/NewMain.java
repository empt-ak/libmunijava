package library;


import library.entity.User;
import library.entity.dto.UserDTO;
import library.service.UserService;
import org.dozer.Mapper;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author emptak
 */
public class NewMain {

    private static final ApplicationContext appContext = new ClassPathXmlApplicationContext("spring/applicationContext-test.xml");
    
    //@Autowired
    private static Mapper mapper = (Mapper) appContext.getBean("mapper");
    
    //@Autowired
    private static UserService userService = (UserService) appContext.getBean("userService");
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        //appContext.getBean("userService");
        createUser();              
        User udto = mapper.map(userService.getUserByID(1L), User.class);
        
        dumpudto(udto);
        
        
    }
    
    private static void dumpudto(User udto)
    {
        System.out.println(udto.getClass().equals(User.class));
        System.out.println(udto.getPassword());
        System.out.println(udto.getRealName());
        System.out.println(udto.getSystemRole());
        System.out.println(udto.getUsername());
        System.out.println(udto.getUserID());
    }
    
    private static void createUser()
    {
        UserDTO u = new UserDTO();
        u.setPassword("heslo");
        u.setRealName("meno");
        u.setSystemRole("USER");
        u.setUsername("login");
        
        userService.createUser(u);
    }
}
