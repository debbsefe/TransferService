package com.example.transferservice.repositories;
import com.example.transferservice.entities.Transactions;
import org.springframework.data.repository.CrudRepository;

public interface TransactionsRepository extends CrudRepository<Transactions, Integer> {
}
