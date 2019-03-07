package com.matt.service.material;

import com.matt.bean.MaterialBean;
import com.matt.bean.ResultBean;
import com.matt.model.Material;
import com.matt.model.Material_supplier;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface MaterialManageService {
    
    public List<Material> findMaterials();

    public List<Material_supplier> findSuppliers();

    public ResultBean updateMaterial(Material material);

    public ResultBean deleteByMaterialSn(Integer sn);
}
