package com.kata.account.contoller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.kata.account.model.Account;
import com.kata.account.service.AccountService;
import com.kata.account.service.StatementService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/account")
public class AccountController {

    @Autowired
    private AccountService accountService;
    @Autowired
    private StatementService statementService;
    @Autowired
    private ObjectMapper mapper;

    /*
        POST http://localhost:8080/account/deposit
        body : {
            "accountUid": "61c460dc-fc7a-4ce0-86b0-e0fc9b08e6c7",
            "amount": 100
             }
     */
    @PostMapping("/deposit")
    public ResponseEntity<StatementDTO> saveMoney(@RequestBody TransactionDTO transactionDTO) {
        try {
            Account account = accountService.deposit(transactionDTO.getAccountUid(), transactionDTO.getAmount());
            return new ResponseEntity<>(mapper.convertValue(account.getStatement(), StatementDTO.class), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    /*
        POST http://localhost:8080/account/withdraw
        body : {
            "accountUid": "61c460dc-fc7a-4ce0-86b0-e0fc9b08e6c7",
            "amount": 30
             }
     */
    @PostMapping("/withdraw")
    public ResponseEntity<StatementDTO> withdrawMoney(@RequestBody TransactionDTO operation) {
        try {
            Account account = accountService.withdraw(operation.getAccountUid(), operation.getAmount());
            return new ResponseEntity<>(mapper.convertValue(account.getStatement(), StatementDTO.class), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    // GET http://localhost:8080/account/history/61c460dc-fc7a-4ce0-86b0-e0fc9b08e6c7
    @GetMapping("/history/{accountUid}")
    public ResponseEntity<List<StatementDTO>> getHistoryByAccount(@PathVariable UUID accountUid) {
        return new ResponseEntity<>(
                statementService.getHistory(accountUid).
                        stream().map(statement -> mapper.convertValue(statement, StatementDTO.class))
                        .toList(),
                HttpStatus.OK);
    }
}
