package com.frontwit.barcodeapp.administration.catalogue;

import com.frontwit.barcodeapp.administration.catalogue.dto.OrderSearchCriteria;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Calendar;

import static java.lang.Boolean.TRUE;
import static java.lang.String.format;
import static java.time.ZoneOffset.UTC;
import static java.util.Objects.nonNull;
import static org.springframework.util.StringUtils.isEmpty;

@Service
public class CriteriaBuilder {

    Criteria build(OrderSearchCriteria searchCriteria) {
        var result = new Criteria();
        name(searchCriteria, result);
        customer(searchCriteria, result);
        completed(searchCriteria, result);
        packed(searchCriteria, result);
        stage(searchCriteria, result);
        route(searchCriteria, result);
        lastProcessedOn(searchCriteria, result);
        return result;
    }

    private void name(OrderSearchCriteria searchCriteria, Criteria result) {
        if (isNotEmpty(searchCriteria.getName())) {
            addRegex("name", searchCriteria.getName(), result);
        }
    }

    private void customer(OrderSearchCriteria searchCriteria, Criteria result) {
        if (isNotEmpty(searchCriteria.getCustomer())) {
            addRegex("customer", searchCriteria.getCustomer(), result);
        }
    }

    private void completed(OrderSearchCriteria searchCriteria, Criteria result) {
        if (TRUE.equals(searchCriteria.getCompleted())) {
            result.and("completed").is(true);
        } else {
            result.and("completed").is(false);
        }
    }

    private void packed(OrderSearchCriteria searchCriteria, Criteria result) {
        if (TRUE.equals(searchCriteria.getPacked())) {
            result.and("packed").is(true);
        }
    }

    private void stage(OrderSearchCriteria searchCriteria, Criteria result) {
        if (nonNull(searchCriteria.getStage())) {
            result.and("stage").is(searchCriteria.getStage());
        }
    }

    private void route(OrderSearchCriteria searchCriteria, Criteria result) {
        if (isNotEmpty(searchCriteria.getRoute())) {
            addRegex("route", searchCriteria.getRoute(), result);
        }
    }

    private void lastProcessedOn(OrderSearchCriteria searchCriteria, Criteria result) {
        LocalDateTime startOfDay = searchCriteria.getProcessingDate().atStartOfDay();
        if (nonNull(searchCriteria.getProcessingDate())) {
            result.and("lastProcessedOn")
                    .gte(startOfDay)
                    .lt(startOfDay.plusDays(1));
        }
    }

    private static void addRegex(String field, String value, Criteria result) {
        result.and(field).regex(format("%s", value), "i");
    }

    private static boolean isNotEmpty(String arg) {
        return !isEmpty(arg);
    }
}
