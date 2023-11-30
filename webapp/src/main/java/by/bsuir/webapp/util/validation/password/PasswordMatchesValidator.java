package by.bsuir.webapp.util.validation.password;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class PasswordMatchesValidator
        implements ConstraintValidator<PasswordMatches, Object> {

    @Override
    public void initialize(PasswordMatches constraintAnnotation) {
    }
    @Override
    public boolean isValid(Object obj, ConstraintValidatorContext context){
        PasswordForm form = (PasswordForm) obj;
        return form.getPassword().equals(form.getMatchingPassword());
    }
}
