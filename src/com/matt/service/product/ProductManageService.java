package com.matt.service.product;

import com.matt.bean.ComponentBean;
import com.matt.bean.ProductBean;
import com.matt.bean.ResultBean;
import com.matt.model.Component;
import com.matt.model.Product;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface ProductManageService {
    public List<ProductBean> findProducts();

    public ResultBean updateProduct(Product product) throws Exception;

    public ResultBean delete(Product product) throws Exception;

    public List<Component> findComponents();

    public ResultBean updateComponentOfProduct(List<ComponentBean> componentBeans, Integer product_sn) throws Exception;
}
