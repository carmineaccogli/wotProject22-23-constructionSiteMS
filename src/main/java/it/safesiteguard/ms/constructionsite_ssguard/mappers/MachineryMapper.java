package it.safesiteguard.ms.constructionsite_ssguard.mappers;

import it.safesiteguard.ms.constructionsite_ssguard.domain.Beacon;
import it.safesiteguard.ms.constructionsite_ssguard.domain.ConstructionMachineryType;
import it.safesiteguard.ms.constructionsite_ssguard.domain.Machinery;
import it.safesiteguard.ms.constructionsite_ssguard.dto.*;
import it.safesiteguard.ms.constructionsite_ssguard.exceptions.MachineryTypeNotFoundException;
import it.safesiteguard.ms.constructionsite_ssguard.service.MachineryTypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class MachineryMapper {

    @Autowired
    private MachineryTypeService machineryTypeService;

    public Machinery fromRegistrationDTOToMachinery(MachineryRegistrationDTO machineryDTO) {
        Machinery machinery = new Machinery();

        machinery.setName(machineryDTO.getName());
        machinery.setPlate(machineryDTO.getIdentificationPlate());
        machinery.setSpec(machineryDTO.getSpecs());
        machinery.setTypeID(machineryDTO.getTypeName());
        machinery.setBoard_macBLE(machineryDTO.getBoard_macBLE());

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
        machineryDTO.setBoard_macBLE(machinery.getBoard_macBLE());

        return machineryDTO;
    }

    public Beacon fromMachineryBeaconDTOToBeacon (MachineryBeaconOnlyDTO beaconOnlyDTO) {
        Beacon beacon = new Beacon();

        beacon.setPosition(beaconOnlyDTO.getPosition());
        beacon.setMacAddress(beaconOnlyDTO.getMacAddress());
        beacon.setSafetyDistance(beaconOnlyDTO.getSafetyDistance());

        return beacon;
    }


    public EquipmentOperatorMachineryDTO fromEnabledMachineriesForDriverToDTO(Machinery machinery) {
        EquipmentOperatorMachineryDTO equipmentOperatorMachineryDTO = new EquipmentOperatorMachineryDTO();

        equipmentOperatorMachineryDTO.setMachineryID(machinery.getId());
        equipmentOperatorMachineryDTO.setMachineryName(machinery.getName());
        equipmentOperatorMachineryDTO.setMachineryType(machinery.getTypeID()); // fatto apposta
        equipmentOperatorMachineryDTO.setMachinerySerialNumber(machinery.getPlate().getSerialNumber());
        equipmentOperatorMachineryDTO.setBoardMacBLE(machinery.getBoard_macBLE());


        return equipmentOperatorMachineryDTO;
    }

    public EnabledMachineryDTO fromMachineryToEnabledMachineryDTO(Machinery machinery) {
        EnabledMachineryDTO enabledMachineryDTO = new EnabledMachineryDTO();

        enabledMachineryDTO.setMachineryID(machinery.getId());
        enabledMachineryDTO.setBeaconList(machinery.getBeaconsAssociated());

        return enabledMachineryDTO;
    }
}
