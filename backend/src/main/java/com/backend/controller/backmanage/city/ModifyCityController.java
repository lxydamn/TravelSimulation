package com.backend.controller.backmanage.city;


import com.backend.service.backmanage.city.ModifyCityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
public class ModifyCityController {

    @Autowired
    private ModifyCityService modifyCityService;

    @PostMapping("api/back/modifycity")
    public void modifyCity(@RequestParam Map<String, String> map) {

        Integer adcode = Integer.parseInt(map.get("adcode"));
        Integer grade = Integer.parseInt(map.get("grade"));
        modifyCityService.modifyCity(adcode, grade);
    }
}
