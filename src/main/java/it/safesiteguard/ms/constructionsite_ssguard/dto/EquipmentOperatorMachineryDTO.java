package it.safesiteguard.ms.constructionsite_ssguard.dto;

public class EquipmentOperatorMachineryDTO {

    private String machinery_ID;

    private String machinery_name;

    private String machinery_type;

    private String machinery_serialNumber;

    private String board_macBLE;

    public String getMachinery_ID() {
        return machinery_ID;
    }

    public void setMachinery_ID(String machinery_ID) {
        this.machinery_ID = machinery_ID;
    }

    public String getBoard_macBLE() {
        return board_macBLE;
    }

    public void setBoard_macBLE(String board_macBLE) {
        this.board_macBLE = board_macBLE;
    }


    public String getMachinery_name() {
        return machinery_name;
    }

    public void setMachinery_name(String machinery_name) {
        this.machinery_name = machinery_name;
    }

    public String getMachinery_type() {
        return machinery_type;
    }

    public void setMachinery_type(String machinery_type) {
        this.machinery_type = machinery_type;
    }

    public String getMachinery_serialNumber() {
        return machinery_serialNumber;
    }

    public void setMachinery_serialNumber(String machinery_serialNumber) {
        this.machinery_serialNumber = machinery_serialNumber;
    }
}
