package com.backend.service.impl.backmanage.city;

import com.backend.mapper.CityMapper;
import com.backend.pojo.City;
import com.backend.service.backmanage.city.ModifyCityService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ModifyCityServiceImpl implements ModifyCityService {

    @Autowired
    private CityMapper cityMapper;
    @Override
    public void modifyCity(Integer adcode, Integer grade) {

        QueryWrapper<City> queryWrapper = new QueryWrapper<>();

        queryWrapper.eq("city_adcode", adcode);

        City city = new City();
        city.setGrade(grade);

        cityMapper.update(city, queryWrapper);
    }
}
