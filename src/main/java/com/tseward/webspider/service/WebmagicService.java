package com.tseward.webspider.service;

import com.baomidou.mybatisplus.service.IService;
import com.tseward.webspider.model.Webmagic;

import java.util.List;

public interface WebmagicService extends IService<Webmagic> {

    /**
     * 根据站点模糊匹配有效的站点
     * @param webSite
     * @return
     */
    List<Webmagic> getValidWebmagic(String webSite);
}
