package com.matt.service.impl.product;

import com.matt.bean.ProductBean;
import com.matt.bean.ProductFlowingHistoryBean;
import com.matt.bean.ResultBean;
import com.matt.model.Component;
import com.matt.model.Component_of_product;
import com.matt.model.Product;
import com.matt.model.Product_flowing_history;
import com.matt.repository.ComponentRepository;
import com.matt.repository.ComponentsOfProductRepository;
import com.matt.repository.ProductFlowingHistoryRepository;
import com.matt.repository.ProductRepository;
import com.matt.service.BaseService;
import com.matt.service.product.ProductProduceService;
import com.matt.util.ListUtil;
import com.matt.util.MattUtil;
import com.matt.util.MisUtil;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
public class ProductProduceServiceImpl extends BaseService implements ProductProduceService {

    @Autowired
    private ProductFlowingHistoryRepository productFlowingHistoryRepository;
    @Autowired
    private ProductRepository productRepository;
    @Autowired
    private ComponentRepository componentRepository;
    @Autowired
    private ComponentsOfProductRepository componentsOfProductRepository;

    private List<Product> products;
    private List<Component> components;
    private List<Component_of_product> componentOfProducts;
    @Override
    public List<ProductFlowingHistoryBean> findHistories() {
        List<Product_flowing_history> histories = productFlowingHistoryRepository.findHistories();
        components = componentRepository.findComponents();
        componentOfProducts = componentsOfProductRepository.findComponentOdProducts();
        products = productRepository.findProducts();
        return MisUtil.getCombinedProductFlowingHistories(components, componentOfProducts, products, histories);
    }

    @Override
    public List<ProductBean> findProducts() {
        List<ProductBean> productBeans = new ArrayList<>();
        for(Product product : products){
            ProductBean productBean = new ProductBean();
            BeanUtils.copyProperties(product, productBean);
            productBean.setComponentsActive(MisUtil.isComponentActive(product.getSn(), components, componentOfProducts));
            productBeans.add(productBean);
        }
        return productBeans;
    }

    @Override
    @Transactional(rollbackFor = Exception.class, transactionManager = "mis")
    public ResultBean insert(Product_flowing_history history) throws Exception {
        components = componentRepository.findComponents();
        componentOfProducts = componentsOfProductRepository.findComponentOdProducts();
        Product product = productRepository.findProducts(history.getProduct_sn());
        String errString = "";
        boolean isValid = true;
        List<Component> componentsToUpdate = new ArrayList<>();
        if(history.getAction().equals(Product_flowing_history.ACTION_PRODUCE)){
            for(Component_of_product componentOfProduct : componentOfProducts){
                if(componentOfProduct.getProduct_sn().equals(history.getProduct_sn())){
                    Integer neededQuantity = history.getAmount() * componentOfProduct.getComponent_amount();
                    for(Component component : components){
                        if(component.getSn().equals(componentOfProduct.getComponent_sn())){
                            if(component.getRemain() < neededQuantity){
                                errString += "Component:" + component.getName() + " is not enough";
                                isValid = false;
                            }else{
                                component.setRemain(component.getRemain() - neededQuantity);
                                componentsToUpdate.add(component);
                            }
                        }
                    }
                }
            }
            product.setRemain(history.getAmount() + product.getRemain());
            history.setRemain(history.getRemain() + history.getAmount());
        }else if(history.getAction().equals(Product_flowing_history.ACTION_SELL)){
            if(product.getRemain() < history.getAmount()){
                errString += "Product is not enough";
                isValid = false;
            }
            product.setRemain(Math.negateExact(history.getAmount()) + product.getRemain());
            history.setRemain(history.getRemain() + Math.negateExact(history.getAmount()));
        }
        if(!isValid){
            return new ResultBean(false, errString);
        }else{
            history.setUpdate_time(MattUtil.getDateTime());
            if(productFlowingHistoryRepository.insert(history) < 1){
                throw new Exception();
            }
            if(history.getAction().equals(Product_flowing_history.ACTION_PRODUCE)){
                for(Component component : componentsToUpdate){
                    if(componentRepository.update(component) < 1){
                        throw new Exception();
                    }
                }
            }
            if(productRepository.update(product) < 1){
                throw new Exception();
            }
        }
        return new ResultBean(true, "Save Successfully");
    }

    @Override
    @Transactional(rollbackFor = Exception.class, transactionManager = "mis")
    public ResultBean delete(Product_flowing_history history) throws Exception {
        Product product = productRepository.findProducts(history.getProduct_sn());
        components = componentRepository.findComponents();
        componentOfProducts = componentsOfProductRepository.findComponentOdProducts();
        List<Product_flowing_history> histories = productFlowingHistoryRepository.findHistories();
        if(history.getAction().equals(Product_flowing_history.ACTION_PRODUCE)){
            for(Component_of_product componentOfProduct : componentOfProducts){
                if(componentOfProduct.getProduct_sn().equals(history.getProduct_sn())){
                    Integer neededQuantity = history.getAmount() * componentOfProduct.getComponent_amount();
                    for(Component component : components){
                        if(component.getSn().equals(componentOfProduct.getComponent_sn())){
                            component.setRemain(component.getRemain() + neededQuantity);
                            if(componentRepository.update(component) < 1){
                                throw new Exception();
                            }
                            break;
                        }
                    }
                }
            }
        }
        for(Product_flowing_history existedHistory : histories){
            if(existedHistory.getSn() > history.getSn() && history.getProduct_sn().equals(existedHistory.getProduct_sn())){
                if(history.getAction().equals(Product_flowing_history.ACTION_PRODUCE)){
                    existedHistory.setRemain(existedHistory.getRemain() - history.getAmount());
                }else{
                    existedHistory.setRemain(existedHistory.getRemain() - Math.negateExact(history.getAmount()));
                }
                if(productFlowingHistoryRepository.update(existedHistory) < 1){
                    throw new Exception();
                }
            }
        }
        if(history.getAction().equals(Product_flowing_history.ACTION_PRODUCE)){
            product.setRemain(product.getRemain() - history.getAmount());
        }else if(history.getAction().equals(Product_flowing_history.ACTION_SELL)){
            product.setRemain(product.getRemain() - Math.negateExact(history.getAmount()));
        }

        if(productRepository.update(product) < 1){
            throw new Exception();
        }
        if(productFlowingHistoryRepository.deleteBySn(history.getSn()) < 1){
            throw new Exception();
        }
        return new ResultBean(true, "Delete Successfully");
    }
}
