package com.tseward.webspider.processor.pipeline;

import com.tseward.webspider.model.CrawlDataModel;
import com.tseward.webspider.service.CrawlDataService;
import com.tseward.webspider.service.CrawlOperationService;
import com.tseward.webspider.utils.DateUtil;
import com.tseward.webspider.utils.KeyWordFilter;
import lombok.Getter;
import lombok.Setter;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import us.codecraft.webmagic.ResultItems;
import us.codecraft.webmagic.Task;
import us.codecraft.webmagic.pipeline.Pipeline;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.PrintStream;
import java.util.*;


/**
 * Description：将爬取到的内容持久化或输出到文件
 *
 * @author tseward
 * @date 2018/10/18
 */
@Component
@Setter
@Getter
public class RepositoryPipeline implements Pipeline {

    @Autowired
    private CrawlDataService crawlDataService;

    @Autowired
    private CrawlOperationService crawlOperationService;

    /**
     * 详情页头部
     */
    private static final String URL_HEADER = "http://bxjg.circ.gov.cn";

    /**
     * 关键字列表
     */
    private List<String> keyWordList;

    /**
     * 文件存放路径
     */
    private String path;

    /**
     * 为详情链接的相对路径添加请求主站点地址
     */
    private String urlHeader;

    /**
     * 输出文件的编号（为防止文件名重复，后面的结果被覆盖）
     */
    private int fileNo;

    /**
     * 最新爬取到的数据的发布时间
     */
    private long lastCrawlTime;

    @SuppressWarnings("unchecked")
    @Override
    public void process(ResultItems resultItems, Task task) {
        //从爬取结果中获取
        Map<String, Object> mapResults = resultItems.getAll();
        List<String> titleList = (List<String>) mapResults.get("titles");
        List<String> detailUrlList = (List<String>) mapResults.get("detailURLs");
        List<String> publishTimeList = (List<String>) mapResults.get("publishTime");
        String webmagicIp = (String) mapResults.get("webmagicIp");
        //存储结果
        Set<CrawlDataModel> results = new HashSet<>();
        if (titleList != null) {
            for (int i = 0; i < titleList.size(); i++) {
                String title = titleList.get(i);
                String detailUrl = detailUrlList.get(i);
                String publishTime = publishTimeList.get(i);
                if (StringUtils.isNotBlank(title)) {
                    //根据关键字列表过滤，并获取包含的关键字
                    Set<String> containsKeyWord = KeyWordFilter.getKeyWord(title, getKeyWordList(), 1);
                    if (containsKeyWord.size() > 0) {
                        CrawlDataModel crawlDataModel = new CrawlDataModel();
                        crawlDataModel.setKeyword(containsKeyWord.toString());
                        crawlDataModel.setWebsite(webmagicIp);
                        crawlDataModel.setTitle(title);
                        crawlDataModel.setUrl(URL_HEADER + detailUrl);
                        crawlDataModel.setCrtTime(new Date());
                        crawlDataModel.setReleaseTime(DateUtil.parseToDate(publishTime));
                        results.add(crawlDataModel);
                    }
                }
            }
        }
        //持久化
        if (StringUtils.isEmpty(getPath())) {
            for (CrawlDataModel result : results) {
                if (result != null) {
                    crawlDataService.insert(result);
                }
            }
        } else {
            //打印到文件
            try {
                for (CrawlDataModel result : results) {
                    File file = new File(getPath() + "result" + (++fileNo) + ".txt");
                    PrintStream ps = new PrintStream(new FileOutputStream(file));
                    if (result != null) {
                        ps.println(result);
                    }
                }
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
        }
    }
}
