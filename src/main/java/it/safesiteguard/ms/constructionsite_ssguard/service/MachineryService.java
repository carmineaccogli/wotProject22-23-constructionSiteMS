package it.safesiteguard.ms.constructionsite_ssguard.service;

import it.safesiteguard.ms.constructionsite_ssguard.domain.Beacon;
import it.safesiteguard.ms.constructionsite_ssguard.domain.Machinery;
import it.safesiteguard.ms.constructionsite_ssguard.exceptions.BeaconAlreadyAssociatedException;
import it.safesiteguard.ms.constructionsite_ssguard.exceptions.BeaconNotFoundException;
import it.safesiteguard.ms.constructionsite_ssguard.exceptions.MachineryNotFoundException;
import it.safesiteguard.ms.constructionsite_ssguard.exceptions.MachineryTypeNotFoundException;
import org.eclipse.paho.mqttv5.common.MqttException;

import java.util.List;

public interface MachineryService {

    List<Machinery> getAll();

    Machinery findMachineryById(String machineryID) throws MachineryNotFoundException;

    String addNewMachinery(Machinery machinery) throws MachineryTypeNotFoundException;

    void addBeaconToMachinery(String machineryID, Beacon beaconToAdd) throws MachineryNotFoundException, BeaconAlreadyAssociatedException;

    void updateMachineryInfo(Machinery machinery);

    List<Machinery> findMachineriesByState(Machinery.State state);

    void deleteBeaconFromMachinery(String machineryID, String beaconID) throws MachineryNotFoundException, BeaconNotFoundException;

    boolean sendGeneralAlarm(String description, String priority);
}
