package com.example.solidbank.controller;

import com.example.solidbank.*;
import com.example.solidbank.service.AccountService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/accounts")
@SecurityRequirement(name = "basicauth")
public class AccountController {
    @Autowired
    private AccountService accountService;
    @Autowired
    private TransactionDeposit transactionDeposit;
    @Autowired
    private TransactionWithdraw transactionWithdraw;

    //ok

    @GetMapping
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<List<Account>> getAllAccounts() {
        List<Account> accounts = accountService.getAllAccounts();
        return new ResponseEntity<>(accounts, HttpStatus.OK);
    }


    //ok

    @PostMapping
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<String> createAccount(@RequestParam AccountType accountType,
                                                @RequestParam String clientID) {
        accountService.createAccount(accountType, clientID);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body("Account created successfully");
    }

    //ok

    @GetMapping("/{account_id}")
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<Account> getAccountById(@PathVariable("account_id") String accountId) {
        Account account = accountService.getAccountById(accountId);
        if (account == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(account, HttpStatus.OK);
    }

    //ok

    @DeleteMapping("/{account_id}")
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<Void> deleteAccountById(@PathVariable("account_id") String accountId) {
        accountService.deleteAccountById(accountId);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }


    @PostMapping("/{account_id}/withdraw")
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<String> withdrawFromAccount(@PathVariable("account_id") String accountId,
                                                      @RequestParam double amount) {
        Account account = accountService.getAccountById(accountId);
        if (account == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("Account not found");
        }
        transactionWithdraw.execute((AccountWithdraw) account, amount);
        return ResponseEntity.ok("Withdrawal successful");
    }


    @PostMapping("/{account_id}/deposit")
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<String> depositToAccount(@PathVariable("account_id") String accountId,
                                                   @RequestParam double amount) {
        Account account = accountService.getAccountById(accountId);
        if (account == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("Account not found");
        }
        transactionDeposit.execute(account, amount);
        return ResponseEntity.ok("Deposit successful");
    }

    //ok

    @GetMapping("/{id}/transactions")
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<Optional<Transaction>> getAccountTransactions(@PathVariable("id")
                                                                        String id) {
        Optional<Transaction> transactions = accountService.getAccountTransactions(id);
        return new ResponseEntity<>(transactions, HttpStatus.OK);
    }


    @PostMapping("/transfer")
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<String> transferAccount(@RequestParam String accountId
            ,@RequestParam String accountIdTwo,@RequestParam double amount){
        Account account = accountService.getAccountById(accountId);
        if (account == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("Account not found");
        }
        transactionWithdraw.execute((AccountWithdraw) account, amount);
        //deposit
        Account accountTwo = accountService.getAccountById(accountIdTwo);
        if (accountTwo == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("Account not found");
        }
        transactionDeposit.execute(accountTwo, amount);
        return ResponseEntity.ok("transfer successful");
    }
}
