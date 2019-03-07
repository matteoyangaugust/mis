package com.matt.controller.product;

import com.google.gson.Gson;
import com.matt.bean.ComponentBean;
import com.matt.bean.ResultBean;
import com.matt.controller.BaseController;
import com.matt.model.Product;
import com.matt.service.product.ProductManageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Controller
@RequestMapping(value="/product/manage/")
public class ProductManageController extends BaseController {

    private final String MANAGE_VIEW = "product/manage";
    @Autowired
    private ProductManageService productManageService;

    @RequestMapping(value="toManage.do")
    public String toManage(Model model){
        model.addAttribute("products", productManageService.findProducts());
        model.addAttribute("components", productManageService.findComponents());
        return super.toView(MANAGE_VIEW, model);
    }

    @RequestMapping(value="modifying.do")
    public @ResponseBody ResultBean modifying(Product product){
        try{
            return productManageService.updateProduct(product);
        }catch (Exception e){
            log.error(e.getMessage(), e);
            return new ResultBean(false, "Save Failed");
        }
    }

    @RequestMapping(value="delete.do")
    public @ResponseBody ResultBean delete(Product productToDelete){
        try{
            List<Integer> componentSnList = new ArrayList<>();
            return productManageService.delete(productToDelete);
        }catch (Exception e){
            log.error(e.getMessage(), e);
            return new ResultBean(false, "Delete Failed");
        }
    }

    @RequestMapping(value="updating.do")
    public @ResponseBody ResultBean updating(String selectedComponentsJson, Integer product_sn){
        Gson gson = new Gson();
        List<ComponentBean> ComponentBeans = Arrays.asList(gson.fromJson(selectedComponentsJson, ComponentBean[].class));
        try{
            return productManageService.updateComponentOfProduct(ComponentBeans, product_sn);
        }catch (Exception e){
            log.error(e.getMessage(), e);
            return new ResultBean(false, "Save Failed");
        }
    }
}
