package com.example.demo.service;

import com.example.demo.model.dto.productDTO;

import com.example.demo.entity.product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public interface ProductService {
    Page<product> GetListProduct(Pageable pageable);

    product createProduct(productDTO productDTO);

    productDTO UpdateProduct(Integer id, productDTO productDTO);

    product DeleteProduct(Integer id);

    product getProductById(Integer id);

    List<product> Search(String name);

    product UpdateImageProduct(Integer id, productDTO productDTO);

    List<product> FindByName(String name);

    List<product> FindByTop1Name(String name);

    Page<product> findAllProductByPriceAscending (int page);

    Page<product> SearchProductByAvailableAndCreatedDateAndPriceAndName(int page,String keyword);
}
