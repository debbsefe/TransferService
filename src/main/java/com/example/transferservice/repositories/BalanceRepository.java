package com.example.transferservice.repositories;
import com.example.transferservice.entities.Balances;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

public interface BalanceRepository extends CrudRepository<Balances, Integer> {
    Balances findByAccountNumber(@Param("accountNumber") String accountNumber);
}
