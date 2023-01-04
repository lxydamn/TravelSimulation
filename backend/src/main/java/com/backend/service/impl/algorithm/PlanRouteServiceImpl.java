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
        Graph graph = new Graph(pathMapper.selectList(null), startTime);
        List<Path> paths = graph.bellmanFord(st, ed, transit, weightType, set);

        if(paths == null) {
            return null;
        }

        return new Planed(paths);
    }

    @Override
    public Planed getDijkstraPlanRoute(Integer st, Integer ed, Date startTime, Integer weightType, Set<Integer> set) {
        Graph graph = new Graph(pathMapper.selectList(null), startTime);
        List<Path> paths = graph.getDijkstra(st, ed, weightType, set);

        if(paths == null) {
            return null;
        }
        System.out.println(new Planed(paths));
        return new Planed(paths);
    }



}
