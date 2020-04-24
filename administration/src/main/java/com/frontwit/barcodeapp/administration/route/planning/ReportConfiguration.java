package com.frontwit.barcodeapp.administration.route.planning;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ReportConfiguration {

    @Bean
    public RouteReportGenerator routeReportGenerator() {
        return new RouteReportGenerator();
    }

}

