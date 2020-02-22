package com.frontwit.barcode.reader.messaging;

import com.amazonaws.auth.DefaultAWSCredentialsProviderChain;
import com.amazonaws.services.sqs.AmazonSQS;
import com.amazonaws.services.sqs.AmazonSQSClientBuilder;
import com.amazonaws.services.sqs.model.SendMessageRequest;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.frontwit.barcode.reader.application.ProcessFrontCommand;
import com.frontwit.barcode.reader.application.PublishBarcode;
import com.frontwit.barcode.reader.application.PublishingException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.time.LocalDateTime;

import static com.amazonaws.regions.Regions.EU_CENTRAL_1;

@Service
public class SqsCommandPublisher implements PublishBarcode {
    private static final Logger LOGGER = LoggerFactory.getLogger(SqsCommandPublisher.class);

    private final String url;
    private final ObjectMapper objectMapper;
    private final AmazonSQS sqs;

    SqsCommandPublisher(@Value("${aws.sqs.url}") String url,
                        ObjectMapper objectMapper) {
        this.url = url;
        this.objectMapper = objectMapper;
        sqs = AmazonSQSClientBuilder.standard()
                .withCredentials(new DefaultAWSCredentialsProviderChain())
                .withRegion(EU_CENTRAL_1)
                .build();
    }

    @Override
    public void publish(ProcessFrontCommand command) throws PublishingException {
        try {
            SendMessageRequest request = new SendMessageRequest()
                    .withQueueUrl(url)
                    .withMessageBody(objectMapper.writeValueAsString(command));
            sqs.sendMessage(request);
            LOGGER.info("SQS Published " + command.toString());
        } catch (JsonProcessingException e) {
            LOGGER.error("Error while JSON processing {}", command);
        }
    }
}
