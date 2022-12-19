package com.backend.controller.backmanage.path;

import com.backend.pojo.Path;
import com.backend.service.backmanage.path.GetPathsService;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.web.bind.annotation.GetMapping;

import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;


@RestController
public class GetPathsContrller {

    @Autowired
    private GetPathsService getPathsService;

    @GetMapping("/api/back/getpaths/")
    public Map<String, List<Path>> getPaths(){
        return getPathsService.getPaths();
    }
}
