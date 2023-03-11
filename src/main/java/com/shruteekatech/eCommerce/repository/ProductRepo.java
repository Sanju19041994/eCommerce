package com.shruteekatech.eCommerce.repository;

import com.shruteekatech.eCommerce.entity.Category;
import com.shruteekatech.eCommerce.entity.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductRepo extends JpaRepository<Product,Long> {

    // Pageable object added in custom method for pagination
    public Page<Product> findByCategory(Category category, Pageable pageable);
}
