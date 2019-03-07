package com.matt.service.impl.material;

import com.matt.bean.ResultBean;
import com.matt.model.Material;
import com.matt.model.Material_supplier;
import com.matt.repository.MaterialRepository;
import com.matt.repository.MaterialSupplierRepository;
import com.matt.service.BaseService;
import com.matt.service.material.MaterialCreateService;
import com.matt.util.ListUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MaterialCreateServiceImpl extends BaseService implements MaterialCreateService{
    @Autowired
    private MaterialSupplierRepository materialSupplierRepository;
    @Autowired
    private MaterialRepository materialRepository;
    @Override
    public List<Material_supplier> findMaterialSuppliers() {
        List<Material_supplier> suppliers = materialSupplierRepository.findMaterialSuppliers();
        ListUtil.sort(suppliers, "name");
        return suppliers;
    }

    @Override
    public ResultBean insertMaterial(Material material) {
        List<Material> materials = materialRepository.findMaterials();
        for(Material existedMaterial : materials){
            if(existedMaterial.getName().equals(material.getName())){
                return new ResultBean(false, "The Material Existed");
            }
        }
        return materialRepository.insert(material) > 0 ? new ResultBean(true, "Success") :
                new ResultBean(false, "Failed");
    }
}
