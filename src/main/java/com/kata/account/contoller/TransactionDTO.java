package com.kata.account.contoller;

import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
public class TransactionDTO {

    private UUID accountUid;
    private double amount;

}
