package com.backend.controller.web;

import com.backend.service.web.DeleteRecordService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
public class DeleteRecordController {

    @Autowired
    private DeleteRecordService deleteRecordService;

    @PostMapping("/api/back/deleterecord/")
    public Map<String, String> deleteRecord(@RequestParam Map<String, String> map) {

        return deleteRecordService.deleteRecord(map);
    }
}
