package com.matt.bean;

import com.matt.model.System_main_menu;
import com.matt.model.System_sub_menu;
import net.sf.json.JSONObject;

public class MenuRecordBean {
    private boolean init;
    private System_main_menu mainMenu;
    private System_sub_menu subMenu;

    public boolean isInit() {
        return init;
    }

    public void setInit(boolean init) {
        this.init = init;
    }

    public System_main_menu getMainMenu() {
        return mainMenu;
    }

    public void setMainMenu(System_main_menu mainMenu) {
        this.mainMenu = mainMenu;
    }

    public System_sub_menu getSubMenu() {
        return subMenu;
    }

    public void setSubMenu(System_sub_menu subMenu) {
        this.subMenu = subMenu;
    }

    @Override
    public String toString() {
        JSONObject jsonObj = JSONObject.fromObject(this);
        return jsonObj.toString();
    }
}
