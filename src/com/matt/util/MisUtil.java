package com.matt.util;

import com.matt.bean.*;
import com.matt.model.*;
import org.springframework.beans.BeanUtils;

import java.util.ArrayList;
import java.util.List;

public class MisUtil {
    public static List<ComponentBean> getComponentBeans(List<Component> components, List<Material> materials, List<Process_category> processCategories,
                                                        List<Elements_of_component> usedMaterials){
        List<ComponentBean> componentInfoBeans = new ArrayList<>();
        for(Component component : components){
            ComponentBean componentInfoBean = new ComponentBean();
            BeanUtils.copyProperties(component, componentInfoBean);
            componentInfoBean.setTotalTimeBean(MattUtil.timeConvert(componentInfoBean.getProcess_duration()));
            componentInfoBeans.add(componentInfoBean);
            componentInfoBean.setMaterialActive(true);
            for(Elements_of_component materialOfComponent : usedMaterials){
                componentInfoBean.setPurchase(true);
                if(materialOfComponent.getElements_type() == 1){
                    componentInfoBean.setPurchase(false);
                    for(Material material : materials){
                        if(materialOfComponent.getElement_sn().equals(material.getSn()) && component.getSn().equals(materialOfComponent.getComponent_sn())){
                            if(material.getActive() == 0){
                                componentInfoBean.setMaterialActive(false);
                            }
                            ElementsOfComponentBean elementsOfComponentBean = new ElementsOfComponentBean();
                            BeanUtils.copyProperties(material, elementsOfComponentBean);
                            elementsOfComponentBean.setElement_amount(materialOfComponent.getElement_amount());
                            elementsOfComponentBean.setElement_type(1);
                            componentInfoBean.getMaterials().add(elementsOfComponentBean);
                        }
                    }
                }else{
                    for(Component usedComponent : components){
                        componentInfoBean.setPurchase(false);
                        if(materialOfComponent.getElement_sn().equals(usedComponent.getSn()) && component.getSn().equals(materialOfComponent.getComponent_sn())){
                            if(usedComponent.getActive() == 0){
                                componentInfoBean.setMaterialActive(false);
                            }
                            ElementsOfComponentBean elementsOfComponentBean = new ElementsOfComponentBean();
                            BeanUtils.copyProperties(usedComponent, elementsOfComponentBean);
                            elementsOfComponentBean.setElement_amount(materialOfComponent.getElement_amount());
                            elementsOfComponentBean.setElement_type(2);
                            componentInfoBean.getComponents().add(elementsOfComponentBean);
                        }
                    }
                }
            }

            for(Process_category processCategory : processCategories){
                if(component.getProcess_category_sn().equals(processCategory.getSn())){
                    componentInfoBean.setProcessCategoryName(processCategory.getName());
                    break;
                }
            }
        }
        ListUtil.sort(componentInfoBeans, "identifier");
        return componentInfoBeans;
    }

    public static List<ProductFlowingHistoryBean> getCombinedProductFlowingHistories(List<Component> components,
                                                                                     List<Component_of_product> componentOfProducts,
                                                                                     List<Product> products,
                                                                                     List<Product_flowing_history> histories){
        List<ProductFlowingHistoryBean> historyDataList = new ArrayList<>();
        ListUtil.reverseSort(histories, "sn");
        ListUtil.sort(products, "identifier");
        for(Product_flowing_history history : histories){
            ProductFlowingHistoryBean historyData = new ProductFlowingHistoryBean();
            BeanUtils.copyProperties(history, historyData);
            historyData.setUpdateTimeWithUnit(MattUtil.getDateTimeWithUnit(historyData.getUpdate_time()));
            for(Product product : products){
                if(historyData.getProduct_sn().equals(product.getSn())){
                    historyData.setProduct(product);
                    historyDataList.add(historyData);
                    break;
                }
            }
        }
        return historyDataList;
    }

