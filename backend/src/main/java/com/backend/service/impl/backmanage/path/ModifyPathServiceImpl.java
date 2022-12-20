package com.backend.service.impl.backmanage.path;

import com.backend.mapper.PathMapper;
import com.backend.pojo.Path;
import com.backend.service.backmanage.path.ModifyPathService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Service
public class ModifyPathServiceImpl implements ModifyPathService {

    @Autowired
    private PathMapper pathMapper;
    @Override
    public Map<String, String> modifyPath(Map<String, String> map) {
        Map<String, String> resp = new HashMap<>();

        try {
            Integer id = Integer.parseInt(map.get("id"));
            Integer startCity = Integer.parseInt(map.get("startCity"));
            Integer endCity = Integer.parseInt(map.get("endCity"));
            Long startTime = Long.parseLong(map.get("startTime"));
            Long endTime = Long.parseLong(map.get("endTime"));
            Integer cost = Integer.parseInt(map.get("cost"));
            Integer type = Integer.parseInt(map.get("type"));

            Date st = new Date(startTime);
            Date et = new Date(endTime);

            Path p = new Path(id, startCity, endCity, st, et, cost, type);

            pathMapper.updateById(p);

        } catch (Exception e) {
            resp.put("error_message", "参数错误");
            return resp;
        }
        resp.put("error_message", "success");
        return resp;
    }
}
