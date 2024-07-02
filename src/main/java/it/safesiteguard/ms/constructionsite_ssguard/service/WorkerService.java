package it.safesiteguard.ms.constructionsite_ssguard.service;

import com.mongodb.DuplicateKeyException;
import it.safesiteguard.ms.constructionsite_ssguard.domain.Worker;
import it.safesiteguard.ms.constructionsite_ssguard.event.WorkerDeletionEvent;
import it.safesiteguard.ms.constructionsite_ssguard.exceptions.MachineryTypeNotFoundException;
import it.safesiteguard.ms.constructionsite_ssguard.exceptions.WorkerNotFoundException;
import it.safesiteguard.ms.constructionsite_ssguard.exceptions.WorkerTypeNotValidException;
import org.springframework.web.reactive.function.client.WebClientRequestException;
import org.springframework.web.reactive.function.client.WebClientResponseException;

import java.util.List;
import java.util.Optional;

public interface WorkerService {

    List<Worker> getAll();

    String addNewWorker(Worker worker) throws WorkerTypeNotValidException, DuplicateKeyException, WebClientResponseException, WebClientRequestException;

    Worker findWorkerById(String workerID) throws WorkerNotFoundException;

    void handleWorkerDeletion(WorkerDeletionEvent event);

    List<Worker> filterWorkersByType(String type);

    List<Worker> getEligibleOperatorsByType(String machineryTypeID) throws MachineryTypeNotFoundException;

    Worker findWorkerByUserID(String userID) throws WorkerNotFoundException;
}
