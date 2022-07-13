package com.lw.entity;

import lombok.Data;

@Data
public class Message {
    private String srcId;
    private String destId;
    private String content;
    private Integer status;
}
