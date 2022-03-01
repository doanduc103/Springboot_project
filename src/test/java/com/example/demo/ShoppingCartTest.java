package com.example.demo;

import com.example.demo.entity.CartItem;
import com.example.demo.entity.User;
import com.example.demo.entity.product;
//import com.example.demo.repository.CartRepository;
import com.example.demo.repository.CartRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.annotation.Rollback;

import java.util.List;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@Rollback(value = false)
public class ShoppingCartTest {

    @Autowired
    private CartRepository cartRepository;
    @Autowired
    private TestEntityManager testEntityManager;

    @Test
    public void testAddCart() {
        product Product = testEntityManager.find(product.class, 66);
        User user = testEntityManager.find(User.class,103);

        CartItem cartItem = new CartItem();
        cartItem.setProduct(Product);
        cartItem.setUser(user);
        cartItem.setQuantity(2);

        CartItem saved = cartRepository.save(cartItem);

        Assertions.assertTrue(saved.getId() > 0);
    }

//    @Test
//    public void GetCartItembyUser(){
//        User user = new User();
//        user.setId(98);
//        List<CartItem> cartItemList = cartRepository.findByUser(user);
//        Assertions.assertEquals(3,cartItemList.size());
//    }
}
