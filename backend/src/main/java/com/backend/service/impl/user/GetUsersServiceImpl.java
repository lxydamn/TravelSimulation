package com.backend.service.impl.user;

import com.backend.mapper.UserMapper;
import com.backend.pojo.User;
import com.backend.service.backmanage.user.GetUsersService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class GetUsersServiceImpl implements GetUsersService {

    @Autowired
    private UserMapper userMapper;

    @Override
    public List<User> getUsers() {
        return userMapper.selectList(null);
    }
}
