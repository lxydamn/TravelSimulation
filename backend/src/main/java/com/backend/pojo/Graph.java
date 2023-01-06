package com.backend.pojo;


import com.backend.utils.CityRisk;

import java.util.*;

public class Graph {
    private Map<Integer, List<Integer>> cities;
    private Map<Integer, Path> toPaths;
    private Integer index;
    private List<Path> paths;
    private CityRisk cityRisk;
    private final int[] risks = {2, 5, 9};


    /**
     * 构造邻接表
     * @param paths
     */
    public Graph(List<Path> paths) {
        cityRisk = new CityRisk();
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

        cityRisk = new CityRisk();
        cities = new HashMap<>();
        toPaths = new HashMap<>();

        List<Path> pathList = new ArrayList<>();
        index = 0;
        for(Path p : paths) {

            if(p.getStartTime().before(startTime)) continue;

            pathList.add(p);

            if(cities.containsKey(p.getStartCity())) {

                if(cities.get(p.getStartCity()) == null)  {
                    cities.replace(p.getStartCity(), new ArrayList<Integer>());
                }

                cities.get(p.getStartCity()).add(index);

            } else {
                List<Integer> temp = new ArrayList<>();
                temp.add(index);
                cities.put(p.getStartCity(), temp);
                if(!cities.containsKey(p.getEndCity())) {
                    cities.put(p.getEndCity(), null);
                }
            }
            toPaths.put(index ++, p);
        }

        this.paths = pathList;
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
        if(weightType == 2) {
            return cityRisk.getCityRisk(tail.getStartCity()) +
                    cityRisk.getCityRisk(tail.getEndCity()) + risks[tail.getType() - 1];

        } else if(weightType == 3) {

            return tail.getCost();

        } else {

            if(pre != null) {
                return tail.getEndTime().getTime() - pre.getEndTime().getTime();
            } else {
                return tail.getEndTime().getTime() - tail.getStartTime().getTime();
            }

        }
    }

