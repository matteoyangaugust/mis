package com.matt.repository;

import com.matt.model.Material_supplier;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MaterialSupplierRepository {
    public List<Material_supplier> findMaterialSuppliers();
    public Integer insert(Material_supplier supplier);
    public Integer delete(Integer sn);

    public Integer update(Material_supplier supplierToUpdate);
}
