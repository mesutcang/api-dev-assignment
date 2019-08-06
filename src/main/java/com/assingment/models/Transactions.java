package com.assingment.models;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@JsonIgnoreProperties(ignoreUnknown = true)
@Data
@JsonFormat(with = JsonFormat.Feature.ACCEPT_SINGLE_VALUE_AS_ARRAY)
public class Transactions {
    private String accountId;
    private Integer companyId;
    private Integer bankId;
    private Number amount;
    private String bookingDate;
    private String valueDate;
    private String type;
    private String category;
    private String currencyCode;
    private String status;

}
