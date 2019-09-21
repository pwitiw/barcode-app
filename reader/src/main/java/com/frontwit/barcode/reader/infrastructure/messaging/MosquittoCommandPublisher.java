package com.frontwit.barcode.reader.infrastructure.messaging;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.frontwit.barcode.reader.application.CommandPublisher;
import com.frontwit.barcode.reader.application.ProcessBarcodeCommand;
import org.eclipse.paho.client.mqttv3.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;

import static java.lang.String.format;

@Component
public class MosquittoCommandPublisher implements CommandPublisher {
    private static final Logger LOGGER = LoggerFactory.getLogger(MosquittoCommandPublisher.class);


    private IMqttClient mqttClient;
    private ObjectMapper objectMapper;

    @Value("${mqtt.topic}")
    private String topic;

    public MosquittoCommandPublisher(ObjectMapper objectMapper) {
        this.objectMapper = objectMapper;
    }

    @PostConstruct
    void connect() throws MqttException {
        mqttClient = new MqttClient("tcp://localhost:1883", "reader");
        MqttConnectOptions options = new MqttConnectOptions();
        options.setAutomaticReconnect(true);
        options.setCleanSession(false);
        options.setConnectionTimeout(10);
        mqttClient.connect(options);
    }

    @PreDestroy
    void disconnect() throws MqttException {
        if (mqttClient.isConnected()) {
            mqttClient.close();
        }
    }

    @Override
    public void publish(ProcessBarcodeCommand command) {
        new Thread(() -> publish(topic, createMessage(command))).start();
    }

    private synchronized void publish(String topic, MqttMessage mqttMessage) {
        try {
            mqttClient.publish(topic, mqttMessage);
        } catch (Exception e) {
            LOGGER.warn(format("Error while publishing. Reason: %s", e.getMessage()));
        }
    }

    private MqttMessage createMessage(ProcessBarcodeCommand command) {
        String json = "";
        try {
            json = objectMapper.writeValueAsString(command);
        } catch (JsonProcessingException e) {
            LOGGER.warn(e.getMessage());
        }
        MqttMessage msg = new MqttMessage();
        msg.setQos(1);
        msg.setPayload(json.getBytes());
        return msg;
    }

}
