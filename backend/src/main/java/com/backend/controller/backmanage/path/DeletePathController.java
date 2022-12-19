package com.backend.controller.backmanage.path;

import com.backend.service.backmanage.path.DeletePathService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
public class DeletePathController {

    @Autowired
    private DeletePathService deletePathService;

    @PostMapping("/api/back/deletepath/")
    public Map<String, String> deletePath(@RequestParam Integer id) {
        return deletePathService.deletePath(id);
    }
}
