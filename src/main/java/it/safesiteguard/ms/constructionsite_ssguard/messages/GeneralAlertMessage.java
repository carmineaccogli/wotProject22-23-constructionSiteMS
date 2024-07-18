package it.safesiteguard.ms.constructionsite_ssguard.messages;


import java.io.Serializable;

public class GeneralAlertMessage extends AlertMessage {

    private String description;


    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
