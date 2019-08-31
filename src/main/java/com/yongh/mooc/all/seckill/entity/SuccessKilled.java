package com.yongh.mooc.all.seckill.entity;

import lombok.Data;
import lombok.ToString;

import java.io.Serializable;
import java.util.Date;

@ToString
@Data
public class SuccessKilled implements Serializable {
    private Long seckillId;

    private Long userPhone;

    private Long state;

    private Date createTime;

    private static final long serialVersionUID = 1L;
}