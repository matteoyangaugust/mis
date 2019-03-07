package com.matt.repository;

import com.matt.model.Material_flowing_history;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MaterialFlowingHistoryRepository {

    public Integer insert(Material_flowing_history history);

    public Integer update(Material_flowing_history history);

    public Integer delete(Integer sn);

    public List<Material_flowing_history> findMaterialFlowingHistories();

    public Material_flowing_history findMaterialFlowingHistory(Integer sn);
}
