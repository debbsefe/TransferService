package com.example.transferservice;

import com.example.transferservice.entities.Balances;
import com.example.transferservice.repositories.BalanceRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class DataLoader implements CommandLineRunner {

    @Autowired
    BalanceRepository balanceRepository;

    @Override
    public void run(String... args) throws Exception {
        loadData();
    }

    private String generateAccountNumber(){
        long number = (long) Math.floor(Math.random() * 9_000_000_000L) + 1_000_000_000L;
        return Long.toString(number);
    }
    private void loadData() {
        if (balanceRepository.count() == 0) {

            Balances balance1 = new Balances(10000, generateAccountNumber());
            Balances balance2 = new Balances(20000, generateAccountNumber());
            Balances balance3 = new Balances(30000, generateAccountNumber());
            Balances balance4 = new Balances(40000, generateAccountNumber());
            balanceRepository.save(balance1);
            balanceRepository.save(balance2);
            balanceRepository.save(balance3);
            balanceRepository.save(balance4);
        }
        System.out.println(balanceRepository.count());
    }
}