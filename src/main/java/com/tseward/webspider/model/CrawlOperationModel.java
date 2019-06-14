package com.tseward.webspider.model;

import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableName;
import lombok.Data;

import java.util.Date;

/**
 * @author:
 * @date: 2018/10/17 15:48
 * @description:
 */
@Data
@TableName(value = "t_crawl_operation")
public class CrawlOperationModel {

    /**
     * id
     */
    @TableId
    private Long id;

    /**
     * 来源站点
     */
    private String website;

    /**
     * 最新爬取的数据的时间，指网站内容的时间
     */
    @TableField(value = "last_craw_data_time")
    private Date lastCrawDataTime;
}
