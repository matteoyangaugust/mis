package com.matt.controller.component;

import com.google.gson.Gson;
import com.matt.bean.ComponentBean;
import com.matt.bean.ElementsOfComponentBean;
import com.matt.bean.ResultBean;
import com.matt.controller.BaseController;
import com.matt.model.Component;
import com.matt.service.component.ComponentManageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.Arrays;
import java.util.List;

@Controller
@RequestMapping(value="/component/manage/")
public class ComponentManageController extends BaseController{

    private final String MANAGE_VIEW = "component/manage";
    @Autowired
    private ComponentManageService componentManageService;

    @RequestMapping(value="toManage.do")
    public String toManage(Model model){
        model.addAttribute("components", componentManageService.findComponents());
        model.addAttribute("materials", componentManageService.findMaterials());
        model.addAttribute("processCategories", componentManageService.findProcessCategories());
        return super.toView(MANAGE_VIEW, model);
    }

    @RequestMapping(value="modifying.do")
    public @ResponseBody ResultBean modifying(Component component){
        try{
            return componentManageService.update(component);
        }catch (Exception e){
            log.error(e.getMessage(), e);
            return new ResultBean(false, "Modify Failed");
        }
    }

    @RequestMapping(value="changeMaterials.do")
    public @ResponseBody ResultBean changeMaterials(Integer component_sn, String selectedMaterialsJSON) {
        try{
            Gson gson = new Gson();
            List<ElementsOfComponentBean> materials = Arrays.asList(gson.fromJson(selectedMaterialsJSON, ElementsOfComponentBean[].class));
            return componentManageService.changeMaterials(component_sn, materials);
        }catch (Exception e){
            log.error(e.getMessage(), e);
            return new ResultBean(false, "Save Failed");
        }
    }

    @RequestMapping(value="changeUsedComponents.do")
    public @ResponseBody ResultBean changeUsedComponents(Integer component_sn, String selectedComponentsJSON){
        try{
            Gson gson = new Gson();
            List<ComponentBean> components = Arrays.asList(gson.fromJson(selectedComponentsJSON, ComponentBean[].class));
            return componentManageService.changeUsedComponents(component_sn, components);
        }catch (Exception e){
            log.error(e.getMessage(), e);
            return new ResultBean(false, "Save Failed");
        }
    }

    @RequestMapping(value="delete.do")
    public @ResponseBody ResultBean delete(Integer sn){
        try{
            return componentManageService.delete(sn);
        }catch (Exception e){
            log.error(e.getMessage(), e);
            return new ResultBean(false, "Delete Failed");
        }
    }
}
