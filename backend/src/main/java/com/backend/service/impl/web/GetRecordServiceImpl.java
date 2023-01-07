package com.backend.service.impl.web;

import com.backend.mapper.PlanMapper;
import com.backend.pojo.Path;
import com.backend.pojo.Plan;
import com.backend.service.web.GetRecordService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class GetRecordServiceImpl implements GetRecordService {

    @Autowired
    private PlanMapper planMapper;

    @Override
    public List<Plan> getRecords(Map<String, String> map) {
        Integer userId = Integer.parseInt(map.get("userId"));
        QueryWrapper<Plan> qw = new QueryWrapper<>();
        List<Plan> p = planMapper.selectList(qw.eq("user_id",userId));
        return p;
    }
}
