package com.matt.service.impl.product;

import com.matt.bean.ComponentBean;
import com.matt.bean.ResultBean;
import com.matt.model.Component;
import com.matt.model.Component_of_product;
import com.matt.model.Product;
import com.matt.repository.ComponentRepository;
import com.matt.repository.ComponentsOfProductRepository;
import com.matt.repository.ProductRepository;
import com.matt.service.BaseService;
import com.matt.service.product.ProductCreateService;
import com.matt.util.ListUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class ProductCreateServiceImpl extends BaseService implements ProductCreateService {

    @Autowired
    private ComponentRepository componentRepository;
    @Autowired
    private ProductRepository productRepository;
    @Autowired
    private ComponentsOfProductRepository componentsOfProductRepository;
    @Override
    public List<Component> findComponents() {
        List<Component> components = componentRepository.findComponents();
        ListUtil.sort(components);
        return components;
    }

    @Override
    @Transactional(rollbackFor = Exception.class, transactionManager = "mis")
    public ResultBean insertProductData(ComponentBean[] componentBeans, Product newProduct) throws Exception {
        List<Product> products = productRepository.findProducts();
        for(Product product : products){
            if(newProduct.getIdentifier().equals(product.getIdentifier())){
                return new ResultBean(false, "Duplicate Product Code");
            }
        }
        Integer productSn = productRepository.insert(newProduct);
        if(productSn < 1){
            throw new Exception();
        }
        for(ComponentBean componentBean : componentBeans){
            Component_of_product componentsOfProduct = new Component_of_product();
            componentsOfProduct.setProduct_sn(productSn);
            componentsOfProduct.setComponent_sn(componentBean.getSn());
            componentsOfProduct.setComponent_amount(componentBean.getQuantity());
            if(componentsOfProductRepository.insert(componentsOfProduct) < 1) {
                throw new Exception();
            }
        }
        return new ResultBean(true, "Save Successfully");
    }
}
