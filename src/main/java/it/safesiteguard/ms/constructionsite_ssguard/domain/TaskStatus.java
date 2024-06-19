package it.safesiteguard.ms.constructionsite_ssguard.domain;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDate;

@Document(value="scheduledTask")
public class TaskStatus {

    @Id
    private String id;

    private String taskName;

    private LocalDate lastExecution;


    public LocalDate getLastExecution() {
        return lastExecution;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTaskName() {
        return taskName;
    }

    public void setTaskName(String taskName) {
        this.taskName = taskName;
    }

    public void setLastExecution(LocalDate lastExecution) {
        this.lastExecution = lastExecution;
    }
}
