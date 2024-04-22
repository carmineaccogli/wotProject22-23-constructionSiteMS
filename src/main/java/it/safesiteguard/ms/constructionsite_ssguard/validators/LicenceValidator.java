package it.safesiteguard.ms.constructionsite_ssguard.validators;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

import java.util.List;

public class LicenceValidator implements ConstraintValidator<ValidLicences, String> {

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {

        if(value == null)
            return false;

        String pattern = "^(A1|B|C)$";
        if (!value.matches(pattern))
            return false;


        return true;
    }
}
