package com.matt.service.impl.component;

import com.matt.bean.ComponentBean;
import com.matt.bean.MaterialBean;
import com.matt.bean.ResultBean;
import com.matt.model.Component;
import com.matt.model.Material;
import com.matt.model.Elements_of_component;
import com.matt.model.Process_category;
import com.matt.repository.ComponentRepository;
import com.matt.repository.MaterialRepository;
import com.matt.repository.ProcessCategoryRepository;
import com.matt.repository.impl.MaterialOfComponentRepository;
import com.matt.service.BaseService;
import com.matt.service.component.ComponentCreateService;
import com.matt.util.ListUtil;
import com.matt.util.MisUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class ComponentCreateServiceImpl extends BaseService implements ComponentCreateService{
    @Autowired
    private ProcessCategoryRepository processCategoryRepository;
    @Autowired
    private MaterialRepository materialRepository;
    @Autowired
    private ComponentRepository componentRepository;
    @Autowired
    private MaterialOfComponentRepository materialOfComponentRepository;
    @Override
    public List<Process_category> findProcessCategories() {
        List<Process_category> processCategories = processCategoryRepository.findProcessCategories();
        ListUtil.sort(processCategories, "sn");
        return processCategories;
    }

    @Override
    public List<Material> findMaterials() {
        List<Material> materials = materialRepository.findMaterials();
        ListUtil.sort(materials, "identifier");
        return materials;
    }

    @Override
    public List<Component> findComponents() {
        List<Component> components = componentRepository.findComponents();
        ListUtil.sort(components, "identifier");
        return components;
    }

    @Override
    @Transactional(rollbackFor = Exception.class, transactionManager = "mis")
    public ResultBean insertNewComponent(Component component, List<MaterialBean> usedMaterials, List<ComponentBean> usedComponents) throws Exception {
        String errStr = "";
        boolean isValid = true;
        List<Component> components = findComponents();
        for(Component existedComponent : components){
            if(component.getName().equals(existedComponent.getName())){
                isValid = false;
                errStr += "Duplicate Component\n";
            }
        }
        List<Process_category> processes = findProcessCategories();
        MisUtil.fixComponentValueByPurchase(component, processes);
        if(isValid){
            component.setRemain(0);
            int componentSn = componentRepository.insert(component);
            if(componentSn < 1){
                throw new Exception();
            }else{
                for(MaterialBean materialBean : usedMaterials){
                    Elements_of_component elementsOfComponent = new Elements_of_component();
                    elementsOfComponent.setElement_sn(materialBean.getSn());
                    elementsOfComponent.setComponent_sn(componentSn);
                    elementsOfComponent.setElement_amount(materialBean.getQuantity());
                    elementsOfComponent.setElements_type(1);
                    if(materialOfComponentRepository.insert(elementsOfComponent) < 1){
                        throw new Exception();
                    }
                }
                for(ComponentBean componentBean : usedComponents){
                    Elements_of_component elementsOfComponent = new Elements_of_component();
                    elementsOfComponent.setElement_sn(componentBean.getSn());
                    elementsOfComponent.setComponent_sn(componentSn);
                    elementsOfComponent.setElement_amount((double)componentBean.getComponentQuantity());
                    elementsOfComponent.setElements_type(2);
                    if(materialOfComponentRepository.insert(elementsOfComponent) < 1){
                        throw new Exception();
                    }
                }
                return new ResultBean(true, "Save Successfully");
            }
        }else{
            return new ResultBean(false, errStr);
        }
    }
}
