package it.safesiteguard.ms.constructionsite_ssguard.dto;

import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import it.safesiteguard.ms.constructionsite_ssguard.validators.SSNFormat;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDate;

@JsonTypeInfo(
        use = JsonTypeInfo.Id.NAME,
        include = JsonTypeInfo.As.EXISTING_PROPERTY,
        property = "type",
        visible = true)
@JsonSubTypes({
        @JsonSubTypes.Type(value = EquipmentOperatorDTO.class, name = "EQUIPMENT_OPERATOR"),
        @JsonSubTypes.Type(value = GroundWorkerDTO.class, name = "GROUND_WORKER")
})
public abstract class WorkerRegistrationDTO {


    @NotBlank(message = "{NotBlank.worker.name}")
    private String name;
    @NotBlank(message = "{NotBlank.worker.surname}")
    private String surname;
    @NotBlank(message = "{NotBlank.worker.ssn}")
    @SSNFormat(message= "{SSNFormat.worker.ssn}")
    private String ssn;

    @NotBlank(message = "{NotBlank.worker.email}")
    @Email(message = "{Email.worker.email}")
    private String email;

    @NotBlank(message = "{NotBlank.worker.type}")
    private String type;

    @NotNull(message = "{NotBlank.worker.dateOfBirth}")
    private LocalDate dateOfBirth;

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
}
