package com.matt.service;

import com.matt.bean.MenuRecordBean;
import com.matt.model.System_user;
import org.springframework.stereotype.Service;

@Service
public interface InitializeService {
    public MenuRecordBean getDefaultMenu(System_user user) throws Exception;
}
