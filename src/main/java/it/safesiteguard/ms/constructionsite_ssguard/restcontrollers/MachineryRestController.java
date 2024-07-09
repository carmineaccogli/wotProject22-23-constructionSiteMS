package it.safesiteguard.ms.constructionsite_ssguard.restcontrollers;


import it.safesiteguard.ms.constructionsite_ssguard.domain.Beacon;
import it.safesiteguard.ms.constructionsite_ssguard.domain.ConstructionMachineryType;
import it.safesiteguard.ms.constructionsite_ssguard.domain.Machinery;
import it.safesiteguard.ms.constructionsite_ssguard.domain.Worker;
import it.safesiteguard.ms.constructionsite_ssguard.dto.*;
import it.safesiteguard.ms.constructionsite_ssguard.exceptions.*;
import it.safesiteguard.ms.constructionsite_ssguard.mappers.MachineryMapper;
import it.safesiteguard.ms.constructionsite_ssguard.service.MachineryService;
import jakarta.validation.Valid;
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
@RequestMapping(value="/api/machineries")
public class MachineryRestController {

    @Autowired
    private MachineryMapper machineryMapper;

    @Autowired
    private MachineryService machineryService;

    @RequestMapping(value="/", method= RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ResponseDTO> machineryRegistration(@Valid @RequestBody MachineryRegistrationDTO machineryRegistrationDTO) throws MachineryTypeNotFoundException {

        Machinery machineryToAdd = machineryMapper.fromRegistrationDTOToMachinery(machineryRegistrationDTO);
        String addedMachineryID = machineryService.addNewMachinery(machineryToAdd);

        Map<String, Object> payload = new HashMap<>();
        payload.put("id",addedMachineryID);
        payload.put("location","/api/machineries/"+addedMachineryID);

        return new ResponseEntity<>(
                new ResponseDTO("Machinery successfully registered",payload),
                HttpStatus.CREATED
        );
    }

    @RequestMapping(value="/", method= RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<MachineryDTO>> getAllMachineries() {
        List<Machinery> allMachineries = machineryService.getAll();

        if(allMachineries.isEmpty())
            return ResponseEntity.noContent().build();

        List<MachineryDTO> allMachineriesDTO = fromMachineryToDTOArray(allMachineries);
        return ResponseEntity.ok(allMachineriesDTO);
    }

    @RequestMapping(value="/{machineryID}", method=RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<MachineryDTO> getMachineryByID(@PathVariable("machineryID")String machineryID) throws MachineryNotFoundException {

        Machinery machinery = machineryService.findMachineryById(machineryID);
        MachineryDTO result = machineryMapper.fromMachineryToDTO(machinery);

        return ResponseEntity.ok(result);
    }

    @RequestMapping(value="/{machineryID}/beacons", method=RequestMethod.PATCH, consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> addBeaconToMachinery(@PathVariable("machineryID") String machineryID, @Valid @RequestBody MachineryBeaconOnlyDTO machineryBeaconOnlyDTO) throws MachineryNotFoundException, BeaconAlreadyAssociatedException {

        Beacon beaconToAdd = machineryMapper.fromMachineryBeaconDTOToBeacon(machineryBeaconOnlyDTO);
        machineryService.addBeaconToMachinery(machineryID, beaconToAdd);
        return ResponseEntity.noContent().build();
    }

    @RequestMapping(value="/{machineryID}/beacons/{beaconID}", method = RequestMethod.DELETE)
    public ResponseEntity<?> removeBeaconFromMachinery(@PathVariable("machineryID") String machineryID,
                                                       @PathVariable("beaconID") String beaconID) throws MachineryNotFoundException, BeaconNotFoundException {

        machineryService.deleteBeaconFromMachinery(machineryID, beaconID);
        return ResponseEntity.noContent().build();
    }

    /*@RequestMapping(value="/alarms", method=RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ResponseDTO> sendGeneralAlarms(@RequestBody GeneralAlarmDTO generalAlarmDTO) {



    }*/



    private List<MachineryDTO> fromMachineryToDTOArray(List<Machinery> entityMachinaries) {
        List<MachineryDTO> result = new ArrayList<>();

        for(Machinery machinery: entityMachinaries) {
            MachineryDTO machineryDTO = machineryMapper.fromMachineryToDTO(machinery);
            result.add(machineryDTO);
        }
        return result;
    }
}
