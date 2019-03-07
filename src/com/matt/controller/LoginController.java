package com.matt.controller;

import com.matt.bean.MenuRecordBean;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.SessionAttributes;

import javax.servlet.http.HttpServletRequest;

@Controller
@SessionAttributes("menuRecord")
@RequestMapping(value="/login/")
public class LoginController extends BaseController {

	@ModelAttribute("menuRecord")
	public MenuRecordBean menuRecord(){
		return new MenuRecordBean();
	}
	@RequestMapping(value="init.do")
	public String login(@ModelAttribute("menuRecord") MenuRecordBean menuRecord, HttpServletRequest request){
		menuRecord.setInit(false);
		log.info("登入畫面 ");
		log.info("URL = " + request.getRequestURL());
		return "login/init";
	}

	@RequestMapping(value="loginFailed.do")
	public String loginFailed(Model model){
		log.info("登入失敗");
		model.addAttribute("error", "true");
		return "login/init";
	}

	@RequestMapping(value="logout.do")
	public String logout(){
		log.info("登出");
		return "redirect:/login/init.do";
	}

	@RequestMapping(value = "accessDenied.do")
	public String accessDenied(){
		log.info("無權限");
		return "login/denied";
	}
}
