package com.example.demo.service;

import com.example.demo.entity.CartItem;
import com.example.demo.entity.User;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface ShoppingCartService  {

    List<CartItem> getListCartItem(User user);


    public Integer addProduct(Integer quantity, Integer productId, User user);

    public float UpdateQuantity(Integer productId, Integer quantity, User user);
}
