package it.safesiteguard.ms.constructionsite_ssguard.dto;

import com.mongodb.lang.NonNull;
import it.safesiteguard.ms.constructionsite_ssguard.domain.DailySiteConfiguration;
import it.safesiteguard.ms.constructionsite_ssguard.validators.ValidActiveMachines;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDate;
import java.util.List;

public class AddDailyMappingDTO {

    @NotNull(message = "{NotNull.mapping.date}")
    private LocalDate date;

    @NotNull(message = "{NotNull.mapping.activeMachines}")
    @ValidActiveMachines
    private List<DailySiteConfiguration.ActiveMachines> activeMachines;



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
