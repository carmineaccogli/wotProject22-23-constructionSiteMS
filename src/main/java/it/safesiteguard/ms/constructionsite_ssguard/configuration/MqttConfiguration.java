package it.safesiteguard.ms.constructionsite_ssguard.configuration;


import org.eclipse.paho.client.mqttv3.MqttConnectOptions;
import org.eclipse.paho.mqttv5.client.*;
import org.eclipse.paho.mqttv5.client.persist.MemoryPersistence;
import org.eclipse.paho.mqttv5.common.MqttException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.integration.channel.DirectChannel;
import org.springframework.integration.dsl.IntegrationFlow;
import org.springframework.integration.mqtt.inbound.MqttPahoMessageDrivenChannelAdapter;
import org.springframework.integration.mqtt.outbound.Mqttv5PahoMessageHandler;
import org.springframework.integration.mqtt.support.MqttHeaderMapper;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.MessageHeaders;



@Configuration
public class MqttConfiguration {

    @Value("${mqtt.broker.url}")
    private String brokerUrl;

    @Value("${mqtt.client.id}")
    private String clientId;

    @Value("${mqtt.auth.username}")
    private String username;

    @Value("${mqtt.auth.password}")
    private String password;





    /*@Bean
    public MqttAsyncClient mqttAsyncClient()  {
        MqttAsyncClient asyncClient = null;

        try {
            asyncClient = new MqttAsyncClient(brokerUrl, clientId);

            MqttConnectionOptions mqttConnectionOptions = new MqttConnectionOptions();
            mqttConnectionOptions.setUserName(username);
            mqttConnectionOptions.setPassword(password.getBytes());
            mqttConnectionOptions.setCleanStart(true);
            mqttConnectionOptions.setAutomaticReconnect(true);

            IMqttToken token = asyncClient.connect(mqttConnectionOptions);
            token.waitForCompletion();

            if(!asyncClient.isConnected())
                asyncClient.reconnect();

        } catch(MqttException ex) {
            ex.printStackTrace();
        }


        return asyncClient;
    }*/

    @Bean
    public IMqttAsyncClient mqttClient() throws MqttException {

        IMqttAsyncClient client = new MqttAsyncClient(brokerUrl, clientId, new MemoryPersistence());
        MqttConnectionOptions options = new MqttConnectionOptions();
        options.setUserName(username);
        options.setPassword(password.getBytes());
        options.setAutomaticReconnect(true);
        options.setCleanStart(true);

        client.connect(options).waitForCompletion();
        return client;
    }

}





