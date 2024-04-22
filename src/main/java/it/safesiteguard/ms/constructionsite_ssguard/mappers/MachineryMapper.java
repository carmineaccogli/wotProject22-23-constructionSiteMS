package it.safesiteguard.ms.constructionsite_ssguard.mappers;

import it.safesiteguard.ms.constructionsite_ssguard.domain.ConstructionMachineryType;
import it.safesiteguard.ms.constructionsite_ssguard.domain.Machinery;
import it.safesiteguard.ms.constructionsite_ssguard.dto.MachineryDTO;
import it.safesiteguard.ms.constructionsite_ssguard.dto.MachineryRegistrationDTO;
import it.safesiteguard.ms.constructionsite_ssguard.exceptions.MachineryTypeNotFoundException;
import it.safesiteguard.ms.constructionsite_ssguard.service.MachineryTypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class MachineryMapper {

    @Autowired
    private MachineryTypeService machineryTypeService;

    public Machinery fromRegistrationDTOToMachinery(MachineryRegistrationDTO machineryDTO) {
        Machinery machinery = new Machinery();

        machinery.setName(machineryDTO.getName());
        machinery.setPlate(machineryDTO.getIdentificationPlate());
        machinery.setSecureDistance(machineryDTO.getSecureDistance());
        machinery.setSpec(machineryDTO.getSpecs());
        machinery.setTypeID(machineryDTO.getTypeName());

        return machinery;
    }


    public MachineryDTO fromMachineryToDTO(Machinery machinery) {
        MachineryDTO machineryDTO = new MachineryDTO();

        machineryDTO.setId(machinery.getId());
        machineryDTO.setName(machinery.getName());
        machineryDTO.setState(machinery.getState());
        try {
           ConstructionMachineryType type = machineryTypeService.getTypeByID(machinery.getTypeID());
           machineryDTO.setTypeName(type.getName());
        } catch (MachineryTypeNotFoundException ex) {
            machineryDTO.setTypeName(null);
        }
        machineryDTO.setSpec(machinery.getSpec());
        machineryDTO.setPlate(machinery.getPlate());
        machineryDTO.setBeaconsAssociated(machinery.getBeaconsAssociated());
        machineryDTO.setSeacureDistance(machinery.getSecureDistance());

        return machineryDTO;
    }
}
