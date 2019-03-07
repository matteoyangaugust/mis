package com.matt.service.manage;

import com.matt.bean.ResultBean;
import com.matt.model.Material_supplier;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface ManageSupplierService {
    public ResultBean insertSupplier(Material_supplier supplier) throws Exception;
    public List<Material_supplier> findSuppliers();

    public ResultBean deleteSupplierBySn(Integer sn) throws Exception;

    public ResultBean update(Material_supplier supplier) throws Exception;
}
