package com.example.demo.service;

import com.example.demo.model.dto.productDTO;

import com.example.demo.entity.product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.io.File;

@Service
public interface ProductService {
    Page<product> GetListProduct(Pageable pageable);

    product createProduct(productDTO productDTO);

    productDTO UpdateProduct(productDTO productDTO, Long id);

    product DeleteProduct(Long id);

    product getProductById(Long id);
}
