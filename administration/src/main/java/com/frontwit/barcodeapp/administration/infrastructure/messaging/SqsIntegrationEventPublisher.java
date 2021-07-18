package com.frontwit.barcodeapp.administration.infrastructure.messaging;

import com.amazonaws.auth.profile.ProfileCredentialsProvider;
import com.amazonaws.services.sqs.AmazonSQS;
import com.amazonaws.services.sqs.AmazonSQSClientBuilder;
import com.amazonaws.services.sqs.model.SendMessageRequest;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.frontwit.barcodeapp.api.integration.PrintableLabelEvent;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import com.frontwit.barcodeapp.administration.infrastructure.integration.IntegrationEventPublisher;


import static com.amazonaws.regions.Regions.EU_CENTRAL_1;

@Service
public class SqsIntegrationEventPublisher implements IntegrationEventPublisher{
    private static final Logger LOGGER = LoggerFactory.getLogger(SqsIntegrationEventPublisher.class);

    private final String labelsQueueUrl;
    private final ObjectMapper objectMapper;
    private final AmazonSQS sqs;

    SqsIntegrationEventPublisher(@Value("${barcode-app.labels.front-packed-queue-url}") String labelsQueueUrl,
                                 @Value("${aws.sqs.profile:default}") String profile,
                                 ObjectMapper objectMapper) {
        this.labelsQueueUrl = labelsQueueUrl;
        this.objectMapper = objectMapper;
        this.sqs = AmazonSQSClientBuilder.standard()
                .withCredentials(new ProfileCredentialsProvider(profile))
                .withRegion(EU_CENTRAL_1)
                .build();
    }

    @Override
    public void publish(PrintableLabelEvent event) {
        try {
            SendMessageRequest request = new SendMessageRequest()
                    .withQueueUrl(labelsQueueUrl)
                    .withMessageBody(objectMapper.writeValueAsString(event));
            sqs.sendMessage(request);
            LOGGER.debug("Published event to labels queue, event={}", event.toString());
        } catch (JsonProcessingException e) {
            LOGGER.error("Error while JSON processing {}", event, e);
        }
    }
}