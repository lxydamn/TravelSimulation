package com.backend.controller.algorithm;

import com.backend.pojo.Planed;
import com.backend.service.algorithm.PlanRouteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
public class PlanRouteController {

    @Autowired
    private PlanRouteService planRouteService;

    @PostMapping("/api/algorithm/dfs/")
    public Planed getAllRoute(@RequestParam Map<String, String> map) {

        return planRouteService.getDfsPlanRoute(map);
    }


}
