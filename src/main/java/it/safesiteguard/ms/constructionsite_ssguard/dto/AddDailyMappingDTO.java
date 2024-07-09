package it.safesiteguard.ms.constructionsite_ssguard.dto;

import it.safesiteguard.ms.constructionsite_ssguard.domain.DailySiteConfiguration;
import it.safesiteguard.ms.constructionsite_ssguard.validators.ValidActiveMachineries;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDate;
import java.util.List;

public class AddDailyMappingDTO {

    @NotNull(message = "{NotNull.mapping.date}")
    private LocalDate date;

    @NotNull(message = "{NotNull.mapping.activeMachines}")
    @ValidActiveMachineries
    private List<DailySiteConfiguration.ActiveMachines> activeMachineries;



    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public List<DailySiteConfiguration.ActiveMachines> getActiveMachineries() {
        return activeMachineries;
    }

    public void setActiveMachineries(List<DailySiteConfiguration.ActiveMachines> activeMachineries) {
        this.activeMachineries = activeMachineries;
    }
}
