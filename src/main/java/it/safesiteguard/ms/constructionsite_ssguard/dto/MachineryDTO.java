package it.safesiteguard.ms.constructionsite_ssguard.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import it.safesiteguard.ms.constructionsite_ssguard.domain.Beacon;
import it.safesiteguard.ms.constructionsite_ssguard.domain.Machinery;

import java.util.List;

public class MachineryDTO {

    private String id;

    private String name;

    private String typeName;

    private Machinery.State state;

    private List<Beacon> beaconsAssociated;

    private Machinery.IdentificationPlate plate;

    private Machinery.TechSpecifications spec;


    private String board_macBLE;

    @JsonProperty("isRemote")
    private Boolean isRemote;



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

    public String getTypeName() {
        return typeName;
    }

    public void setTypeName(String typeName) {
        this.typeName = typeName;
    }

    public Machinery.State getState() {
        return state;
    }

    public void setState(Machinery.State state) {
        this.state = state;
    }

    public List<Beacon> getBeaconsAssociated() {
        return beaconsAssociated;
    }

    public void setBeaconsAssociated(List<Beacon> beaconsAssociated) {
        this.beaconsAssociated = beaconsAssociated;
    }

    public Machinery.IdentificationPlate getPlate() {
        return plate;
    }

    public void setPlate(Machinery.IdentificationPlate plate) {
        this.plate = plate;
    }

    public Machinery.TechSpecifications getSpec() {
        return spec;
    }

    public void setSpec(Machinery.TechSpecifications spec) {
        this.spec = spec;
    }

    public String getBoard_macBLE() {
        return board_macBLE;
    }

    public void setBoard_macBLE(String board_macBLE) {
        this.board_macBLE = board_macBLE;
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
