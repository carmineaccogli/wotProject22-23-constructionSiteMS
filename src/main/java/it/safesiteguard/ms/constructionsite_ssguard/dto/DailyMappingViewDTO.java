package it.safesiteguard.ms.constructionsite_ssguard.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import it.safesiteguard.ms.constructionsite_ssguard.domain.DailySiteConfiguration;
import org.springframework.data.annotation.Id;

import java.time.LocalDate;
import java.util.List;

public class DailyMappingViewDTO {

    private String id;

    private LocalDate date;

    private List<DailySiteConfiguration.ActiveMachines> activeMachines;


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public List<DailySiteConfiguration.ActiveMachines> getActiveMachines() {
        return activeMachines;
    }

    public void setActiveMachines(List<DailySiteConfiguration.ActiveMachines> activeMachines) {
        this.activeMachines = activeMachines;
    }
}
