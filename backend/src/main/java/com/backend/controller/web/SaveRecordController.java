package com.backend.controller.web;

import com.backend.service.web.SaveRecordService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
public class SaveRecordController {
    @Autowired
    private SaveRecordService saveRecordService;

    @PostMapping("/api/back/saverecord/")

    public Map<String,String> saveRecord(@RequestParam Map<String,String>map)
    {
        return saveRecordService.saveRecord(map);
    }
}
