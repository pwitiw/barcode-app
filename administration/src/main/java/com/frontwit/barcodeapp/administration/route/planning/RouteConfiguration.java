package com.frontwit.barcodeapp.administration.route.planning;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
class RouteConfiguration {

    @Bean
    public RouteGenerator routeReportGenerator() {
        RoutePdfParts pdfParts = new RoutePdfParts();
        return new RouteGenerator(new RouteTable(pdfParts), pdfParts);
    }
}

