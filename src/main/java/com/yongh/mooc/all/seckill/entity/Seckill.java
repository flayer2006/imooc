package com.yongh.mooc.all.seckill.entity;

import lombok.Data;
import lombok.ToString;

import java.io.Serializable;
import java.util.Date;

@ToString
@Data
public class Seckill implements Serializable {
    private Long seckillId;

    private String name;

    private Integer number;

    private Date startTime;

    private Date endTime;

    private Date createTime;

    private static final long serialVersionUID = 1L;
}