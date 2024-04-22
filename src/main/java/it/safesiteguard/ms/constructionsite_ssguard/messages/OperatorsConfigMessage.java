package it.safesiteguard.ms.constructionsite_ssguard.messages;

import java.time.LocalDate;
import java.util.List;

public class OperatorsConfigMessage {

    private LocalDate date;

    private String machineryID;

    private List<String> authMacAddresses;



    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public String getMachineryID() {
        return machineryID;
    }

    public void setMachineryID(String machineryID) {
        this.machineryID = machineryID;
    }

    public List<String> getAuthMacAddresses() {
        return authMacAddresses;
    }

    public void setAuthMacAddresses(List<String> authMacAddresses) {
        this.authMacAddresses = authMacAddresses;
    }
}
