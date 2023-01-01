package com.backend.pojo;


import com.backend.utils.CityRisk;

import java.util.*;

public class Graph {
    private Map<Integer, List<Integer>> cities;
    private Map<Integer, Path> toPaths;
    private Integer index;
    private List<Path> paths;





    /**
     * 构造邻接表
     * @param paths
     */
    public Graph(List<Path> paths) {
        this.paths = paths;
        cities = new HashMap<>();
        toPaths = new HashMap<>();
        index = 0;
        for(Path p : paths) {
            if(cities.containsKey(p.getStartCity())) {
                cities.get(p.getStartCity()).add(index);
            } else {
                List<Integer> temp = new ArrayList<>();
                temp.add(index);
                cities.put(p.getStartCity(), temp);
            }
            toPaths.put(index ++, p);
        }

    }

    /**
     * 筛除不合法时间的构造
     * @param paths
     * @param startTime
     */
    public Graph(List<Path> paths, Date startTime) {

        cities = new HashMap<>();
        toPaths = new HashMap<>();
        index = 0;
        for(Path p : paths) {

            if(p.getStartTime().before(startTime)) continue;

            if(cities.containsKey(p.getStartCity())) {
                cities.get(p.getStartCity()).add(index);
            } else {
                List<Integer> temp = new ArrayList<>();
                temp.add(index);
                cities.put(p.getStartCity(), temp);
            }
            toPaths.put(index ++, p);
        }

    }

    /**
     * 计算权值
     * pre 为该路径的上一条
     * @param pre
     * @param tail
     * @param weightType
     * @return
     */
    public long getWeights(Path pre, Path tail, Integer weightType) {
        if(weightType == 1) {
            CityRisk cityRisk = new CityRisk();
            return cityRisk.getCityRisk(tail.getStartCity()) + cityRisk.getCityRisk(tail.getEndCity());
        } else if(weightType == 2) {
            return tail.getCost();
        } else{
            if(pre != null) {
                return tail.getEndTime().getTime() - pre.getEndTime().getTime();
            } else {
                return tail.getEndTime().getTime() - tail.getStartTime().getTime();
            }
        }
    }

    public void dfs(Integer st, Integer ed, Stack<Path> stk, List<List<Path>> res, Set<Integer> set) {
        if(st.equals(ed)) {
            List<Path> pathList = new ArrayList<>(stk);
            res.add(pathList);
            return;
        }

        List<Integer> ne = cities.get(st);
        if(ne == null) {
            return;
        }
        for(Integer i : ne) {
            Path p = toPaths.get(i);

            if(!stk.empty() && p.getStartTime().before(stk.peek().getEndTime())) continue;
            if(!set.contains(p.getType())) continue;
            stk.push(p);
            dfs(p.getEndCity(), ed, stk, res, set);
            stk.pop();
        }

    }
    public Planed getDfs(Integer st, Integer ed,Set<Integer> set) {
        Stack<Path> stk = new Stack<>();
        List<List<Path>> paths = new ArrayList<>();
        dfs(st, ed, stk, paths, set);

        List<Planed> planeds = new ArrayList<>();

        for(List<Path> ps : paths) {
            planeds.add(new Planed(ps));
        }

        planeds.sort((o1, o2) -> o1.getCost() - o2.getCost());

        if(planeds.isEmpty()) return null;
        return planeds.get(0);
    }


    public List<Path> bellmanFord(Integer st, Integer ed, Integer transit, Integer weightType, Set<Integer> set) {

        Map<Integer, Path> recordPath = new HashMap<>();

        long[] dist = new long[900000];
        long[] backup;

        Arrays.fill(dist, Long.MAX_VALUE / 2);
        dist[st] = 0;
        for(int i = 0; i < transit; i ++) {

            backup = dist.clone();
            for(Path p : paths) {
                Integer sc = p.getStartCity(), ec = p.getEndCity();

                if(recordPath.containsKey(sc) && recordPath.get(sc).getEndTime().after(p.getStartTime()))
                    continue;
                if(!set.contains(p.getType()))
                    continue;

                Path pre = null;
                if(recordPath.containsKey(sc))  pre = recordPath.get(sc);
                Long w = getWeights(pre, p, weightType);

                // 排除掉非法路径

                if(dist[ec] > backup[sc] + w) {
                    dist[ec] = backup[sc] + w;
                    if(recordPath.containsKey(ec)) recordPath.replace(ec, p);
                    else recordPath.put(ec, p);
                }
            }

        }


        if(dist[ed] == Long.MAX_VALUE / 2) {
            return null;
        }

        List<Path> pathList = new ArrayList<>();
        Integer cur = ed;

        while(!cur.equals(st)) {
            pathList.add(recordPath.get(cur));
            cur = recordPath.get(cur).getStartCity();
        }

        return pathList;
    }
}