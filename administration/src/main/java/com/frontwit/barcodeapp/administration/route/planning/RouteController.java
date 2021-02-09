package com.frontwit.barcodeapp.administration.route.planning;

import com.frontwit.barcodeapp.administration.catalogue.RouteDetailsDto;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Objects;

@RestController
@RequestMapping("/api")
public class RouteController {

    private final RouteGenerator routeGenerator;

    public RouteController(RouteGenerator routeGenerator) {
        this.routeGenerator = routeGenerator;
    }

    @PostMapping(value = "/route/summary", produces = MediaType.APPLICATION_PDF_VALUE)
    public byte[] generateRouteSummary(@RequestBody RouteDetailsDto dto) {
        Objects.requireNonNull(dto);
        var routeReportDetails = RouteDetails.of(dto);
        return routeGenerator.generateRouteSummary(routeReportDetails);
    }
}
