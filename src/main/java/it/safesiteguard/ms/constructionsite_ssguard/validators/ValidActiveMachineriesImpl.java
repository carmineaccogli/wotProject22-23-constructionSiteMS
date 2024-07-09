package it.safesiteguard.ms.constructionsite_ssguard.validators;

import it.safesiteguard.ms.constructionsite_ssguard.domain.DailySiteConfiguration;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

import java.util.List;

public class ValidActiveMachineriesImpl implements ConstraintValidator<ValidActiveMachineries, List<DailySiteConfiguration.ActiveMachines>> {

    @Override
    public boolean isValid(List<DailySiteConfiguration.ActiveMachines> value, ConstraintValidatorContext context) {

        if(value.isEmpty())
            return false;

        for(DailySiteConfiguration.ActiveMachines activeMachine: value) {
            if(activeMachine.getMachineryID() != null) {

                for(DailySiteConfiguration.InfoOperator operator:activeMachine.getInfoOperator()) {
                    if(operator.getId() == null)
                        return false;
                }
            } else
                return false;
        }


        return true;
    }
}
