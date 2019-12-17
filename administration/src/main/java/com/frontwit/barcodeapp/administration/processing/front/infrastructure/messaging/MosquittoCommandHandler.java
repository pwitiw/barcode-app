package com.frontwit.barcodeapp.administration.processing.front.infrastructure.messaging;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.frontwit.barcodeapp.administration.processing.front.application.ProcessingFront;
import com.frontwit.barcodeapp.administration.processing.front.application.dto.ProcessFrontCommand;
import com.frontwit.barcodeapp.administration.processing.shared.ProcessingException;
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

    private ProcessingFront processingFront;
    private ObjectMapper objectMapper;

    private IMqttClient mqttClient;

    @Value("${mqtt.uri}")
    private String uri;

    @Value("${mqtt.topic}")
    private String topic;

    public MosquittoCommandHandler(ProcessingFront processingFront, ObjectMapper objectMapper) {
        this.processingFront = processingFront;
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
    }

    @PreDestroy
    void disconnect() throws MqttException {
        if (mqttClient.isConnected()) {
            mqttClient.disconnect();
        }
    }

    @Override
    public void connectionLost(Throwable cause) {

    }

    @Override
    public void messageArrived(String topic, MqttMessage message) {
        try {
            var command = objectMapper.readValue(message.getPayload(), ProcessFrontCommand.class);
            LOGGER.info(format("Front (barcode: %d) is being processed.", command.getBarcode()));
            new Thread((() -> process(command))).start();
        } catch (IOException e) {
            LOGGER.error(e.getMessage());
        }
    }

    private void process(ProcessFrontCommand command) {
        try {
            processingFront.process(command);
        } catch (ProcessingException ex) {
            LOGGER.warn(ex.getMessage());
        }
    }

    @Override
    public void deliveryComplete(IMqttDeliveryToken token) {
        System.out.println(token);
    }
}
