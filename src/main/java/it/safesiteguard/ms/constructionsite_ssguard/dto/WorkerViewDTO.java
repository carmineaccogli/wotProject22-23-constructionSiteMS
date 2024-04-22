package it.safesiteguard.ms.constructionsite_ssguard.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import it.safesiteguard.ms.constructionsite_ssguard.domain.Worker;

import java.time.LocalDate;
import java.util.List;


@JsonInclude(JsonInclude.Include.NON_NULL)
public class WorkerViewDTO {


    private String id;
    private String name;
    private String surname;
    private String ssn;
    private String email;
    private String userID;
    private String type;
    private LocalDate dateOfBirth;

    private String generalLicense;

    private List<String> specificLicences;


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

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public String getSsn() {
        return ssn;
    }

    public void setSsn(String ssn) {
        this.ssn = ssn;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getUserID() {
        return userID;
    }

    public void setUserID(String userID) {
        this.userID = userID;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public LocalDate getDateOfBirth() {
        return dateOfBirth;
    }

    public void setDateOfBirth(LocalDate dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

    public String getGeneralLicense() {
        return generalLicense;
    }

    public void setGeneralLicense(String generalLicense) {
        this.generalLicense = generalLicense;
    }

    public List<String> getSpecificLicences() {
        return specificLicences;
    }

    public void setSpecificLicences(List<String> specificLicences) {
        this.specificLicences = specificLicences;
    }
}
