package com.matt.service.impl.product;

import com.matt.bean.ComponentBean;
import com.matt.bean.ProductBean;
import com.matt.bean.ResultBean;
import com.matt.model.Component;
import com.matt.model.Component_of_product;
import com.matt.model.Product;
import com.matt.repository.BaseRepository;
import com.matt.repository.ComponentRepository;
import com.matt.repository.ComponentsOfProductRepository;
import com.matt.repository.ProductRepository;
import com.matt.service.product.ProductManageService;
import com.matt.util.ListUtil;
import com.matt.util.MisUtil;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.method.P;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
public class ProductManageServiceImpl extends BaseRepository implements ProductManageService{
    @Autowired
    private ProductRepository productRepository;
    @Autowired
    private ComponentRepository componentRepository;
    @Autowired
    private ComponentsOfProductRepository componentsOfProductRepository;

    @Override
    public List<ProductBean> findProducts() {

        List<Product> products = productRepository.findProducts();
        List<Component_of_product> component_of_products = componentsOfProductRepository.findComponentOdProducts();
        List<Component> components = componentRepository.findComponents();
        ListUtil.sort(products, "identifier");
        List<ProductBean> productDataList = MisUtil.getCombinedProducts(products, component_of_products, components);
        return productDataList;
    }

    @Override
    public ResultBean updateProduct(Product product) throws Exception {
        List<Product> existedProducts = productRepository.findProducts();
        for(Product existedProduct : existedProducts){
            if(product.getIdentifier().equals(existedProduct.getIdentifier())){
                if(!product.getSn().equals(existedProduct.getSn())){
                    return new ResultBean(false, "Duplicate Product Code");
                }
            }
        }
        if(productRepository.update(product) < 1){
            throw new Exception();
        }
        return new ResultBean(true, "Save Successfully");
    }

    @Override
    public ResultBean delete(Product product) throws Exception {
        product.setActive(0);
        if(productRepository.update(product) < 1){
            throw new Exception();
        }
        return new ResultBean(true, "Delete Successfully");
    }

    @Override
    public List<Component> findComponents() {
        List<Component> components = componentRepository.findComponents();
        ListUtil.sort(components);
        return components;
    }

    @Override
    @Transactional(rollbackFor = Exception.class, transactionManager = "mis")
    public ResultBean updateComponentOfProduct(List<ComponentBean> componentBeans, Integer product_sn) throws Exception {
        if(componentsOfProductRepository.delete(product_sn) < 1){
            throw new Exception();
        }
        for(ComponentBean componentBean : componentBeans){
            Component_of_product componentOfProduct = new Component_of_product();
            componentOfProduct.setProduct_sn(product_sn);
            componentOfProduct.setComponent_sn(componentBean.getSn());
            componentOfProduct.setComponent_amount(componentBean.getQuantity());
            if(componentsOfProductRepository.insert(componentOfProduct) < 1){
                throw new Exception();
            }
        }
        return new ResultBean(true, "Save Successfully");
    }
}
