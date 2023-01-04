package com.backend.controller.algorithm;

import com.backend.pojo.Planed;
import com.backend.service.algorithm.PlanRouteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

@RestController
public class PlanRouteController {

    @Autowired
    private PlanRouteService planRouteService;

    @PostMapping("/api/algorithm/dfs/")
    public Planed getAllRoute(@RequestParam Map<String, String> map) {
        Integer st = Integer.parseInt(map.get("startCity"));
        Integer ed = Integer.parseInt(map.get("endCity"));
        Integer transit = Integer.parseInt(map.get("transit"));
        Integer weightType = Integer.parseInt(map.get("valueStrategy"));
        Date startTime = new Date(Long.parseLong(map.get("startTime")));

        String[] names = {"car", "train", "plane"};
        Set<Integer> set = new HashSet<>();
        for(int i = 1; i <= 3; i ++) {
            if(map.get(names[i - 1]).equals("true")) {
                set.add(i);
            }
        }
        if(transit == -1) return planRouteService.getDfsPlanRoute(st, ed, startTime, transit, weightType, set);
        else if (transit == 0) {
            return planRouteService.getDijkstraPlanRoute(st, ed, startTime, weightType, set);
        } else return planRouteService.getBellmanPlanRoute(st, ed, startTime, transit, weightType, set);
    }
}
