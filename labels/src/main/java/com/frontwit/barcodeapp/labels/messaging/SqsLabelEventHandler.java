package com.frontwit.barcodeapp.labels.messaging;

import com.amazonaws.auth.DefaultAWSCredentialsProviderChain;
import com.amazonaws.services.sqs.AmazonSQS;
import com.amazonaws.services.sqs.AmazonSQSClientBuilder;
import com.amazonaws.services.sqs.model.DeleteMessageBatchRequestEntry;
import com.amazonaws.services.sqs.model.ReceiveMessageRequest;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.frontwit.barcodeapp.api.integration.PrintableLabelEvent;
import com.frontwit.barcodeapp.api.shared.Stage;
import com.frontwit.barcodeapp.labels.application.LabelProcessor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

import static com.amazonaws.regions.Regions.EU_CENTRAL_1;

@Service
public class SqsLabelEventHandler {
    private static final Logger LOGGER = LoggerFactory.getLogger(SqsLabelEventHandler.class);
    private static final int LONG_POLL_TIME = 20;
    private static final int MAX_MSGS = 10;

    private final AmazonSQS sqs;
    private final String url;
    private final Stage processableStage;
    private final ObjectMapper objectMapper;
    private final LabelProcessor labelProcessor;

    SqsLabelEventHandler(@Value("${barcode-app.labels.front-packed-queue-url}") String url,
                         @Value("${barcode-app.labels.stage}") Stage processableStage,
                         ObjectMapper objectMapper,
                         LabelProcessor labelProcessor
    ) {
        this.sqs = AmazonSQSClientBuilder
                .standard()
                .withCredentials(new DefaultAWSCredentialsProviderChain())
                .withRegion(EU_CENTRAL_1)
                .build();
        this.labelProcessor = labelProcessor;
        this.objectMapper = objectMapper;
        this.url = url;
        this.processableStage = processableStage;
    }

    @Scheduled(cron = "${barcode-app.labels.polling.frequency}")
    public void handleCommands() {
       LOGGER.info("BUM");
        List<DeleteMessageBatchRequestEntry> processedMessages = new ArrayList<>();
        sqs.receiveMessage(aRequest())
                .getMessages()
                .forEach(msg -> {
                    try {
                        var event = objectMapper.readValue(msg.getBody(), PrintableLabelEvent.class);
                        if (event.getStage() == this.processableStage) {
                            labelProcessor.process(LabelInfoMapper.map(event));
                            processedMessages.add(new DeleteMessageBatchRequestEntry(msg.getMessageId(), msg.getReceiptHandle()));
                        }
                    } catch (JsonProcessingException e) {
                        LOGGER.error("Mapping failed, body={}", msg.getBody(), e);
                    } catch (Exception ex) {
                        LOGGER.warn(ex.getMessage(), ex);
                    }
                });
        if (!processedMessages.isEmpty()) {
            sqs.deleteMessageBatch(url, processedMessages);
        }
        LOGGER.debug("Cron processing, processed commands: {}", processedMessages.size());
    }

    private ReceiveMessageRequest aRequest() {
        return new ReceiveMessageRequest()
                .withQueueUrl(url)
                .withWaitTimeSeconds(LONG_POLL_TIME)
                .withMaxNumberOfMessages(MAX_MSGS);
    }

}
