package com.frontwit.barcode.reader.infrastructure.http;

import com.frontwit.barcode.reader.application.CommandPublisher;
import com.frontwit.barcode.reader.application.ProcessBarcodeCommand;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;

import javax.annotation.PostConstruct;
import javax.ws.rs.ProcessingException;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.logging.Logger;

import static java.lang.String.format;
import static javax.ws.rs.client.Entity.entity;

public class HttpEventPublisher implements CommandPublisher {

    private static final Logger LOGGER = Logger.getLogger(HttpEventPublisher.class.getName());

    private WebTarget webTarget;

    @Value("${administration.url}")
    private String barCodeAppUrl;

    @Value("${administration.token}")
    private String token;

    @PostConstruct
    private void initialize() {
        webTarget = ClientBuilder
                .newClient()
                .target(barCodeAppUrl);
    }

    public void publish(ProcessBarcodeCommand processBarcodeCommand) {
        LOGGER.info(format("Publishing event: %s", processBarcodeCommand));
        try {
            WebTarget barcodeWebTarget = webTarget.path("barcode");
            Response response = barcodeWebTarget
                    .request()
                    .header(HttpHeaders.AUTHORIZATION, "Bearer " + token)
                    .post(entity(processBarcodeCommand, MediaType.APPLICATION_JSON));

//            return response.getStatus() == HttpStatus.OK.value();
        } catch (
                ProcessingException ex) {
            LOGGER.warning(ex.getMessage());
            LOGGER.warning(format("Publishing failed: %s", processBarcodeCommand));
        }
    }
}