package it.safesiteguard.ms.constructionsite_ssguard.service;

import it.safesiteguard.ms.constructionsite_ssguard.messages.OperatorsConfigMessage;
import org.eclipse.paho.mqttv5.common.MqttException;

import java.util.List;

public interface MqttPublisher {


    void sendToConfigurationTopic(List<OperatorsConfigMessage> configMessage) throws MqttException;
}
