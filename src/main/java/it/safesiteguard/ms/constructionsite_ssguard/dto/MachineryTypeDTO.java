package it.safesiteguard.ms.constructionsite_ssguard.dto;


import it.safesiteguard.ms.constructionsite_ssguard.validators.ValidLicences;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;

public class MachineryTypeDTO {

    private String id;

    @NotBlank(message="{NotBlank.type.name}")
    private String name;

    @NotBlank(message="{NotBlank.type.description}")
    private String description;

    @NotBlank(message="{NotBlank.type.generalLicence}")
    @Pattern(regexp ="^(A1|B|C)$", message = "{Pattern.type.generalLicence}")
    private String generalLicence;

    @NotNull(message="{NotNull.type.requiredSpecificLicence}")
    private boolean requiredSpecificLicence;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getGeneralLicence() {
        return generalLicence;
    }

    public void setGeneralLicence(String generalLicence) {
        this.generalLicence = generalLicence;
    }

    public boolean isRequiredSpecificLicence() {
        return requiredSpecificLicence;
    }

    public void setRequiredSpecificLicence(boolean requiredSpecificLicence) {
        this.requiredSpecificLicence = requiredSpecificLicence;
    }
}
