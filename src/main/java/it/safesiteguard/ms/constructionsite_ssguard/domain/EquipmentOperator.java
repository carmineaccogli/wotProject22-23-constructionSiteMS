package it.safesiteguard.ms.constructionsite_ssguard.domain;


import org.springframework.data.annotation.TypeAlias;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import java.util.List;

@Document(collection="workers")
@TypeAlias("equipment_operator")
public class EquipmentOperator extends Worker{

    private String generalLicence;

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
