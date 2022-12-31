package com.backend.utils;

import com.backend.mapper.CityMapper;
import com.backend.pojo.City;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import org.jetbrains.annotations.TestOnly;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

@Component
public class CityRisk {
    @Autowired
    private CityMapper cityMapper;
    private final int[] riskValue = {0, 3, 5};

    private static CityRisk cityRisk;

    @PostConstruct
    public void init() {

        cityRisk = this;
        cityRisk.cityMapper = this.cityMapper;

    }
    public Integer getCityRisk(Integer adcode) {


            QueryWrapper<City> queryWrapper = new QueryWrapper<>();
            queryWrapper.eq("city_adcode", adcode);
            City c = cityRisk.cityMapper.selectOne(queryWrapper);

            return riskValue[c.getGrade()];
    }
}
