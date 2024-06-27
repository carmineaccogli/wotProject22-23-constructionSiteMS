package it.safesiteguard.ms.constructionsite_ssguard.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

public class EquipmentOperatorMachineryDTO {

    private String machineryID;

    private String machineryName;

    private String machineryType;

    private String machinerySerialNumber;

    private String boardMacBLE;

    @JsonProperty("isRemote")
    private Boolean isRemote;

    public String getMachineryID() {
        return machineryID;
    }

    public void setMachineryID(String machineryID) {
        this.machineryID = machineryID;
    }

    public String getMachineryName() {
        return machineryName;
    }

    public void setMachineryName(String machineryName) {
        this.machineryName = machineryName;
    }

    public String getMachineryType() {
        return machineryType;
    }

    public void setMachineryType(String machineryType) {
        this.machineryType = machineryType;
    }

    public String getMachinerySerialNumber() {
        return machinerySerialNumber;
    }

    public void setMachinerySerialNumber(String machinerySerialNumber) {
        this.machinerySerialNumber = machinerySerialNumber;
    }

    public String getBoardMacBLE() {
        return boardMacBLE;
    }

    public void setBoardMacBLE(String boardMacBLE) {
        this.boardMacBLE = boardMacBLE;
    }

    @JsonProperty("isRemote")
    public Boolean getIsRemote() {
        return isRemote;
    }

    @JsonProperty("isRemote")
    public void setIsRemote(Boolean isRemote) {
        this.isRemote = isRemote;
    }
}
