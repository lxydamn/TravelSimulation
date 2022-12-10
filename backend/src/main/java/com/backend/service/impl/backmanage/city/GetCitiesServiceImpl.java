package com.backend.service.impl.backmanage.city;

import com.backend.mapper.CityMapper;
import com.backend.pojo.City;
import com.backend.service.backmanage.city.GetCitiesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class GetCitiesServiceImpl implements GetCitiesService {

    @Autowired
    private CityMapper cityMapper;
    @Override
    public List<City> getCities() {
        return cityMapper.selectList(null);
    }
}
