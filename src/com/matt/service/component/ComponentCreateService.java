package com.matt.service.component;

import com.matt.bean.ComponentBean;
import com.matt.bean.MaterialBean;
import com.matt.bean.ResultBean;
import com.matt.model.Component;
import com.matt.model.Material;
import com.matt.model.Process_category;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface ComponentCreateService {
    public List<Process_category> findProcessCategories();

    public List<Material> findMaterials();

    public List<Component> findComponents();

    public ResultBean insertNewComponent(Component component, List<MaterialBean> materials, List<ComponentBean> components) throws Exception;
}
