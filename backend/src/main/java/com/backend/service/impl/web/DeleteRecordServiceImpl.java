package com.backend.service.impl.web;

import com.backend.mapper.PlanMapper;
import com.backend.pojo.Path;
import com.backend.service.web.DeleteRecordService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
public class DeleteRecordServiceImpl implements DeleteRecordService {

    @Autowired
    private PlanMapper planMapper;

    @Override
    public Map<String, String> deleteRecord(Map<String, String> map) {
        Map<String, String> resp = new HashMap<>();

        try {
            Integer id = Integer.parseInt(map.get("id"));
            planMapper.deleteById(id);
        } catch (Exception e) {
            resp.put("error_message", "参数错误或路径不存在");
            return resp;
        }

        resp.put("error_message", "success");
        return resp;
    }
}
