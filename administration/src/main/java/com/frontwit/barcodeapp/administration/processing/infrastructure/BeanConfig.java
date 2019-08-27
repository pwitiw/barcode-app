package com.frontwit.barcodeapp.administration.processing.infrastructure;

import com.frontwit.barcodeapp.administration.processing.application.FrontProcessingService;
import com.frontwit.barcodeapp.administration.processing.application.ProcessingFacade;
import com.frontwit.barcodeapp.administration.processing.application.readmodel.ProcessingQueryFacade;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class BeanConfig {

    @Bean
    FrontProcessingService frontProcessingService() {
        return new FrontProcessingService();
    }

    @Bean
    ProcessingQueryFacade processingQueryFacade() {
        return new ProcessingQueryFacade();
    }

    @Bean
    ProcessingFacade processingFacade(FrontProcessingService processingService,
                                      ProcessingQueryFacade processingQueryFacade) {
        return new ProcessingFacade(processingService, processingQueryFacade);
    }
}
