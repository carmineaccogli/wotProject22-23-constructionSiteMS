package it.safesiteguard.ms.constructionsite_ssguard.domain;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(value="machineryType")
public class ConstructionMachineryType {

    @Id
    private String id;

    @Indexed(unique = true)
    private String name;

    private String description;

    private String generalLicence;

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
