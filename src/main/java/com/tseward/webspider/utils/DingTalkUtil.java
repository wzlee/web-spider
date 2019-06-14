package com.tseward.webspider.utils;


import com.tseward.webspider.utils.dingtalk.DingTalkRobotClient;
import com.tseward.webspider.utils.dingtalk.message.LinkMessage;

import java.io.IOException;

/**
 * Description: 钉钉机器人
 *
 * @author tseward
 * @version 1.0
 * @date 12, 06 2019
 */

public class DingTalkUtil {


    /**
     * 发送钉钉机器人消息
     *
     * @param title   标题
     * @param url     详情链接
     * @param content 显示内容
     */
    public static void sendDingTalkRobot(String title, String url, String content, String webhook) {
        LinkMessage linkMessage = new LinkMessage();
        linkMessage.setDetailUrl(url);
        linkMessage.setContent(content);
        linkMessage.setTitle(title);
        try {
            DingTalkRobotClient.send(webhook, linkMessage);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
