package com.backend.controller.backmanage.city;

import com.backend.pojo.User;
import com.backend.service.backmanage.user.GetUsersService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class GetUsersController {

    @Autowired
    private GetUsersService getUsersService;

    @GetMapping("/api/back/users")
    public List<User> getUsers() {
        return getUsersService.getUsers();
    }
}
