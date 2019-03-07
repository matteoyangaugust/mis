package com.matt.service;

import com.matt.model.System_user;
import com.matt.repository.ProcessCategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface MisService {
    public List<Integer> getProcessCategorySnByUser(System_user user);
}
