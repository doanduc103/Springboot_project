package com.example.demo.repository;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.example.demo.entity.product;

import java.util.List;

@Repository
public interface ProductRepository extends JpaRepository<product, Integer> {
    @Query("SELECT u from product u where u.name = :name ")
    product findByNameProduct(String name);

    @Query("SELECT p from product p where concat(p.name,p.product_price) like %?1%")
    List<product> SearchKeyword(String keyword);
}
