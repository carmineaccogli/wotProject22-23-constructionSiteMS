package it.safesiteguard.ms.constructionsite_ssguard.service;


import it.safesiteguard.ms.constructionsite_ssguard.domain.ConstructionMachineryType;
import it.safesiteguard.ms.constructionsite_ssguard.domain.Machinery;
import it.safesiteguard.ms.constructionsite_ssguard.domain.Worker;
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


    public void addBeaconToMachinery(String machineryID, String beaconID) throws MachineryNotFoundException {

        Machinery machinery = findMachineryById(machineryID);

        List<String> beaconsAssociated = machinery.getBeaconsAssociated();

        /**
         * TODO Check unicità id (necessità di avere una collection o meno?)
         */

        beaconsAssociated.add(beaconID);
        machinery.setBeaconsAssociated(beaconsAssociated);

        // una volta configurato, di default viene settato come disattivo se è il primo beacon da inserire
        if(machinery.getState().equals(Machinery.State.TO_CONFIGURE))
            machinery.setState(Machinery.State.INACTIVE);

        machineryRepository.save(machinery);
    }

}
