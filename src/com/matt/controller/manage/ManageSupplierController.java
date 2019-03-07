package com.matt.controller.manage;

import com.matt.bean.MenuRecordBean;
import com.matt.bean.ResultBean;
import com.matt.controller.BaseController;
import com.matt.model.Material_supplier;
import com.matt.service.manage.ManageSupplierService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.SessionAttribute;

@Controller
@RequestMapping(value="/manage/supplier/")
public class ManageSupplierController extends BaseController{

    private final String CREATE_VIEW = "manage/supplier/create";
    private final String MANAGE_VIEW = "manage/supplier/manage";
    @Autowired
    private ManageSupplierService manageSupplierService;

    @RequestMapping(value = "toCreate.do")
    public String toCreate(@SessionAttribute("menuRecord") MenuRecordBean menuRecord, Model model){
        return super.toView(CREATE_VIEW, model);
    }

    @RequestMapping(value="creating.do")
    public @ResponseBody ResultBean creating(Material_supplier supplier){
        try{
            return manageSupplierService.insertSupplier(supplier);
        }catch (Exception e){
            log.error(e.getMessage(), e);
            return new ResultBean(false, "Save Failed");
        }
    }

    @RequestMapping(value="toManage.do")
    public String toManage(@SessionAttribute("menuRecord") MenuRecordBean menuRecord, Model model){
        model.addAttribute("suppliers", manageSupplierService.findSuppliers());
        return toView(MANAGE_VIEW, model);
    }

    @RequestMapping(value="delete.do")
    public @ResponseBody ResultBean modify(Integer sn){
        try{
            return manageSupplierService.deleteSupplierBySn(sn);
        }catch(Exception e){
            log.error(e.getMessage(), e);
            return new ResultBean(false, "Modify Failed");
        }
    }

    @RequestMapping(value="save.do")
    public @ResponseBody ResultBean save(Material_supplier supplier){
        try{
            return manageSupplierService.update(supplier);
        }catch (Exception e){
            log.error(e.getMessage(), e);
            return new ResultBean(false, "Save Failed");
        }
    }
}
