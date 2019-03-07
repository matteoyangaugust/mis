package com.matt.repository;

import com.matt.model.Product;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductRepository {

    public Integer insert(Product product);

    public List<Product> findProducts();

    public int update(Product product);

    public Integer delete(Integer sn);

    public Product findProducts(Integer product_sn);
}
