package com.frontwit.barcode.reader.messaging;

import com.amazonaws.auth.DefaultAWSCredentialsProviderChain;
import com.amazonaws.services.sqs.AmazonSQS;
import com.amazonaws.services.sqs.AmazonSQSClientBuilder;
import com.amazonaws.services.sqs.model.SendMessageRequest;
import com.amazonaws.services.sqs.model.SendMessageResult;
import com.frontwit.barcode.reader.application.ProcessBarcodeCommand;
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

    @Value("${aws.sqs.url}")
    private String url;

    private final AmazonSQS sqs;

    SqsCommandPublisher() {
        sqs = AmazonSQSClientBuilder.standard()
                .withCredentials(new DefaultAWSCredentialsProviderChain())
                .withRegion(EU_CENTRAL_1)
                .build();
    }

    @PostConstruct
    void run() {
        publish(new ProcessBarcodeCommand(2, 1L, LocalDateTime.now()));
    }

    @Override
    public void publish(ProcessBarcodeCommand command) throws PublishingException {
        SendMessageRequest request = new SendMessageRequest()
                .withQueueUrl(url)
                .withMessageBody(command.toString());
        sqs.sendMessage(request);
        LOGGER.info("SQS Published " + command.toString());
    }
}
