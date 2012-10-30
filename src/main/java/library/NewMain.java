package library;


import library.entity.UserDO;
import library.entity.dto.User;
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
        UserDO udto = mapper.map(userService.getUserByID(1L), UserDO.class);
        
        dumpudto(udto);
        
        
    }
    
    private static void dumpudto(UserDO udto)
    {
        System.out.println(udto.getClass().equals(UserDO.class));
        System.out.println(udto.getPassword());
        System.out.println(udto.getRealName());
        System.out.println(udto.getSystemRole());
        System.out.println(udto.getUsername());
        System.out.println(udto.getUserID());
    }
    
    private static void createUser()
    {
        User u = new User();
        u.setPassword("heslo");
        u.setRealName("meno");
        u.setSystemRole("USER");
        u.setUsername("login");
        
        userService.createUser(u);
    }
}
