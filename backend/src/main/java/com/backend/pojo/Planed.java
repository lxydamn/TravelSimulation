package com.backend.pojo;

import com.backend.mapper.CityMapper;
import com.backend.utils.CityRisk;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Planed {
    private List<Path> paths;

    private Integer cost;

    private Integer risk;

    private Integer transit;

    private Date startTime;

    private Date endTime;

    public Planed(List<Path> ps) {
        paths = ps;
        cost = risk = transit = 0;
        startTime = ps.get(0).getStartTime();
        endTime = ps.get(ps.size() - 1).getEndTime();
        transit = ps.size() - 1;
        CityRisk cityRisk = new CityRisk();

        for(Path p : ps) {
            cost += p.getCost();
            risk += cityRisk.getCityRisk(p.getStartCity());
            risk += cityRisk.getCityRisk(p.getEndCity());
        }
    }
}
