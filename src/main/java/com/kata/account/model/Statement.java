package com.kata.account.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.UUID;

@Getter
@Builder
public class Statement {

    @Setter
    private long id;
    private double amount;
    private double balance;
    private OperationType type;
    @JsonFormat(pattern = "dd-MM-yyyy HH:mm:ss")
    private LocalDateTime dateTime;
    private UUID accountUid;
    private String description;

}
