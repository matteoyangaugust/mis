package com.matt.controller.material;

import com.matt.bean.ResultBean;
import com.matt.controller.BaseController;
import com.matt.model.Material_flowing_history;
import com.matt.service.material.MaterialProduceService;
import com.matt.util.MattUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping(value="/material/produce/")
public class MaterialProduceController extends BaseController{

    private final String PRODUCE_VIEW = "material/produce";

    @Autowired
    private MaterialProduceService materialProduceService;

    @RequestMapping(value="toProduce.do")
    public String toProduce(Model model){
        materialProduceService.initialList();
        model.addAttribute("suppliers", materialProduceService.findSuppliers());
        model.addAttribute("materials", materialProduceService.findMaterials());
        model.addAttribute("histories", materialProduceService.findMaterialFLowingHistories());
        model.addAttribute("today", MattUtil.getToday());
        return super.toView(PRODUCE_VIEW, model);
    }

    @RequestMapping(value="saving.do")
    public @ResponseBody ResultBean saving(Material_flowing_history history){
        try{
            return materialProduceService.save(history);
        }catch (Exception e){
            log.error(e.getMessage(), e);
            return new ResultBean(false, "儲存失敗");
        }
    }

    @RequestMapping(value="delete.do")
    public @ResponseBody ResultBean delete(Integer sn){
        try{
            return materialProduceService.delete(sn);
        }catch (Exception e){
            log.error(e.getMessage(), e);
            return new ResultBean(false, "刪除失敗");
        }
    }
}
