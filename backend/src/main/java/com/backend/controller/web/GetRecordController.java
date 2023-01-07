package com.backend.controller.web;

import com.backend.pojo.Plan;
import com.backend.service.web.GetRecordService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

@RestController
public class GetRecordController {
    @Autowired
    private GetRecordService getRecordService;

    @PostMapping("/api/back/getrecord/")
    public List<Plan> getRecord(@RequestParam Map<String,String>map) {
        return getRecordService.getRecords(map);
    }
}
