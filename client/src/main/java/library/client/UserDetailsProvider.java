///*
// * To change this template, choose Tools | Templates
// * and open the template in the editor.
// */
//package library.client;
//
//import java.util.ArrayList;
//import java.util.Collection;
//import java.util.List;
//import library.entity.dto.UserDTO;
//import library.service.UserService;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.security.core.GrantedAuthority;
//import org.springframework.security.core.authority.GrantedAuthorityImpl;
//import org.springframework.security.core.userdetails.User;
//import org.springframework.security.core.userdetails.UserDetails;
//import org.springframework.security.core.userdetails.UserDetailsService;
//import org.springframework.security.core.userdetails.UsernameNotFoundException;
//
///**
// *
// * @author emptak
// */
//
//public class UserDetailsProvider implements UserDetailsService
//{
//    @Autowired
//    UserService userService;
//
//    @Override
//    public UserDetails loadUserByUsername(String string) throws UsernameNotFoundException {
//        UserDetails user = null;
//        try
//        {
//            UserDTO userDTO = userService.getUserByUsername(string);
//            if(userDTO != null)
//            {
//                user = new User(userDTO.getUsername(), userDTO.getPassword(), getAuthorities(userDTO.getSystemRole()));
//            }
//        }
//        catch(Exception e)
//        {
//            throw new UsernameNotFoundException(e.getMessage());
//        }
//        
//        return user;
//    }
//    
//    private Collection<GrantedAuthority> getAuthorities(String role)
//    {
//        List<GrantedAuthority> authList = new ArrayList<>(2);
//        authList.add(new GrantedAuthorityImpl("ROLE_USER"));
//        
//        if(role.equals("ADMINISTRATOR"))
//        {
//            authList.add(new GrantedAuthorityImpl("ROLE_ADMINISTRATOR"));
//        }
//        
//        return authList;
//    }
//    
//    
//}
