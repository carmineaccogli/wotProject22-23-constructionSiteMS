package it.safesiteguard.ms.constructionsite_ssguard.dto;

import it.safesiteguard.ms.constructionsite_ssguard.validators.ValidLicences;
import jakarta.validation.constraints.NotNull;

import java.util.List;

public class EquipmentOperatorDTO extends WorkerRegistrationDTO{


    @ValidLicences(message = "{ValidLicence.worker.generalLicence}")
    private String generalLicence;

    @NotNull(message = "{NotNull.worker.specificLicences}")
    private List<String> specificLicences;


    public String getGeneralLicence() {
        return generalLicence;
    }

    public void setGeneralLicence(String generalLicence) {
        this.generalLicence = generalLicence;
    }

    public List<String> getSpecificLicences() {
        return specificLicences;
    }

    public void setSpecificLicences(List<String> specificLicences) {
        this.specificLicences = specificLicences;
    }
}
