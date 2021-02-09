package com.frontwit.barcodeapp.administration.catalogue.routes;

import com.frontwit.barcodeapp.administration.catalogue.orders.dto.CustomerOrdersDto;
import com.frontwit.barcodeapp.administration.catalogue.routes.dto.RouteDetailsDto;
import com.frontwit.barcodeapp.administration.catalogue.routes.summary.RouteDetails;
import com.frontwit.barcodeapp.administration.catalogue.routes.summary.RouteSummary;
import lombok.AllArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import java.util.*;
import java.util.stream.Collectors;

import static java.util.Collections.emptyList;
import static org.springframework.http.ResponseEntity.ok;

@RestController
@RequestMapping("/api/routes")
@AllArgsConstructor
public class RouteResource {
    private static final Map<String, RouteDetailsDto> ROUTES = new HashMap<>();

    private final RouteQuery routeQuery;
    private final RouteSummary routeSummary;

    @PostMapping(value = "/summary", produces = MediaType.APPLICATION_PDF_VALUE)
    public byte[] generateRouteSummary(@RequestBody RouteDetailsDto dto) {
        Objects.requireNonNull(dto);
        var routeReportDetails = RouteDetails.of(dto);
        return routeSummary.create(routeReportDetails);
    }

    @GetMapping("/deliveryinfo")
    List<CustomerOrdersDto> getOrdersForRoute(@RequestParam String route) {
        if (StringUtils.isEmpty(route)) {
            return emptyList();
        }
        return routeQuery.findCustomersWithOrdersForRoute(route);
    }

    @GetMapping
    List<RouteDetailsDto> getRoutes() {
        return ROUTES.values().stream()
                .filter(dto -> !dto.isFulfilled())
                .collect(Collectors.toList());
    }

    @PostMapping
    void saveRoute(@RequestBody RouteDetailsDto routeDetails) {
        String id = routeDetails.getId() != null ? routeDetails.getId() : UUID.randomUUID().toString();
        routeDetails.setId(id);
        ROUTES.put(id, routeDetails);
    }

    @DeleteMapping("/{id}")
    ResponseEntity<Boolean> deleteRoute(@PathVariable("id") String id) {
        RouteDetailsDto result = ROUTES.remove(id);
        return ok().body(result != null);
    }

    @PutMapping("/{id}/fulfill")
    ResponseEntity<Boolean> fulfillRoute(@PathVariable("id") String id) {
        var dto = ROUTES.get(id);
        if (dto != null) {
            dto.setFulfilled(true);
        }
        return ok().body(dto != null);
    }
}
