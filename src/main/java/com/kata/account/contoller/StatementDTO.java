package com.kata.account.contoller;

import com.kata.account.model.OperationType;
import lombok.Builder;
import lombok.Getter;

import java.util.UUID;

@Getter
@Builder
public class StatementDTO {
    private long id;
    private double amount;
    private double balance;
    private OperationType type;
    private String dateTime;
    private UUID accountUid;
    private String description;
}
