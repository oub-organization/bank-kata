package com.kata.account.service;

import com.kata.account.model.Account;
import com.kata.account.model.OperationType;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

public class AccountServiceTest {

    @InjectMocks
    private AccountServiceImpl accountService;

    @Mock
    private StatementService statementService;

    private static final String EXCEPTION_MESSAGE = "Amount should be positive.";

    private final UUID accountUid = UUID.fromString("61c460dc-fc7a-4ce0-86b0-e0fc9b08e6c7");

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void when_deposit_successful() throws Exception {
        double initialBalance = 1000;
        double amount = 200;

        Account account = accountService.deposit(accountUid, amount);

        assertNotNull(account);
        assertEquals(OperationType.DEPOSIT, account.getStatement().getType());
        assertEquals(amount, account.getStatement().getAmount());
        assertEquals(initialBalance + amount, account.getStatement().getBalance());
        verify(statementService, times(1)).print(account.getStatement());
    }

    @Test
    public void when_amountNegative_deposit_throwsException() {
        double depositAmount = -100;

        Exception exception = assertThrows(IllegalArgumentException.class, () -> accountService.deposit(accountUid, depositAmount));
        assertEquals(EXCEPTION_MESSAGE, exception.getMessage());
    }

    @Test
    public void when_withdraw_successful() throws Exception {
        double initialBalance = 1000;
        double withdrawAmount = 200;


        Account account = accountService.withdraw(accountUid, withdrawAmount);

        assertNotNull(account);
        assertEquals(OperationType.WITHDRAWAL, account.getStatement().getType());
        assertEquals(withdrawAmount, account.getStatement().getAmount());
        assertEquals(initialBalance - withdrawAmount, account.getStatement().getBalance());
        verify(statementService, times(1)).print(account.getStatement());
    }

    @Test
    public void when_amountNegative_withdraw_throwsException() {
        double withdrawAmount = -100;

        Exception exception = assertThrows(IllegalArgumentException.class, () -> accountService.withdraw(accountUid, withdrawAmount));
        assertEquals(EXCEPTION_MESSAGE, exception.getMessage());
    }
}
