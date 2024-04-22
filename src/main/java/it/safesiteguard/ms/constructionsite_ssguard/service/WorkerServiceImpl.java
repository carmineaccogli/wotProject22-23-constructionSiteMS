package it.safesiteguard.ms.constructionsite_ssguard.service;


import com.mongodb.DuplicateKeyException;
import it.safesiteguard.ms.constructionsite_ssguard.domain.ConstructionMachineryType;
import it.safesiteguard.ms.constructionsite_ssguard.domain.EquipmentOperator;
import it.safesiteguard.ms.constructionsite_ssguard.domain.GroundWorker;
import it.safesiteguard.ms.constructionsite_ssguard.domain.Worker;
import it.safesiteguard.ms.constructionsite_ssguard.event.WorkerDeletionEvent;
import it.safesiteguard.ms.constructionsite_ssguard.exceptions.MachineryTypeNotFoundException;
import it.safesiteguard.ms.constructionsite_ssguard.exceptions.WorkerNotFoundException;
import it.safesiteguard.ms.constructionsite_ssguard.exceptions.WorkerTypeNotValidException;
import it.safesiteguard.ms.constructionsite_ssguard.repositories.WorkerRepository;
import it.safesiteguard.ms.constructionsite_ssguard.utils.LicenceUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClientRequestException;
import org.springframework.web.reactive.function.client.WebClientResponseException;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class WorkerServiceImpl implements WorkerService{

    @Autowired
    private WorkerRepository workerRepository;

    @Autowired
    private MachineryTypeService machineryTypeService;

    @Autowired
    private APIService apiService;

    public List<Worker> getAll() {
        return workerRepository.findAll();
    }

    public Worker findWorkerById(String workerID) throws WorkerNotFoundException {

        Worker worker = null;

        Optional<Worker> optWorker = workerRepository.findWorkerById(workerID);
        if(!optWorker.isPresent())
            throw new WorkerNotFoundException();

        worker = optWorker.get();
        return worker;
    }

    /**
     *
     * @param worker
     * @return
     * @throws DuplicateKeyException
     * @throws WebClientResponseException
     * @throws WebClientRequestException
     * @throws WorkerTypeNotValidException -> controllo aggiuntivo dato che è già previsto nella classe di mapping per evitare
     *                                        errori di compilatore
     */
    public String addNewWorker(Worker worker) throws DuplicateKeyException, WebClientResponseException, WebClientRequestException, WorkerTypeNotValidException {

        Worker addedWorker = saveForType(worker);

        // chiamata all'API di LOGINMS per creare lo user per l'accesso al sistema
        //String userID_created = apiService.APICALL_createUser(worker);
        String userID_created = "carlo";

        // chiamata all'API successfull
        // inserimento dello user ID appena creato
        worker.setUserID(userID_created);

        // update del cittadino
        saveForType(worker);

        return addedWorker.getId();
    }

    public List<Worker> filterWorkersByType(String type) {
        return workerRepository.findWorkersByType(type);
    }


    public List<Worker> getEligibleOperatorsByType(String machineryTypeID) throws MachineryTypeNotFoundException {

        // 1
        ConstructionMachineryType machineryType = machineryTypeService.getTypeByID(machineryTypeID);

        // 2
        if(machineryType.isRequiredSpecificLicence())
            return workerRepository.findBySpecificLicences(machineryType.getName());
        else {
            int machineryLicence = LicenceUtil.licencesClassification(machineryType.getGeneralLicence());
            List<Worker> equipmentOperators = filterWorkersByType("EQUIPMENT_OPERATOR");
            List<Worker> results = new ArrayList<>();

            for(Worker worker: equipmentOperators) {
                int driverLicence = LicenceUtil.licencesClassification(((EquipmentOperator) worker).getGeneralLicence());
                if(driverLicence >= machineryLicence)
                    results.add(worker);
            }

            return results;
        }
    }




    @EventListener
    public void handleWorkerDeletion(WorkerDeletionEvent event) {
        Worker worker = event.getWorker();
        workerRepository.delete(worker);
    }


    private Worker saveForType(Worker worker) throws WorkerTypeNotValidException{

        if(worker instanceof EquipmentOperator){
            return workerRepository.save((EquipmentOperator) worker);
        }
        else if(worker instanceof GroundWorker) {
            return workerRepository.save((GroundWorker) worker);
        }
        else {
            throw new WorkerTypeNotValidException();
        }
    }



}
