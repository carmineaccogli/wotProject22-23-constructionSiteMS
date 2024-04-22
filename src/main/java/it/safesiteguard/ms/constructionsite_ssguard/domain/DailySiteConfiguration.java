package it.safesiteguard.ms.constructionsite_ssguard.domain;

import com.fasterxml.jackson.annotation.JsonFormat;
import it.safesiteguard.ms.constructionsite_ssguard.dto.DailyMappingViewDTO;
import jakarta.validation.constraints.NotNull;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDate;
import java.util.List;

@Document("dailyMapping")
public class DailySiteConfiguration {


    @Id
    private String id;

    @Indexed(unique = true)
    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDate date;

    private List<ActiveMachines> activeMachines;




    public static class ActiveMachines {
        private String machineryID;
        private String machineryName;

        private String machineryType;
        private String machineryLicenceRequired;

        private List<InfoOperator> infoOperator;



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

        public String getMachineryLicenceRequired() {
            return machineryLicenceRequired;
        }

        public void setMachineryLicenceRequired(String machineryLicenceRequired) {
            this.machineryLicenceRequired = machineryLicenceRequired;
        }

        public List<InfoOperator> getInfoOperator() {
            return infoOperator;
        }

        public void setInfoOperator(List<InfoOperator> infoOperator) {
            this.infoOperator = infoOperator;
        }
    }


    public static class InfoOperator {
        private String id;
        private String fullName;

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getFullName() {
            return fullName;
        }

        public void setFullName(String fullName) {
            this.fullName = fullName;
        }
    }


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public List<ActiveMachines> getActiveMachines() {
        return activeMachines;
    }

    public void setActiveMachines(List<ActiveMachines> activeMachines) {
        this.activeMachines = activeMachines;
    }
}
