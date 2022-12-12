package com.backend.controller.backmanage.city;


import com.backend.service.backmanage.city.DeleteCityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
public class DeleteCityController {

    @Autowired
    private DeleteCityService deleteCityService;

    @PostMapping("api/back/deletecity/")
    public Map<String,String> deleteCity(@RequestParam Map<String, String> map)  {

        Integer adcode = Integer.parseInt(map.get("adcode"));

        return deleteCityService.deleteCity(adcode);
    }
}
