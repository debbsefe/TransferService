package com.example.transferservice.controllers;

import com.example.transferservice.entities.Balances;
import com.example.transferservice.entities.Transactions;
import com.example.transferservice.repositories.BalanceRepository;
import com.example.transferservice.repositories.TransactionsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.time.Instant;
import java.util.regex.Pattern;

@Controller
@RequestMapping(path = "/account")
public class MainController {
    @Autowired
    TransactionsRepository transactionsRepository;

    @Autowired
    BalanceRepository balanceRepository;

    @PostMapping(path = "/transfer")
    public @ResponseBody
    Object transferToAccount(@RequestParam String from, @RequestParam String to
            , @RequestParam Integer amount) {
        boolean isNum = checkNumeric(amount.toString());
        if(!isNum){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Amount has to be a number");
        }
        if (from.equals(to)) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "You cannot send money to your account");
        }

        Balances balance = getSingleBalance(from);
        Integer availableBalance = balance.getBalance();
        if (availableBalance > amount) {
            saveTransaction(amount, from);
            saveFromBalance(amount, from);
            saveToBalance(amount, to);
            return getSingleBalance(from);
        } else if (availableBalance < amount) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Insufficient Balance");
        }
        throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Something went wrong");
    }


    @GetMapping(path = "/transactions")
    public @ResponseBody
    Iterable<Transactions> getAllTransactions() {
        return transactionsRepository.findAll();
    }


    @GetMapping(path = "/balances")
    public @ResponseBody
    Iterable<Balances> getAllBalances() {
        return balanceRepository.findAll();
    }


    @GetMapping(path = "/balances/{accountNumber}")
    public @ResponseBody
    Balances getSingleBalance(@PathVariable("accountNumber") String accountNumber) {
        Balances balance = balanceRepository.findByAccountNumber(accountNumber);
        if (balance == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Unable to find account");
        }
        return balanceRepository.findByAccountNumber(accountNumber);
    }


    private void saveTransaction(Integer amount, String from) {
        Transactions transactions = new Transactions();
        Instant instant = Instant.now();
        long timeStampSeconds = instant.getEpochSecond();
        String reference = "transfer/" + timeStampSeconds + "/account";
        transactions.setReference(reference);
        transactions.setAmount(amount);
        transactions.setAccountNumber(from);
        transactionsRepository.save(transactions);
    }

    private void saveFromBalance(Integer amount, String from) {
        Balances balance = getSingleBalance(from);
        Integer availableBalance = balance.getBalance();
        balance.setBalance(availableBalance - amount);
        balanceRepository.save(balance);
    }

    private void saveToBalance(Integer amount, String to) {
        Balances balance = getSingleBalance(to);
        Integer availableBalance = balance.getBalance();
        balance.setBalance(availableBalance + amount);
        balanceRepository.save(balance);
    }

    private static boolean checkNumeric(String value)
    {
        Pattern pattern = Pattern.compile("-?\\d+(\\.\\d+)?");

        return pattern.matcher(value).matches();
    }
}