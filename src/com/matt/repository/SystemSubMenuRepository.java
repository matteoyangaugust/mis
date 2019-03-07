package com.matt.repository;

import com.matt.model.System_sub_menu;
import org.springframework.stereotype.Repository;

import java.util.LinkedHashSet;
import java.util.List;

@Repository
public interface SystemSubMenuRepository {
    public List<System_sub_menu> findSubMenus();
}
