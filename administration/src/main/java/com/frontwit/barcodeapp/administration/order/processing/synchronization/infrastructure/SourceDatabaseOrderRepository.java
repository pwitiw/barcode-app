package com.frontwit.barcodeapp.administration.order.processing.synchronization.infrastructure;

import com.frontwit.barcodeapp.administration.order.processing.shared.OrderId;
import com.frontwit.barcodeapp.administration.order.processing.synchronization.Dictionary;
import com.frontwit.barcodeapp.administration.order.processing.synchronization.SourceOrder;
import com.frontwit.barcodeapp.administration.order.processing.synchronization.SourceOrderRepository;
import lombok.AllArgsConstructor;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;

import java.util.Optional;
import java.util.stream.Collectors;

@AllArgsConstructor
public class SourceDatabaseOrderRepository implements SourceOrderRepository {

    private final JdbcTemplate jdbcTemplate;

    @Override
    public Optional<SourceOrder> findBy(OrderId orderId) {
        var order = jdbcTemplate.queryForObject(findOrderQuery(), new BeanPropertyRowMapper<>(SourceOrder.class), orderId.getOrderId());
        return Optional.ofNullable(order);
    }

    @Override
    public Dictionary getDictionary() {
        var entities = jdbcTemplate.query(getDictionaryQuery(), new BeanPropertyRowMapper<>(DictionaryEntity.class));
        var entries = entities.stream().map(DictionaryEntity::toDictionaryEntry).collect(Collectors.toList());
        return new Dictionary(entries);
    }

    private String findOrderQuery() {
        return "SELECT " +
                "z.id as id, " +
                "z.numer as nr, " +
                "z.pozycje as fronts, " +
                "data_z as orderedAt, " +
                "z.nr_zam_kl as additionalInfo, " +
                "z.opis as description, " +
                "z.cechy as features, " +
                "k.nazwa as customer " +
                "FROM tzamowienia z JOIN tklienci k " +
                "ON z.tklienci_id = k.id " +
                "WHERE z.id=?";
    }

    private String getDictionaryQuery() {
        return "SELECT " +
                "id, " +
                "name as value " +
                "FROM tdictionary";
    }

}
