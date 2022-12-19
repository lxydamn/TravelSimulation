package com.backend.service.impl.backmanage.path;

import com.backend.mapper.PathMapper;
import com.backend.pojo.Path;
import com.backend.service.backmanage.path.GetPathsService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class GetPathsServiceImpl implements GetPathsService {
    @Autowired
    private PathMapper pathMapper;
    @Override
    public Map<String, List<Path>> getPaths() {
       Map<String, List<Path>> resp = new HashMap<>();
        QueryWrapper<Path> qw = new QueryWrapper<>();

       resp.put("car", pathMapper.selectList(qw.eq("type", 1)));
       qw.clear();
       resp.put("train", pathMapper.selectList(qw.eq("type", 2)));
       qw.clear();
       resp.put("plane", pathMapper.selectList(qw.eq("type", 3)));

       return resp;
    }
}
