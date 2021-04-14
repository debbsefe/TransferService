package com.example.transferservice.repositories;

import com.example.transferservice.entities.Balances;
import com.example.transferservice.entities.Transactions;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

public interface TransactionsRepository extends CrudRepository<Transactions, Integer> {
}
