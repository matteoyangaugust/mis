package com.matt.service.product;

import com.matt.bean.ComponentBean;
import com.matt.bean.ResultBean;
import com.matt.model.Component;
import com.matt.model.Product;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface ProductCreateService {
    public List<Component> findComponents();

    public ResultBean insertProductData(ComponentBean[] componentBeans, Product product) throws Exception;
}
