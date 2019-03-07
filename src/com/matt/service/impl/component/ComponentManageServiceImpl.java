package com.matt.service.impl.component;

import com.matt.bean.ComponentBean;
import com.matt.bean.ElementsOfComponentBean;
import com.matt.bean.ResultBean;
import com.matt.model.Component;
import com.matt.model.Elements_of_component;
import com.matt.model.Material;
import com.matt.model.Process_category;
import com.matt.repository.ComponentRepository;
import com.matt.repository.MaterialRepository;
import com.matt.repository.ProcessCategoryRepository;
import com.matt.repository.impl.MaterialOfComponentRepository;
import com.matt.service.BaseService;
import com.matt.service.component.ComponentManageService;
import com.matt.util.ListUtil;
import com.matt.util.MisUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Iterator;
import java.util.List;

@Service
public class ComponentManageServiceImpl extends BaseService implements ComponentManageService{
    @Autowired
    private ComponentRepository componentRepository;
    @Autowired
    private MaterialRepository materialRepository;
    @Autowired
    private ProcessCategoryRepository processCategoryRepository;
    @Autowired
    private MaterialOfComponentRepository materialOfComponentRepository;

    @Override
    public List<ComponentBean> findComponents() {
        List<Component> components = componentRepository.findComponents();
        Iterator<Component> itr = components.iterator();
        while (itr.hasNext()){
            Component component = itr.next();
            if(component.getActive() == 0){
                itr.remove();
            }
        }
        List<Material> materials = materialRepository.findMaterials();
        List<Process_category> processCategories = processCategoryRepository.findProcessCategories();
        List<Elements_of_component> usedMaterials = materialOfComponentRepository.findMaterialsOfComponent();
        return MisUtil.getComponentBeans(components, materials, processCategories, usedMaterials);
    }

    @Override
    public List<Material> findMaterials() {
        List<Material> materials = materialRepository.findMaterials();
        ListUtil.sort(materials, "name");
        return materials;
    }

    @Override
    public List<Process_category> findProcessCategories() {
        List<Process_category> processCategories = processCategoryRepository.findProcessCategories();
        ListUtil.sort(processCategories, "sn");
        return processCategories;
    }

    @Override
    public ResultBean update(Component component) throws Exception {
        List<Component> components = componentRepository.findComponents();
        for(Component existedComponent : components){
            if(component.getName().equals(existedComponent.getName())){
                if(!component.getSn().equals(existedComponent.getSn())){
                    return new ResultBean(false, "Duplicate Component");
                }
            }
        }
        List<Process_category> processes = findProcessCategories();
        MisUtil.fixComponentValueByPurchase(component, processes);
        if(componentRepository.update(component) < 1){
            throw new Exception();
        }else{
            return new ResultBean(true, "Update Successfully");
        }
    }

    @Override
    public ResultBean delete(Integer sn) throws Exception {
        Component component = componentRepository.findComponents(sn);
        component.setActive(0);
        if(componentRepository.update(component) < 1){
            throw new Exception();
        }else{
            return new ResultBean(true, "Delete Successfully");
        }
    }

    @Override
    @Transactional(rollbackFor = Exception.class, transactionManager = "mis")
    public ResultBean changeMaterials(Integer component_sn, List<ElementsOfComponentBean> materials) throws Exception {
        if(materialOfComponentRepository.delete(component_sn, Elements_of_component.MATERIAL_TYPE) < 1){
            throw new Exception();
        }
        for(ElementsOfComponentBean material : materials){
            Elements_of_component materialOfComponent = new Elements_of_component();
            materialOfComponent.setComponent_sn(component_sn);
            materialOfComponent.setElement_sn(material.getSn());
            materialOfComponent.setElement_amount(material.getElement_amount());
            materialOfComponent.setElements_type(1);
            if(materialOfComponentRepository.insert(materialOfComponent) < 1){
                throw new Exception();
            }
        }
        return new ResultBean(true, "Update Successfully");
    }

    @Override
    @Transactional(rollbackFor = Exception.class, transactionManager = "mis")
    public ResultBean changeUsedComponents(Integer componentSn, List<ComponentBean> components) throws Exception {
        if(materialOfComponentRepository.delete(componentSn, Elements_of_component.COMPONENT_TYPE) < 1){
            throw new Exception();
        }
        for(ComponentBean component : components){
            Elements_of_component elements_of_component = new Elements_of_component();
            elements_of_component.setComponent_sn(componentSn);
            elements_of_component.setElement_sn(component.getSn());
            elements_of_component.setElement_amount((double)component.getElement_amount());
            elements_of_component.setElements_type(2);
            if(materialOfComponentRepository.insert(elements_of_component) < 1){
                throw new Exception();
            }
        }
        return new ResultBean(true, "Update Successfully");
    }
}
