package it.safesiteguard.ms.constructionsite_ssguard.validators;

import it.safesiteguard.ms.constructionsite_ssguard.domain.Machinery;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ValidTechSpecificationsImpl implements ConstraintValidator<ValidTechSpecifications, Machinery.TechSpecifications> {


    private static final String DIMENSIONS_REGEX = "\\d{1,3}x\\d{1,3}x\\d{1,3}";

    public static boolean validateString(String input) {
        Pattern pattern = Pattern.compile(DIMENSIONS_REGEX);
        Matcher matcher = pattern.matcher(input);
        return matcher.matches();
    }

    public static boolean validateOperatingSpeed(Float operatingSpeed) {
        float minSpeed = 0.0f;
        float maxSpeed = 100.0f;

        return operatingSpeed > minSpeed && operatingSpeed <= maxSpeed;
    }

    @Override
    public boolean isValid(Machinery.TechSpecifications value, ConstraintValidatorContext context) {

        if(value.getDimensions() == null || value.getMass() == null || value.getOperatingSpeed() == null)
            return false;

        if(value.getMass() <= 0 || !validateOperatingSpeed(value.getOperatingSpeed()))
            return false;

        if (!validateString(value.getDimensions()))
            return false;

        return true;
    }
}
