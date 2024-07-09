package it.safesiteguard.ms.constructionsite_ssguard.validators;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.*;

@Constraint(validatedBy = ValidActiveMachineriesImpl.class)
@Documented
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
public @interface ValidActiveMachineries {

    String message() default "Invalid active machineries format";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}
