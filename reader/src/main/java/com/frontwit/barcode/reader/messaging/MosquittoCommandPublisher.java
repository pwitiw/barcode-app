package com.frontwit.barcode.reader.messaging;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.frontwit.barcode.reader.application.ConnectedToMqtt;
import com.frontwit.barcode.reader.application.ProcessBarcodeCommand;
import com.frontwit.barcode.reader.application.PublishBarcode;
import com.frontwit.barcode.reader.application.PublishingException;
import org.eclipse.paho.client.mqttv3.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationEventPublisher;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;

import static java.lang.String.format;

public class MosquittoCommandPublisher implements PublishBarcode {
    private static final Logger LOG = LoggerFactory.getLogger(MosquittoCommandPublisher.class);


    @Value("${mqtt.uri}")
    private String uri;
    @Value("${mqtt.topic}")
    private String topic;
    @Value("${mqtt.reconnect.interval:5000}")
    private Long interval;
    @Value("${mqtt.client.id:reader}")
    private String username;

    private IMqttClient mqttClient;
    private MqttConnectOptions options;
    private ObjectMapper objectMapper;
    private ApplicationEventPublisher eventPublisher;

    public MosquittoCommandPublisher(ObjectMapper objectMapper, ApplicationEventPublisher eventPublisher) {
        this.objectMapper = objectMapper;
        this.eventPublisher = eventPublisher;
    }

    @PostConstruct
    void init() throws MqttException {
        mqttClient = new MqttClient(uri, "reader");
        options = new MqttConnectOptions();
        options.setAutomaticReconnect(true);
        options.setCleanSession(false);
        options.setConnectionTimeout(10);

        LOG.info("[MQTT] Connecting ...");
        connect();
    }

    @PreDestroy
    void disconnect() {
        if (mqttClient.isConnected()) {
            try {
                mqttClient.close();
            } catch (MqttException ignored) {
            }
        }
    }

    @Override
    public void publish(ProcessBarcodeCommand command) throws PublishingException {
        if (!mqttClient.isConnected() && !connect()) {
            throw new PublishingException("Error while publishing.");
        }
        new Thread(() -> publish(topic, createMessage(command))).start();
    }

    private boolean connect() {
        try {
            mqttClient.connect(options);
            LOG.info("[MQTT] Connection succeed");
            eventPublisher.publishEvent(new ConnectedToMqtt());
            return true;
        } catch (MqttException e) {
            LOG.warn(e.getMessage());
        }

        return false;
    }

    private synchronized void publish(String topic, MqttMessage mqttMessage) throws PublishingException {
        try {
            mqttClient.publish(topic, mqttMessage);
        } catch (MqttException e) {
            LOG.warn(format("[MQTT] Error while publishing. Reason: %s", e.getMessage()));
        }
    }

    private MqttMessage createMessage(ProcessBarcodeCommand command) {
        String json = "";
        try {
            json = objectMapper.writeValueAsString(command);
        } catch (JsonProcessingException e) {
            LOG.warn(e.getMessage());
        }
        MqttMessage msg = new MqttMessage();
        msg.setQos(1);
        msg.setPayload(json.getBytes());
        return msg;
    }

}
