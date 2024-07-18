package it.safesiteguard.ms.constructionsite_ssguard.service;


import it.safesiteguard.ms.constructionsite_ssguard.domain.Beacon;
import it.safesiteguard.ms.constructionsite_ssguard.domain.ConstructionMachineryType;
import it.safesiteguard.ms.constructionsite_ssguard.domain.Machinery;
import it.safesiteguard.ms.constructionsite_ssguard.exceptions.*;
import it.safesiteguard.ms.constructionsite_ssguard.messages.AlertMessage;
import it.safesiteguard.ms.constructionsite_ssguard.messages.GeneralAlertMessage;
import it.safesiteguard.ms.constructionsite_ssguard.repositories.MachineryRepository;
import org.eclipse.paho.mqttv5.common.MqttException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class MachineryServiceImpl implements MachineryService {

    @Autowired
    private MachineryRepository machineryRepository;

    @Autowired
    private MachineryTypeService machineryTypeService;

    @Autowired
    private MqttPublisher mqttPublisher;


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
        beaconToAdd.setId("BEACON-"+(list_beacons.size() + 1)+"-"+beaconToAdd.getPosition().toLowerCase().replace(" ", "_"));

        // 4
        list_beacons.add(beaconToAdd);
        machinery.setBeaconsAssociated(list_beacons);

        // 5
        // una volta configurato, di default viene settato come disattivo se è il primo beacon da inserire
        if(machinery.getState().equals(Machinery.State.TO_CONFIGURE))
            machinery.setState(Machinery.State.INACTIVE);

        machineryRepository.save(machinery);
    }

    public void deleteBeaconFromMachinery(String machineryID, String beaconID) throws MachineryNotFoundException, BeaconNotFoundException {

        Machinery machinery = findMachineryById(machineryID);
        List<Beacon> list_beacons = machinery.getBeaconsAssociated();

        if(list_beacons.isEmpty())
            throw new BeaconNotFoundException();

        for(Beacon beacon: list_beacons) {

            if(beacon.getId().equalsIgnoreCase(beaconID)) {
                list_beacons.remove(beacon);
                machinery.setBeaconsAssociated(list_beacons);

                if(list_beacons.isEmpty())
                    machinery.setState(Machinery.State.TO_CONFIGURE);

                machineryRepository.save(machinery);
                return;
            }
        }

        throw  new BeaconNotFoundException();
    }


    public boolean sendGeneralAlarm(String description, String priority)  {

        GeneralAlertMessage alarmMessage = new GeneralAlertMessage();
        alarmMessage.setType(AlertMessage.Type.GENERAL);
        alarmMessage.setTechnologyID("mqtt-communication");
        alarmMessage.setDescription(description);
        alarmMessage.setPriority(AlertMessage.Priority.valueOf(priority.toUpperCase()));

        /*ZoneId zoneId = ZoneId.systemDefault();
        ZonedDateTime zonedDateTime = LocalDateTime.now().atZone(zoneId);*/

        alarmMessage.setTimestamp(LocalDateTime.now());

        System.out.println(alarmMessage.getType());
        System.out.println(alarmMessage.getDescription());
        System.out.println(alarmMessage.getTechnologyID());
        System.out.println(alarmMessage.getPriority());
        System.out.println(alarmMessage.getTimestamp());

        return mqttPublisher.sendGeneralAlarm(alarmMessage);
    }

}
