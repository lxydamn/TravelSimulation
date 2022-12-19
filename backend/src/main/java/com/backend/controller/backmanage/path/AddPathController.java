package com.backend.controller.backmanage.path;


import com.backend.service.backmanage.path.AddPathService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
public class AddPathController {

    @Autowired
    private AddPathService addPathService;

    @PostMapping("/api/back/addpath")

    public Map<String, String> addPath(@RequestParam Map<String, String> map){
        return addPathService.addPath(map);
    }
}
