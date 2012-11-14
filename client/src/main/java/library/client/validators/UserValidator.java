/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package library.client.validators;

import library.entity.dto.UserDTO;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

/**
 *
 * @author emptak
 */
public class UserValidator implements Validator
{

    @Override
    public boolean supports(Class<?> clazz) {
        return UserDTO.class.equals(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        UserDTO user = (UserDTO) target;
        
        ValidationUtils.rejectIfEmpty(errors, "username", "error.field.username.empty");
        ValidationUtils.rejectIfEmpty(errors, "realName", "error.field.realname.empty");
        ValidationUtils.rejectIfEmpty(errors, "password", "error.field.password.empty");
        
    }
    
}
