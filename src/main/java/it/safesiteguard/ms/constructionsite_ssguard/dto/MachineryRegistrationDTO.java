package it.safesiteguard.ms.constructionsite_ssguard.dto;

import it.safesiteguard.ms.constructionsite_ssguard.domain.Machinery;
import it.safesiteguard.ms.constructionsite_ssguard.validators.ValidIdentificationPlate;
import it.safesiteguard.ms.constructionsite_ssguard.validators.ValidTechSpecifications;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;

public class MachineryRegistrationDTO {


    @NotBlank(message = "{NotBlank.machinery.name}")
    private String name;

    @NotBlank(message = "{NotBlank.machinery.typeName}")
    private String typeName;

    @NotNull(message="{NotNull.machinery.identificationPlate}")
    @ValidIdentificationPlate
    private Machinery.IdentificationPlate identificationPlate;

    @NotNull(message="{NotNull.machinery.techSpecifications}")
    @ValidTechSpecifications
    private Machinery.TechSpecifications specs;

    @NotNull(message = "{NotBlank.machinery.secureDistance}")
    @Min(value = 0, message = "{Min.machinery.secureDistance}")
    private Float secureDistance;

    @Pattern(regexp = "^(([0-9A-Fa-f]{2}:){5}([0-9A-Fa-f]{2}))$", message = "Mac address format not valid")
    private String board_macBLE;


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getTypeName() {
        return typeName;
    }

    public void setTypeName(String typeName) {
        this.typeName = typeName;
    }


    public Machinery.IdentificationPlate getIdentificationPlate() {
        return identificationPlate;
    }

    public void setIdentificationPlate(Machinery.IdentificationPlate identificationPlate) {
        this.identificationPlate = identificationPlate;
    }

    public Machinery.TechSpecifications getSpecs() {
        return specs;
    }

    public void setSpecs(Machinery.TechSpecifications specs) {
        this.specs = specs;
    }

    public Float getSecureDistance() {
        return secureDistance;
    }

    public void setSecureDistance(Float secureDistance) {
        this.secureDistance = secureDistance;
    }

    public String getBoard_macBLE() {
        return board_macBLE;
    }

    public void setBoard_macBLE(String board_macBLE) {
        this.board_macBLE = board_macBLE;
    }
}
