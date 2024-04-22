package it.safesiteguard.ms.constructionsite_ssguard.validators;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.*;

@Constraint(validatedBy = ValidIdentificationPlateImpl.class)
@Documented
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
public @interface ValidIdentificationPlate {

    String message() default "Invalid identification plate format";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}
