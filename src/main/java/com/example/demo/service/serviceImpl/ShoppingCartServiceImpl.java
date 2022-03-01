package com.example.demo.service.serviceImpl;

import com.example.demo.entity.CartItem;
import com.example.demo.entity.User;
import com.example.demo.entity.product;
import com.example.demo.repository.CartRepository;
import com.example.demo.repository.ProductRepository;
import com.example.demo.service.ShoppingCartService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Component
@Transactional
public class ShoppingCartServiceImpl implements ShoppingCartService {

    @Autowired
    private CartRepository cartRepository;

    @Autowired
    private ProductRepository productRepository;

    @Override
    public List<CartItem> getListCartItem(User user) {
        return cartRepository.findByUser(user);
    }



    public Integer addProduct(Integer quantity, Integer productId, User user) {
        Integer addedQuantity = quantity;
        product product = productRepository.getById(productId);
        CartItem cartItem = cartRepository.findByUserAndProduct(user, product);
        if (cartItem != null) {
            addedQuantity = cartItem.getQuantity() + quantity;
            cartItem.setQuantity(addedQuantity);
        } else {
            cartItem = new CartItem();
            cartItem.setProduct(product);
            cartItem.setUser(user);
            cartItem.setQuantity(quantity);
        }
        cartRepository.save(cartItem);
        return addedQuantity;
    }

    @Override
    public float UpdateQuantity(Integer productId, Integer quantity, User user) {
        System.out.println(quantity + "-" + productId + "-" + user.getId());
        cartRepository.updateQuantity(productId,quantity,user.getId());
        product product = productRepository.getById(productId);
        float subtotal = product.getProduct_price() * quantity;
        return subtotal;
    }

    @Override
    public void removeProduct(Integer productId, User user) {
        cartRepository.deleteByUserAndProduct(user.getId(), productId);
    }
}
