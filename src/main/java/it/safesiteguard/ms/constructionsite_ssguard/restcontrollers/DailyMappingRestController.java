package it.safesiteguard.ms.constructionsite_ssguard.restcontrollers;


import it.safesiteguard.ms.constructionsite_ssguard.domain.ConstructionMachineryType;
import it.safesiteguard.ms.constructionsite_ssguard.domain.DailySiteConfiguration;
import it.safesiteguard.ms.constructionsite_ssguard.domain.Machinery;
import it.safesiteguard.ms.constructionsite_ssguard.domain.Worker;
import it.safesiteguard.ms.constructionsite_ssguard.dto.*;
import it.safesiteguard.ms.constructionsite_ssguard.exceptions.DailyMappingDateNotValidException;
import it.safesiteguard.ms.constructionsite_ssguard.exceptions.InvalidDailyMappingException;
import it.safesiteguard.ms.constructionsite_ssguard.exceptions.MappingAlreadyExistsException;
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

    /**
     * TODO Ottenere la lista di beacon attivi durante una certa giornata
     */
    /*
    @RequestMapping(value="/activebeacons", method =RequestMethod.GET, produces=MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<String>> getActiveBeaconsListForToday() {


    }*/

    @RequestMapping(value="/equipmentOperators/{driverID}", method=RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<EquipmentOperatorMachineryDTO>> getEnabledMachinesForDriver(@PathVariable("driverID") String driverID) {
        Map<String, String> todayEnabledMachines = siteConfigurationService.getTodayEnabledMachinesForDriver(driverID);

        if(todayEnabledMachines.isEmpty())
            return ResponseEntity.noContent().build();

        List<EquipmentOperatorMachineryDTO> allMachinesDTO = fromMappingToDTO(todayEnabledMachines);
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

}
