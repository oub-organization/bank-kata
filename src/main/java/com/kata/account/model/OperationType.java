package com.kata.account.model;

import lombok.Getter;

@Getter
public enum OperationType {
    DEPOSIT("Deposit"), WITHDRAWAL("Withdrawal");

    private final String value;

    OperationType(String value) {
        this.value = value;
    }

}
