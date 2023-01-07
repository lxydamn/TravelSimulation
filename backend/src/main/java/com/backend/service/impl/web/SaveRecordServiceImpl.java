package com.backend.service.impl.web;

import com.alibaba.fastjson.JSONArray;
import com.backend.mapper.PlanMapper;
import com.backend.pojo.Path;
import com.backend.pojo.Plan;
import com.backend.service.web.SaveRecordService;
import com.mysql.cj.xdevapi.JsonArray;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class SaveRecordServiceImpl implements SaveRecordService {

    @Autowired
    private PlanMapper planMapper;

    @Override
    public Map<String, String> saveRecord(Map<String, String> map) {
        Map<String, String> resp = new HashMap<>();
        Integer userId,strategy,startCity,endCity,risk,cost,transit;
        String content;
        Date createdTime = new Date();
        Object obj = JSONArray.toJSON(map);
        try{
            userId = Integer.parseInt(map.get("userId"));
            content = obj.toString();
            startCity = Integer.parseInt(map.get("startCity"));
            endCity = Integer.parseInt(map.get("endCity"));
            strategy = Integer.parseInt(map.get("strategy"));
            risk = Integer.parseInt(map.get("risk"));
            cost = Integer.parseInt(map.get("cost"));
            transit = Integer.parseInt(map.get("transit"));
        }catch (Exception e) {
            resp.put("error_message", "参数错误");
            return resp;
        }

        Plan p = new Plan(userId,null,content,createdTime,startCity,endCity,cost,strategy,transit,risk);
        planMapper.insert(p);
        resp.put("error_message", "success");
        return resp;
    }
}
