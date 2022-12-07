package com.backend.controller.backmanage;

import com.backend.pojo.City;
import com.backend.service.backmanage.GetCitiesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class GetCitiesController {

    @Autowired
    private GetCitiesService getCitiesService;

    @GetMapping("/api/back/getcities/")
    public List<City> getCities() {
        return getCitiesService.getCities();
    }
}
