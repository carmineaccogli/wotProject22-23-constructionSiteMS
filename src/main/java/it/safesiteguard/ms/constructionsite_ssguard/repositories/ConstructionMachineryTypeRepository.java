package it.safesiteguard.ms.constructionsite_ssguard.repositories;

import it.safesiteguard.ms.constructionsite_ssguard.domain.ConstructionMachineryType;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import java.util.List;
import java.util.Optional;

public interface ConstructionMachineryTypeRepository extends MongoRepository<ConstructionMachineryType, String> {

    List<ConstructionMachineryType> findAll();


    List<ConstructionMachineryType> findConstructionMachineryTypeByRequiredSpecificLicence(boolean specificLicence);

    Optional<ConstructionMachineryType> findConstructionMachineryTypeByName(String typeName);

    Optional<ConstructionMachineryType> findConstructionMachineryTypeById(String typeID);
}
