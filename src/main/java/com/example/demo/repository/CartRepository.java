package com.example.demo.repository;

import com.example.demo.entity.CartItem;
import com.example.demo.entity.User;
import com.example.demo.entity.product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@EnableJpaRepositories
public interface CartRepository extends JpaRepository<CartItem,Integer> {
    List<CartItem> findByUser(User user);


    CartItem findByUserAndProduct(User user, product product);

    @Query(value = "UPDATE CartItem c set c.quantity = ?1 where c.product.id = ?2 and c.user.id = ?3")
    @Modifying
    public void updateQuantity(Integer quantity, Integer productId, Integer userId);
}
