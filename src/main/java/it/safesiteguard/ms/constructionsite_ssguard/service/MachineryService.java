package it.safesiteguard.ms.constructionsite_ssguard.service;

import it.safesiteguard.ms.constructionsite_ssguard.domain.Machinery;
import it.safesiteguard.ms.constructionsite_ssguard.exceptions.MachineryNotFoundException;
import it.safesiteguard.ms.constructionsite_ssguard.exceptions.MachineryTypeNotFoundException;

import java.util.List;

public interface MachineryService {

    List<Machinery> getAll();

    Machinery findMachineryById(String machineryID) throws MachineryNotFoundException;

    String addNewMachinery(Machinery machinery) throws MachineryTypeNotFoundException;

    void addBeaconToMachinery(String machineryID, String beaconID) throws MachineryNotFoundException;

    void updateMachineryInfo(Machinery machinery);

    List<Machinery> findMachineriesByState(Machinery.State state);
}
