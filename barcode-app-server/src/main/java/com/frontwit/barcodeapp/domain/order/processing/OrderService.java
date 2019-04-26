//package com.frontwit.barcodeapp.domain.orderprocessing;
//
//import com.frontwit.barcodeapp.domain.orderprocessing.processing.ports.OrderRepository;
//import com.frontwit.barcodeapp.domain.order.dto.OrderDetailDto;
//import com.frontwit.barcodeapp.domain.order.dto.OrderDto;
//import com.frontwit.barcodeapp.domain.order.dto.OrderSearchCriteria;
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//import org.springframework.data.domain.Page;
//import org.springframework.data.domain.Pageable;
//
//import java.util.*;
//import java.util.stream.Collectors;
//
//import static com.frontwit.barcodeapp.zzzdeprecated.BarcodeGeneratorService.MAX_ORDER_AMOUNT;
//import static java.lang.String.format;
//import static org.springframework.util.Assert.notNull;
//
//public class OrderService {
//    private static final Logger LOG = LoggerFactory.getLogger(OrderService.class);
//
//    private OrderRepository orderDao;
//
//    public OrderService(OrderRepository orderDao) {
//        this.orderDao = orderDao;
//    }
//
//    public Page<OrderDto> getOrders(Pageable pageable) {
//        Page<Order> orderPage = orderDao.findAll(pageable);
//        LOG.debug("Orders have been collected.");
//        return orderPage.map(OrderDto::valueOf);
//    }
//
//    public Page<OrderDto> getOrders(Pageable pageable, OrderSearchCriteria searchCriteria) {
//        Page<Order> orderPage = orderDao.findForCriteria(pageable, searchCriteria);
//        LOG.debug(format("Orders collected for criteria: %s", searchCriteria));
//        return orderPage.map(OrderDto::valueOf);
//    }
//
//    public OrderDetailDto getOrder(Long barcode) {
//        Order order = orderDao.findOne(barcode);
//        LOG.debug(format("Order collected for barcode %s.", barcode));
//        return OrderDetailDto.valueOf(order);
//    }
//
//    public void save(List<OrderDetailDto> dtos) {
//        List<Order> orders = dtos.stream()
//                .map(dto -> dto.toEntity(routeDao))
//                .peek(this::setBarcodes)
//                .collect(Collectors.toList());
//        orderDao.save(orders);
//        LOG.debug(format("%d orders saved successfully.", orders.size()));
//    }
//
//    public void updateOrdersForProcesses(final List<ProcessCommand> dtos) {
//        notNull(dtos, "dtos can not be null");
//        Map<Long, Set<ProcessCommand>> processes = organizeProcesses(dtos);
//        Collection<Order> orders = orderDao.findByBarcodes(processes.keySet());
//        orders.forEach(order -> processOrder(order, processes));
//        orderDao.save(orders);
//        LOG.debug(format("%d processes applied ", dtos.size()));
//    }
//
//    private Map<Long, Set<ProcessCommand>> organizeProcesses(List<ProcessCommand> dtos) {
//        Map<Long, Set<ProcessCommand>> processes = new HashMap<>();
//        dtos.forEach(dto -> {
//            Long barcode = dto.getBarcode() / MAX_ORDER_AMOUNT * MAX_ORDER_AMOUNT;
//            if (!processes.containsKey(barcode)) {
//                processes.put(barcode, new HashSet<>());
//            }
//            processes.create(barcode).add(dto);
//        });
//        return processes;
//    }
//
//    private void processOrder(Order order, Map<Long, Set<ProcessCommand>> processes) {
//        processes.create(order.getBarcode())
//                .forEach(process -> applyProcess(order, process));
//    }
//
//    private void applyProcess(Order order, ProcessCommand dto) {
//        order.getComponents().stream()
//                .filter(component -> Objects.equals(dto.getBarcode(), component.getBarcode()))
//                .findFirst()
//                .ifPresent(component -> {
//                    try {
//                        component.applyProcess(Stage.valueOf(dto.getReaderId()), dto.getDate());
//                    } catch (IllegalProcessingOrderException ex) {
//                        LOG.warn(ex.getMessage());
//                    }
//                });
//    }
//
//    private void setBarcodes(Order order) {
//        Long barcode = barcodeGeneratorService.generate();
//        order.setBarcode(barcode);
//        for (Component component : order.getComponents()) {
//            component.setBarcode(++barcode);
//        }
//    }
//}
