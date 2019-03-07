package com.matt.repository;

import com.matt.model.Product_flowing_history;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductFlowingHistoryRepository {

    public List<Product_flowing_history> findHistories();

    public Integer deleteBySn(Integer sn);

    public Integer insert(Product_flowing_history history);

    public Integer update(Product_flowing_history history);
}
