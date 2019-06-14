package com.tseward.webspider.service.impl;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.mapper.Wrapper;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.tseward.webspider.mapper.WebmagicMapper;
import com.tseward.webspider.model.Webmagic;
import com.tseward.webspider.service.WebmagicService;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author:
 * @date: 2018/10/17 16:02
 * @description:
 */
@Service
public class WebmagicServiceImpl extends ServiceImpl<WebmagicMapper, Webmagic> implements WebmagicService {


    @Override
    public List<Webmagic> getValidWebmagic(String webSite) {
        Wrapper<Webmagic> webmagicWrapper = new EntityWrapper<>();
        webmagicWrapper.like("WEBMAGIC_IP", webSite).eq("STATUS", "1");
        return this.selectList(webmagicWrapper);
    }
}
