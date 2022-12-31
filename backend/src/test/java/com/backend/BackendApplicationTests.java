package com.backend;

import com.backend.mapper.PathMapper;
import com.backend.mapper.UserMapper;
import com.backend.pojo.Graph;
import com.backend.pojo.User;
import com.backend.utils.CityRisk;
import org.junit.jupiter.api.Test;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import javax.annotation.PostConstruct;

@SpringBootTest
class BackendApplicationTests {

    @Autowired
    private PathMapper pathMapper;
    @Test
    void contextLoads() {
        Graph graph = new Graph(pathMapper.selectList(null));
        graph.getBellmanPlan(110100, 120100, 1);
    }

}
