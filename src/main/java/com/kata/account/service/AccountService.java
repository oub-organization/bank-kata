package com.kata.account.service;

import com.kata.account.model.Account;

import java.util.UUID;

public interface AccountService {

    Account deposit(UUID accountUid, double amount) throws Exception;

    Account withdraw(UUID accountUid, double amount) throws Exception;
}
