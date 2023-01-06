package com.backend.pojo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Plan {
    private Integer userId;
    @TableId(type = IdType.AUTO)
    private Integer id;
    private String content;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm", timezone = "Asia/Shanghai")
    private Date createTime;
    private Integer startCity;
    private Integer endCity;
    private Integer cost;
    private Integer strategy;
    private Integer transit;
    private Integer risk;
}
