package com.matt.service.component;

import com.matt.bean.ComponentBean;
import com.matt.bean.ElementsOfComponentBean;
import com.matt.bean.ResultBean;
import com.matt.model.Component;
import com.matt.model.Material;
import com.matt.model.Process_category;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface ComponentManageService {
    public List<ComponentBean> findComponents();

    public List<Material> findMaterials();

    public List<Process_category> findProcessCategories();

    public ResultBean update(Component component) throws Exception;

    public ResultBean delete(Integer sn) throws Exception;

    public ResultBean changeMaterials(Integer component_sn, List<ElementsOfComponentBean> materials) throws Exception;

    public ResultBean changeUsedComponents(Integer componentSn, List<ComponentBean> components) throws Exception;
}
