package com.frontwit.barcodeapp.administration.catalogue.routes.summary;

import com.frontwit.barcodeapp.administration.catalogue.routes.dto.DeliveryInfoDto;
import com.frontwit.barcodeapp.administration.catalogue.routes.dto.RouteDetailsDto;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.ToString;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.List;
import java.util.stream.Collectors;

@ToString
@Getter
@RequiredArgsConstructor
public final class RouteDetails {

    private final List<Report> reports;
    private final String route;
    private final Instant date;

    public static RouteDetails of(RouteDetailsDto dto) {
        List<Report> reports = dto.getDeliveryInfos().stream()
                .map(Report::of)
                .collect(Collectors.toList());
        return new RouteDetails(reports, dto.getName(), dto.getDate());
    }

    @ToString
    @AllArgsConstructor
    static class Report {
        private static final String NEW_LINE = "\n";
        @Getter
        private final String customer;
        @Getter
        private final String address;
        @Getter
        private final String phoneNumber;
        private final List<Order> orders;
        @Getter
        private final SettlementType settlementType;

        String displayOrders() {
            return orders.stream()
                    .map(Order::displayOrder)
                    .reduce((order1, order2) -> order1 + NEW_LINE + order2)
                    .orElse("");
        }

        BigDecimal getAmount() {
            return orders.stream()
                    .map(Order::getValuation)
                    .reduce(BigDecimal::add)
                    .orElse(BigDecimal.valueOf(0));
        }

        static Report of(DeliveryInfoDto dto) {
            String customer = dto.getCustomer();
            String address = dto.getAddress();
            String phoneNumber = dto.getPhoneNumber();
            SettlementType paymentType = SettlementType.of(dto.getPaymentType());
            List<Order> orders = dto.getOrders().stream()
                    .map(Order::of)
                    .collect(Collectors.toList());
            return new Report(customer, address, phoneNumber, orders, paymentType);
        }

        String getCustomerInfo() {
            return customer + displayAddress() + displayContactInfo();
        }

        private Object displayContactInfo() {
            return phoneNumber == null ? "" : NEW_LINE + "tel. " + phoneNumber;
        }

        private String displayAddress() {
            return address == null ? "" : NEW_LINE + address;
        }
    }
}
