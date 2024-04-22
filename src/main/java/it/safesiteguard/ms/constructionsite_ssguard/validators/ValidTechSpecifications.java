package it.safesiteguard.ms.constructionsite_ssguard.validators;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.*;


@Constraint(validatedBy = ValidTechSpecificationsImpl.class)
@Documented
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
public @interface ValidTechSpecifications {

    String message() default "Invalid tech specifications format";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}
