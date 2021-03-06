package com.frontwit.barcodeapp.administration.catalogue.orders;

import com.frontwit.barcodeapp.administration.catalogue.orders.dto.OrderSearchCriteria;
import lombok.AllArgsConstructor;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

import static java.lang.Boolean.TRUE;
import static java.lang.String.format;
import static java.util.Objects.nonNull;
import static org.springframework.util.StringUtils.isEmpty;

@Service
@AllArgsConstructor
public class OrderCriteriaBuilder {
    static final String COMPLETED_FIELD = "completed";
    static final String DEADLINE_FIELD = "deadline";
    static final String NAME_FIELD = "name";


    Criteria build(OrderSearchCriteria searchCriteria) {
        final var result = new Criteria();
        name(searchCriteria, result);
        completed(searchCriteria, result);
        packed(searchCriteria, result);
        stage(searchCriteria, result);
        lastProcessedOn(searchCriteria, result);
        orderedAt(searchCriteria, result);
        return result;
    }

    private void name(OrderSearchCriteria searchCriteria, Criteria result) {
        if (isNotEmpty(searchCriteria.getName())) {
            addRegex(NAME_FIELD, searchCriteria.getName(), result);
        }
    }

    private void completed(OrderSearchCriteria searchCriteria, Criteria result) {
        if (TRUE.equals(searchCriteria.getCompleted())) {
            result.and(COMPLETED_FIELD).is(true);
        } else {
            result.and(COMPLETED_FIELD).is(false);
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

    private void lastProcessedOn(OrderSearchCriteria searchCriteria, Criteria result) {
        if (nonNull(searchCriteria.getProcessingDate())) {
            LocalDateTime startOfDay = searchCriteria.getProcessingDate().atStartOfDay();
            result.and("lastProcessedOn")
                    .gte(startOfDay)
                    .lt(startOfDay.plusDays(1));
        }
    }

    private void orderedAt(OrderSearchCriteria searchCriteria, Criteria result) {
        if (nonNull(searchCriteria.getOrderedAt())) {
            LocalDateTime startOfDay = searchCriteria.getOrderedAt().atStartOfDay();
            result.and("orderedAt")
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
