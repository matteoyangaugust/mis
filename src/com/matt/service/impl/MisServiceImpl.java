package com.matt.service.impl;

import com.matt.model.Process_category;
import com.matt.model.System_user;
import com.matt.repository.ProcessCategoryRepository;
import com.matt.service.BaseService;
import com.matt.service.MisService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

@Service
public class MisServiceImpl extends BaseService implements MisService {

    @Autowired
    private ProcessCategoryRepository processCategoryRepository;

    @Override
    public List<Integer> getProcessCategorySnByUser(System_user user) {
        List<Process_category> processCategories = processCategoryRepository.findProcessCategories();
        List<Integer> processCategorySnList = new ArrayList<>();
        Iterator<Process_category> itr = processCategories.iterator();
        while(itr.hasNext()){
            Process_category processCategory = itr.next();
            if(processCategory.getRelation().contains(user.getRole())){
                processCategorySnList.add(processCategory.getSn());
            }
        }
        return processCategorySnList;
    }
}
