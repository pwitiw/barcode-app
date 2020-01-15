package com.frontwit.barcodeapp.administration.processing.front.infrastructure.messaging;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.frontwit.barcodeapp.administration.processing.front.application.FrontProcessor;
import com.frontwit.barcodeapp.administration.processing.front.application.dto.ProcessFrontCommand;
import org.eclipse.paho.client.mqttv3.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import java.io.IOException;

import static java.lang.String.format;


public class MosquittoCommandHandler implements MqttCallback {
    private static final Logger LOGGER = LoggerFactory.getLogger(MosquittoCommandHandler.class);

    private FrontProcessor frontProcessor;
    private ObjectMapper objectMapper;

    private IMqttClient mqttClient;

    @Value("${mqtt.uri}")
    private String uri;

    @Value("${mqtt.topic}")
    private String topic;

    public MosquittoCommandHandler(FrontProcessor frontProcessor, ObjectMapper objectMapper) {
        this.frontProcessor = frontProcessor;
        this.objectMapper = objectMapper;
    }

    @PostConstruct
    void connect() throws MqttException {
        var options = new MqttConnectOptions();
        options.setAutomaticReconnect(true);
        mqttClient = new MqttClient(uri, "administration");
        mqttClient.setCallback(this);
        mqttClient.connect(options);
        mqttClient.subscribe(topic);
        LOGGER.info("[MQTT] Connected");
    }

    @PreDestroy
    void disconnect() throws MqttException {
        if (mqttClient.isConnected()) {
            mqttClient.disconnect();
        }
        LOGGER.info("[MQTT] Disconnected");
    }

    @Override
    public void connectionLost(Throwable cause) {
        LOGGER.warn("[MQTT] Connection lost");
    }

    @Override
    public void messageArrived(String topic, MqttMessage message) {
        try {
            var command = objectMapper.readValue(message.getPayload(), ProcessFrontCommand.class);
            LOGGER.debug(format("[MQTT] topic: %s, payload: %s", topic, command.toString()));
            new Thread((() -> frontProcessor.process(command))).start();
        } catch (IOException e) {
            LOGGER.warn(e.getMessage());
        }
    }

    @Override
    public void deliveryComplete(IMqttDeliveryToken token) {
        System.out.println(token);
    }
}
