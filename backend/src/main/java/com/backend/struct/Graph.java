package com.backend.struct;

import java.util.List;
import java.util.Map;

public class Graph {

    private Map<City, List<Integer>> nextCity;
    private Map<Integer, City> toNextCity;
    private Map<Integer, Integer> toCost;
    private Map<Integer, Long> toTimes; // 时间戳
    private Map<Integer, Integer> toType;

}
