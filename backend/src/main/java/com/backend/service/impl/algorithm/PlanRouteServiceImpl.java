package com.backend.service.impl.algorithm;

import com.backend.mapper.PathMapper;
import com.backend.pojo.Graph;
import com.backend.pojo.Path;
import com.backend.pojo.Planed;
import com.backend.service.algorithm.PlanRouteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class PlanRouteServiceImpl implements PlanRouteService {

    @Autowired
    private PathMapper pathMapper;

    @Override
    public Planed getDfsPlanRoute(Integer st, Integer ed, Date startTime, Integer transit, Integer weightType, Set<Integer> set) {

        try {
            Graph graph = new Graph(pathMapper.selectList(null), startTime);

            return graph.getDfs(st, ed, set);

        } catch (Exception e) {

            return null;
        }

    }

    @Override
    public Planed getBellmanPlanRoute(Integer st, Integer ed, Date startTime, Integer transit, Integer weightType, Set<Integer> set) {
//        Integer st = Integer.parseInt(map.get("startCity"));
//        Integer ed = Integer.parseInt(map.get("endCity"));
//        Integer transit = Integer.parseInt(map.get("transit"));
//        Integer weightType = Integer.parseInt(map.get("weightType"));
//        Date startTime = new Date(Long.parseLong(map.get("startTime")));
//
//        String[] names = {"car", "train", "plane"};
//        Set<Integer> set = new HashSet<>();
//        for(int i = 1; i <= 3; i ++) {
//            if(map.get(names[i - 1]).equals("true")) {
//                set.add(i);
//            }
//        }

        Graph graph = new Graph(pathMapper.selectList(null), startTime);
        List<Path> paths = graph.bellmanFord(st, ed, transit, weightType, set);

        if(paths == null) {
            return null;
        }

        return new Planed(paths);
    }


}
