package com.tseward.webspider.model;

import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableName;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * @author:
 * @date: 2018/10/17 15:27
 * @description:
 */
@Data
@TableName(value = "t_crawl_data")
public class CrawlDataModel implements Serializable {

    /**
     * id
     */
    @TableId
    private Long id;

    /**
     * 链接url
     */
    private String url;

    /**
     * 标题
     */
    private String title;

    /**
     * 关键字
     */
    private String keyword;

    /**
     * 文章发布时间
     */
    @TableField(value = "release_time")
    private Date releaseTime;

    /**
     * 创建时间
     */
    @TableField(value = "crt_time")
    private Date crtTime;

    /**
     * 来源站点
     */
    private String website;

    @Override
    public String toString() {
        return "CrawlDataModel{" +
                "url='" + url + '\'' +
                ", title='" + title + '\'' +
                ", keyword='" + keyword + '\'' +
                ", releaseTime=" + releaseTime +
                ", crtTime=" + crtTime +
                ", website='" + website + '\'' +
                '}';
    }
}
