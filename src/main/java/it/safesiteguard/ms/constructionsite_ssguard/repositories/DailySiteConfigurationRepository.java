package it.safesiteguard.ms.constructionsite_ssguard.repositories;

import it.safesiteguard.ms.constructionsite_ssguard.domain.DailySiteConfiguration;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.time.LocalDate;
import java.util.Optional;

public interface DailySiteConfigurationRepository extends MongoRepository<DailySiteConfiguration, String> {


    Optional<DailySiteConfiguration> findFirstByOrderByDateDesc();

    boolean existsDailySiteConfigurationByDate(LocalDate date);

    Optional<DailySiteConfiguration> findByDate(LocalDate date);
}
