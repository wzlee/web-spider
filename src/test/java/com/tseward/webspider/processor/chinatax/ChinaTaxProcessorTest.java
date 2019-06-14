package com.tseward.webspider.processor.chinatax;

import com.tseward.webspider.pipeline.RepositoryPipeline;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * Description：
 *
 * @author tseward
 * @date 05, 16 2019
 */
public class ChinaTaxProcessorTest {

    @Autowired
    private ChinaTaxProcessor chinaTaxProcessor;

    @Autowired
    private RepositoryPipeline repositoryPipeline;

    public static void main(String[] args) {
       String url = "../n810341/n810755/c4308939/content.html";
        System.out.println(url.replace("..", "http://www.chinatax.gov.cn"));
        System.out.println(url);
    }

}