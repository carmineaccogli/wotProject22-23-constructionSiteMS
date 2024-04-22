package it.safesiteguard.ms.constructionsite_ssguard.mappers;


import it.safesiteguard.ms.constructionsite_ssguard.domain.ConstructionMachineryType;
import it.safesiteguard.ms.constructionsite_ssguard.dto.MachineryTypeDTO;
import org.springframework.stereotype.Component;

@Component
public class MachineryTypeMapper {


    public MachineryTypeDTO fromMachineryTypeToDTO(ConstructionMachineryType machineryType) {

        MachineryTypeDTO machineryTypeDTO = new MachineryTypeDTO();

        machineryTypeDTO.setId(machineryType.getId());
        machineryTypeDTO.setName(machineryType.getName());
        machineryTypeDTO.setDescription(machineryType.getDescription());
        machineryTypeDTO.setGeneralLicence(machineryType.getGeneralLicence());
        machineryTypeDTO.setRequiredSpecificLicence(machineryType.isRequiredSpecificLicence());

        return machineryTypeDTO;
    }

    public ConstructionMachineryType fromDTOToMachineryType(MachineryTypeDTO machineryTypeDTO) {
        ConstructionMachineryType machineryType = new ConstructionMachineryType();

        machineryType.setName(machineryTypeDTO.getName());
        machineryType.setDescription(machineryTypeDTO.getDescription());
        machineryType.setGeneralLicence(machineryTypeDTO.getGeneralLicence());
        machineryType.setRequiredSpecificLicence(machineryTypeDTO.isRequiredSpecificLicence());

        return machineryType;
    }
}
