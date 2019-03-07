package com.matt.controller.material;

import com.matt.bean.ResultBean;
import com.matt.controller.BaseController;
import com.matt.model.Material;
import com.matt.service.material.MaterialManageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping(value = "/material/manage/")
public class MaterialManageController extends BaseController {

    private static final String SHOW_MATERIALS_VIEWS = "material/toManage";
    @Autowired
    private MaterialManageService materialManageService;
    @RequestMapping(value="toManage.do")
    public String toManage(Model model){
        model.addAttribute("materials", materialManageService.findMaterials());
        model.addAttribute("suppliers", materialManageService.findSuppliers());
        return super.toView(SHOW_MATERIALS_VIEWS, model);
    }

    @RequestMapping(value="modify.do")
    public @ResponseBody ResultBean modify(Material material){
        try{
            return materialManageService.updateMaterial(material);
        }catch (Exception e){
            log.error(e.getMessage(), e);
            return new ResultBean(false, "儲存失敗");
        }
    }

    @RequestMapping(value="delete.do")
    public @ResponseBody ResultBean delete(Integer sn){
        try{
            return materialManageService.deleteByMaterialSn(sn);
        }catch (Exception e){
            log.error(e.getMessage(), e);
            return new ResultBean(false, "刪除失敗");
        }
    }
}
