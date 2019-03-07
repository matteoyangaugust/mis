package com.matt.controller.component;

import com.google.gson.Gson;
import com.matt.bean.ComponentBean;
import com.matt.bean.MaterialBean;
import com.matt.bean.MenuRecordBean;
import com.matt.bean.ResultBean;
import com.matt.controller.BaseController;
import com.matt.model.Component;
import com.matt.service.component.ComponentCreateService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.SessionAttribute;

import java.util.Arrays;
import java.util.List;

@Controller
@RequestMapping(value="/component/create/")
public class ComponentCreateController extends BaseController{

    private final String CREATE_VIEW = "component/create";
    @Autowired
    private ComponentCreateService componentCreateService;

    @RequestMapping(value="toCreate.do")
    public String toCreate(@SessionAttribute("menuRecord") MenuRecordBean menuRecord, Model model){
        model.addAttribute("processCategories", componentCreateService.findProcessCategories());
        model.addAttribute("materials", componentCreateService.findMaterials());
        model.addAttribute("components", componentCreateService.findComponents());
        return toView(CREATE_VIEW, model);
    }

    @RequestMapping(value="creating.do")
    public @ResponseBody ResultBean creating(Component component, String selectedMaterialsJSON, String selectedComponentsJSON){
        try{
            Gson gson = new Gson();
            List<MaterialBean> materials = Arrays.asList(gson.fromJson(selectedMaterialsJSON, MaterialBean[].class));
            List<ComponentBean> components = Arrays.asList(gson.fromJson(selectedComponentsJSON, ComponentBean[].class));
            return componentCreateService.insertNewComponent(component, materials, components);
        }catch (Exception e){
            log.error(e.getMessage(), e);
            return new ResultBean(false, "Save Failed");
        }
    }
}
