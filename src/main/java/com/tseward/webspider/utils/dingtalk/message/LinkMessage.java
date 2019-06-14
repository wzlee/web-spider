package com.tseward.webspider.utils.dingtalk.message;

import com.alibaba.fastjson.JSON;
import lombok.Data;

import javax.validation.constraints.NotNull;
import java.util.HashMap;
import java.util.Map;

/**
 * Description: link消息类型
 *
 * @author tseward
 * @version 1.0
 * @date 12, 06 2019
 */
@Data
public class LinkMessage implements Message {

    private static final String MSG_TYPE = "link";

    @NotNull
    private String title;

    @NotNull
    private String content;

    @NotNull
    private String detailUrl;

    private String picUrl;

    @Override
    public String toJsonString() {

        Map<String, String> innerLinkNode = new HashMap<>();
        innerLinkNode.put("text", content);
        innerLinkNode.put("title", title);
        innerLinkNode.put("picUrl", picUrl);
        innerLinkNode.put("messageUrl", detailUrl);

        Map<String, Object> message = new HashMap<>();
        message.put("msgtype", MSG_TYPE);
        message.put("link", innerLinkNode);


        return JSON.toJSONString(message);
    }
}
