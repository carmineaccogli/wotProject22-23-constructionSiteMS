package it.safesiteguard.ms.constructionsite_ssguard.repositories;

import it.safesiteguard.ms.constructionsite_ssguard.domain.EquipmentOperator;
import it.safesiteguard.ms.constructionsite_ssguard.domain.Worker;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import java.util.List;
import java.util.Optional;

public interface WorkerRepository extends MongoRepository<Worker, String> {

    List<Worker> findAll();

    Optional<Worker> findWorkerById(String workerID);

    Optional<Worker> findWorkerByUserID(String userID);

    @Query(value="{'type': {$regex:  '^?0$', $options:  'i'}}" )
    List<Worker> findWorkersByType(String typeName);

    @Query("{'type' : 'EQUIPMENT_OPERATOR', 'specificLicences' : ?0}")
    List<Worker> findBySpecificLicences(String specificLicence);
}
