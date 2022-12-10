package com.backend.service.impl.backmanage.city;

import com.backend.mapper.CityMapper;
import com.backend.pojo.City;
import com.backend.service.backmanage.city.AddCityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.management.QueryEval;
import java.util.HashMap;
import java.util.Map;

@Service
public class AddCityServiceImpl  implements AddCityService {

    @Autowired
    private CityMapper cityMapper;


    @Override
    public Map<String, String> addCiry(Integer adcode, String name, Integer grade) {

        Map<String, String> map = new HashMap<>();

        City city = new City(adcode, name, grade);

        try {
            cityMapper.insert(city);
        } catch (Exception e) {
            map.put("error_message", "error");
            return map;
        }

        map.put("error_message", "success");
        return map;
    }
}
