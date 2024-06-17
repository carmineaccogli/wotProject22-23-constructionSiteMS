package it.safesiteguard.ms.constructionsite_ssguard.service;

import it.safesiteguard.ms.constructionsite_ssguard.domain.DailySiteConfiguration;
import it.safesiteguard.ms.constructionsite_ssguard.exceptions.DailyMappingDateNotValidException;
import it.safesiteguard.ms.constructionsite_ssguard.exceptions.InvalidDailyMappingException;
import it.safesiteguard.ms.constructionsite_ssguard.exceptions.MappingAlreadyExistsException;
import org.eclipse.paho.mqttv5.common.MqttException;

import java.util.Map;

public interface SiteConfigurationService {

    String addDailyMapping(DailySiteConfiguration siteConfiguration) throws InvalidDailyMappingException, DailyMappingDateNotValidException, MappingAlreadyExistsException, MqttException;

    DailySiteConfiguration getLastSiteConfiguration();

    Map<String,String> getTodayEnabledMachinesForDriver(String driverID);
}
