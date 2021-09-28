package com.example.demo.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.demo.entity.finance;

@Repository
public interface FinanceRepository extends JpaRepository<finance, Integer> {

}
