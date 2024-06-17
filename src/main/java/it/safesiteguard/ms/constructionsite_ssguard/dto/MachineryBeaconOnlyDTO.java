package it.safesiteguard.ms.constructionsite_ssguard.dto;

import jakarta.validation.constraints.*;

public class MachineryBeaconOnlyDTO {

    @NotBlank(message = "{NotBlank.machineryBeaconOnly.macAddress}")
    @Pattern(regexp = "^(([0-9A-Fa-f]{2}:){5}([0-9A-Fa-f]{2}))$", message = "Mac address format not valid")
    private String macAddress;

    @NotBlank(message = "{NotBlank.machineryBeaconOnly.position}")
    private String position;

    @NotNull(message = "{NotNull.machineryBeaconOnly.safetyDistance}")
    @DecimalMin(value = "0.1", message = "{DecimalMin.machineryBeaconOnly.safetyDistance}")
    @DecimalMax(value = "100.0", message = "{DecimalMax.machineryBeaconOnly.safetyDistance}")
    private Float safetyDistance;


    public String getMacAddress() {
        return macAddress;
    }

    public void setMacAddress(String macAddress) {
        this.macAddress = macAddress;
    }

    public String getPosition() {
        return position;
    }

    public void setPosition(String position) {
        this.position = position;
    }

    public Float getSafetyDistance() {
        return safetyDistance;
    }

    public void setSafetyDistance(Float safetyDistance) {
        this.safetyDistance = safetyDistance;
    }
}
