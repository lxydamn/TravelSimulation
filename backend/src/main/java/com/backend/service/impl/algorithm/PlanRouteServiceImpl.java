package com.backend.service.impl.algorithm;

import com.backend.mapper.PathMapper;
import com.backend.pojo.Graph;
import com.backend.pojo.Planed;
import com.backend.service.algorithm.PlanRouteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.Map;

@Service
public class PlanRouteServiceImpl implements PlanRouteService {

    @Autowired
    private PathMapper pathMapper;

    @Override
    public Planed getDfsPlanRoute(Map<String, String> map) {

        try {
            Integer startCity = Integer.parseInt(map.get("startCity"));
            Integer endCity = Integer.parseInt(map.get("endCity"));
            Date startTime = new Date(Long.parseLong(map.get("startTime")));

            Graph graph = new Graph(pathMapper.selectList(null), startTime);

            return graph.getDfs(startCity, endCity);

        } catch (Exception e) {

            return null;
        }

    }

    @Override
    public Planed getBellmanPlanRoute(Map<String, String> map) {

        return null;
    }


}
