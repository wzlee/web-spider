package com.tseward.webspider.service.impl;

import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.tseward.webspider.mapper.CrawlOperationMapper;
import com.tseward.webspider.model.CrawlOperationModel;
import com.tseward.webspider.service.CrawlOperationService;
import org.springframework.stereotype.Service;

/**
 * @author:
 * @date: 2018/10/17 16:10
 * @description:
 */
@Service
public class CrawlOperationServiceImpl extends ServiceImpl<CrawlOperationMapper, CrawlOperationModel> implements CrawlOperationService {

    @Override
    public CrawlOperationModel queryCrawlOperationByWebSite(String webSite) {
        CrawlOperationModel queryModel = new CrawlOperationModel();
        queryModel.setWebsite(webSite);
        return this.baseMapper.selectOne(queryModel);
    }
}
