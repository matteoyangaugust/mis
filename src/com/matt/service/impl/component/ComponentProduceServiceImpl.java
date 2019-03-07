package com.matt.service.impl.component;

import com.matt.bean.ComponentBean;
import com.matt.bean.ComponentFlowingHistoryBean;
import com.matt.bean.ElementsOfComponentBean;
import com.matt.bean.ResultBean;
import com.matt.model.*;
import com.matt.repository.ComponentFlowingHistoryRepository;
import com.matt.repository.ComponentRepository;
import com.matt.repository.MaterialRepository;
import com.matt.repository.ProcessCategoryRepository;
import com.matt.repository.impl.MaterialOfComponentRepository;
import com.matt.service.BaseService;
import com.matt.service.component.ComponentProduceService;
import com.matt.util.DoubleUtil;
import com.matt.util.ListUtil;
import com.matt.util.MattUtil;
import com.matt.util.MisUtil;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

@Service
public class ComponentProduceServiceImpl extends BaseService implements ComponentProduceService{
    @Autowired
    private MaterialRepository materialRepository;
    @Autowired
    private ProcessCategoryRepository processCategoryRepository;
    @Autowired
    private ComponentFlowingHistoryRepository componentFlowingHistoryRepository;
    @Autowired
    private ComponentRepository componentRepository;
    @Autowired
    private MaterialOfComponentRepository materialOfComponentRepository;

    @Override
    public List<ComponentBean> findComponents() {
        List<Component> components = componentRepository.findComponents();
        List<Material> materials = materialRepository.findMaterials();
        List<Process_category> processCategories = processCategoryRepository.findProcessCategories();
        List<Elements_of_component> usedMaterials = materialOfComponentRepository.findMaterialsOfComponent();
        return MisUtil.getComponentBeans(components, materials, processCategories, usedMaterials);
    }

    @Override
    public List<ComponentFlowingHistoryBean> findFlowingHistory(System_user user) {
        List<Component_flowing_history> flowingHistories = componentFlowingHistoryRepository.findFlowingHistories(user);
        List<Component> components = componentRepository.findComponents();
        List<Material> materials = materialRepository.findMaterials();
        List<Process_category> processCategories = processCategoryRepository.findProcessCategories();
        List<Elements_of_component> materialOfComponents = materialOfComponentRepository.findMaterialsOfComponent();
        return MisUtil.getCombinedComponentFlowingHistories(flowingHistories, components, materials, processCategories, materialOfComponents);
    }

    @Override
    public List<Process_category> findProcessCategories(System_user user) {
        List<Process_category> processCategories = processCategoryRepository.findProcessCategories();
        Iterator<Process_category> itr = processCategories.iterator();
        while(itr.hasNext()){
            Process_category processCategory = itr.next();
            if(!processCategory.getRelation().contains(user.getRole())){
                itr.remove();
            }
        }
        ListUtil.sort(processCategories, "sn");
        return processCategories;
    }

    @Override
    @Transactional(rollbackFor = Exception.class, transactionManager = "mis")
    public ResultBean save(Component_flowing_history flowingHistory) throws Exception {
        List<Component> components = componentRepository.findComponents();
        Component targetComponent = new Component();
        for(Component component : components){
            if(component.getSn().equals(flowingHistory.getComponent_sn())){
                targetComponent = component;
            }
        }
        List<Elements_of_component> materialOfComponents = materialOfComponentRepository.findMaterialsOfComponent(targetComponent.getSn());
        Process_category processCategory = processCategoryRepository.findProcessCategory(flowingHistory.getProcess_category_sn());
        //不是以購買的情況來增加配件
        if(!processCategory.getRelation().contains(Process_category.ROLE_PURCHASE)){
            ArrayList<Integer> materialSns = new ArrayList<>();
            for(Elements_of_component materialOfComponent : materialOfComponents){
                materialSns.add(materialOfComponent.getElement_sn());
            }
            //包好Element的細節
            List<ElementsOfComponentBean> usedMaterials = MisUtil.getMaterialOfComponent(targetComponent, materialOfComponents, materialRepository.findMaterials(), components);
            //先做數量檢查
            for(ElementsOfComponentBean usedMaterial : usedMaterials){
                if(usedMaterial.getElement_type() == 1){
                    if(usedMaterial.getQuantity() < DoubleUtil.mul((double)flowingHistory.getAmount(), usedMaterial.getElement_amount())){
                        return new ResultBean(false, "「" + usedMaterial.getName() + "」is not enough，please buy material first");
                    }
                }else{
                    if(usedMaterial.getQuantity() < DoubleUtil.mul((double)flowingHistory.getAmount(), usedMaterial.getElement_amount())){
                        return new ResultBean(false, "「" + usedMaterial.getName() + "」is not enough，please build component first");
                    }else if(usedMaterial.getQuantity() != usedMaterial.getQuantity().intValue()){
                        return new ResultBean(false, "「" + usedMaterial.getName() + "」stock in't integer，ERROR");
                    }
                }
            }
            //檢查完送進資料庫
            for(ElementsOfComponentBean usedMaterial : usedMaterials) {
                if (usedMaterial.getElement_type() == 1) {
                    usedMaterial.setQuantity(DoubleUtil.sub(usedMaterial.getQuantity(), DoubleUtil.mul((double)flowingHistory.getAmount(), usedMaterial.getElement_amount())));
                    Material material = new Material();
                    BeanUtils.copyProperties(usedMaterial, material);
                    if(materialRepository.update(material) < 1){
                        throw new Exception();
                    }
                } else {
                    usedMaterial.setQuantity(DoubleUtil.sub(usedMaterial.getQuantity(), DoubleUtil.mul((double)flowingHistory.getAmount(), usedMaterial.getElement_amount())));
                    Component component = new Component();
                    component.setSn(usedMaterial.getSn());
                    component.setRemain(usedMaterial.getQuantity().intValue());
                    if(componentRepository.update(component) < 1){
                        throw new Exception();
                    }
                }
            }
        }
        //新增一筆History
        flowingHistory.setUpdate_time(MattUtil.getDateTime());
        flowingHistory.setRemain(targetComponent.getRemain() + flowingHistory.getAmount());
        if(componentFlowingHistoryRepository.insert(flowingHistory) < 1){
            throw new Exception();
        }else{
            targetComponent.setRemain(targetComponent.getRemain() + flowingHistory.getAmount());
            if(componentRepository.update(targetComponent) < 1){
                throw new Exception();
            }else{
                return new ResultBean(true, "儲存成功");
            }
        }
    }

