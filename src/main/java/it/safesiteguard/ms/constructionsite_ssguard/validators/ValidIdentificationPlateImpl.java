package it.safesiteguard.ms.constructionsite_ssguard.validators;

import it.safesiteguard.ms.constructionsite_ssguard.domain.Machinery;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

import java.time.Year;

public class ValidIdentificationPlateImpl implements ConstraintValidator<ValidIdentificationPlate, Machinery.IdentificationPlate>  {


    private static final int MIN_YEAR = 1900;

    @Override
    public boolean isValid(Machinery.IdentificationPlate value, ConstraintValidatorContext context) {

        if (value.getModel() == null || value.getManufacturerName() == null ||
                value.getSerialNumber() == null || value.getYearOfManufacture() == null)
            return false;

        if(value.getYearOfManufacture() < MIN_YEAR || value.getYearOfManufacture() > Year.now().getValue())
            return false;

        return true;
    }
}

