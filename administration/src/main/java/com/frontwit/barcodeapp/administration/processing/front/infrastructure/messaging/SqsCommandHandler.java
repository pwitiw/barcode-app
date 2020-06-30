package com.frontwit.barcodeapp.administration.processing.front.infrastructure.messaging;

import com.amazonaws.auth.DefaultAWSCredentialsProviderChain;
import com.amazonaws.services.sqs.AmazonSQS;
import com.amazonaws.services.sqs.AmazonSQSClientBuilder;
import com.amazonaws.services.sqs.model.DeleteMessageBatchRequestEntry;
import com.amazonaws.services.sqs.model.Message;
import com.amazonaws.services.sqs.model.ReceiveMessageRequest;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.frontwit.barcodeapp.administration.processing.front.application.FrontProcessor;
import com.frontwit.barcodeapp.administration.processing.front.application.dto.ProcessFrontCommand;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

import static com.amazonaws.regions.Regions.EU_CENTRAL_1;

@Service
public class SqsCommandHandler {
    private static final Logger LOGGER = LoggerFactory.getLogger(SqsCommandHandler.class);
    private static final int LONG_POLL_TIME = 20;
    private static final int MAX_MSGS = 10;

    private final String url;
    private final AmazonSQS sqs;
    private final FrontProcessor frontProcessor;
    private final ObjectMapper objectMapper;

    public SqsCommandHandler(@Value("${aws.sqs.url}") String url,
                             FrontProcessor frontProcessor,
                             ObjectMapper objectMapper) {
        this.url = url;
        this.frontProcessor = frontProcessor;
        this.objectMapper = objectMapper;
        sqs = AmazonSQSClientBuilder
                .standard()
                .withCredentials(new DefaultAWSCredentialsProviderChain())
                .withRegion(EU_CENTRAL_1)
                .build();
    }

    @Scheduled(cron = "0 * * * * MON-SAT")
    public void handleCommands() {
        List<DeleteMessageBatchRequestEntry> messagesForDeletion =
                sqs.receiveMessage(aRequest())
                        .getMessages().stream()
                        .peek(this::processMessage)
                        .map(this::toDeleteMessageBatchRequestEntry)
                        .collect(Collectors.toList());
        if (!messagesForDeletion.isEmpty()) {
            sqs.deleteMessageBatch(url, messagesForDeletion);
        }
        LOGGER.info("Cron processing, processed commands: {}", messagesForDeletion.size());
    }

    private ReceiveMessageRequest aRequest() {
        return new ReceiveMessageRequest()
                .withQueueUrl(url)
                .withWaitTimeSeconds(LONG_POLL_TIME)
                .withMaxNumberOfMessages(MAX_MSGS);
    }

    private void processMessage(Message message) {
        try {
            var command = objectMapper.readValue(message.getBody(), ProcessFrontCommand.class);
            frontProcessor.process(command);
        } catch (JsonProcessingException e) {
            LOGGER.error("Mapping failed, body={}", message.getBody(), e);
        } catch (Exception ex) {
            LOGGER.warn(ex.getMessage(), ex);
        }
    }

    private DeleteMessageBatchRequestEntry toDeleteMessageBatchRequestEntry(Message msg) {
        return new DeleteMessageBatchRequestEntry(msg.getMessageId(), msg.getReceiptHandle());
    }
}
