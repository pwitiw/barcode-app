package com.frontwit.barcode.reader.barcode;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.ws.rs.ProcessingException;
import javax.ws.rs.client.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.Collection;
import java.util.logging.Logger;

import static com.frontwit.barcode.reader.common.Messages.SERVER_RESPONSE;
import static java.lang.String.format;

@Component
public class BarcodeRestClient {

    private static final Logger LOGGER = Logger.getLogger(BarcodeRestClient.class.getName());

    private WebTarget webTarget;

    private static final String TOKEN = "Bearer " +
            "eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJhZG1pbiIsInJvbGUiOlsiQURNSU4iXSwiZXhwIjoyNDk0OTcyMTUwfQ.0ciUsuvRoW_MsNV9t8p-WltjNGD6r6LQQJu0iywF8MsxOEYcBPs1YGPdN5-Qqz2pdDKz1M3Cc0LslvGeXP8rUg";

    @Value("${barcode-app.url}")
    private String barCodeAppUrl;

    @PostConstruct
    private void initialize() {
        Client client = ClientBuilder.newClient();
        webTarget = client.target(barCodeAppUrl);
    }

    public boolean sendBarCode(Collection<BarcodeCommand> barcodeCommands) {
        WebTarget barcodeWebTarget = webTarget.path("barcode");
        Invocation.Builder invocationBuilder = barcodeWebTarget.request();
        invocationBuilder.header(HttpHeaders.AUTHORIZATION, TOKEN);
        try {

            Response response = invocationBuilder.post(Entity.entity(barcodeCommands, MediaType.APPLICATION_JSON));
            LOGGER.info(format(SERVER_RESPONSE, response.getStatus()));
            return response.getStatus() == HttpStatus.OK.value();
        } catch (ProcessingException ex) {
            LOGGER.warning(ex.getMessage());
        }
        return false;
    }
}

