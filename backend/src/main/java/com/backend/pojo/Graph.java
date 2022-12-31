package com.backend.pojo;


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


    public void dfs(Integer st, Integer ed, Stack<Path> stk, List<List<Path>> res) {
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

            stk.push(p);
            dfs(p.getEndCity(), ed, stk, res);
            stk.pop();
        }

    }
    public Planed getDfs(Integer st, Integer ed) {
        Stack<Path> stk = new Stack<>();
        List<List<Path>> paths = new ArrayList<>();
        dfs(st, ed, stk, paths);

        List<Planed> planeds = new ArrayList<>();

        for(List<Path> ps : paths) {
            planeds.add(new Planed(ps));
        }

        planeds.sort((o1, o2) -> o1.getCost() - o2.getCost());

        if(planeds.isEmpty()) return null;
        return planeds.get(0);
    }

    public Planed getBellmanPlan(Integer st, Integer ed, Integer type) {
        List<Path> pathList = bellmanFord(st, ed, 1);

        if(pathList == null) {
            return null;
        }
        System.out.println(pathList);
        return new Planed(pathList);
    }

    /**
     * 限制中转次数的最短路
     * @param st
     * @param ed
     * @param transit
     * @return
     */
    public List<Path> bellmanFord(Integer st, Integer ed, Integer transit) {

        Map<Integer, Path> recordPath = new HashMap<>();

        int[] dist = new int[900000];
        int[] backup;

        Arrays.fill(dist, Short.MAX_VALUE);
        dist[st] = 0;

        for(int i = 0; i < transit; i ++) {

            backup = dist.clone();
            for(Path p : paths) {
                Integer sc = p.getStartCity(), ec = p.getEndCity(), w = p.getCost();

                // 排除掉非法路径
                if(recordPath.containsKey(sc) && recordPath.get(sc).getEndTime().after(p.getStartTime()))
                    continue;

                if(dist[ec] > backup[sc] + w) {
                    dist[ec] = backup[sc] + w;
                    if(recordPath.containsKey(ec)) recordPath.replace(ec, p);
                    else recordPath.put(ec, p);
                }
            }

        }


        if(dist[ed] == Short.MAX_VALUE) {
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
