package com.matt.repository;

import com.matt.model.System_user;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Set;

@Repository
public interface SystemUserRepository {
    public Integer insertSystemUser(System_user user);

    public List<System_user> findUsers();

    public System_user findUserByUsername(String username);

    public List<System_user> findUserByRole(String role);

    public List<System_user> findUserBySn(Set<Integer> sn);

    public Integer update(System_user user);

    public Integer delete(Integer sn);
}
