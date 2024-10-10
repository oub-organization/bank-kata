package com.kata.account.service;

import com.kata.account.model.Statement;

import java.util.List;
import java.util.UUID;

public interface StatementService {

    void print(Statement statement);
    void addHistory(Statement statement);
    List<Statement> getHistory(UUID accountUid);
}
