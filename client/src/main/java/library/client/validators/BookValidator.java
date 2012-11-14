/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package library.client.validators;

import library.entity.dto.BookDTO;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

/**
 *
 * @author empt
 */
public class BookValidator implements Validator
{

    @Override
    public boolean supports(Class<?> clazz) {
        return BookDTO.class.equals(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
       ValidationUtils.rejectIfEmpty(errors, "title", "error.field.booktitle.empty");
       ValidationUtils.rejectIfEmpty(errors, "author", "error.field.bookauthor.empty");
    }
    
}
