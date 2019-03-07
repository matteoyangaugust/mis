package com.matt.controller;

import com.matt.bean.MenuRecordBean;
import com.matt.bean.ResultBean;
import com.matt.service.InitializeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.HashMap;
import java.util.Map;

@Controller
@RequestMapping(value="/initialize/")
public class InitializeController extends BaseController{
    @Autowired
    private InitializeService initializeService;
    private final String LOADING_VIEW = "initialize/loading";
    @RequestMapping(value = "loading.do")
    public String loading(Model model){
        return toView(LOADING_VIEW, model);
    }

    @RequestMapping(value="setupMenuSn.do")
    public @ResponseBody ResultBean setupMenuSn(){
        try{
            MenuRecordBean menuRecordBean = initializeService.getDefaultMenu(super.getUser());
            Map<String, Object> currentMenuMap = new HashMap<>();
            currentMenuMap.put("menuRecord", menuRecordBean);
            return new ResultBean(true, "", currentMenuMap);
        }catch (Exception e){
            log.error(e.getMessage(), e);
            return new ResultBean(false, "發生錯誤，請重開系統");
        }
    }
}
