package com.example.demo.repository;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.example.demo.entity.product;

@Repository
public interface ProductRepository extends JpaRepository<product, Long> {
    @Query("SELECT u from product u where u.name = :name ")
    product findByNameProduct(String name);
}
