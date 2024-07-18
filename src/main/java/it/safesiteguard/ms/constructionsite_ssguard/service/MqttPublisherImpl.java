package it.safesiteguard.ms.constructionsite_ssguard.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import it.safesiteguard.ms.constructionsite_ssguard.messages.GeneralAlertMessage;
import it.safesiteguard.ms.constructionsite_ssguard.messages.OperatorsConfigMessage;
import org.eclipse.paho.mqttv5.client.IMqttAsyncClient;
import org.eclipse.paho.mqttv5.client.MqttAsyncClient;
import org.eclipse.paho.mqttv5.common.MqttException;
import org.eclipse.paho.mqttv5.common.MqttMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.SerializationUtils;

import java.util.List;

@Service
public class MqttPublisherImpl implements MqttPublisher {


    @Autowired
    private IMqttAsyncClient mqttAsyncClient;

    @Value("${mqtt.topic.machineriesConfigTopic}")
    private String machineriesConfigTopic;

    @Value("${mqtt.topic.allMachineriesTopic}")
    private String allMachineriesTopic;

    @Value("${mqtt.client.pubQoS}")
    private int pubQoS;



    /** Verrà inviato un unico messaggio ogni giorno contenente la configurazione di tutti i macchinari
     * L'uso del flag "retained" nell'header del messaggio permette a subscriver che verranno accesi
     * solo successivamente all'invio del messaggio di poterlo comunque ricevere
     *
     * @param configMessage
     */

    public boolean sendToConfigurationTopic(List<OperatorsConfigMessage> configMessage)  {

        MqttMessage message = new MqttMessage();

        try {
            ObjectMapper objectMapper = new ObjectMapper();
            objectMapper.registerModule(new JavaTimeModule());
            objectMapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);
            String jsonPayload = objectMapper.writeValueAsString(configMessage);

            message.setPayload(jsonPayload.getBytes());
            message.setQos(pubQoS);
            message.setRetained(true); // il broker manterrà memorizzato il messaggio per permettere a nuovi subscriber di riceverlo anche dopo la pubblicazione

            if(!mqttAsyncClient.isConnected())
                mqttAsyncClient.reconnect();

            mqttAsyncClient.publish(machineriesConfigTopic, message);
            return true;
        }catch (Exception ex) {
            ex.printStackTrace();
            return false;
        }



    }

    public boolean sendGeneralAlarm(GeneralAlertMessage alarmMessage)  {
        MqttMessage message = new MqttMessage();

        try {
            ObjectMapper objectMapper = new ObjectMapper();
            objectMapper.registerModule(new JavaTimeModule());
            objectMapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);
            String jsonPayload = objectMapper.writeValueAsString(alarmMessage);

            message.setPayload(jsonPayload.getBytes());
            message.setQos(pubQoS);
            message.setRetained(false);

            if(!mqttAsyncClient.isConnected())
                mqttAsyncClient.reconnect();

            mqttAsyncClient.publish(allMachineriesTopic, message);
            return true;

        }catch(Exception ex) {
            ex.printStackTrace();
            return false;
        }

    }


}