    @Override
    public ResultBean delete(Component_flowing_history flowingHistory, System_user user) throws Exception {
        List<Component_flowing_history> histories = componentFlowingHistoryRepository.findFlowingHistoriesByComponent(flowingHistory.getComponent_sn());
        List<Elements_of_component> materialOfComponents = materialOfComponentRepository.findMaterialsOfComponent();
        List<Material> materials = materialRepository.findMaterials();
        List<Component> components = componentRepository.findComponents();
        Process_category process = processCategoryRepository.findProcessCategory(flowingHistory.getProcess_category_sn());
        Component component = new Component();
        boolean isPurchase = false;
        if(process.getRelation().contains("ROLE_PURCHASE")){
            isPurchase = true;
        }
        for(Component_flowing_history history : histories){
            if(history.getSn().equals(flowingHistory.getSn())){
                component = componentRepository.findComponents(history.getComponent_sn());
                List<ElementsOfComponentBean> usedMaterials = MisUtil.getMaterialOfComponent(component, materialOfComponents, materials, components);
                if(!isPurchase){
                    for(ElementsOfComponentBean usedMaterial : usedMaterials){
                        usedMaterial.setQuantity(DoubleUtil.add(usedMaterial.getQuantity(), DoubleUtil.mul((double)flowingHistory.getAmount(), usedMaterial.getElement_amount())));
                        //Material
                        if(usedMaterial.getElement_type() == 1){
                            Material material = new Material();
                            BeanUtils.copyProperties(usedMaterial, material);
                            if(materialRepository.update(material) < 1){
                                throw new Exception();
                            }
                        }else{
                            //Component
                            Component usedComponent = new Component();
                            usedComponent.setSn(usedMaterial.getSn());
                            usedComponent.setRemain(usedMaterial.getQuantity().intValue());
                            if(componentRepository.update(usedComponent) < 1){
                                throw new Exception();
                            }
                        }
                    }
                }
            }
        }
        Iterator<Component_flowing_history> itr = histories.iterator();
        while(itr.hasNext()){
            Component_flowing_history history = itr.next();
            if(history.getSn() < flowingHistory.getSn()){
                itr.remove();
            }
        }
        for(Component_flowing_history history : histories){
            history.setRemain(history.getRemain() - flowingHistory.getAmount());
            if(componentFlowingHistoryRepository.update(history) < 1){
                throw new Exception();
            }
        }
        ListUtil.sort(histories, "sn");
        component.setRemain(histories.get(histories.size()-1).getRemain());
        if(componentRepository.update(component) < 1){
            throw new Exception();
        }
//        if(!isPurchase){
//            material.setQuantity(DoubleUtil.add(material.getQuantity(), DoubleUtil.mul((double)flowingHistory.getAmount(), component.getMaterial_amount())));
//            if(materialRepository.update(material) < 1){
//                throw new Exception();
//            }
//        }
        if(componentFlowingHistoryRepository.delete(flowingHistory.getSn()) < 1){
            throw new Exception();
        }
        return new ResultBean(true, "Delete Successfully");
    }
}
