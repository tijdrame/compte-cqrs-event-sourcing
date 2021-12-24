package com.emard.comptecqrseventsourcing.commonapi.queries;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class GetAccountByIdQuery {
    private String id;
    public GetAccountByIdQuery(String id) {
        this.id = id;
    }   
}
