package it.safesiteguard.ms.constructionsite_ssguard.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import it.safesiteguard.ms.constructionsite_ssguard.domain.DailySiteConfiguration;
import org.springframework.data.annotation.Id;

import java.time.LocalDate;
import java.util.List;

public class DailyMappingViewDTO {

    private String id;

    private String date;

    private List<DailySiteConfiguration.ActiveMachines> activeMachineries;


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public List<DailySiteConfiguration.ActiveMachines> getActiveMachineries() {
        return activeMachineries;
    }

    public void setActiveMachineries(List<DailySiteConfiguration.ActiveMachines> activeMachineries) {
        this.activeMachineries = activeMachineries;
    }
}
