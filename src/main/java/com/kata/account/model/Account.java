package com.kata.account.model;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
@Builder
public class Account {

    private UUID accountUid;
    private double balance;
    private Statement statement;
}