    public static List<ComponentFlowingHistoryBean> getCombinedComponentFlowingHistories(List<Component_flowing_history> flowingHistories,
                                                                                         List<Component> components, List<Material> materials,
                                                                                         List<Process_category> processCategories, List<Elements_of_component> elements_of_components){
        List<ComponentFlowingHistoryBean> historyDataList = new ArrayList<>();
        for(Component_flowing_history flowingHistory : flowingHistories){
            ComponentFlowingHistoryBean historyData = new ComponentFlowingHistoryBean();
            BeanUtils.copyProperties(flowingHistory, historyData);
            historyData.setTimeTookInHour(MattUtil.getHourMinuteWithUnit(historyData.getTotal_duration()));
            historyData.setUpdateTimeWithUnit(MattUtil.getDateTimeWithUnit(historyData.getUpdate_time()));
            for(Component component : components){
                if(historyData.getComponent_sn().equals(component.getSn())){
                    historyData.setComponent(component);
                    List<ElementsOfComponentBean> usedMaterials = MisUtil.getMaterialOfComponent(component, elements_of_components, materials, components);
                    for(ElementsOfComponentBean usedMaterial : usedMaterials){
                        if(usedMaterial.getElement_type() == 1){
                            historyData.getMaterials().add(usedMaterial);
                            historyData.setMaterialQuantity(DoubleUtil.mul((double)historyData.getAmount(), usedMaterial.getElement_amount()));
                        }else{
                            ComponentBean usedComponent = new ComponentBean();
                            usedComponent.setName(usedMaterial.getName());
                            usedComponent.setElement_amount(usedMaterial.getElement_amount().intValue());
                            historyData.getUsedComponents().add(usedComponent);
                        }
                    }
                    break;
                }
            }
            for(Process_category processCategory : processCategories){
                if(historyData.getProcess_category_sn().equals(processCategory.getSn())){
                    historyData.setProcessCategory(processCategory);
                    break;
                }
            }
            historyDataList.add(historyData);
        }
        ListUtil.reverseSort(historyDataList, "sn");
        return historyDataList;
    }

    public static List<MaterialFlowingHistoryBean> getCombinedMaterialFlowingHistories(List<Material_flowing_history> histories,
                                                                                       List<Material> materials, List<Material_supplier> suppliers){
        List<MaterialFlowingHistoryBean> historyList = new ArrayList<>();
        ListUtil.reverseSort(histories, "sn");
        for(Material_flowing_history history : histories){
            MaterialFlowingHistoryBean historyBean = new MaterialFlowingHistoryBean();
            BeanUtils.copyProperties(history, historyBean);
            historyBean.setBuyingTimeWithUnit(MattUtil.getDateWithUnit(history.getBuying_time()));
            historyBean.setUpdateTimeWithUnit(MattUtil.getDateTimeWithUnit(history.getUpdate_time()));
            for(Material material : materials){
                if(history.getMaterial_sn().equals(material.getSn())){
                    historyBean.setMaterialIdentifier(material.getIdentifier());
                    historyBean.setLast_update_time(material.getLast_update_time());
                    historyBean.setMaterialSupplierSn(material.getSupplier());
                    historyBean.setMaterialName(material.getName());
                    historyBean.setUnit(material.getUnit());
                    historyBean.setUnitName(MisUtil.getUnitNameFromMaterial(material.getUnit()));
                    historyBean.setMaterialActive(material.getActive() == 1 ? true : false);
                    break;
                }
            }
            for(Material_supplier supplier : suppliers){
                if(historyBean.getMaterialSupplierSn().equals(supplier.getSn())){
                    historyBean.setSupplierName(supplier.getName());
                    break;
                }
            }
            historyList.add(historyBean);
        }
        return historyList;
    }

    public static List<ProductBean> getCombinedProducts(List<Product> products, List<Component_of_product> component_of_products, List<Component> components){
        List<ProductBean> productDataList = new ArrayList<>();
        ListUtil.sort(products, "identifier");
        for(Product product : products){
            ProductBean productBean = new ProductBean();
            BeanUtils.copyProperties(product, productBean);
            List<Integer> componentsSn = new ArrayList<>();
            for(Component_of_product componentOfProduct : component_of_products){
                if(product.getSn().equals(componentOfProduct.getProduct_sn())){
                    componentsSn.add(componentOfProduct.getComponent_sn());
                    for(Component component : components){
                        if(component.getSn().equals(componentOfProduct.getComponent_sn())){
                            ComponentBean componentBean = new ComponentBean();
                            BeanUtils.copyProperties(component, componentBean);
                            componentBean.setQuantity(componentOfProduct.getComponent_amount());
                            productBean.getComponents().add(componentBean);
                        }
                    }
                }
            }
            productDataList.add(productBean);
        }
        return productDataList;
    }

    public static boolean isComponentActive(Integer productSn, List<Component> components, List<Component_of_product> componentOfProducts){
        boolean isActive = true;
        outer:
        for(Component_of_product componentOfProduct : componentOfProducts){
            if(componentOfProduct.getProduct_sn().equals(productSn)){
                for(Component component : components){
                    if(componentOfProduct.getComponent_sn().equals(component.getSn())){
                        if(component.getActive() == 0){
                            isActive = false;
                            break outer;
                        }
                    }
                }
            }
        }
        return isActive;
    }

    /**
     * 以工序是否為購買來重設Component的值
     * @param component
     * @param processes
     */
    public static void fixComponentValueByPurchase(Component component, List<Process_category> processes){
        for(Process_category process : processes){
            if(component.getProcess_category_sn().equals(process.getSn())){
                if(process.getRelation().contains("ROLE_PURCHASE")){
//                    component.setMaterial_sn(0);
//                    component.setMaterial_amount((double)0);
                    component.setProcess_duration(0);
                    component.setProcess_yield(0);
                }else{
                    component.setPurchase_fee((double)0);
                }
            }
        }
    }

