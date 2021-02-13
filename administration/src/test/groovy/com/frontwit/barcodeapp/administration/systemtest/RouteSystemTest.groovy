package com.frontwit.barcodeapp.administration.systemtest

import com.frontwit.barcodeapp.administration.IntegrationSpec
import com.frontwit.barcodeapp.administration.catalogue.routes.RouteCommand
import com.frontwit.barcodeapp.administration.catalogue.routes.RouteQuery
import com.frontwit.barcodeapp.administration.catalogue.routes.dto.DeliveryInfoDto
import com.frontwit.barcodeapp.administration.catalogue.routes.dto.DeliveryOrderDto
import com.frontwit.barcodeapp.administration.catalogue.routes.dto.RouteDetailsDto
import org.springframework.beans.factory.annotation.Autowired

class RouteSystemTest extends IntegrationSpec {

    @Autowired
    private RouteCommand routeCommand
    @Autowired
    private RouteQuery routeQuery

    def "should successfully fulfill route"() {
        given:
        def dto = aRouteDetailsDto()

        when:
        def routeId = routeCommand.save(dto)

        then: "route is successfully saved"
        routeQuery.getOpenedRoutes().size() == 1

        when:
        routeCommand.fulfill(routeId)

        then: "route is completed"
        routeQuery.getOpenedRoutes().size() == 0

        when:
        routeCommand.delete(routeId)

        then: "there are no routes"
        routeQuery.getAllRoutes().size() == 0

    }

    private static RouteDetailsDto aRouteDetailsDto() {
        RouteDetailsDto.builder()
                .name('test')
                .deliveryInfos([
                        DeliveryInfoDto.builder()
                                .orders([
                                        DeliveryOrderDto.builder()
                                                .id(123)
                                                .build()
                                ])
                                .build()
                ])
                .build()
    }
}
