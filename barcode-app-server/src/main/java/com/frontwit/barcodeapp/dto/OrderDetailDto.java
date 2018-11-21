package com.frontwit.barcodeapp.dto;

import com.frontwit.barcodeapp.dao.RouteDao;
import com.frontwit.barcodeapp.datatype.Stage;
import com.frontwit.barcodeapp.model.Component;
import com.frontwit.barcodeapp.model.Order;
import lombok.Data;
import org.bson.types.ObjectId;

import java.time.LocalDate;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Data
public class OrderDetailDto {

    private String id;
    private Long extId;
    private String name;
    private String color;
    private String size;
    private String cutter;
    private String comment;
    private String route;
    private String customer;
    private Stage stage;
    private LocalDate orderedAt;
    private List<ComponentDto> components = new LinkedList();


    public static OrderDetailDto valueOf(Order order) {
        OrderDetailDto dto = new OrderDetailDto();
        dto.id = order.getId().toHexString();
        dto.name = order.getName();
        dto.name = order.getName();
        dto.components = order.getComponents()
                .stream()
                .map(ComponentDto::valueOf)
                .collect(Collectors.toList());
        return dto;
    }

    public Order toEntity(RouteDao routeDao) {
        Order order = new Order();
        if (id != null) {
            order.setId(new ObjectId(id));
        }
        order.setName(name);
        order.setColor(color);
        order.setSize(size);
        order.setCutter(cutter);
        order.setComment(comment);
        order.setCustomer(customer);
        order.setRoute(routeDao.findByName(route));
        order.setStage(stage);
        order.setOrderedAt(orderedAt);
        order.setExtId(extId);
        Set<Component> componentsToSave = components.stream()
                .map(ComponentDto::toEntity)
                .collect(Collectors.toSet());
        order.setComponents(componentsToSave);
        return order;
    }

}
