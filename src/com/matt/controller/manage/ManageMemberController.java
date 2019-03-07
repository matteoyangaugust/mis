package com.matt.controller.manage;

import com.matt.bean.MenuRecordBean;
import com.matt.bean.ResultBean;
import com.matt.controller.BaseController;
import com.matt.model.System_user;
import com.matt.service.manage.ManageMemberService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.SessionAttribute;

@Controller
@RequestMapping(value="/manage/member/")
public class ManageMemberController extends BaseController{
    private final String CREATE_MEMBER_VIEW = "manage/member/create";
    private final String MANGE_MEMBER_VIEW = "manage/member/manage";
    @Autowired
    private ManageMemberService manageMemberService;
    @RequestMapping(value="toCreate.do")
    public String toCreate(@SessionAttribute("menuRecord")MenuRecordBean menuRecordBean, Model model){
        return toView(CREATE_MEMBER_VIEW, model);
    }

    @RequestMapping(value="creating.do")
    public @ResponseBody ResultBean creating(System_user newUser){
        try{
            return manageMemberService.insertUser(newUser);
        }catch (Exception e){
            log.error(e.getMessage(), e);
            return new ResultBean(false, "Save Failed");
        }
    }

    @RequestMapping(value="toManage.do")
    public String toManage(Model model){
        model.addAttribute("users", manageMemberService.findAllUsers());
        return super.toView(MANGE_MEMBER_VIEW, model);
    }

    @RequestMapping(value="modifyUser.do")
    public @ResponseBody ResultBean modifyUser(System_user user){
        try{
            return manageMemberService.updateUser(user);
        }catch (Exception e){
            log.error(e.getMessage(), e);
            return new ResultBean(false, "Modify Failed");
        }
    }

    @RequestMapping(value="delete.do")
    public @ResponseBody ResultBean delete(Integer sn){
        try{
            return manageMemberService.deleteUser(sn);
        }catch (Exception e){
            log.error(e.getMessage(), e);
            return new ResultBean(false, "Delete Failed");
        }
    }

    @RequestMapping(value="changePassword.do")
    public @ResponseBody ResultBean changePassword(System_user user){
        try{
            return manageMemberService.updateUser(user);
        }catch (Exception e){
            log.error(e.getMessage(), e);
            return new ResultBean(false, "Update Password Failed");
        }
    }
}
