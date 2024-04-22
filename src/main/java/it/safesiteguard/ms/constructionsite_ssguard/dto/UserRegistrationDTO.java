package it.safesiteguard.ms.constructionsite_ssguard.dto;

import it.safesiteguard.ms.constructionsite_ssguard.domain.Worker;

public class UserRegistrationDTO {

    private String email;

    private Worker.Type role;

    public Worker.Type getRole() {
        return role;
    }

    public void setRole(Worker.Type role) {
        this.role = role;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
