package it.safesiteguard.ms.constructionsite_ssguard.service;

import it.safesiteguard.ms.constructionsite_ssguard.domain.ConstructionMachineryType;
import it.safesiteguard.ms.constructionsite_ssguard.domain.DailySiteConfiguration;
import it.safesiteguard.ms.constructionsite_ssguard.domain.Machinery;
import it.safesiteguard.ms.constructionsite_ssguard.domain.Worker;
import it.safesiteguard.ms.constructionsite_ssguard.dto.AuthorizedOperatorDTO;
import it.safesiteguard.ms.constructionsite_ssguard.exceptions.DailyMappingDateNotValidException;
import it.safesiteguard.ms.constructionsite_ssguard.exceptions.InvalidDailyMappingException;
import it.safesiteguard.ms.constructionsite_ssguard.exceptions.MappingAlreadyExistsException;
import it.safesiteguard.ms.constructionsite_ssguard.messages.OperatorsConfigMessage;
import it.safesiteguard.ms.constructionsite_ssguard.repositories.DailySiteConfigurationRepository;
import org.eclipse.paho.mqttv5.common.MqttException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class SiteConfigurationServiceImpl implements SiteConfigurationService {

    @Autowired
    private DailySiteConfigurationRepository siteConfigurationRepository;

    @Autowired
    private MachineryService machineryService;

    @Autowired
    private MachineryTypeService machineryTypeService;

    @Autowired
    private WorkerService workerService;

    @Autowired
    private APIService apiService;

    @Autowired
    private MqttPublisher mqttPublisher;


    /** Funzione per l'aggiunta di una nuova configurazione per il cantiere
     *  1) Check di validità per la data
     *  2) Check di unicità per la data indicata
     *  3) Creazione del nuovo oggetto di tipo "activeMachines" da salvare completando le informazioni mancanti
     *      (Introduzione di ridondanza)
     *  4) Salvataggio nel db
     *  5) Solo se il mapping si riferisce alla data attuale (ATTUAZIONE CONFIGURAZIONE):
     *      5.1) Reset stato macchinari
     *      5.2) Attivazione macchinari indicati
     *      5.3) Invio lista operatori autorizzati al topic MQTT di configurazione dei macchinari
     *
     *
     * @param siteConfiguration
     * @return
     * @throws InvalidDailyMappingException
     * @throws DailyMappingDateNotValidException
     * @throws MappingAlreadyExistsException
     */
    public String addDailyMapping(DailySiteConfiguration siteConfiguration) throws InvalidDailyMappingException, DailyMappingDateNotValidException, MappingAlreadyExistsException, MqttException {

        // 1
        LocalDate mappingDate = siteConfiguration.getDate();
        checkValidDate(mappingDate);

        // 2
        boolean mappingExists = siteConfigurationRepository.existsDailySiteConfigurationByDate(mappingDate);
        if (mappingExists)
            throw new MappingAlreadyExistsException("Site configuration for " + mappingDate + " already exists");

        // 3
        completeObjectInformation(siteConfiguration);

        // 4
        DailySiteConfiguration addedConfiguration = siteConfigurationRepository.save(siteConfiguration);

        // 5
        if(mappingDate.equals(LocalDate.now())) {
            // 5.1
            resetMachineriesState();
            //5.2
            updateMachineriesState(siteConfiguration.getActiveMachines());
            // 5.3
            sendAuthOperatorsInfo_ViaMQTT(siteConfiguration.getActiveMachines());
        }

        return addedConfiguration.getId();
    }




    /** Funzione per ottenere l'ultima configurazione presente
     *  1) Cerchiamo se esiste un mapping per la data odierna
     *  2) Se non esiste, cerchiamo l'ultima configurazione disponibile
     *      2.1) Se neanche questa esiste, viene ritornato null: la collezione è vuota
     */
    public DailySiteConfiguration getLastSiteConfiguration() {

        LocalDate date = LocalDate.now();
        Optional<DailySiteConfiguration> todayConfiguration = siteConfigurationRepository.findByDate(date);

        if(todayConfiguration.isEmpty()) {
            Optional<DailySiteConfiguration> optSiteConfiguration = siteConfigurationRepository.findFirstByOrderByDateDesc();

            return optSiteConfiguration.orElse(null);
        }

        return todayConfiguration.get();
    }


    /** TASK DI SCHEDULING PER L'AGGIORNAMENTO AUTOMATICO DELLA CONFIGURAZIONE DEL CANTIERE
     *  Timing schedulazione: ogni giorno a 00:00
     *
     *  1) Reset di quei macchinari con campo "status"="ACTIVE" a "INACTIVE"
     *  2) Verificare se è presente una daily configuration relativa al giorno corrente
     *      2.1) Se si, significa che il responsabile della sicurezza ha già aggiunto nella giornata precedente la configurazione per il giorno corrente
     *           Iteriamo sui macchinari indicati nella configurazione e settiamo il loro campo "status"="ACTIVE"
     *      2.2) Invio configurazione operatori al topic MQTT di configurazione dei macchinari
     */

    @Scheduled(cron="*/60 * * * * *") // test ogni 60 secondi
    //(cron="@midnight")
    /**
     * TODO gestire retry policy del task schedulato
     */
    public void dailyConfigurationUpdate() {

        // 1
        resetMachineriesState();

        // 2
        Optional<DailySiteConfiguration> todayConfiguration = siteConfigurationRepository.findByDate(LocalDate.now());
        if(!todayConfiguration.isEmpty()) {
            updateMachineriesState(todayConfiguration.get().getActiveMachines());

            try {
                sendAuthOperatorsInfo_ViaMQTT(todayConfiguration.get().getActiveMachines());
            }catch (MqttException ex) {

            }
        }
    }


    private void checkValidDate(LocalDate dateToCheck) throws DailyMappingDateNotValidException {
        LocalDate today = LocalDate.now();
        if( !dateToCheck.equals(today) && !dateToCheck.equals(today.plusDays(1)))
            throw new DailyMappingDateNotValidException();
    }


    private void resetMachineriesState() {
        List<Machinery> activeMachineries = machineryService.findMachineriesByState(Machinery.State.ACTIVE);

        for(Machinery machinery: activeMachineries) {
            machinery.setState(Machinery.State.INACTIVE);
            machineryService.updateMachineryInfo(machinery);
        }
    }


    private void updateMachineriesState(List<DailySiteConfiguration.ActiveMachines> activeMachinesList) {

        for(DailySiteConfiguration.ActiveMachines machine: activeMachinesList) {

            try {
                Machinery machinery = machineryService.findMachineryById(machine.getMachineryID());
                machinery.setState(Machinery.State.ACTIVE);
                machineryService.updateMachineryInfo(machinery);
            } catch(Exception ex) {
                ex.printStackTrace();
            }
        }
    }

    /** Funzione per aggiungere le informazioni mancanti all'oggetto "activeMachines"
     *  1) Creazione della nuova lista da settare
     *  2) Iterazione su tutti gli oggetti (macchinari) presenti nella lista
     *      2.1) Setting id macchinario
     *      2.2) Setting nome e nome tipologia
     *      2.3) Controllo stato
     *      2.4) Setting informazione patente
     *
     *      2.5) Iterazione sulla lista di operatori inseriti
     *          2.5.1) Recupero e controllo informazioni riguardo gli operatori
     *
     *      2.6) Aggiunta dell'oggetto completo alla lista
     *  3) Setting lista finale
     *
     * @param siteConfiguration
     * @throws InvalidDailyMappingException
     */
    private void completeObjectInformation(DailySiteConfiguration siteConfiguration) throws InvalidDailyMappingException{

        // 1
        List<DailySiteConfiguration.ActiveMachines> extendedActiveMachines = new ArrayList<>();

        //2
        for(DailySiteConfiguration.ActiveMachines activeMachine :siteConfiguration.getActiveMachines()) {

            DailySiteConfiguration.ActiveMachines newActiveMachine = new DailySiteConfiguration.ActiveMachines();

            // 2.1
            newActiveMachine.setMachineryID(activeMachine.getMachineryID());
            try {

                // 2.2
                Machinery machinery = machineryService.findMachineryById(activeMachine.getMachineryID());
                ConstructionMachineryType machineryType = machineryTypeService.getTypeByID(machinery.getTypeID());
                newActiveMachine.setMachineryName(machinery.getName());
                newActiveMachine.setMachineryType(machineryType.getName());

                // 2.3
                if(machinery.getState().equals(Machinery.State.TO_CONFIGURE)) {
                    throw new InvalidDailyMappingException();
                }

                // 2.4
                if(machineryType.isRequiredSpecificLicence())
                    newActiveMachine.setMachineryLicenceRequired("SPECIAL LICENCE");
                else
                    newActiveMachine.setMachineryLicenceRequired(machineryType.getGeneralLicence());

            } catch(Exception ex) {
                throw new InvalidDailyMappingException();
            }

            // 2.5
            List<DailySiteConfiguration.InfoOperator> infoOperatorList = new ArrayList<>();
            for(DailySiteConfiguration.InfoOperator operator:activeMachine.getInfoOperator()) {

                // 2.5.1
                DailySiteConfiguration.InfoOperator newOperator = new DailySiteConfiguration.InfoOperator();
                newOperator.setId(operator.getId());
                try {
                    Worker worker = workerService.findWorkerById(operator.getId());

                    if(!worker.getType().equals(Worker.Type.EQUIPMENT_OPERATOR))
                        throw new InvalidDailyMappingException();

                    newOperator.setFullName(worker.getName()+" "+worker.getSurname());
                } catch(Exception ex) {
                    throw new InvalidDailyMappingException();
                }

                infoOperatorList.add(newOperator);
            }

            newActiveMachine.setInfoOperator(infoOperatorList);

            // 2.6
            extendedActiveMachines.add(newActiveMachine);
        }
        // 3
        siteConfiguration.setActiveMachines(extendedActiveMachines);
    }

    /** Gestione dell'invio degli indirizzi MAC relativi agli operatori autorizzati
     *
     *  1) Chiamata API a LOGIN_MS per ottenere i mac Addresses di tutti gli operatori esistenti
     *  Per ogni macchinario presente:
     *      2) Find dei macAddresses degli operatori autorizzati per quel mezzo
     *      3) Creazione dell'oggetto configurazione (messaggio MQTT)
     *      4) Invio sul topic MQTT relativo alla configurazione
     *
     */
    private void sendAuthOperatorsInfo_ViaMQTT(List<DailySiteConfiguration.ActiveMachines> activeMachinesList) throws MqttException {

        // 1
        // Singola chiamata API con dimensione di ritorno maggiore per evitare piccole chiamate e ridurre l'overhead di rete
        List<AuthorizedOperatorDTO> allOperatorsInfo = apiService.APICALL_getAuthOperatorsMacAddresses();


        List<OperatorsConfigMessage> configurationMessage = new ArrayList<>();

        for(DailySiteConfiguration.ActiveMachines activeMachinery: activeMachinesList) {

            // 2
            List<String> macAddresses = activeMachinery.getInfoOperator().stream()
                    .map(infoOperator -> allOperatorsInfo.stream()
                            .filter(genericOperator -> genericOperator.getUserID().equals(infoOperator.getId()))
                            .map(AuthorizedOperatorDTO::getMacAddress)
                            .findFirst()
                            .orElse(null))
                    .filter(Objects::nonNull)
                    .toList();

            // 3
            OperatorsConfigMessage singleMessage = new OperatorsConfigMessage();
            singleMessage.setMachineryID(activeMachinery.getMachineryID());
            singleMessage.setDate(LocalDate.now());
            singleMessage.setAuthMacAddresses(macAddresses);

            configurationMessage.add(singleMessage);

            // 4
            System.out.println(singleMessage);
            mqttPublisher.sendToConfigurationTopic(configurationMessage);
        }

    }

}
