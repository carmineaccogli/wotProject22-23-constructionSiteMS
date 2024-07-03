package it.safesiteguard.ms.constructionsite_ssguard.mappers;


import it.safesiteguard.ms.constructionsite_ssguard.domain.EquipmentOperator;
import it.safesiteguard.ms.constructionsite_ssguard.domain.GroundWorker;
import it.safesiteguard.ms.constructionsite_ssguard.domain.Worker;
import it.safesiteguard.ms.constructionsite_ssguard.dto.EquipmentOperatorDTO;
import it.safesiteguard.ms.constructionsite_ssguard.dto.GroundWorkerDTO;
import it.safesiteguard.ms.constructionsite_ssguard.dto.WorkerRegistrationDTO;
import it.safesiteguard.ms.constructionsite_ssguard.dto.WorkerViewDTO;
import it.safesiteguard.ms.constructionsite_ssguard.exceptions.WorkerTypeNotValidException;
import org.springframework.stereotype.Component;

@Component
public class WorkerMapper {

    public WorkerViewDTO fromWorkerTypeToViewDTO(Worker worker) {

        WorkerViewDTO workerViewDTO = new WorkerViewDTO();

        workerViewDTO.setId(worker.getId());
        workerViewDTO.setName(worker.getName());
        workerViewDTO.setSurname(worker.getSurname());
        workerViewDTO.setSsn(worker.getSsn());
        workerViewDTO.setEmail(worker.getEmail());
        workerViewDTO.setDateOfBirth(worker.getDateOfBirth().toString());

        if(worker.getType().equals(Worker.Type.EQUIPMENT_OPERATOR)) {
            workerViewDTO.setGeneralLicense(((EquipmentOperator) worker).getGeneralLicence());
            workerViewDTO.setSpecificLicences(((EquipmentOperator) worker).getSpecificLicences());
        }

        return workerViewDTO;
    }

    public Worker fromRegistrationDTOToEntity(WorkerRegistrationDTO workerRegistrationDTO) throws WorkerTypeNotValidException{

        Worker worker;

        if (workerRegistrationDTO instanceof EquipmentOperatorDTO equipmentOperatorDTO) {
            EquipmentOperator equipmentOperator = new EquipmentOperator();
            equipmentOperator.setGeneralLicence(equipmentOperatorDTO.getGeneralLicence());
            equipmentOperator.setSpecificLicences(equipmentOperatorDTO.getSpecificLicences());
            worker = equipmentOperator;
            worker.setType(Worker.Type.EQUIPMENT_OPERATOR);
        } else if (workerRegistrationDTO instanceof GroundWorkerDTO) {
            worker = new GroundWorker();
            worker.setType(Worker.Type.GROUND_WORKER);
        } else {
            throw new WorkerTypeNotValidException();
        }

        // Setting dei campi comuni
        worker.setName(workerRegistrationDTO.getName());
        worker.setSurname(workerRegistrationDTO.getSurname());
        worker.setSsn(workerRegistrationDTO.getSsn());
        worker.setEmail(workerRegistrationDTO.getEmail());
        worker.setDateOfBirth(workerRegistrationDTO.getDateOfBirth());
        worker.setType(worker.getType());

        return worker;
    }
}
