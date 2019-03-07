package com.matt.repository;

import com.matt.model.Material;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MaterialRepository {
    public Integer insert(Material material);
    public List<Material> findMaterials();
    public Material findMaterials(Integer material_sn);
    public List<Material> findMaterials(List<Integer> material_sn);
    public Integer update(Material material);

    public Integer delete(Integer sn);


}
