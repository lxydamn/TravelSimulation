package com.backend.service.algorithm;

import com.backend.pojo.Planed;

import java.util.Map;

public interface PlanRouteService {
    Planed getDfsPlanRoute(Map<String, String> map);

    Planed getBellmanPlanRoute(Map<String, String> map);
}
