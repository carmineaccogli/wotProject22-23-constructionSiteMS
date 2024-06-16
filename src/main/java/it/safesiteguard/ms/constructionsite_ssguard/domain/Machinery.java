package it.safesiteguard.ms.constructionsite_ssguard.domain;


import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import java.util.List;

@Document(value="machinery")
public class Machinery {

    @Id
    private String id;

    private String typeID;

    @Indexed(unique = true)
    private String name;

    private State state;

    private List<String> beaconsAssociated;

    private IdentificationPlate plate;

    private TechSpecifications spec;

    private String board_macBLE;






    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTypeID() {
        return typeID;
    }

    public void setTypeID(String typeID) {
        this.typeID = typeID;
    }

    public State getState() {
        return state;
    }

    public void setState(State state) {
        this.state = state;
    }

    public List<String> getBeaconsAssociated() {
        return beaconsAssociated;
    }

    public void setBeaconsAssociated(List<String> beaconsAssociated) {
        this.beaconsAssociated = beaconsAssociated;
    }

    public IdentificationPlate getPlate() {
        return plate;
    }

    public void setPlate(IdentificationPlate plate) {
        this.plate = plate;
    }

    public TechSpecifications getSpec() {
        return spec;
    }

    public void setSpec(TechSpecifications spec) {
        this.spec = spec;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getBoard_macBLE() {
        return board_macBLE;
    }

    public void setBoard_macBLE(String board_macBLE) {
        this.board_macBLE = board_macBLE;
    }

    public static class IdentificationPlate {
        private Integer yearOfManufacture;
        private String manufacturerName;
        private String serialNumber;
        private String model;

        public Integer getYearOfManufacture() {
            return yearOfManufacture;
        }

        public void setYearOfManufacture(Integer yearOfManufacture) {
            this.yearOfManufacture = yearOfManufacture;
        }

        public String getManufacturerName() {
            return manufacturerName;
        }

        public void setManufacturerName(String manufacturerName) {
            this.manufacturerName = manufacturerName;
        }

        public String getSerialNumber() {
            return serialNumber;
        }

        public void setSerialNumber(String serialNumber) {
            this.serialNumber = serialNumber;
        }

        public String getModel() {
            return model;
        }

        public void setModel(String model) {
            this.model = model;
        }
    }





    public static class TechSpecifications {

        @Field("operatingSpeed_km/h")
        private Float operatingSpeed;
        @Field("mass_kg")
        private Integer mass;

        @Field("dimensions_mt(WxLxH)")
        private String dimensions;

        public Float getOperatingSpeed() {
            return operatingSpeed;
        }

        public void setOperatingSpeed(Float operatingSpeed) {
            this.operatingSpeed = operatingSpeed;
        }

        public Integer getMass() {
            return mass;
        }

        public void setMass(Integer mass) {
            this.mass = mass;
        }

        public String getDimensions() {
            return dimensions;
        }

        public void setDimensions(String dimensions) {
            this.dimensions = dimensions;
        }
    }


    public enum State {
        ACTIVE, INACTIVE, TO_CONFIGURE
    }





}
