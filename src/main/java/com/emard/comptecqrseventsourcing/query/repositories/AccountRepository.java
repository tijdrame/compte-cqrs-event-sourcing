package com.emard.comptecqrseventsourcing.query.repositories;

import com.emard.comptecqrseventsourcing.query.entities.Account;

import org.springframework.data.jpa.repository.JpaRepository;

public interface AccountRepository extends JpaRepository<Account, String> {
    
}
