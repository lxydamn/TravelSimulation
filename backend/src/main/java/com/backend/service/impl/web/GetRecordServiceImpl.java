package com.backend.service.impl.web;

import com.backend.pojo.City;
import com.backend.pojo.Plan;
import com.backend.service.backmanage.city.GetCitiesService;
import com.backend.service.web.GetRecordService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class GetRecordServiceImpl implements GetRecordService {


    @Override
    public List<Plan> getRecords() {
        return null;
    }
}
