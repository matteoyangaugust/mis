package com.matt.service.manage;

import com.matt.bean.ResultBean;
import com.matt.model.System_user;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface ManageMemberService {
    public ResultBean insertUser(System_user user) throws Exception;

    public List<System_user> findAllUsers();

    public ResultBean updateUser(System_user user) throws Exception;

    public ResultBean deleteUser(Integer sn) throws Exception;
}
