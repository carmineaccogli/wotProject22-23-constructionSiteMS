package it.safesiteguard.ms.constructionsite_ssguard.dto;

import jakarta.validation.constraints.NotBlank;

public class MachineryBeaconOnlyDTO {

    @NotBlank(message = "{NotBlank.machineryBeaconOnly.beaconID}")
    private String beaconID;

    public String getBeaconID() {
        return beaconID;
    }

    public void setBeaconID(String beaconID) {
        this.beaconID = beaconID;
    }
}
