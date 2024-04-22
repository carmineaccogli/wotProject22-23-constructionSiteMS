package it.safesiteguard.ms.constructionsite_ssguard.validators;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.*;

@Constraint(validatedBy = ValidActiveMachinesImpl.class)
@Documented
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
public @interface ValidActiveMachines {

    String message() default "Invalid active machines format";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}
