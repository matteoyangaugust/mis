package com.matt.repository.impl;

import com.matt.model.Elements_of_component;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MaterialOfComponentRepository {
    public Integer insert(Elements_of_component elements_of_component);
    public Integer delete(Integer component_sn);
    public Integer delete(List<Integer> component_sns);
    public List<Elements_of_component> findMaterialsOfComponent();
    public List<Elements_of_component> findMaterialsOfComponent(Integer componentSn);
    public Integer delete(Integer componentSn, Integer componentType);
}
