package com.shruteekatech.eCommerce.repository;

import com.shruteekatech.eCommerce.entity.Cart;
import com.shruteekatech.eCommerce.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CartRepo extends JpaRepository<Cart,Long> {

    Optional<Cart> findByUser(User user);
}
