package it.safesiteguard.ms.constructionsite_ssguard.service;

import it.safesiteguard.ms.constructionsite_ssguard.messages.OperatorsConfigMessage;
import org.eclipse.paho.mqttv5.client.MqttAsyncClient;
import org.eclipse.paho.mqttv5.common.MqttException;
import org.eclipse.paho.mqttv5.common.MqttMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.messaging.handler.MessageCondition;
import org.springframework.stereotype.Service;

import java.nio.charset.StandardCharsets;
import java.util.List;

@Service
public class MqttPublisherImpl implements MqttPublisher {


    @Autowired
    private MqttAsyncClient mqttAsyncClient;

    @Value("${mqtt.topic.machineriesConfigTopic}")
    private String machineriesConfigTopic;

    @Value("${mqtt.client.pubQoS}")
    private int pubQoS;



    /** Verrà inviato un unico messaggio ogni giorno contenente la configurazione di tutti i macchinari
     * L'uso del flag "retained" nell'header del messaggio permette a subscriver che verranno accesi
     * solo successivamente all'invio del messaggio di poterlo comunque ricevere
     *
     * @param configMessage
     * @throws MqttException
     */

    public void sendToConfigurationTopic(List<OperatorsConfigMessage> configMessage) throws MqttException {

        MqttMessage message = new MqttMessage();
        message.setPayload(configMessage.toString().getBytes(StandardCharsets.UTF_8));
        message.setQos(pubQoS);
        message.setRetained(true); // il broker manterrà memorizzato il messaggio per permettere a nuovi subscriber di riceverlo anche dopo la pubblicazione
        mqttAsyncClient.publish(machineriesConfigTopic, message);
    }


}
