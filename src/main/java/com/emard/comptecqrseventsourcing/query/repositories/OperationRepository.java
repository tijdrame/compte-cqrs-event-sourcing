package com.emard.comptecqrseventsourcing.query.repositories;

import com.emard.comptecqrseventsourcing.query.entities.Operation;

import org.springframework.data.jpa.repository.JpaRepository;

public interface OperationRepository extends JpaRepository<Operation, Long> {
    
}
