package com.matt.controller.material;

import com.matt.bean.MenuRecordBean;
import com.matt.bean.ResultBean;
import com.matt.controller.BaseController;
import com.matt.model.Material;
import com.matt.service.material.MaterialCreateService;
import com.matt.util.MattUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.SessionAttribute;

import javax.servlet.http.HttpServletRequest;

@Controller
@RequestMapping(value="/material/create/")
public class MaterialCreateController extends BaseController{

    private final String CREATE_VIEW = "material/create";

    @Autowired
    private MaterialCreateService materialCreateService;

    @RequestMapping(value="toCreate.do")
    public String toCreate(@SessionAttribute("menuRecord") MenuRecordBean menuRecord, Model model, HttpServletRequest request){
        model.addAttribute("suppliers", materialCreateService.findMaterialSuppliers());
        return super.toView(CREATE_VIEW, model);
    }

    @RequestMapping(value="creating.do")
    public @ResponseBody ResultBean creating(Material material){
        try{
            material.setLast_update_time(MattUtil.getDateTime());
            return materialCreateService.insertMaterial(material);
        }catch (Exception e){
            log.error(e.getMessage(), e);
            return new ResultBean(false, "儲存失敗");
        }
    }
}
