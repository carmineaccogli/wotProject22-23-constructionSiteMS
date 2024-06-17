package it.safesiteguard.ms.constructionsite_ssguard.domain;

public class Beacon {

    private String id;

    private String position;

    private String macAddress;

    private Float safetyDistance;



    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getPosition() {
        return position;
    }

    public void setPosition(String position) {
        this.position = position;
    }

    public String getMacAddress() {
        return macAddress;
    }

    public void setMacAddress(String macAddress) {
        this.macAddress = macAddress;
    }

    public Float getSafetyDistance() {
        return safetyDistance;
    }

    public void setSafetyDistance(Float safetyDistance) {
        this.safetyDistance = safetyDistance;
    }
}
