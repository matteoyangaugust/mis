package com.matt.repository;

import com.matt.model.Process_category;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProcessCategoryRepository {

    public List<Process_category> findProcessCategories();

    public Process_category findProcessCategory(Integer process_category_sn);
}
