package com.frontwit.barcodeapp.administration.catalogue;

import com.amazonaws.util.IOUtils;
import com.frontwit.barcodeapp.administration.catalogue.barcodes.BarcodePdf;
import com.frontwit.barcodeapp.administration.catalogue.barcodes.BarcodePdfGenerator;
import com.frontwit.barcodeapp.administration.catalogue.dto.*;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.io.BufferedOutputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.List;


@RestController
@RequestMapping("/api")
@AllArgsConstructor
public class OrderResource {

    private final OrderQuery orderQuery;
    private final OrderCommand orderCommand;
    private final BarcodePdfGenerator barcodeFacade;

    @GetMapping("/orders/{id}")
    OrderDetailDto getOrder(@PathVariable Long id) {
        return orderQuery.getOrderDetails(id);
    }

    @PostMapping("/orders")
    Page<OrderDto> getOrdersForSearchCriteria(Pageable pageable, @RequestBody(required = false) OrderSearchCriteria searchCriteria) {
        return orderQuery.getOrders(pageable, searchCriteria);
    }

    @PutMapping("/orders/status")
    void changeStatus(@RequestBody OrderStatusesDto dto) {
        orderCommand.updateStatuses(dto.getIds(), dto.isCompleted());
    }

    @PutMapping("/orders/{orderId}/deadline")
    void updateOrder(@PathVariable Long orderId, @RequestBody UpdateOrderDto dto) {
        orderCommand.updateOrder(UpdateOrder.of(orderId, dto.getDeadline(), dto.getValuation()));
    }

    @GetMapping("/orders/reminders")
    public Page<ReminderDto> getReminders(@RequestParam Integer page, @RequestParam Integer size) {
        return orderQuery.findDeadlines(PageRequest.of(page, size));
    }

    @GetMapping("/routes")
    List<CustomerOrdersDto> getOrdersForRoute(@RequestParam String routes) {
        return orderQuery.findCustomersWithOrdersForRoute(routes);
    }

    @GetMapping(value = "/orders/{orderId}/barcodes")
    public void getBarcodesForOrder(@PathVariable Long orderId, HttpServletResponse response) throws IOException {
        var orderDetails = orderQuery.getOrderDetails(orderId);
        BarcodePdf pdf = barcodeFacade. createBarcodesFor(orderDetails);
        ByteArrayOutputStream bytes = pdf.asStream();
        response.setContentLength(bytes.size());
        response.setContentType("application/pdf");
        BufferedOutputStream bos = new BufferedOutputStream(response.getOutputStream());
        bytes.writeTo(bos);
        bos.flush();
        bos.close();
    }
}
