package com.kata.account.service;

import com.kata.account.model.Statement;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class StatementServiceTest {

    private StatementServiceImpl statementService;

    private final UUID accountUid = UUID.randomUUID();

    @BeforeEach
    public void setUp() {
        statementService = new StatementServiceImpl();
    }

    @Test
    public void when_addHistory_historyListContainsOneStatement() {
        Statement statement = Statement.builder()
                .accountUid(accountUid)
                .dateTime(LocalDateTime.now())
                .description("Deposit of amount : 200")
                .amount(200.0)
                .balance(1200.0)
                .build();

        statementService.addHistory(statement);

        assertEquals(1, statementService.history.size());
        assertEquals(statement, statementService.history.get(0));
    }

    @Test
    public void when_getHistory_returnsAListWithTwoStatements() {
        Statement statement1 = Statement.builder()
                .accountUid(accountUid)
                .dateTime(LocalDateTime.now())
                .description("Deposit of amount : 200")
                .amount(200)
                .balance(1200)
                .build();

        UUID anotherAccountUid = UUID.randomUUID();
        Statement statement2 = Statement.builder()
                .accountUid(anotherAccountUid)
                .dateTime(LocalDateTime.now())
                .description("Withdrawal of amount : 100")
                .amount(-100)
                .balance(900)
                .build();

        statementService.addHistory(statement1);
        statementService.addHistory(statement2);

        List<Statement> accountHistory = statementService.getHistory(accountUid);

        assertEquals(1, accountHistory.size());
        assertEquals(statement1, accountHistory.get(0));

        assertTrue(accountHistory.stream()
                .noneMatch(statement -> statement.getAccountUid().equals(anotherAccountUid)));
    }
}