    public static String getComponentListReportString(List<Component_of_product> componentOfProducts, List<Component> components,
                                                      ProductFlowingHistoryBean history){
        String componentStr = "";
        boolean isFirst = true;
        for(Component_of_product component_of_product : componentOfProducts){
            if(history.getProduct_sn().equals(component_of_product.getProduct_sn())){
                for(Component component : components){
                    if(component.getSn().equals(component_of_product.getComponent_sn())){
                        if(!isFirst){
                            componentStr += "\n";
                        }
                        componentStr += component.getName() + " X " + (history.getAmount() * component_of_product.getComponent_amount()) + "件";
                        isFirst = false;
                        break;
                    }
                }
            }
        }
        return componentStr;
    }

    public static String getUnitNameFromMaterial(Integer unit){
        if(unit == null){
            return "件";
        }else{
            switch (unit){
                case 1 :
                    return "g";
                case 2 :
                    return "kg";
                case 3 :
                    return "piece";
                default:
                    return "";
            }
        }
    }

    public static List<ElementsOfComponentBean> getMaterialOfComponent(Component targetComponent, List<Elements_of_component> materialOfComponents, List<Material> materials, List<Component> components) {
        List<ElementsOfComponentBean> usedMaterials = new ArrayList<>();
        for(Elements_of_component materialOfComponent : materialOfComponents){
            if(materialOfComponent.getComponent_sn().equals(targetComponent.getSn())){
                if(materialOfComponent.getElements_type() == 1){
                    for(Material material : materials){
                        if(materialOfComponent.getElement_sn().equals(material.getSn())){
                            ElementsOfComponentBean elementsOfComponentBean = new ElementsOfComponentBean();
                            BeanUtils.copyProperties(material, elementsOfComponentBean);
                            elementsOfComponentBean.setElement_amount(materialOfComponent.getElement_amount());
                            elementsOfComponentBean.setElement_type(materialOfComponent.getElements_type());
                            usedMaterials.add(elementsOfComponentBean);
                        }
                    }
                }else{
                    for(Component component : components){
                        if(materialOfComponent.getElement_sn().equals(component.getSn())){
                            ElementsOfComponentBean elementsOfComponentBean = new ElementsOfComponentBean();
                            elementsOfComponentBean.setSn(component.getSn());
                            elementsOfComponentBean.setName(component.getName());
                            elementsOfComponentBean.setElement_amount(materialOfComponent.getElement_amount());
                            elementsOfComponentBean.setElement_type(materialOfComponent.getElements_type());
                            elementsOfComponentBean.setQuantity((double)component.getRemain());
                            usedMaterials.add(elementsOfComponentBean);
                        }
                    }
                }
            }
        }
        return usedMaterials;
    }

    public static String getElementOfComponentStringForReport(List<Component> components, List<Material> materials, List<ElementsOfComponentBean> elementsOfComponent){
        String elementString = "";
        for(ElementsOfComponentBean element : elementsOfComponent) {
            if (element.getElement_type().equals(Elements_of_component.MATERIAL_TYPE)) {
                for (Material material : materials) {
                    if (element.getSn().equals(material.getSn())) {
                        elementString += material.getName() + " X " + element.getElement_amount() + MisUtil.getUnitNameFromMaterial(material.getUnit());
                    }
                }
            } else {
                for (Component component : components) {
                    if (element.getSn().equals(component.getSn())) {
                        elementString += component.getName() + " X " + element.getElement_amount() + "件";
                    }
                }
            }
            if (elementsOfComponent.indexOf(element) != (elementsOfComponent.size() - 1)) {
                elementString += "\n";
            }
        }
        return elementString;
    }

    public static String getMaterialOfComponentStringForReport(ComponentFlowingHistoryBean history){
        String elementString = "";
        for(ElementsOfComponentBean material : history.getMaterials()){
            elementString += material.getName() + " X " + DoubleUtil.mul((double)history.getAmount(), material.getElement_amount())
                    + MisUtil.getUnitNameFromMaterial(material.getUnit());
            if(!(history.getMaterials().indexOf(material) == history.getMaterials().size()-1) || history.getUsedComponents().size()
                     != 0){
                elementString += "\n";
            }
        }
        for(ComponentBean componentBean : history.getUsedComponents()){
            if(history.getUsedComponents().indexOf(componentBean) == 0){
                elementString += "---------------------\n";
            }
            elementString += componentBean.getName() + " X " + DoubleUtil.mul((double)history.getAmount(), (double)componentBean.getElement_amount()) + "件";
            if(!(history.getMaterials().indexOf(componentBean) == history.getMaterials().size()-1)){
                elementString += "\n";
            }
        }
        return elementString;
    }
}
