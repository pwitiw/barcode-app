package com.frontwit.barcodeapp.administration.route.planning;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
class RouteConfiguration {

    @Bean
    public RouteGenerator routeReportGenerator() {
        return new RouteGenerator(new RouteTable());
    }

}

