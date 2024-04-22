package it.safesiteguard.ms.constructionsite_ssguard.validators;


import jakarta.validation.Constraint;
import jakarta.validation.Payload;
import jakarta.validation.constraints.Pattern;

import java.lang.annotation.*;

@Constraint(validatedBy = LicenceValidator.class)
@Documented
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
public @interface ValidLicences {
    String message() default "Invalid licence type";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}
