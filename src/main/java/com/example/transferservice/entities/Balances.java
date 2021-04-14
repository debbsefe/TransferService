package com.example.transferservice.entities;
import javax.persistence.*;

@Entity // This tells Hibernate to make a table out of this class
public class Balances {
    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    private Integer id;
    private Integer balance;
    @Column(name="accountNumber", unique = true)
    private String accountNumber;

    public Balances(Integer balance, String accountNumber) {
        this.balance = balance;
        this.accountNumber = accountNumber;
    }

    public Balances(){
    }

    public Integer getId() {
        return id;
    }
    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getBalance() {
        return balance;
    }
    public void setBalance(Integer balance) {
        this.balance = balance;
    }

    public String getAccountNumber() {
        return accountNumber;
    }

    public void setAccountNumber(String accountNumber) {
        this.accountNumber = accountNumber;
    }
}