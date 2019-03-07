package com.matt.service.material;

import com.matt.bean.ResultBean;
import com.matt.model.Material;
import com.matt.model.Material_supplier;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface MaterialCreateService {
    /**
     * 撈取所有的原料廠商資料
     * @return
     */
    public List<Material_supplier> findMaterialSuppliers();

    /**
     * 儲存新的原料
     * @return
     */
    public ResultBean insertMaterial(Material material);
}
