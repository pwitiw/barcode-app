package com.frontwit.barcode.reader.infrastructure.messaging;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.frontwit.barcode.reader.application.CommandPublisher;
import com.frontwit.barcode.reader.application.ProcessBarcodeCommand;
import org.eclipse.paho.client.mqttv3.*;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.UUID;
import java.util.logging.Logger;

@Component
public class MosquittoCommandPublisher implements CommandPublisher {
    private static final Logger LOGGER = Logger.getLogger(MosquittoCommandPublisher.class.getName());

    private IMqttClient mqttClient;

    @Value("${mqtt.topic}")
    private String topic;

    @PostConstruct
    void connect() throws MqttException {
        mqttClient = new MqttClient("tcp://localhost:1883", UUID.randomUUID().toString());
        MqttConnectOptions options = new MqttConnectOptions();
        options.setAutomaticReconnect(true);
        options.setCleanSession(false);
        options.setConnectionTimeout(10);
        mqttClient.connect(options);
    }

    @Override
    public void publish(ProcessBarcodeCommand processBarcodeCommand) {
        try {
            var payload = createPayload(processBarcodeCommand);
            var msg = createMessage(payload);
            mqttClient.publish(topic, msg);
        } catch (Exception e) {
            LOGGER.warning("Error while publishing command: " + processBarcodeCommand);
            LOGGER.warning(e.getMessage());
        }
    }

    private MqttMessage createMessage(byte[] payload) {
        MqttMessage msg = new MqttMessage();
        msg.setQos(2);
        msg.setPayload(payload);
        return msg;
    }

    private byte[] createPayload(ProcessBarcodeCommand command) throws JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();
        var json = mapper.writeValueAsString(command);
        return json.getBytes();
    }


}
