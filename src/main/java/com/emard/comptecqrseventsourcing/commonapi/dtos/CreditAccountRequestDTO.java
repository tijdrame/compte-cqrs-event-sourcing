package com.emard.comptecqrseventsourcing.commonapi.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CreditAccountRequestDTO {
    private String accountId;
    private Double amount;
    private String currency;
}
