package com.matt.repository;

import com.matt.model.System_main_menu;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SystemMainMenuRepository {
    public List<System_main_menu> findMainMenu();
}
