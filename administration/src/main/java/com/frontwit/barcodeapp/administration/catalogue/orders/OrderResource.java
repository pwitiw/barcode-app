package com.frontwit.barcodeapp.administration.catalogue.orders;

import com.frontwit.barcodeapp.administration.catalogue.orders.barcodes.BarcodePdf;
import com.frontwit.barcodeapp.administration.catalogue.orders.barcodes.BarcodesX21PdfGenerator;
import com.frontwit.barcodeapp.administration.catalogue.orders.dto.*;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.io.BufferedOutputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;


@RestController
@RequestMapping("/api")
@AllArgsConstructor
@SuppressWarnings("ClassFanOutComplexity")
public class OrderResource {

    private final OrderQuery orderQuery;
    private final OrderCommand orderCommand;
    private final BarcodesX21PdfGenerator barcodeFacade;

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
    Page<ReminderDto> getReminders(@RequestParam Integer page, @RequestParam Integer size) {
        return orderQuery.findDeadlines(PageRequest.of(page, size));
    }

    @PostMapping("/orders/barcodes")
    void barcodesForOrder(@RequestBody BarcodesDto dto, HttpServletResponse response) throws IOException {
        var orders = orderQuery.getOrderDetails(dto.getIds());
        BarcodePdf pdf = barcodeFacade.createBarcodesFor(orders);
        ByteArrayOutputStream bytes = pdf.asStream();
        response.setContentLength(bytes.size());
        response.setContentType("application/pdf");
        BufferedOutputStream bos = new BufferedOutputStream(response.getOutputStream());
        bytes.writeTo(bos);
        bos.flush();
        bos.close();
    }
}
