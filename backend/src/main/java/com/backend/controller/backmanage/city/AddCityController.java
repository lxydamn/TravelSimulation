package com.backend.controller.backmanage.city;

import com.backend.service.backmanage.city.AddCityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
public class AddCityController {

    @Autowired
    private AddCityService addCityService;

    @PostMapping("api/back/addcity/")
    public Map<String, String> addCity(@RequestParam Map<String, String> map) {

        Integer adcode = Integer.parseInt(map.get("adcode"));
        String name = map.get("name");
        Integer grade = Integer.parseInt(map.get("grade"));

        return addCityService.addCiry(adcode, name, grade);
    }
}
