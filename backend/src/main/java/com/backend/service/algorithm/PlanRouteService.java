package com.backend.service.algorithm;

import com.backend.pojo.Planed;

import java.util.Date;
import java.util.Map;
import java.util.Set;

public interface PlanRouteService {
    Planed getDfsPlanRoute(Integer st, Integer ed, Date startTime, Integer transit, Integer weightType, Set<Integer> set);

    Planed getBellmanPlanRoute(Integer st, Integer ed, Date startTime, Integer transit, Integer weightType, Set<Integer> set);

    Planed getDijkstraPlanRoute(Integer st, Integer ed, Date startTime, Integer weightType, Set<Integer> set);

}
