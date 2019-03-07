package com.matt.controller.component;

import com.matt.bean.ResultBean;
import com.matt.controller.BaseController;
import com.matt.model.Component_flowing_history;
import com.matt.service.component.ComponentProduceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping(value="/component/produce/")
public class ComponentProduceController extends BaseController{

    private final String PRODUCE_VIEW = "component/produce";

    @Autowired
    private ComponentProduceService componentProduceService;
    @RequestMapping(value="toProduce.do")
    public String toProduce(Model model){
        model.addAttribute("components", componentProduceService.findComponents());
        model.addAttribute("historyDataList", componentProduceService.findFlowingHistory(super.getUser()));
        model.addAttribute("processCategories", componentProduceService.findProcessCategories(super.getUser()));
        return toView(PRODUCE_VIEW, model);
    }

    @RequestMapping(value="saving.do")
    public @ResponseBody ResultBean saving(Component_flowing_history flowingHistory){
        try{
            return componentProduceService.save(flowingHistory);
        }catch (Exception e){
            log.error(e.getMessage(), e);
            return new ResultBean(false, "Save Failed");
        }
    }

    @RequestMapping(value="delete.do")
    public @ResponseBody ResultBean delete(Component_flowing_history flowingHistory){
        try{
            return componentProduceService.delete(flowingHistory, super.getUser());
        }catch (Exception e){
            log.error(e.getMessage(), e);
            return new ResultBean(false, "Delete Failed");
        }
    }
}
