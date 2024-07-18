package it.safesiteguard.ms.constructionsite_ssguard.restcontrollers;


import com.mongodb.DuplicateKeyException;
import it.safesiteguard.ms.constructionsite_ssguard.domain.ConstructionMachineryType;
import it.safesiteguard.ms.constructionsite_ssguard.domain.Worker;
import it.safesiteguard.ms.constructionsite_ssguard.dto.MachineryTypeDTO;
import it.safesiteguard.ms.constructionsite_ssguard.dto.ResponseDTO;
import it.safesiteguard.ms.constructionsite_ssguard.dto.WorkerViewDTO;
import it.safesiteguard.ms.constructionsite_ssguard.exceptions.MachineryTypeNotFoundException;
import it.safesiteguard.ms.constructionsite_ssguard.mappers.MachineryTypeMapper;
import it.safesiteguard.ms.constructionsite_ssguard.service.MachineryTypeService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@CrossOrigin
@RestController
@RequestMapping(value="/api/machineries/types")
public class ConstructionMachineryTypeRestController {

    @Autowired
    private MachineryTypeService machineryTypeService;

    @Autowired
    private MachineryTypeMapper machineryTypeMapper;


    @PreAuthorize("hasAnyRole('ROLE_ADMIN','ROLE_SAFETY_MANAGER')")
    @RequestMapping(value="/", method= RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<MachineryTypeDTO>> getAllMachineryTypes() {
        List<ConstructionMachineryType> allMachineryTypes = machineryTypeService.getAll();

        if(allMachineryTypes.isEmpty())
            return ResponseEntity.noContent().build();

        List<MachineryTypeDTO> allTypesDTO = fromMachineryToDTOArray(allMachineryTypes);
        return ResponseEntity.ok(allTypesDTO);
    }

    @PreAuthorize("hasAnyRole('ROLE_ADMIN','ROLE_SAFETY_MANAGER')")
    @RequestMapping(value="", method= RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<MachineryTypeDTO>> getMachineryTypesWithSpecificLicence(@RequestParam(value="specificLicence", required = true) boolean specificLicence ) {
        List<ConstructionMachineryType> machineryTypeFound = machineryTypeService.getMachineryTypesBySpecificLicence(specificLicence);

        if(machineryTypeFound.isEmpty())
            return ResponseEntity.noContent().build();

        List<MachineryTypeDTO> typesDTO = fromMachineryToDTOArray(machineryTypeFound);
        return ResponseEntity.ok(typesDTO);
    }

    @PreAuthorize("hasAnyRole('ROLE_ADMIN','ROLE_SAFETY_MANAGER')")
    @RequestMapping(value="/", method=RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ResponseDTO> addNewType(@Valid @RequestBody MachineryTypeDTO machineryTypeDTO) throws DuplicateKeyException {

        ConstructionMachineryType typeToAdd = machineryTypeMapper.fromDTOToMachineryType(machineryTypeDTO);
        String addedTypeID = machineryTypeService.saveNewType(typeToAdd);

        Map<String, Object> payload = new HashMap<>();
        payload.put("id",addedTypeID);
        payload.put("location","/api/machineries/types/"+addedTypeID);

        return new ResponseEntity<>(
                new ResponseDTO("Machinery type successfully added",payload),
                HttpStatus.CREATED
        );
    }


    private List<MachineryTypeDTO> fromMachineryToDTOArray(List<ConstructionMachineryType> entityTypes) {
        List<MachineryTypeDTO> result = new ArrayList<>();

        for(ConstructionMachineryType machineryType: entityTypes) {
            MachineryTypeDTO machineryTypeDTO = machineryTypeMapper.fromMachineryTypeToDTO(machineryType);
            result.add(machineryTypeDTO);
        }
        return result;
    }


}
