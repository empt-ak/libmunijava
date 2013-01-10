/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package library.security;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import library.dao.UserDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * @author Empt
 */
@Transactional(readOnly=true)
@Service("customUserDetailsService")
public class CustomUserDetailsService implements UserDetailsService
{
    @Autowired
    private UserDAO userDAO;

    @Override
    public UserDetails loadUserByUsername(String string) throws UsernameNotFoundException {
        library.entity.User domainUser = null;
        User u = null;
        try
        {
            domainUser = userDAO.getUserByUsername(string);
            u = new User(domainUser.getUsername(), domainUser.getPassword(), getAuthorities(domainUser)); 
            System.out.println(u.getAuthorities());
            
            return u;
        }
        catch(Exception e)
        {
            throw new RuntimeException(e);
        }
    }
    
    private Collection<? extends GrantedAuthority> getAuthorities(library.entity.User u)
    {
        return getGrantedAuthorities(getRoles(u.getSystemRole()));        
    }
    
    
    private static List<GrantedAuthority> getGrantedAuthorities(List<String> roles)
    {
        List<GrantedAuthority> authorities = new ArrayList<>();
        for(String s : roles)
        {
            authorities.add(new SimpleGrantedAuthority(s));
        }
        
        return authorities;
    }
    
    
    private List<String> getRoles(String role)
    {
        List<String> roles = new ArrayList<>();
        if(role.equals("ADMINISTRATOR"))
        {
            roles.add("ROLE_ADMINISTRATOR");
            roles.add("ROLE_USER");
        }
        else
        {
            roles.add("ROLE_USER");
        }
        
        return roles;
    }
    
}
