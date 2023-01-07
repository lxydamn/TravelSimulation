package com.backend.service.web;

import com.backend.pojo.Plan;

import java.util.List;
import java.util.Map;

public interface GetRecordService {
    List<Plan> getRecords(Map<String, String> map);
}
