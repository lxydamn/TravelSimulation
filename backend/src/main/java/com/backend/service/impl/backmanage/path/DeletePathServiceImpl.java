package com.backend.service.impl.backmanage.path;


import com.backend.mapper.PathMapper;
import com.backend.pojo.Path;
import com.backend.service.backmanage.path.DeletePathService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
public class DeletePathServiceImpl implements DeletePathService {

    @Autowired
    private PathMapper pathMapper;
    @Override
    public Map<String, String> deletePath(Integer id) {

        QueryWrapper<Path> queryWrapper = new QueryWrapper<>();
        Map<String, String> resp = new HashMap<>();

        try {
            pathMapper.deleteById(id);
        } catch (Exception e) {
            resp.put("error_message", "路径不存在");
            return resp;
        }

        resp.put("err_or_message", "success");
        return resp;

    }
}
