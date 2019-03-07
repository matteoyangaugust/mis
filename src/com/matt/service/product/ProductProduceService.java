package com.matt.service.product;

import com.matt.bean.ProductBean;
import com.matt.bean.ProductFlowingHistoryBean;
import com.matt.bean.ResultBean;
import com.matt.model.Product;
import com.matt.model.Product_flowing_history;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface ProductProduceService {
    public List<ProductFlowingHistoryBean> findHistories();

    public List<ProductBean> findProducts();

    public ResultBean insert(Product_flowing_history history) throws Exception;

    public ResultBean delete(Product_flowing_history history) throws Exception;
}
