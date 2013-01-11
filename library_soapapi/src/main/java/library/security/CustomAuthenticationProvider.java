///*
// * To change this template, choose Tools | Templates
// * and open the template in the editor.
// */
//package library.security;
//
///**
// *
// * @author Nemko
// */
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//import org.springframework.security.Authentication;
//import org.springframework.security.BadCredentialsException;
//import org.springframework.security.providers.UsernamePasswordAuthenticationToken;
// 
//import java.util.HashMap;
//import java.util.Map;
//import library.entity.User;
//import library.service.UserService;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.security.authentication.AuthenticationProvider;
//import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
//import org.springframework.security.core.Authentication;
// 
///**
// * Simple demo of a custom authentication provider.
// *
// * @author Fred Simon
// */
//public class CustomAuthenticationProvider implements AuthenticationProvider {
//    
//	@Autowired
//	private UserService userService;
//
//	@Override
//	public boolean supports(Class<? extends Object> authentication) {
//		return authentication.equals(UsernamePasswordAuthenticationToken.class);
//	}
//
//	@Override
//	public Authentication authenticate(Authentication authentication) {
//		try {
//			boolean authenticated = userService.authenticate(
//				authentication.getName(),
//				authentication.getCredentials().toString()
//				);
//
//			Administrator administrator = new Administrator();
//			administrator.setLastName(user.getLastName());
//			administrator.setLastName(user.getFirstName());
//			administrator.setEmail(new EmailAddress(user.getEmailAddress()));
//			administrator.setUsername(user.getName());
//
//			List<GrantedAuthority> authorities = new ArrayList<GrantedAuthority>();
//			authorities.add(new GrantedAuthorityImpl("ROLE_USER"));
//
//			// Optional: Define granted authorities based on
//			// groups to which the user is a member.
//			List<Group> groups = crowd.getGroupsForUser(user.getName(), 0, -1);
//			for (Group group: groups) {
//				if (group.getName().equals("..."))
//				 	authorities.add(new GrantedAuthorityImpl("ROLE_..."));
//			}
//
//			return new UserAuthentication(administrator, authorities);
//		}
//		catch (UserNotFoundException e) {
//			return null;
//		}
//		catch (InactiveAccountException e) {
//			throw new DisabledException(e.getMessage(), e);
//		}
//		catch (ExpiredCredentialException e) {
//			throw new CredentialsExpiredException(e.getMessage(), e);
//		}
//		catch (InvalidAuthenticationException e) {
//			throw new BadCredentialsException(e.getMessage(), e);
//		}
//		catch (ApplicationPermissionException e) {
//			throw new AuthenticationServiceException(e.getMessage(), e);
//		}
//		catch (OperationFailedException e) {
//			throw new AuthenticationServiceException(e.getMessage(), e);
//		}
//	}
//}
