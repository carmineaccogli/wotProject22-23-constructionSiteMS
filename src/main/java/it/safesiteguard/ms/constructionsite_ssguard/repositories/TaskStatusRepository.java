package it.safesiteguard.ms.constructionsite_ssguard.repositories;

import it.safesiteguard.ms.constructionsite_ssguard.domain.TaskStatus;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.Optional;

public interface TaskStatusRepository extends MongoRepository<TaskStatus, String> {

    Optional<TaskStatus> findByTaskName(String taskName);

}
