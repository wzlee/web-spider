package com.tseward.webspider.model;

import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableName;
import lombok.Data;

import java.io.Serializable;

/**
 * @author:
 * @date: 2018/10/19 09:40
 * @description:
 */
@Data
@TableName(value = "t_webmagic")
public class Webmagic implements Serializable {

    @TableId
    private Long webmagicId;

    private String webmagicIp;

    private String keyword;

    private String createUser;

    private String status;

    private String bizData;

    private String modifyTime;

    private String type;
}
