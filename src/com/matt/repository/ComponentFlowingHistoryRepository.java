package com.matt.repository;

import com.matt.model.Component_flowing_history;
import com.matt.model.System_user;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ComponentFlowingHistoryRepository {

    public Integer insert(Component_flowing_history flowingHistory);

    public List<Component_flowing_history> findFlowingHistories(System_user user);

    public Integer delete(Integer sn);

    public Integer update(Component_flowing_history history);

    public List<Component_flowing_history> findFlowingHistoriesByComponent(Integer component_sn);
}
