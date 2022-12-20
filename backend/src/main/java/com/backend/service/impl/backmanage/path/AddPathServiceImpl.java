package com.backend.service.impl.backmanage.path;

import com.backend.mapper.CityMapper;
import com.backend.mapper.PathMapper;
import com.backend.pojo.Path;
import com.backend.service.backmanage.path.AddPathService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Service
public class AddPathServiceImpl implements AddPathService {
    @Autowired
    private PathMapper pathMapper;
    @Autowired
    private CityMapper cityMapper;

    @Override
    public Map<String, String> addPath(Map<String, String> map) {
        Map<String, String> resp = new HashMap<>();
        Integer type, cost, startCity, endCity;
        Long startTime, endTime;

        try {
            type = Integer.parseInt(map.get("type"));
            cost = Integer.parseInt(map.get("cost"));
            startCity = Integer.parseInt(map.get("startCity"));
            endCity = Integer.parseInt(map.get("endCity"));
            startTime = Long.parseLong(map.get("startTime"));
            endTime =Long.parseLong(map.get("endTime"));
        } catch (Exception e) {
            resp.put("error_message", "参数错误");
            return resp;
        }
        Date st = new Date(startTime);
        Date ed = new Date(endTime);
        Path p = new Path(null,startCity, endCity, st, ed , cost, type);
        pathMapper.insert(p);

        resp.put("error_message", "success");
        return resp;
    }
}
