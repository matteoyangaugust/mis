package com.matt.service.impl.material;

import com.matt.bean.MaterialFlowingHistoryBean;
import com.matt.bean.ResultBean;
import com.matt.model.Material;
import com.matt.model.Material_flowing_history;
import com.matt.model.Material_supplier;
import com.matt.repository.MaterialFlowingHistoryRepository;
import com.matt.repository.MaterialRepository;
import com.matt.repository.MaterialSupplierRepository;
import com.matt.service.BaseService;
import com.matt.service.material.MaterialProduceService;
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
public class MaterialProduceServiceImpl extends BaseService implements MaterialProduceService {

    @Autowired
    private MaterialFlowingHistoryRepository materialFlowingHistoryRepository;
    @Autowired
    private MaterialRepository materialRepository;
    @Autowired
    private MaterialSupplierRepository materialSupplierRepository;

    private List<Material> materials;
    private List<Material_supplier> suppliers;

    @Override
    public void initialList() {
        materials = materialRepository.findMaterials();
        ListUtil.sort(materials, "identifier");
        suppliers = materialSupplierRepository.findMaterialSuppliers();
        ListUtil.sort(suppliers, "name");
    }

    @Override
    public List<Material> findMaterials() {
        return this.materials;
    }

    @Override
    public List<MaterialFlowingHistoryBean> findMaterialFLowingHistories() {
        List<MaterialFlowingHistoryBean> historyList = new ArrayList<>();
        List<Material_flowing_history> histories = materialFlowingHistoryRepository.findMaterialFlowingHistories();
        return MisUtil.getCombinedMaterialFlowingHistories(histories, materials, suppliers);
    }

    @Override
    public List<Material_supplier> findSuppliers() {
        return this.suppliers;
    }

    @Override
    @Transactional(rollbackFor = Exception.class, transactionManager = "mis")
    public ResultBean save(Material_flowing_history history) throws Exception {
        initialList();
        Integer closestDayRange = null;
        List<Material_flowing_history> existedHistories = materialFlowingHistoryRepository.findMaterialFlowingHistories();
        ListUtil.sort(existedHistories);
        Iterator<Material_flowing_history> itr = existedHistories.iterator();
        history.setBuying_time(history.getBuying_time().replaceAll("-", ""));
        history.setUpdate_time(MattUtil.getDateTime());
        for(Material material : materials){
            if(material.getSn().equals(history.getMaterial_sn())){
                double remain = DoubleUtil.add(material.getQuantity(), history.getQuantity());
                material.setQuantity(remain);
                history.setRemain(remain);
                if(materialRepository.update(material) < 1){
                    throw new Exception();
                }
                break;
            }
        }
        while(itr.hasNext()){
            Material_flowing_history existedHistory = itr.next();
            if(existedHistory.getMaterial_sn().equals(history.getMaterial_sn())){
                if(Integer.parseInt(existedHistory.getBuying_time()) < Integer.parseInt(history.getBuying_time())){
                    if(closestDayRange == null
                            || Math.abs(Integer.parseInt(history.getBuying_time()) - Integer.parseInt(existedHistory.getBuying_time())) < closestDayRange){
                        history.setRemain(existedHistory.getRemain() + history.getQuantity());
                    }
                    itr.remove();
                }else{
                    existedHistory.setRemain(existedHistory.getRemain() + history.getQuantity());
                    if(materialFlowingHistoryRepository.update(existedHistory) < 1){
                        throw new Exception();
                    }
                }
            }else{
                itr.remove();
            }
        }
        if(materialFlowingHistoryRepository.insert(history) < 1){
            throw new Exception();
        }
        return new ResultBean(true, "Successfully Save");
    }

    @Override
    @Transactional(rollbackFor = Exception.class, transactionManager = "mis")
    public ResultBean delete(Integer sn) throws Exception {
        List<Material_flowing_history> histories = materialFlowingHistoryRepository.findMaterialFlowingHistories();
        Material_flowing_history historyToDelete = new Material_flowing_history();
        for(Material_flowing_history history : histories){
            if(sn == history.getSn()){
                historyToDelete = history;
            }
        }
        Material material = materialRepository.findMaterials(historyToDelete.getMaterial_sn());
        material.setQuantity(DoubleUtil.sub(material.getQuantity(), historyToDelete.getQuantity()));
        if(materialRepository.update(material) < 1){
            throw new Exception();
        }
        for(Material_flowing_history history : histories){
            if(history.getMaterial_sn().equals(historyToDelete.getMaterial_sn())){
                if(Integer.parseInt(history.getBuying_time()) > Integer.parseInt(historyToDelete.getBuying_time())){
                    history.setRemain(history.getRemain() - historyToDelete.getQuantity());
                    if(materialFlowingHistoryRepository.update(history) < 1){
                        throw new Exception();
                    }
                }
            }
        }
        if(materialFlowingHistoryRepository.delete(sn) < 1){
            throw new Exception();
        }
        return new ResultBean(true, "Successfully Delete");
    }
}
