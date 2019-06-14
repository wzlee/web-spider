package com.tseward.webspider.processor.douyin;

import com.tseward.webspider.config.WebSiteConfig;
import com.tseward.webspider.service.CrawlDataService;
import com.tseward.webspider.service.CrawlOperationService;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.springframework.beans.factory.annotation.Autowired;
import us.codecraft.webmagic.Page;
import us.codecraft.webmagic.Site;
import us.codecraft.webmagic.Spider;
import us.codecraft.webmagic.processor.PageProcessor;

import java.util.ArrayList;
import java.util.List;

/**
 * Description:
 *
 * @author tseward
 * @version 1.0
 * @date 14, 06 2019
 */
public class ExtractDownloadUrlProcessor implements PageProcessor {

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
        String s = page.getHtml().toString();
        System.out.println(s);

    }

    public static void main(String[] args) {
        String url = "https://www.iesdouyin.com/share/video/6681486767541062915/?region=CN&mid=6681594570850994947&u_code=0&titleType=title";
        Spider.create(new ExtractDownloadUrlProcessor()).addUrl(WebSiteConfig.DOUYIN_FIRST_PAGE_URL).thread(5).run();
//        String key="webdriver.chrome.driver";
//        //谷歌驱动在你本地位置
//        String value="C:/Users/i01007600684/Desktop/chromedriver.exe";
//        System.setProperty(key,value);
//
//        WebDriver driver = new ChromeDriver();
//        driver.get(url);
//
//        WebElement iframe = driver.findElement(By.xpath("/html/body/div/script[3]/text()"));
//        driver.switchTo().frame(iframe);
//
//        ///html/body/div/script[3]/text()
//        List<WebElement> elements = driver.findElements(By.xpath("/html/body/div/script[3]/text()"));
//        //List<String> playLists = new ArrayList<String>();
//        System.out.println(elements);
    }
}
