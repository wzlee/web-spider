package com.tseward.webspider.processor.chinatax;

import com.tseward.webspider.model.CrawlDataModel;
import com.tseward.webspider.model.CrawlOperationModel;
import com.tseward.webspider.service.CrawlDataService;
import com.tseward.webspider.service.CrawlOperationService;
import com.tseward.webspider.utils.DateUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import us.codecraft.webmagic.Page;
import us.codecraft.webmagic.Site;
import us.codecraft.webmagic.Spider;
import us.codecraft.webmagic.processor.PageProcessor;
import us.codecraft.webmagic.selector.Selectable;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Description：国家税务局
 *
 * @author tseward
 * @date 05, 16 2019
 */
@Component
@Slf4j
public class ChinaTaxProcessor implements PageProcessor {

    public static final String SYMBOL = "..";

    public static final String MAIN_WEBSITE = "http://www.chinatax.gov.cn";

    private static final String WEB_SITE = "http://www.chinatax.gov.cn/n810341/index.html";

    /**
     * 获取最后更新时间
     */
    @Autowired
    private CrawlOperationService crawlOperationService;

    @Autowired
    private CrawlDataService crawlDataService;

    /**
     * 抓取网站的相关配置
     */
    private Site site = Site.me().setRetryTimes(3).setSleepTime(1000);

    @Override
    public void process(Page page) {
        Selectable selectable = page.getHtml().xpath("//*[@id=\"menua0\"]/table/tbody/tr/td/div/dl");
        //最后更新日期
        Date lastUpdateTime = DateUtil.parseToDate(selectable.xpath("*/dd[1]/a/span/text()").toString());
        CrawlOperationModel crawlOperationModel = crawlOperationService.queryCrawlOperationByWebSite(WEB_SITE);
        if (crawlOperationModel == null) {
            crawlOperationModel = new CrawlOperationModel();
            crawlOperationModel.setLastCrawDataTime(lastUpdateTime);
            crawlOperationModel.setWebsite(WEB_SITE);
            crawlOperationService.insert(crawlOperationModel);
            getPageInfo(selectable);
        } else {
            Date lastUpdateTimeFromDB = crawlOperationModel.getLastCrawDataTime();
            if (lastUpdateTime != null && (lastUpdateTime.getTime() < lastUpdateTimeFromDB.getTime())) {
                getPageInfo(selectable);
                crawlOperationModel.setLastCrawDataTime(lastUpdateTime);
                crawlOperationService.insertOrUpdate(crawlOperationModel);
            }
        }
    }

    @Override
    public Site getSite() {
        return this.site;
    }

    //@Scheduled(fixedRate = 1000 * 30)
    @Scheduled(cron = "0 0 0/1 * * ?") //每小时
    public void execute() {
        Spider.create(this).addUrl(WEB_SITE).thread(5).run();
    }


    /**
     * 补全获取到的不完整url地址
     *
     * @param urls
     */
    private void completedDetailUrl(List<String> urls) {
        if (CollectionUtils.isEmpty(urls)) {
            return;
        }
        for (int i = 0; i < urls.size(); i++) {
            String url = urls.get(i);
            if (url.contains(SYMBOL)) {
                url = url.replace(SYMBOL, MAIN_WEBSITE);
                urls.set(i, url);
            }
        }
    }

    /**
     * 从爬取页面上获取标题、链接和发布时间
     *
     * @param selectable
     */
    private void getPageInfo(Selectable selectable) {
        List<String> detailUrls = selectable.xpath("*/dd/a/@href").all();
        List<String> titles = selectable.xpath("*/dd/a/text()").all();
        List<String> publishTimeList = selectable.xpath("*/dd/a/span/text()").all();

        //补全url
        completedDetailUrl(detailUrls);
        //爬取的错误数据记录
        int errorDataCount = 0;
        SimpleDateFormat myFmt = new SimpleDateFormat("yyyy年MM月dd日");
        for (int i = 0; i < detailUrls.size(); i++) {
            String title = titles.get(i);
            String detailUrl = detailUrls.get(i);
            if (StringUtils.isEmpty(title) || StringUtils.isEmpty(detailUrl)) {
                errorDataCount += 1;
                titles.remove(title);
                detailUrls.remove(detailUrl);
            }
            Date publishTime = DateUtil.parseToDate(publishTimeList.get(i - errorDataCount));
//            DingTalkUtil.sendDing(title, detailUrl,
//                    "发布时间：" + myFmt.format(publishTime),FINANCE);
        }

        //数据落地
        persistent(titles, detailUrls, publishTimeList);
    }

    /**
     * 数据落库
     *
     * @param titles      标题
     * @param detailUrls  链接
     * @param releaseTime 发布时间
     */
    private void persistent(List<String> titles, List<String> detailUrls, List<String> releaseTime) {

        //数据存储
        List<CrawlDataModel> datas = new ArrayList<>();

        if (titles.size() != detailUrls.size() || titles.size() != releaseTime.size()) {
            return;
        }

        for (int i = 0; i < titles.size(); i++) {
            //数据库存储
            CrawlDataModel crawlDataModel = new CrawlDataModel();
            crawlDataModel.setUrl(detailUrls.get(i));
            crawlDataModel.setTitle(titles.get(i));
            crawlDataModel.setKeyword("税");
            crawlDataModel.setReleaseTime(DateUtil.parseToDate(releaseTime.get(i)));
            crawlDataModel.setCrtTime(new Date());
            crawlDataModel.setWebsite(WEB_SITE);

            datas.add(crawlDataModel);
        }
        crawlDataService.insertBatch(datas);
    }

}
