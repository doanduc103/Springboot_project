package com.example.demo.model.mapper;

import com.example.demo.entity.product;
import com.example.demo.model.dto.productDTO;

import java.sql.Timestamp;
import java.text.ParseException;

public class ProductMapper {

    // convert từ entity - DTO
    public static product toProduct(productDTO productDto) {
        try {
            product product = new product();
            product.setId(productDto.getId());
            product.setName(productDto.getName());
            product.setDescription(productDto.getDescription());
            product.setProduct_price(productDto.getProduct_price());
            product.setCreateddate(new Timestamp(System.currentTimeMillis()));
            product.setQuantity(productDto.getQuantity());
            product.setImage(productDto.getImage());
            product.setExtraImage(productDto.getExtraImageThumbnail());
            product.setExtraImage2(productDto.getExtraImageThumbnail2());
            product.setExtraImage3(productDto.getExtraImageThumbnail3());
            product.setAvailable(true);
            return product;
        } catch (Exception e) {
            throw new RuntimeException();
        }
    }

    //covert từ dto - entity
    public static productDTO toDto(product product) {
        try {
            productDTO productDTO = new productDTO();
            productDTO.setId(product.getId());
            productDTO.setName(product.getName());
            productDTO.setDescription(product.getDescription());
            productDTO.setQuantity(product.getQuantity());
            productDTO.setCreateddate(product.getCreateddate());
            productDTO.setProduct_price(product.getProduct_price());
            productDTO.setImage(product.getImage());
            productDTO.setExtraImageThumbnail(product.getExtraImage());
            productDTO.setExtraImageThumbnail2(product.getExtraImage2());
            productDTO.setExtraImageThumbnail3(product.getExtraImage3());
            productDTO.setAvailable(true);
            return productDTO;
        } catch (Exception e) {
            throw new RuntimeException();
        }
    }
}


