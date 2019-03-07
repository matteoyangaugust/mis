package com.matt.repository;

import com.matt.model.Component;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ComponentRepository {
    public Integer insert(Component component);

    public List<Component> findComponents();

    public Integer update(Component component);

    public Integer delete(Integer sn);

    public Component findComponents(Integer component_sn);
}
