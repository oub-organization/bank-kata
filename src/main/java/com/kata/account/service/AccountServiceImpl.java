package com.kata.account.service;

import com.kata.account.model.Account;
import com.kata.account.model.OperationType;
import com.kata.account.model.Statement;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.*;


@Service
public class AccountServiceImpl implements AccountService {

    private static final String ARGUMENT_EXCEPTION_MESSAGE = "Amount should be positive.";
    private static final String NOT_FOUND_EXCEPTION_MESSAGE = "Account %s not found.";
    private static final String ACCOUNT_UID = "61c460dc-fc7a-4ce0-86b0-e0fc9b08e6c7";

    private final StatementService statementService;

    Map<UUID, Account> accounts = new HashMap<>();

    public AccountServiceImpl(StatementService statementService) {
        this.statementService = statementService;
        accounts.put(
                UUID.fromString(ACCOUNT_UID),
                Account
                        .builder()
                        .accountUid(UUID.fromString(ACCOUNT_UID))
                        .balance(1000)
                        .build()
        );
    }

    @Override
    public Account deposit(UUID accountUid, double amount) throws Exception {
        if (amount <= 0) {
            throw new IllegalArgumentException(ARGUMENT_EXCEPTION_MESSAGE);
        }

        Account account = getAccountByUID(accountUid)
                .orElseThrow(() -> new Exception(String.format(NOT_FOUND_EXCEPTION_MESSAGE, accountUid)));

        account.setBalance(account.getBalance() + amount);

        Statement statement = Statement
                .builder()
                .type(OperationType.DEPOSIT)
                .accountUid(account.getAccountUid())
                .amount(amount)
                .balance(account.getBalance())
                .dateTime(LocalDateTime.now())
                .description(String.format("Deposit of %.2f, your new balance is %.2f.", amount, account.getBalance()))
                .build();
        account.setStatement(statement);

        account = save(account);

        statementService.addHistory(statement);

        statementService.print(statement);

        return account;
    }

    @Override
    public Account withdraw(UUID accountUid, double amount) throws Exception {
        if (amount <= 0) {
            throw new IllegalArgumentException(ARGUMENT_EXCEPTION_MESSAGE);
        }

        Account account = getAccountByUID(accountUid)
                .orElseThrow(() -> new Exception(String.format(NOT_FOUND_EXCEPTION_MESSAGE, accountUid)));

        account.setBalance(account.getBalance() - amount);

        Statement statement = Statement
                .builder()
                .type(OperationType.WITHDRAWAL)
                .accountUid(account.getAccountUid())
                .amount(amount)
                .balance(account.getBalance())
                .dateTime(LocalDateTime.now())
                .description(String.format("Withdrawal of %.2f, your new balance is %.2f.", amount, account.getBalance()))
                .build();
        account.setStatement(statement);

        account = save(account);

        statementService.addHistory(statement);

        statementService.print(statement);

        return account;
    }

    private Optional<Account> getAccountByUID(UUID accountUid) {
        Account account = accounts.get(accountUid);
        if (Objects.nonNull(account)) {
            return Optional.of(account);
        }
        return Optional.empty();
    }

    private Account save(Account account) {
        accounts.put(account.getAccountUid(), account);
        return accounts.get(account.getAccountUid());
    }
}
