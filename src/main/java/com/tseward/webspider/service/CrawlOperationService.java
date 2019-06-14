package com.tseward.webspider.service;

import com.baomidou.mybatisplus.service.IService;
import com.tseward.webspider.model.CrawlOperationModel;

public interface CrawlOperationService extends IService<CrawlOperationModel> {

    /**
     * 查询站点的爬取记录
     * @param webSite
     * @return
     */
    CrawlOperationModel queryCrawlOperationByWebSite(String webSite);
}
