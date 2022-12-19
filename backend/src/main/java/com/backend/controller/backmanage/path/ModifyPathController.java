package com.backend.controller.backmanage.path;

import com.backend.service.backmanage.path.ModifyPathService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
public class ModifyPathController {

    @Autowired
    private ModifyPathService modifyPathService;

    @PostMapping("/api/back/modifypath/")
    public Map<String, String> modifyPath(@RequestParam Map<String, String> map) {

        return modifyPathService.modifyPath(map);
    }
}
