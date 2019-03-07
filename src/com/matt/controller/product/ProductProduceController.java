package com.matt.controller.product;

import com.matt.bean.ProductFlowingHistoryBean;
import com.matt.bean.ResultBean;
import com.matt.controller.BaseController;
import com.matt.model.Product_flowing_history;
import com.matt.service.product.ProductProduceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping(value="/product/produce/")
public class ProductProduceController extends BaseController{

    private final String PRODUCE_VIEW = "product/produce";
    @Autowired
    private ProductProduceService productProduceService;
    @RequestMapping(value="toProduce.do")
    public String toProduce(Model model){
        model.addAttribute("histories", productProduceService.findHistories());
        model.addAttribute("products", productProduceService.findProducts());
        return super.toView(PRODUCE_VIEW, model);
    }

    @RequestMapping(value="saving.do")
    public @ResponseBody ResultBean saving(Product_flowing_history history){
        try{
            return productProduceService.insert(history);
        }catch (Exception e){
            log.error(e.getMessage(), e);
            return new ResultBean(false, "Save Failed");
        }
    }

    @RequestMapping(value="delete.do")
    public @ResponseBody ResultBean delete(Product_flowing_history history){
        try{
            return productProduceService.delete(history);
        }catch (Exception e){
            log.error(e.getMessage(), e);
            return new ResultBean(false, "Delete Failed");
        }
    }
}
