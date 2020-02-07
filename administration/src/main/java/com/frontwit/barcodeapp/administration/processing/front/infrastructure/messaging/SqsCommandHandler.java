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
import java.util.Objects;
import java.util.stream.Collectors;

import static com.amazonaws.regions.Regions.EU_CENTRAL_1;
import static java.lang.String.format;

@Service
public class SqsCommandHandler {
    private static final Logger LOGGER = LoggerFactory.getLogger(SqsCommandHandler.class);
    private static final int LONG_POLL_TIME = 20;
    private static final int MAX_MSGS = 10;

    @Value("${aws.sqs.url}")
    private String url;

    private final AmazonSQS sqs;
    private final FrontProcessor frontProcessor;
    private final ObjectMapper objectMapper;

    public SqsCommandHandler(FrontProcessor frontProcessor, ObjectMapper objectMapper) {
        this.frontProcessor = frontProcessor;
        this.objectMapper = objectMapper;
        sqs = AmazonSQSClientBuilder
                .standard()
                .withCredentials(new DefaultAWSCredentialsProviderChain())
                .withRegion(EU_CENTRAL_1)
                .build();
    }

    @Scheduled(fixedRate = 60000)
    void handleCommands() {
        List<Message> messages = getMessages();
        if (!messages.isEmpty()) {
            processMessages(messages);
            deleteMessages(messages);
        }
    }

    private List<Message> getMessages() {
        ReceiveMessageRequest request =
                new ReceiveMessageRequest()
                        .withQueueUrl(url)
                        .withWaitTimeSeconds(LONG_POLL_TIME)
                        .withMaxNumberOfMessages(MAX_MSGS);
        return sqs.receiveMessage(request).getMessages();
    }

    private void processMessages(List<Message> messages) {
        messages.stream()
                .map(m -> {
                    try {
                        return objectMapper.readValue(m.getBody(), ProcessFrontCommand.class);
                    } catch (JsonProcessingException e) {
                        LOGGER.error(format("Mapping failed %s", m.getBody()), e);
                    }
                    return null;
                })
                .filter(Objects::nonNull)
                .forEach(frontProcessor::process);
    }

    private void deleteMessages(List<Message> messages) {
        List<DeleteMessageBatchRequestEntry> deleteMessageEntries = messages.stream()
                .map(msg -> new DeleteMessageBatchRequestEntry(msg.getMessageId(), msg.getReceiptHandle()))
                .collect(Collectors.toList());
        sqs.deleteMessageBatch(url, deleteMessageEntries);
    }
}
