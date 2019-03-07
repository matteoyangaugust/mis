package com.matt.service.material;

import com.matt.bean.MaterialFlowingHistoryBean;
import com.matt.bean.ResultBean;
import com.matt.model.Material;
import com.matt.model.Material_flowing_history;
import com.matt.model.Material_supplier;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface MaterialProduceService {
    public void initialList();

    public List<Material> findMaterials();

    public List<MaterialFlowingHistoryBean> findMaterialFLowingHistories();

    public List<Material_supplier> findSuppliers();

    public ResultBean save(Material_flowing_history history) throws Exception;

    public ResultBean delete(Integer sn) throws Exception;
}
