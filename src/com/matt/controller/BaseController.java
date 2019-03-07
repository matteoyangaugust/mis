package com.matt.controller;

import com.matt.bean.CustomUser;
import com.matt.bean.MenuRecordBean;
import com.matt.model.System_main_menu;
import com.matt.model.System_sub_menu;
import com.matt.model.System_user;
import org.apache.commons.lang.StringEscapeUtils;
import org.apache.log4j.Logger;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;

import javax.servlet.http.HttpServletRequest;
import java.util.Enumeration;

public class BaseController {
	protected Logger log = Logger.getLogger(BaseController.class);
	@ModelAttribute("menuRecord")
	public MenuRecordBean getMenuRecord(HttpServletRequest request) {
		if (getString(request, "mainMenuSn") != null && getString(request, "subMenuSn") != null) {
			Integer mainMenuSn = Integer.parseInt(getString(request, "mainMenuSn"));
			Integer subMenuSn = Integer.parseInt(getString(request, "subMenuSn"));
			System_user user = getUser();
			MenuRecordBean menuRecordBean = new MenuRecordBean();
			menuRecordBean.setInit(true);
			for (System_main_menu mainMenu : user.getMenus().keySet()) {
				if (mainMenuSn.equals(mainMenu.getSn())) {
					menuRecordBean.setMainMenu(mainMenu);
					for (System_sub_menu subMenu : user.getMenus().get(mainMenu)) {
						if (subMenuSn.equals(subMenu.getSn())) {
							menuRecordBean.setSubMenu(subMenu);
							break;
						}
					}
					break;
				}
			}
			return menuRecordBean;
		}else{
			return new MenuRecordBean();
		}
	}

	protected String toView(String viewPath, Model model){
		model.addAttribute("mainMenus", getUser().getMenus());
		return viewPath;
	}
	/**
	 * display request all parameter
	 */
	public void displayParameters(HttpServletRequest request) {
		Enumeration<?> names = request.getParameterNames();
		while(names.hasMoreElements()){
			String name = (String)names.nextElement();

			if(request.getParameterValues(name).length < 2){
				log.info("name : " + name + " = " + request.getParameter(name));
			}else{
				String[] values = request.getParameterValues(name);
				String valueStr = "name : ";
				for(String value : values){
					valueStr += value + ",";
				}
				log.info("name : " + name + " = " + valueStr.substring(0, valueStr.length() - 1));
			}
		}
	}

	protected System_user getUser(){
		UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		if (userDetails instanceof CustomUser) {
			CustomUser customUserDetails = (CustomUser) userDetails;
			return customUserDetails.getUserInfo();
		}
		return null;
	}

	public String getString(HttpServletRequest request, String name){
		return request.getParameter(name)==null?null: StringEscapeUtils.escapeHtml(request.getParameter(name));
	}
}
