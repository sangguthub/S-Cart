package com.project_1_ecommerce.demo.Repository;

import com.project_1_ecommerce.demo.Model.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductRepo extends JpaRepository<Product, Integer> {
    @Query("select p from Product p where " +
            "lower(p.name) LIKE lower(concat('%' ,:key ,'%')) OR " +
            "lower(p.brand) LIKE lower(concat('%' ,:key ,'%')) OR " +
            "lower(p.description) LIKE lower(concat('%' ,:key ,'%')) OR " +
            "lower(p.category) LIKE lower(concat('%' ,:key ,'%'))")
    List<Product> searchProduct(String key);
}
