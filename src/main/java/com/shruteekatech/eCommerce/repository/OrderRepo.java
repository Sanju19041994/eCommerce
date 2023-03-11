package com.shruteekatech.eCommerce.repository;

import com.shruteekatech.eCommerce.entity.Order;
import com.shruteekatech.eCommerce.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface OrderRepo  extends JpaRepository <Order, Long> {



    Optional<List<Order>> findByUser(User user);

}
