package com.kata.account.service;

import com.kata.account.model.Statement;
import org.springframework.stereotype.Service;

import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
public class StatementServiceImpl implements StatementService {

    private static final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss");

    public final List<Statement> history = new ArrayList<>();

    private long statementId = 1L;
    @Override
    public void print(Statement statement) {
        System.out.printf("Type    : %s \nAmount  : %.2f \nBalance : %.2f \nDate    : %s%n",
                statement.getType().getValue(),
                statement.getAmount(),
                statement.getBalance(),
                formatter.format(statement.getDateTime()));
        System.out.println("--------------------------------------");
    }

    @Override
    public void addHistory(Statement statement) {
        statement.setId(statementId++);
        history.add(statement);
    }

    @Override
    public List<Statement> getHistory(UUID accountUid) {
        return history.stream()
                .filter(history -> history.getAccountUid().equals(accountUid))
                .toList();
    }
}
