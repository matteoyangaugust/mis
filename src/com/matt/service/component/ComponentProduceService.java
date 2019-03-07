package com.matt.service.component;

import com.matt.bean.ComponentBean;
import com.matt.bean.ComponentFlowingHistoryBean;
import com.matt.bean.ResultBean;
import com.matt.model.Component_flowing_history;
import com.matt.model.Process_category;
import com.matt.model.System_user;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface ComponentProduceService {
    public List<ComponentBean> findComponents();
    public List<ComponentFlowingHistoryBean> findFlowingHistory(System_user user);
    public List<Process_category> findProcessCategories(System_user user);
    public ResultBean save(Component_flowing_history flowingHistory) throws Exception;

    public ResultBean delete(Component_flowing_history flowingHistory, System_user user) throws Exception;
}
