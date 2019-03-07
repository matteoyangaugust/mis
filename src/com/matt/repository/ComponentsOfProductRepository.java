package com.matt.repository;

import com.matt.model.Component_of_product;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ComponentsOfProductRepository {
    public Integer insert(Component_of_product component_of_product);
    public List<Component_of_product> findComponentOdProducts();
    public Integer delete(List<Integer> componentSnList);
    public Integer delete(Integer product_sn);
}
