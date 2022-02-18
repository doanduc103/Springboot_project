package com.example.demo.service.serviceImpl;

import java.util.List;
import java.util.Optional;

import com.example.demo.exception.NotFoundException;
import com.example.demo.model.dto.productDTO;
import com.example.demo.model.mapper.ProductMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;

import com.example.demo.entity.product;
import com.example.demo.repository.ProductRepository;
import com.example.demo.service.ProductService;

import javax.management.Query;

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
        product product = new product();
        product = ProductMapper.toProduct(productDTO, product);
        productRepository.save(product);
        return product;
    }

    @Override
    public productDTO UpdateProduct(Integer id, productDTO productDTO) {

        Optional<product> rs = productRepository.findById(id);
        if (rs.isPresent()) {
            product product = rs.get();
//            productDTO.setName(product.getName());
//            productDTO.setDescription(product.getDescription());
//            productDTO.setProduct_price(product.getProduct_price());
//            productDTO.setCreateddate(new Timestamp(System.currentTimeMillis()));
//            productDTO.setQuantity(product.getQuantity());
//            productDTO.setImage(product.getImage());
//            productDTO.setAvailable(true);
//            return product;
            return ProductMapper.toDto(product, productDTO);
        } else {
            return new productDTO();
        }
    }

    @Override
    public product DeleteProduct(Integer id) {

        Optional<product> result = productRepository.findById(id);
        if (result == null) {
            throw new NotFoundException("không tìm thấy id sản phẩm");
        }
        productRepository.deleteById(id);
        return null;
    }

    @Override
    public product getProductById(Integer id) {
        Optional<product> product = productRepository.findById(id);
        if (product.isPresent()) {
            return product.get();
        }
        throw new NotFoundException("Không tìm thấy ID Product");
    }

    @Override
    public List<product> Search(String keyword) {
        if (keyword != null) {
            return productRepository.SearchKeyword(keyword);
        }
        return productRepository.findAll();
    }

    @Override
    public product UpdateImageProduct(Integer id, productDTO productDTO) {
        Optional<product> rs = productRepository.findById(id);
        if (!rs.isPresent()) {
            throw new NotFoundException("Không tìm thấy ID sản phẩm");
        }
        product product = rs.get();
        productDTO.setExtraImageThumbnails(product.getExtraImage());
        productDTO.setExtraImageThumbnails2(product.getExtraImage2());
        productDTO.setExtraImageThumbnails3(product.getExtraImage3());
        return product;
    }

    @Override
    public List<product> FindByName(String name) {
        return productRepository.select4Product();
    }

    @Override
    public List<product> FindByTop1Name(String name) {
        return productRepository.selectTop1Product();
    }


}
