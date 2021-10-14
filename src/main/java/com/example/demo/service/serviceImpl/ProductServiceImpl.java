package com.example.demo.service.serviceImpl;

import java.io.File;
import java.util.List;
import java.util.Optional;

import com.example.demo.exception.NotFoundException;
import com.example.demo.model.dto.productDTO;
import com.example.demo.model.mapper.ProductMapper;
import javassist.bytecode.DuplicateMemberException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;

import com.example.demo.entity.product;
import com.example.demo.repository.ProductRepository;
import com.example.demo.service.ProductService;

@Component
public class ProductServiceImpl implements ProductService {

    @Autowired
    ProductRepository productRepository;

    @Override
    public Page<product> GetListProduct(Pageable pageable) {
        Page<product> products = productRepository.findAll(PageRequest.of(1, 8));
        return products;
    }

    @Override
    public product createProduct(productDTO productDTO) {
//        product product = productRepository.findByNameProduct(productDTO.getName());

       product product = ProductMapper.toProduct(productDTO);
        productRepository.save(product);
        return product;
    }

    @Override
    public productDTO UpdateProduct(productDTO productDTO, Long id) {
        List<product> products = productRepository.findAll();
        for (product product : products) {
            if (!product.getName().equals(productDTO.getName())) {
                for (product product1 : products) {
                    if (product1.getName().equals(productDTO.getName())) {
                        try {
                            throw new DuplicateMemberException("Tên product đã tồn tại");
                        } catch (DuplicateMemberException e) {
                            e.printStackTrace();
                        }
                    }
                }
                product.setName(productDTO.getName());
            }
            product.setImage(productDTO.getImage());
            product.setQuantity(productDTO.getQuantity());
            product.setExtraImage(productDTO.getExtraImageThumbnail());
            product.setExtraImage2(productDTO.getExtraImageThumbnail2());
            product.setExtraImage3(productDTO.getExtraImageThumbnail3());
            return ProductMapper.toDto(product);
        }
        return productDTO;
    }

    @Override
    public product DeleteProduct(Long id) {

        Optional<product> result = productRepository.findById(id);
        if (result == null) {
            throw new NotFoundException("không tìm thấy id sản phẩm");
        }
        productRepository.deleteById(id);
        return null;
    }

    @Override
    public product getProductById(Long id) {
        Optional<product> product = productRepository.findById(id);
        if (product.isPresent()) {
            return product.get();
        }
        throw new NotFoundException("Không tìm thấy ID Product");
    }
}
