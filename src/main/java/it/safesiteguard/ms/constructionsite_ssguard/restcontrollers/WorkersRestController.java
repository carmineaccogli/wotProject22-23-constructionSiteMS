package it.safesiteguard.ms.constructionsite_ssguard.restcontrollers;

import it.safesiteguard.ms.constructionsite_ssguard.domain.ConstructionMachineryType;
import it.safesiteguard.ms.constructionsite_ssguard.domain.Worker;
import it.safesiteguard.ms.constructionsite_ssguard.dto.*;
import it.safesiteguard.ms.constructionsite_ssguard.exceptions.MachineryTypeNotFoundException;
import it.safesiteguard.ms.constructionsite_ssguard.exceptions.WorkerNotFoundException;
import it.safesiteguard.ms.constructionsite_ssguard.exceptions.WorkerTypeNotValidException;
import it.safesiteguard.ms.constructionsite_ssguard.mappers.WorkerMapper;
import it.safesiteguard.ms.constructionsite_ssguard.service.WorkerServiceImpl;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


@CrossOrigin
@RestController
@RequestMapping(value="/api/workers")
public class WorkersRestController {

    @Autowired
    private WorkerMapper workerMapper;

    @Autowired
    private WorkerServiceImpl workerService;



    @RequestMapping(value="/", method=RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ResponseDTO> workerRegistration(@Valid @RequestBody WorkerRegistrationDTO workerRegistrationDTO) throws WorkerTypeNotValidException, HttpMessageNotReadableException {

        Worker workerToAdd = workerMapper.fromRegistrationDTOToEntity(workerRegistrationDTO);
        String addedWorkerID = workerService.addNewWorker(workerToAdd);

        Map<String, Object> payload = new HashMap<>();
        payload.put("id",addedWorkerID);
        payload.put("location","/api/workers/"+addedWorkerID);

        return new ResponseEntity<>(
                new ResponseDTO("Worker of type "+workerToAdd.getType().toString()+" successfully registered",payload),
                HttpStatus.CREATED
        );
    }

    @RequestMapping(value="/", method=RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<WorkerViewDTO>> getAllWorkers() {

        List<Worker> allWorkers = workerService.getAll();

        if(allWorkers.isEmpty())
            return ResponseEntity.noContent().build();

        List<WorkerViewDTO> allWorkersDTO = fromWorkerToDTOArray(allWorkers);
        return ResponseEntity.ok(allWorkersDTO);
    }

    @RequestMapping(value="/{workerID}", method=RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<WorkerViewDTO> getWorkerById(@PathVariable("workerID")String workerID) throws WorkerNotFoundException {

        Worker worker = workerService.findWorkerById(workerID);
        WorkerViewDTO result = workerMapper.fromWorkerTypeToViewDTO(worker);

        return ResponseEntity.ok(result);
    }

    @RequestMapping(value="", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<WorkerViewDTO>> getWorkersByType(@RequestParam(name="type", required = true) String type)  {

        List<Worker> allWorkers = workerService.filterWorkersByType(type);
        if(allWorkers.isEmpty())
            return ResponseEntity.noContent().build();

        List<WorkerViewDTO> allWorkersDTO = fromWorkerToDTOArray(allWorkers);
        return ResponseEntity.ok(allWorkersDTO);
    }

    @RequestMapping(value="/equipment_operators/{machineryTypeID}/eligible", method=RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<WorkerViewDTO>> getDriversForType(@PathVariable("machineryTypeID") String machineryTypeID) throws MachineryTypeNotFoundException {

        List<Worker> eligibleOperators = workerService.getEligibleOperatorsByType(machineryTypeID);

        if(eligibleOperators.isEmpty())
            return ResponseEntity.noContent().build();

        List<WorkerViewDTO> eligibleOperatorsDTO = fromWorkerToDTOArray(eligibleOperators);
        return ResponseEntity.ok(eligibleOperatorsDTO);
    }






    private List<WorkerViewDTO> fromWorkerToDTOArray(List<Worker> entityWorkers) {
        List<WorkerViewDTO> result = new ArrayList<>();

        for(Worker worker: entityWorkers) {
            WorkerViewDTO workerViewDTO = workerMapper.fromWorkerTypeToViewDTO(worker);
            result.add(workerViewDTO);
        }
        return result;
    }

}
