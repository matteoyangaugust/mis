package com.matt.security;

import com.matt.bean.CustomUser;
import com.matt.model.System_main_menu;
import com.matt.model.System_sub_menu;
import com.matt.model.System_user;
import com.matt.repository.SystemMainMenuRepository;
import com.matt.repository.SystemSubMenuRepository;
import com.matt.repository.SystemUserRepository;
import com.matt.service.BaseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.GrantedAuthorityImpl;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.List;

@Service
public class UserDetailServiceImpl extends BaseService implements UserDetailsService{
    @Autowired
    private SystemUserRepository systemUserRepository;
    @Autowired
    private SystemMainMenuRepository systemMainMenuRepository;
    @Autowired
    private SystemSubMenuRepository systemSubMenuRepository;
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        System_user user;
        CustomUser currentUser;
        try{
            log.info(12345678);
            List<System_user> users = systemUserRepository.findUsers();
            log.info(users);
            user = systemUserRepository.findUserByUsername(username);
            List<GrantedAuthority> authorities = new ArrayList<GrantedAuthority>();
            authorities.add(new GrantedAuthorityImpl(user.getRole()));
            User springUser = new User(username, user.getPassword(), authorities);
            setMenuOfUser(user);
            currentUser = new CustomUser(springUser, user);
        }catch (Exception e){
            log.error(username + "無此帳號");
            throw new UsernameNotFoundException(username);
        }
        log.info("登入成功");
        return currentUser;
    }

    /**
     * 在UserInfo裡面裝進該User有權限進入的所有功能頁
     * @param user
     * @return
     */
    public System_user setMenuOfUser(System_user user){
        LinkedHashMap<System_main_menu, List<System_sub_menu>> menus = new LinkedHashMap<>();
        List<System_main_menu> mainMenus = systemMainMenuRepository.findMainMenu();
        List<System_sub_menu> subMenus = systemSubMenuRepository.findSubMenus();
        for(System_main_menu mainMenu : mainMenus){
            List<System_sub_menu> authorisedSubMenu = new ArrayList<>();
            for(System_sub_menu subMenu : subMenus){
                if(mainMenu.getSn().equals(subMenu.getMain_menu_sn())){
                    if(subMenu.getRoles().contains(user.getRole())){
                        authorisedSubMenu.add(subMenu);
                    }
                }
            }
            if(authorisedSubMenu.size() != 0){
                menus.put(mainMenu, authorisedSubMenu);
            }
        }
        user.setMenus(menus);
        return user;
    }
}
