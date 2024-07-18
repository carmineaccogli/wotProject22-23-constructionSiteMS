package it.safesiteguard.ms.constructionsite_ssguard.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public class GeneralAlarmDTO {

    @NotBlank(message ="{NotBlank.generalAlarm.description}")
    private String description;

    @NotBlank(message ="{NotBlank.generalAlarm.priority}")
    private String priority;


    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getPriority() {
        return priority;
    }

    public void setPriority(String priority) {
        this.priority = priority;
    }
}
