package it.safesiteguard.ms.constructionsite_ssguard.restcontrollers;


import it.safesiteguard.ms.constructionsite_ssguard.domain.ConstructionMachineryType;
import it.safesiteguard.ms.constructionsite_ssguard.domain.DailySiteConfiguration;
import it.safesiteguard.ms.constructionsite_ssguard.domain.Machinery;
import it.safesiteguard.ms.constructionsite_ssguard.domain.Worker;
import it.safesiteguard.ms.constructionsite_ssguard.dto.*;
import it.safesiteguard.ms.constructionsite_ssguard.exceptions.DailyMappingDateNotValidException;
import it.safesiteguard.ms.constructionsite_ssguard.exceptions.InvalidDailyMappingException;
import it.safesiteguard.ms.constructionsite_ssguard.exceptions.MappingAlreadyExistsException;
import it.safesiteguard.ms.constructionsite_ssguard.mappers.MachineryMapper;
import it.safesiteguard.ms.constructionsite_ssguard.mappers.SiteConfigurationMapper;
import it.safesiteguard.ms.constructionsite_ssguard.service.SiteConfigurationService;
import jakarta.validation.Valid;
import org.eclipse.paho.mqttv5.common.MqttException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


@CrossOrigin
@RestController
@RequestMapping(value="/api/siteconfiguration")
public class DailyMappingRestController {

    @Autowired
    private SiteConfigurationMapper siteConfigurationMapper;

    @Autowired
    private SiteConfigurationService siteConfigurationService;

    @Autowired
    private MachineryMapper machineryMapper;


    @RequestMapping(value="/", method= RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ResponseDTO> addNewDailyMapping(@Valid @RequestBody AddDailyMappingDTO dailyMappingDTO) throws InvalidDailyMappingException, DailyMappingDateNotValidException, MappingAlreadyExistsException, MqttException {

        DailySiteConfiguration siteConfiguration = siteConfigurationMapper.fromRegistrationDTOToEntity(dailyMappingDTO);
        String addedConfig = siteConfigurationService.addDailyMapping(siteConfiguration);

        Map<String, Object> payload = new HashMap<>();
        payload.put("id",addedConfig);
        payload.put("location","/api/siteconfiguration/"+addedConfig);

        return new ResponseEntity<>(
                new ResponseDTO("Daily mapping successfully registered",payload),
                HttpStatus.CREATED
        );
    }

    @RequestMapping(value="/last", method= RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<DailyMappingViewDTO> getLastDailyMapping() {

        DailySiteConfiguration lastDailyMapping = siteConfigurationService.getLastSiteConfiguration();
        if(lastDailyMapping == null)
            return ResponseEntity.noContent().build();

        DailyMappingViewDTO lastDailyMappingDTO = siteConfigurationMapper.fromEntityToDTO(lastDailyMapping);
        return ResponseEntity.ok(lastDailyMappingDTO);
    }


    @RequestMapping(value="/beacons", method =RequestMethod.GET, produces=MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<EnabledMachineryDTO>> getActiveBeaconsListForToday() {
        List<Machinery> todayEnabledMachines = siteConfigurationService.getTodayEnabledMachines();

        if(todayEnabledMachines.isEmpty())
            return ResponseEntity.noContent().build();

        List<EnabledMachineryDTO> allMachinesDTO = fromListEnabledMachinesToDTO(todayEnabledMachines);
        return ResponseEntity.ok(allMachinesDTO);
    }

    @RequestMapping(value="/equipmentOperators/{driverID}", method=RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<EquipmentOperatorMachineryDTO>> getEnabledMachinesForDriver(@PathVariable("driverID") String driverID) {
        Map<String, String> todayEnabledMachinesByDriver = siteConfigurationService.getTodayEnabledMachinesForDriver(driverID);

        if(todayEnabledMachinesByDriver.isEmpty())
            return ResponseEntity.noContent().build();

        List<EquipmentOperatorMachineryDTO> allMachinesDTO = fromMappingToDTO(todayEnabledMachinesByDriver);
        return ResponseEntity.ok(allMachinesDTO);
    }



    private List<EquipmentOperatorMachineryDTO> fromMappingToDTO(Map<String, String> allMachines) {
        List<EquipmentOperatorMachineryDTO> equipmentList = new ArrayList<>();

        for (Map.Entry<String, String> entry : allMachines.entrySet()) {

            EquipmentOperatorMachineryDTO equipmentDTO = new EquipmentOperatorMachineryDTO();
            equipmentDTO.setMachinery_ID(entry.getKey());
            equipmentDTO.setBoard_macBLE(entry.getValue());
            equipmentList.add(equipmentDTO);
        }
        return equipmentList;
    }

    private List<EnabledMachineryDTO> fromListEnabledMachinesToDTO(List<Machinery> allMachines) {
        List<EnabledMachineryDTO> allMachinesDTO = new ArrayList<>();

        for(Machinery machinery: allMachines) {
            EnabledMachineryDTO machineryDTO = machineryMapper.fromMachineryToEnabledMachineryDTO(machinery);
            allMachinesDTO.add(machineryDTO);
        }

        return allMachinesDTO;
    }

}
