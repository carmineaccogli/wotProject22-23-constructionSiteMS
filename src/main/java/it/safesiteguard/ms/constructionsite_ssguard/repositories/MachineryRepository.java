package it.safesiteguard.ms.constructionsite_ssguard.repositories;

import it.safesiteguard.ms.constructionsite_ssguard.domain.Machinery;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;
import java.util.Optional;

public interface MachineryRepository extends MongoRepository<Machinery, String> {

    List<Machinery> findAll();

    Optional<Machinery> findMachineryById(String machineryID);

    List<Machinery> findByState(Machinery.State state);
}
