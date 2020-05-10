package com.frontwit.barcodeapp.administration.route.planning;

import com.frontwit.barcodeapp.administration.route.planning.dto.DeliveryInfoDto;
import com.frontwit.barcodeapp.administration.route.planning.dto.DeliveryOrderDto;
import com.frontwit.barcodeapp.administration.route.planning.dto.RouteInfoDto;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@ToString
class RouteDetails {

    private final List<Report> reports;
    @Getter
    private final String route;

    private RouteDetails(List<Report> reports, String route) {
        this.reports = reports;
        this.route = route;
    }

    List<Report> getReports() {
        return new ArrayList<>(reports);
    }

    static RouteDetails of(RouteInfoDto dto) {
        List<Report> reports = dto.getDeliveryInfos().stream().map(Report::of).collect(Collectors.toList());
        String route = dto.getRoute();
        return new RouteDetails(reports, route);
    }

    @ToString
    @AllArgsConstructor
    static class Report {
        @Getter
        private final String customer;
        private final List<Order> orders;

        @Getter
        private final SettlementType settlementType;


        String displayOrders() {
            return orders.stream()
                    .map(Order::displayOrder)
                    .reduce((order1, order2) -> order1 + "\n" + order2)
                    .orElse("");
        }

        BigDecimal getAmount() {
            return orders.stream()
                    .map(Order::getPrice)
                    .reduce(BigDecimal::add)
                    .orElse(BigDecimal.valueOf(0));
        }

        static Report of(DeliveryInfoDto dto) {
            String customer = dto.getCustomer();
            SettlementType paymentType = SettlementType.of(dto.getPaymentType());
            List<Order> orders = dto.getOrders().stream()
                    .filter(DeliveryOrderDto::isSelected)
                    .map(Order::of)
                    .collect(Collectors.toList());
            return new Report(customer, orders, paymentType);
        }

    }
}
