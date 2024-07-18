package it.safesiteguard.ms.constructionsite_ssguard.messages;


import java.time.LocalDateTime;

public abstract class AlertMessage {


    private LocalDateTime timestamp;

    private Type type;

    private String technologyID;


    private Priority priority;


    public enum Type {
        DISTANCE, GENERAL, DRIVER_AWAY
    }

    public enum Priority {
        COMMUNICATION, WARNING, DANGER
    }



    public LocalDateTime getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(LocalDateTime timestamp) {
        this.timestamp = timestamp;
    }

    public Type getType() {
        return type;
    }

    public void setType(Type type) {
        this.type = type;
    }

    public void setPriority(Priority priority) {
        this.priority = priority;
    }

    public Priority getPriority() {
        return priority;
    }

    public String getTechnologyID() {
        return technologyID;
    }

    public void setTechnologyID(String technologyID) {
        this.technologyID = technologyID;
    }




}


