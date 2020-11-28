package com.frontwit.barcodeapp.administration.processing.synchronization.infrastructure;

import com.frontwit.barcodeapp.administration.processing.shared.OrderId;
import com.frontwit.barcodeapp.administration.processing.synchronization.Dictionary;
import com.frontwit.barcodeapp.administration.processing.synchronization.SourceCustomer;
import com.frontwit.barcodeapp.administration.processing.synchronization.SourceOrder;
import com.frontwit.barcodeapp.administration.processing.synchronization.SourceRepository;
import lombok.AllArgsConstructor;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;

import java.time.Instant;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@AllArgsConstructor
@SuppressWarnings("MultipleStringLiterals")
public class JdbcSourceRepository implements SourceRepository {

    private final JdbcTemplate jdbcTemplate;

    @Override
    public Optional<SourceOrder> findBy(OrderId orderId) {
        List<SourceOrder> query = jdbcTemplate.query(findOrderByIdQuery(), new BeanPropertyRowMapper<>(SourceOrder.class), orderId.getId());
        if (query.isEmpty()) {
            return Optional.empty();
        }
        return Optional.of(query.get(0));
    }

    @Override
    public Dictionary getDictionary() {
        var entities = jdbcTemplate.query(getDictionaryQuery(), new BeanPropertyRowMapper<>(DictionaryEntity.class));
        var entries = entities.stream().map(DictionaryEntity::toDictionaryEntry).collect(Collectors.toList());
        return new Dictionary(entries);
    }

    @Override
    public List<SourceOrder> findByDateBetween(Instant from, Instant to) {
        Date fromDate = new Date(from.toEpochMilli());
        Date toDate = new Date(to.toEpochMilli());
        return jdbcTemplate.query(findOrdersByDateGteQuery(), new BeanPropertyRowMapper<>(SourceOrder.class), fromDate, toDate);
    }

    @Override
    public List<SourceCustomer> findCustomers() {
        return jdbcTemplate.query(findCustomersQuery(), new BeanPropertyRowMapper<>(SourceCustomer.class));
    }

    private String findCustomersQuery() {
        return "SELECT "
                + "k.id as id, "
                + "k.nazwa as name, "
                + "k.trasa as route, "
                + "k.adres as address, "
                + "k.telefon as phoneNumber "
                + "FROM tklienci k ";
    }

    private String findOrdersQuery() {
        return "SELECT "
                + "z.id as id, "
                + "z.numer as nr, "
                + "z.pozycje as fronts, "
                + "data_z as orderedAt, "
                + "data_r as deadline, "
                + "valuation as valuation, "
                + "rodzaj as type, "
                + "z.nr_zam_kl as additionalInfo, "
                + "z.opis as description, "
                + "z.cechy as features, "
                + "k.nazwa as customerName, "
                + "k.trasa as route, "
                + "k.adres as customerAddress, "
                + "k.id as customerId, "
                + "k.telefon as phoneNumber "
                + "FROM tzamowienia z JOIN tklienci k "
                + "ON z.tklienci_id = k.id ";
    }

    private String findOrderByIdQuery() {
        return findOrdersQuery() + "WHERE z.id=?";
    }

    private String findOrdersByDateGteQuery() {
        return findOrdersQuery() + "WHERE data_z BETWEEN ? AND ?";
    }

    private String getDictionaryQuery() {
        return "SELECT id, name as value FROM tdictionary";
    }
}