    public long getWeights(Path pa, Map<Integer,Date> date,Integer weightType) {
        if(weightType == 3) {
            return pa.getCost();
        } else{
            if(date.get(pa.getStartCity()) != null) {
                return pa.getEndTime().getTime() - date.get(pa.getStartCity()).getTime();
            }
            else {
                return pa.getEndTime().getTime() - pa.getStartTime().getTime();
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

    public List<Path> getDijkstraRisk(Integer st, Integer ed, Set<Integer> set)
    {
        //初始化
        List<Integer> unVisited=new ArrayList();
        List<Integer> hasVisited=new ArrayList();
        Map<Integer,Integer> distRisk = new HashMap<>();
        Map<Integer,Date> date = new HashMap<>();
        Map<Integer,Path> paths = new HashMap<>();

        for(Map.Entry<Integer,List<Integer>> entry : cities.entrySet())
        {
            distRisk.put(entry.getKey(),null);
            paths.put(entry.getKey(),null);
            date.put(entry.getKey(),null);
            unVisited.add(entry.getKey());
        }
        List<Integer> temp= cities.get(st);
        if(temp != null)
        {
            for(Integer t : temp)
            {
                if(set.contains(toPaths.get(t).getType()))
                {
                    distRisk.put(toPaths.get(t).getEndCity(),
                            cityRisk.getCityRisk(toPaths.get(t).getStartCity())
                            + cityRisk.getCityRisk(toPaths.get(t).getEndCity())
                                    + risks[toPaths.get(t).getType() - 1]);
                    paths.put(toPaths.get(t).getEndCity(),toPaths.get(t));
                    date.put(toPaths.get(t).getEndCity(),toPaths.get(t).getEndTime());
                }
            }
        }
        hasVisited.add(st);
        unVisited.remove(st);

        //算法部分
        while(!unVisited.isEmpty()){
            Integer city = -1;
            Integer min = Integer.MAX_VALUE;
            for(Map.Entry<Integer,Integer> entry : distRisk.entrySet())
            {
                if(unVisited.contains(entry.getKey()) && entry.getValue() != null)
                {
                    if(entry.getValue() < min) {
                        city = entry.getKey();
                        min = entry.getValue();
                    }
                }
            }
            if(city == -1) break;
            hasVisited.add(city);
            unVisited.remove(city);

            List<Integer> temp1= cities.get(city);
            if(temp1 != null) {
                for(Integer t : temp1) {
                    if(set.contains(toPaths.get(t).getType())){
                        if(date.get(city) == null || toPaths.get(t).getStartTime().after(date.get(city))) {
                            Integer t1 = cityRisk.getCityRisk(toPaths.get(t).getStartCity())
                                    + cityRisk.getCityRisk(toPaths.get(t).getEndCity())
                                    + risks[toPaths.get(t).getType() - 1];
                            if(distRisk.get(toPaths.get(t).getEndCity()) == null || distRisk.get(city) == null) continue;
                            if(distRisk.get(toPaths.get(t).getEndCity()) > distRisk.get(city) + t1) {
                                date.put(toPaths.get(t).getEndCity(),toPaths.get(t).getEndTime());
                                paths.put(toPaths.get(t).getEndCity(),toPaths.get(t));
                            }
                        }
                    }
                }
            }
        }
        Integer x = ed;
        List<Path> allPath = new ArrayList<>();
        while(paths.get(x) != null)
        {
            allPath.add(paths.get(x));
            x = paths.get(x).getStartCity();
        }
        Collections.reverse(allPath);
        if(!x.equals(st)) return null;
        return allPath;
    }

    public List<Path> getDijkstra(Integer st, Integer ed, Integer weightType, Set<Integer> set)
    {
        //初始化
        List<Integer> unVisited=new ArrayList();
        List<Integer> hasVisited=new ArrayList();
        Map<Integer,Path> dist= new HashMap<>();
        Map<Integer,Date> date = new HashMap<>();
        Map<Integer,Path> paths = new HashMap<>();

        for(Map.Entry<Integer,List<Integer>> entry : cities.entrySet())
        {
            dist.put(entry.getKey(),null);
            paths.put(entry.getKey(),null);
            date.put(entry.getKey(),null);
            unVisited.add(entry.getKey());
        }
        List<Integer> temp= cities.get(st);
        if(temp != null)
        {
            for(Integer t : temp)
            {
                if(set.contains(toPaths.get(t).getType()))
                {
                    dist.put(toPaths.get(t).getEndCity(),toPaths.get(t));
                    paths.put(toPaths.get(t).getEndCity(),toPaths.get(t));
                    date.put(toPaths.get(t).getEndCity(),toPaths.get(t).getEndTime());
                }
            }
        }
        hasVisited.add(st);
        unVisited.remove(st);

        //算法部分
        while(!unVisited.isEmpty()){
            Integer city = -1;
            Path min = null;
            boolean mark = true;
            for(Map.Entry<Integer,Path> entry : dist.entrySet())
            {
                if(unVisited.contains(entry.getKey()) && entry.getValue() != null)
                {
                    if(mark) {
                        min = entry.getValue();
                        mark = false;
                        continue;
                    }
                    if(getWeights(entry.getValue(),date,weightType) < getWeights(min,date,weightType)) {
                        city = entry.getKey();
                        min = entry.getValue();
                    }
                }
            }
            if(city == -1) break;
            hasVisited.add(city);
            unVisited.remove(city);

            List<Integer> temp1= cities.get(city);
            if(temp1 != null) {
                for(Integer t : temp1) {
                    if(set.contains(toPaths.get(t).getType())){
                        if(date.get(city) == null || toPaths.get(t).getStartTime().after(date.get(city))) {
                            long t1 = getWeights(toPaths.get(t),date,weightType);
                            if(getWeights(dist.get(toPaths.get(t).getEndCity()),date,weightType)
                                    > getWeights(dist.get(city),date,weightType) + t1) {
                                Path tsb = new Path();
                                tsb.setEndCity(toPaths.get(t).getEndCity());
                                tsb.setStartTime(dist.get(toPaths.get(t).getEndCity()).getStartTime());
                                tsb.setEndTime(toPaths.get(t).getEndTime());
                                tsb.setCost((int) (getWeights(dist.get(city),date,weightType) + t1));
                                dist.put(toPaths.get(t).getEndCity(),tsb);
                                paths.put(toPaths.get(t).getEndCity(),toPaths.get(t));
                            }
                        }
                    }
                }
            }
        }
        Integer x = ed;
        List<Path> allPath = new ArrayList<>();
        while(paths.get(x) != null)
        {
            allPath.add(paths.get(x));
            x = paths.get(x).getStartCity();
        }
        Collections.reverse(allPath);
        if(!x.equals(st)) return null;
        return allPath;
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

        HashMap<Integer, Path>[] recordPath = new HashMap[transit + 1];

        long[] dist = new long[900000];
        long[] backup;

        Arrays.fill(dist, Long.MAX_VALUE / 2);
        dist[st] = 0;

        if(paths == null) {
            return null;
        }

        for(int i = 1; i <= transit; i ++) {
            recordPath[i] = new HashMap<>();
            backup = dist.clone();
            for(Path p : paths) {
                Integer sc = p.getStartCity(), ec = p.getEndCity();

                if(recordPath[i - 1] != null) {
                    if(recordPath[i - 1].containsKey(sc) &&
                            recordPath[i - 1].get(sc).getEndTime().after(p.getStartTime()))
                        continue;
                }
                if(!set.contains(p.getType()))
                    continue;
                Path pre = null;
                if(recordPath[i - 1] != null && recordPath[i - 1].containsKey(sc))
                    pre = recordPath[i - 1].get(sc);

                Long w = getWeights(pre, p, weightType);


                // 排除掉非法路径
                if(dist[ec] > backup[sc] + w) {
                    dist[ec] = backup[sc] + w;
                    if(recordPath[i].containsKey(ec)) recordPath[i].replace(ec, p);
                    else recordPath[i].put(ec, p);
                }
            }

        }

        if(dist[ed] == Long.MAX_VALUE / 2) {
            return null;
        }

        List<Path> pathList = new ArrayList<>();
        Integer cur = ed;

        int i = transit + 1;
        while(!cur.equals(st)) {

            int j = i - 1;
            for(j = i - 1; j >= 1; j --) {
                if(recordPath[j].containsKey(cur)) {
                    break;
                }
            }
            i = j;
            if(i <= 0) break;
            pathList.add(recordPath[i].get(cur));
            cur = recordPath[i].get(cur).getStartCity();
        }
        return pathList;
    }
}
