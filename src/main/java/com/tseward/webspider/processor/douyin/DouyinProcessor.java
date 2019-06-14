package com.tseward.webspider.processor.douyin;

import com.tseward.webspider.config.WebSiteConfig;
import com.tseward.webspider.service.CrawlDataService;
import com.tseward.webspider.service.CrawlOperationService;
import com.tseward.webspider.utils.dingtalk.DingTalkRobotClient;
import com.tseward.webspider.utils.dingtalk.message.LinkMessage;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import us.codecraft.webmagic.Page;
import us.codecraft.webmagic.Site;
import us.codecraft.webmagic.Spider;
import us.codecraft.webmagic.processor.PageProcessor;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static com.tseward.webspider.utils.dingtalk.WebhookConfig.TEST_URL;

/**
 * Description: 爬取抖音网站视频
 *
 * @author tseward
 * @version 1.0
 * @date 14, 06 2019
 */
@Slf4j
public class DouyinProcessor implements PageProcessor {

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
    public Site getSite() {
        return this.site;
    }

    @Override
    public void process(Page page) {
        //获取首页onclick节点的文本内容
        List<String> onclickNodeList = page.getHtml().xpath("html/body/div/div/ul/li/a/@onclick").all();
        List<String> titles = new ArrayList<>();
        List<String> urls = new ArrayList<>();
        for (String onclick : onclickNodeList) {
            onclick = StringUtils.substringBetween(onclick, "open1('", ")").replace("'", "");
            String[] onclickSplite = onclick.split(",");
            titles.add(onclickSplite[0]);
            urls.add(onclickSplite[2]);
        }


        for (int i = 0; i < titles.size(); i++) {
            LinkMessage linkMessage = new LinkMessage();

            linkMessage.setTitle(titles.get(i));
            linkMessage.setDetailUrl(urls.get(i));
            linkMessage.setContent("抖音，记录美好生活！");
            log.info("title:{}\ndetailUrl:{}", titles.get(i), urls.get(i));
            try {
                DingTalkRobotClient.send(TEST_URL, linkMessage);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

    }

    public static void main(String[] args) {
        Spider.create(new DouyinProcessor()).addUrl(WebSiteConfig.DOUYIN_FIRST_PAGE_URL).thread(5).run();
    }
}
