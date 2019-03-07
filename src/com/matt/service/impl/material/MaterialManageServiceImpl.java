package com.matt.service.impl.material;

import com.matt.bean.ResultBean;
import com.matt.model.Material;
import com.matt.model.Material_supplier;
import com.matt.repository.MaterialRepository;
import com.matt.repository.MaterialSupplierRepository;
import com.matt.service.BaseService;
import com.matt.service.material.MaterialManageService;
import com.matt.util.DoubleUtil;
import com.matt.util.ListUtil;
import com.matt.util.MattUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class MaterialManageServiceImpl extends BaseService implements MaterialManageService{
    @Autowired
    private MaterialRepository materialRepository;
    @Autowired
    private MaterialSupplierRepository materialSupplierRepository;
    @Override
    public List<Material> findMaterials() {
        List<Material> materials = materialRepository.findMaterials();
        ListUtil.sort(materials, "identifier");
        return materials;
    }

    @Override
    public List<Material_supplier> findSuppliers() {
        List<Material_supplier> suppliers = materialSupplierRepository.findMaterialSuppliers();
        ListUtil.sort(suppliers, "name");
        return suppliers;
    }

    @Override
    public ResultBean updateMaterial(Material material) {
        List<Material> materials = materialRepository.findMaterials();
        for(Material existedMaterial : materials){
            if(existedMaterial.getName().equals(material.getName()) &&
                    !existedMaterial.getSn().equals(material.getSn())){
                return new ResultBean(false, "Duplicate Material Name");
            }else if(existedMaterial.getIdentifier().equals(material.getIdentifier()) &&
                    !existedMaterial.getSn().equals(material.getSn())){
                return new ResultBean(false, "Duplicate Material Code");
            }
        }
        material.setLast_update_time(MattUtil.getDateTime());
        if(materialRepository.update(material) > 0){
            return new ResultBean(true, "Success");
        }else{
            return new ResultBean(false, "Failed");
        }
    }

    @Override
    public ResultBean deleteByMaterialSn(Integer sn) {
        Material material = materialRepository.findMaterials(sn);
        material.setActive(0);
        if(materialRepository.update(material) > 0){
            return new ResultBean(true, "Delete Success");
        }else{
            return new ResultBean(false, "Delete Failed");
        }
    }
}
