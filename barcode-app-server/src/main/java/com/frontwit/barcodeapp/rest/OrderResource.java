package com.frontwit.barcodeapp.rest;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@CrossOrigin
@RestController
@RequestMapping("/api/orders")
public class OrderResource {

//    @Autowired
//    OrderService orderService;


//    @GetMapping
//    Page<OrderDto> getOrders(Pageable pageable) {
//        return orderService.getOrders(pageable);
//    }

    @PostMapping("/synchronize")
    public ResponseEntity synchronize() {
//        List<OrderDetailDto> ordersToSave = synchronizationService.getOrders();
//        orderService.save(ordersToSave);
        return ResponseEntity.ok().build();
    }

//    @GetMapping("/{barcode}")
//    OrderDetailDto getOrder(@PathVariable Long barcode) {
//        return orderService.getOrder(barcode);
//    }

//    @PostMapping("/search")
//    Page<OrderDto> getOrdersForSearchCriteria(Pageable pageable, @RequestBody OrderSearchCriteria searchCriteria) {
//        return orderService.getOrders(pageable, searchCriteria);
//    }

}
