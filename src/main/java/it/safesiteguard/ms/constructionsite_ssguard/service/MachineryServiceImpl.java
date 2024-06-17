package it.safesiteguard.ms.constructionsite_ssguard.service;


import it.safesiteguard.ms.constructionsite_ssguard.domain.Beacon;
import it.safesiteguard.ms.constructionsite_ssguard.domain.ConstructionMachineryType;
import it.safesiteguard.ms.constructionsite_ssguard.domain.Machinery;
import it.safesiteguard.ms.constructionsite_ssguard.domain.Worker;
import it.safesiteguard.ms.constructionsite_ssguard.exceptions.BeaconAlreadyAssociatedException;
import it.safesiteguard.ms.constructionsite_ssguard.exceptions.MachineryNotFoundException;
import it.safesiteguard.ms.constructionsite_ssguard.exceptions.MachineryTypeNotFoundException;
import it.safesiteguard.ms.constructionsite_ssguard.exceptions.WorkerNotFoundException;
import it.safesiteguard.ms.constructionsite_ssguard.repositories.MachineryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class MachineryServiceImpl implements MachineryService {

    @Autowired
    private MachineryRepository machineryRepository;

    @Autowired
    private MachineryTypeService machineryTypeService;

    public List<Machinery> getAll() {
        return machineryRepository.findAll();
    }

    public Machinery findMachineryById(String machineryID) throws MachineryNotFoundException {

        Machinery machinery = null;

        Optional<Machinery> optMachinery = machineryRepository.findMachineryById(machineryID);
        if (!optMachinery.isPresent())
            throw new MachineryNotFoundException();

        machinery = optMachinery.get();
        return machinery;
    }


    public String addNewMachinery(Machinery machinery) throws MachineryTypeNotFoundException {

        // Check type validity
        ConstructionMachineryType type = machineryTypeService.getTypeByName(machinery.getTypeID());

        // Setting fields to be automated
        machinery.setState(Machinery.State.TO_CONFIGURE);
        machinery.setBeaconsAssociated(new ArrayList<>());
        machinery.setTypeID(type.getId());

        Machinery machineryAdded = machineryRepository.save(machinery);
        return machineryAdded.getId();
    }

    public void updateMachineryInfo(Machinery machinery) {
        machineryRepository.save(machinery);
    }

    public List<Machinery> findMachineriesByState(Machinery.State state) {
        return machineryRepository.findByState(state);
    }


    /** INSERIMENTO NUOVO BEACON ASSOCIATO AL MACCHINARIO
     *
     *  1. Recupero la lista di beacons già associati
     *  2. Controllo che il mac address del beacon da inserire non sia uguale a un altro già presente
     *  3. Generazione ID nuovo beacon
     *  4. Aggiungo il nuovo beacon alla lista di quelli associati
     *  5. Controllo se questo è il primo beacon aggiunto e cambio lo stato del macchinario di conseguenza
     * @param machineryID
     * @param beaconToAdd
     * @throws MachineryNotFoundException
     */
    public void addBeaconToMachinery(String machineryID, Beacon beaconToAdd) throws MachineryNotFoundException, BeaconAlreadyAssociatedException {

        // 1
        Machinery machinery = findMachineryById(machineryID);
        List<Beacon> list_beacons = machinery.getBeaconsAssociated();

        // 2
        for(Beacon beacon: list_beacons) {
            if(beacon.getMacAddress().equalsIgnoreCase(beaconToAdd.getMacAddress()))
                throw new BeaconAlreadyAssociatedException();
        }

        // 3
        beaconToAdd.setId("BEACON-"+(list_beacons.size() + 1)+"-"+beaconToAdd.getPosition().toLowerCase());

        // 4
        list_beacons.add(beaconToAdd);
        machinery.setBeaconsAssociated(list_beacons);

        // 5
        // una volta configurato, di default viene settato come disattivo se è il primo beacon da inserire
        if(machinery.getState().equals(Machinery.State.TO_CONFIGURE))
            machinery.setState(Machinery.State.INACTIVE);

        machineryRepository.save(machinery);
    }

}
