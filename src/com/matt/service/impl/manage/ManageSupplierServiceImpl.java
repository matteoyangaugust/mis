package com.matt.service.impl.manage;

import com.matt.bean.ResultBean;
import com.matt.model.Material;
import com.matt.model.Material_supplier;
import com.matt.repository.MaterialRepository;
import com.matt.repository.MaterialSupplierRepository;
import com.matt.service.BaseService;
import com.matt.service.manage.ManageSupplierService;
import com.matt.util.ListUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ManageSupplierServiceImpl extends BaseService implements ManageSupplierService{
    @Autowired
    private MaterialSupplierRepository materialSupplierRepository;
    @Autowired
    private MaterialRepository materialRepository;
    @Override
    public ResultBean insertSupplier(Material_supplier supplier) throws Exception {
        List<Material_supplier> suppliers = materialSupplierRepository.findMaterialSuppliers();
        for(Material_supplier existedSupplier : suppliers){
            if(supplier.getName().equals(existedSupplier.getName())){
                return new ResultBean(false, "Duplicate Supplier");
            }
        }
        if(materialSupplierRepository.insert(supplier) < 1){
            throw new Exception();
        }
        return new ResultBean(true, "Save Successfully");
    }

    @Override
    public List<Material_supplier> findSuppliers() {
        List<Material_supplier> suppliers = materialSupplierRepository.findMaterialSuppliers();
        ListUtil.sort(suppliers, "name");
        return suppliers;
    }

    @Override
    public ResultBean deleteSupplierBySn(Integer sn) throws Exception {
        List<Material> materials = materialRepository.findMaterials();
        for(Material material : materials){
            if(material.getSupplier().equals(sn)){
                return new ResultBean(false, "Delete Failed, the Supplier is bond with some materials");
            }
        }
        if(materialSupplierRepository.delete(sn) < 1){
            throw new Exception();
        }
        return new ResultBean(true, "Delete Successfully");
    }

    @Override
    public ResultBean update(Material_supplier supplierToUpdate) throws Exception {
        List<Material_supplier> suppliers = materialSupplierRepository.findMaterialSuppliers();
        for(Material_supplier supplier : suppliers){
            if(supplier.getSn() != supplierToUpdate.getSn()){
                if(supplier.getName().replaceAll(" ", "").toLowerCase().equals(supplierToUpdate.getName().replace(" ", "").toLowerCase())){
                    return new ResultBean(false, "Duplicate Supplier");
                }
            }
        }
        if(materialSupplierRepository.update(supplierToUpdate) < 1){
            throw new Exception();
        }
        return new ResultBean(true, "Save Successfully");
    }
}
