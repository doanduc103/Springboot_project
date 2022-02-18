package com.example.demo.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.example.demo.entity.product;

import java.util.List;

@Repository
public interface ProductRepository extends JpaRepository<product, Integer> {
    @Query("SELECT u from product u where u.name = :name ")
    product findByNameProduct(String name);

    @Query("SELECT p from product p where concat(p.name,p.product_price) like %?1%")
    List<product> SearchKeyword(String keyword);

    @Query(value = "select top 4 * from product s order by s.createddate DESC", nativeQuery = true)
    List<product> select4Product();

    @Query(value = "select top 1 * from product s order by s.createddate ASC ", nativeQuery = true)
    List<product> selectTop1Product();

}
