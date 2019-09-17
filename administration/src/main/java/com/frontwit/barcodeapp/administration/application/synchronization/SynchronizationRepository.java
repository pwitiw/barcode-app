package com.frontwit.barcodeapp.administration.application.synchronization;

import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import java.util.List;
import java.util.Optional;

import static java.lang.String.format;

@AllArgsConstructor
@Repository
class SynchronizationRepository {

    private static final Logger LOG = LoggerFactory.getLogger(SynchronizationRepository.class);

    private EntityManager entityManager;

    Optional<Order> findOrder(long id) {
        try {
            Object[] result = (Object[]) entityManager
                    .createNativeQuery(findOrderQuery(id))
                    .getSingleResult();
            return Optional.ofNullable(Order.valueOf(result));
        } catch (Exception ex) {
            LOG.warn(format("Id: %s \t", id) + ex.getMessage());
            return Optional.empty();
        }
    }

    Dictionary findDictionary() {
        List entries = entityManager.createNativeQuery(findDictionaryQuery()).getResultList();
        return Dictionary.valueOf(entries);
    }

    private String findOrderQuery(long id) {
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
                "WHERE z.id=" + id;
    }

    private String findDictionaryQuery() {
        return "SELECT " +
                "id, " +
                "name as value " +
                "FROM tdictionary";
    }

}
