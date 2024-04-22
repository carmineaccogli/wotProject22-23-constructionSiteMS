package it.safesiteguard.ms.constructionsite_ssguard.mappers;

import it.safesiteguard.ms.constructionsite_ssguard.domain.ConstructionMachineryType;
import it.safesiteguard.ms.constructionsite_ssguard.domain.DailySiteConfiguration;
import it.safesiteguard.ms.constructionsite_ssguard.domain.Machinery;
import it.safesiteguard.ms.constructionsite_ssguard.domain.Worker;
import it.safesiteguard.ms.constructionsite_ssguard.dto.AddDailyMappingDTO;
import it.safesiteguard.ms.constructionsite_ssguard.dto.DailyMappingViewDTO;
import it.safesiteguard.ms.constructionsite_ssguard.exceptions.MachineryNotFoundException;
import it.safesiteguard.ms.constructionsite_ssguard.exceptions.MachineryTypeNotFoundException;
import it.safesiteguard.ms.constructionsite_ssguard.service.MachineryService;
import it.safesiteguard.ms.constructionsite_ssguard.service.MachineryTypeService;
import it.safesiteguard.ms.constructionsite_ssguard.service.WorkerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class SiteConfigurationMapper {


    public DailySiteConfiguration fromRegistrationDTOToEntity(AddDailyMappingDTO addDailyMappingDTO) {
        DailySiteConfiguration siteConfiguration = new DailySiteConfiguration();

        siteConfiguration.setDate(addDailyMappingDTO.getDate());
        siteConfiguration.setActiveMachines(addDailyMappingDTO.getActiveMachines());

        return siteConfiguration;
    }

    public DailyMappingViewDTO fromEntityToDTO(DailySiteConfiguration siteConfiguration) {

        DailyMappingViewDTO dailyMappingViewDTO = new DailyMappingViewDTO();

        dailyMappingViewDTO.setId(siteConfiguration.getId());
        dailyMappingViewDTO.setDate(siteConfiguration.getDate());
        dailyMappingViewDTO.setActiveMachines(siteConfiguration.getActiveMachines());

        return dailyMappingViewDTO;
    }
}
