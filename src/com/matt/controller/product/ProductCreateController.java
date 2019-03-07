package com.matt.controller.product;

import com.google.gson.Gson;
import com.matt.bean.ComponentBean;
import com.matt.bean.ResultBean;
import com.matt.controller.BaseController;
import com.matt.model.Product;
import com.matt.service.product.ProductCreateService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.Arrays;

@Controller
@RequestMapping(value="/product/create/")
public class ProductCreateController extends BaseController{

    private final String CREATE_VIEW = "product/create";
    @Autowired
    private ProductCreateService productCreateService;
    @RequestMapping(value="toCreate.do")
    public String toCreate(Model model){
        model.addAttribute("components", productCreateService.findComponents());
        return super.toView(CREATE_VIEW, model);
    }

    @RequestMapping(value="creating.do")
    public @ResponseBody ResultBean creating(String selectedComponentsJason, Product product){
        Gson gson = new Gson();
        ComponentBean[] componentBeans = gson.fromJson(selectedComponentsJason, ComponentBean[].class);
        try{
            return productCreateService.insertProductData(componentBeans, product);
        }catch (Exception e){
            log.error(e.getMessage(), e);
            return new ResultBean(false, "Save Failed");
        }
    }
}
