package com.backend.service.impl.backmanage.city;

import com.backend.mapper.CityMapper;
import com.backend.pojo.City;
import com.backend.service.backmanage.city.DeleteCityService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
public class DeleteCityServiceImpl implements DeleteCityService {


    @Autowired
    private CityMapper cityMapper;

    @Override
    public Map<String, String> deleteCity(Integer adcode) {
        Map<String, String> map = new HashMap<>();

        QueryWrapper<City> queryWrapper = new QueryWrapper<>();

        queryWrapper.eq("city_adcode", adcode);

        try {
            cityMapper.delete(queryWrapper);
            map.put("error_message", "success");
        } catch (Exception e) {
            map.put("error_message", "error");
        }
        return map;
    }
}
