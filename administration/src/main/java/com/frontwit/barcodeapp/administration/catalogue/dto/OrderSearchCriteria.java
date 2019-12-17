package com.frontwit.barcodeapp.administration.catalogue.dto;

import com.frontwit.barcodeapp.administration.processing.shared.Stage;
import lombok.Getter;
import lombok.ToString;
import org.springframework.util.StringUtils;


@Getter
@ToString
public class OrderSearchCriteria {

    String name;
    Boolean completed;
    Stage stage;

    public boolean empty() {
        return StringUtils.isEmpty(this.name)
                && completed == null;
    }
}
