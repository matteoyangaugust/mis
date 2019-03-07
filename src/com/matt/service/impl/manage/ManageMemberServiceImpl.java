package com.matt.service.impl.manage;

import com.matt.bean.ResultBean;
import com.matt.model.System_user;
import com.matt.repository.SystemUserRepository;
import com.matt.service.BaseService;
import com.matt.service.manage.ManageMemberService;
import com.matt.util.ListUtil;
import com.matt.util.MattUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;

@Service
public class ManageMemberServiceImpl extends BaseService implements ManageMemberService{
    @Autowired
    private SystemUserRepository systemUserRepository;
    @Override
    public ResultBean insertUser(System_user user) throws Exception {
        System_user existedUser = systemUserRepository.findUserByUsername(user.getUsername());
        if(existedUser != null){
            return new ResultBean(false, "Duplicate Username");
        }else{
            if(systemUserRepository.insertSystemUser(user) < 1){
                throw new Exception();
            }else {
                return new ResultBean(true, "Register Successfully");
            }
        }
    }

    @Override
    public List<System_user> findAllUsers() {
        List<System_user> users = systemUserRepository.findUsers();
        for(System_user user : users){
            user.setPassword("");
        }
        ListUtil.sort(users, "role", "name");
        return users;
    }

    @Override
    public ResultBean updateUser(System_user user) throws Exception {
        if(user.getUsername() != null){
            List<System_user> users = systemUserRepository.findUsers();
            for(System_user existedUser : users){
                if(existedUser.getUsername().equals(user.getUsername())){
                    if(!existedUser.getSn().equals(user.getSn())){
                        return new ResultBean(false, "Duplicate Username");
                    }
                }
            }
        }
        if(systemUserRepository.update(user) < 1){
            throw new Exception();
        }else{
            return new ResultBean(true, "Modify Successfully");
        }
    }

    @Override
    public ResultBean deleteUser(Integer sn) throws Exception {
        if(systemUserRepository.delete(sn) < 1){
            throw new Exception();
        }else{
            return new ResultBean(true, "Delete Successfully");
        }
    }
}
