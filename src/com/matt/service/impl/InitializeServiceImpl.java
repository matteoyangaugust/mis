package com.matt.service.impl;

import com.matt.bean.MenuRecordBean;
import com.matt.model.System_main_menu;
import com.matt.model.System_sub_menu;
import com.matt.model.System_user;
import com.matt.service.BaseService;
import com.matt.service.InitializeService;
import org.springframework.stereotype.Service;

@Service
public class InitializeServiceImpl extends BaseService implements InitializeService{

    @Override
    public MenuRecordBean getDefaultMenu(System_user user) throws Exception {
        MenuRecordBean menuRecordBean = new MenuRecordBean();
        for(System_main_menu mainMenu : user.getMenus().keySet()){
            if(mainMenu.getIs_default().equals(System_main_menu.DEFAULT)){
                menuRecordBean.setMainMenu(mainMenu);
                for(System_sub_menu subMenu : user.getMenus().get(mainMenu)){
                    if(subMenu.getIs_default().equals(System_sub_menu.DEFAULT)){
                        menuRecordBean.setSubMenu(subMenu);
                    }
                }
            }
        }
        if(menuRecordBean.getMainMenu() == null || menuRecordBean.getSubMenu() == null){
            for(System_main_menu mainMenu : user.getMenus().keySet()){
                menuRecordBean.setMainMenu(mainMenu);
                for(System_sub_menu subMenu : user.getMenus().get(mainMenu)){
                    if(subMenu.getIs_default().equals(System_sub_menu.DEFAULT)){
                        menuRecordBean.setSubMenu(subMenu);
                    }
                }
                break;
            }
        }
        return menuRecordBean;
    }
}
