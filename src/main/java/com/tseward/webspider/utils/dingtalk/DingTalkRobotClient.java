package com.tseward.webspider.utils.dingtalk;



import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import com.tseward.webspider.utils.dingtalk.message.TextMessage;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;

import com.alibaba.fastjson.JSONObject;

/**
 * @author
 */
public class DingTalkRobotClient {

    private static HttpClient httpclient = HttpClients.createDefault();

    public static SendResult send(String webhook, Message message) throws IOException{

        HttpPost httppost = new HttpPost(webhook);
        httppost.addHeader("Content-Type", "application/json; charset=utf-8");
        StringEntity se = new StringEntity(message.toJsonString(), "utf-8");
        httppost.setEntity(se);

        SendResult sendResult = new SendResult();
        HttpResponse response = httpclient.execute(httppost);
        if (response.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
            String result = EntityUtils.toString(response.getEntity());
            JSONObject obj = JSONObject.parseObject(result);

            Integer errcode = obj.getInteger("errcode");
            sendResult.setErrorCode(errcode);
            sendResult.setErrorMsg(obj.getString("errmsg"));
            sendResult.setIsSuccess(errcode.equals(0));
        }
        return sendResult;
    }

    public static boolean sendRedisError() {
    	boolean success = true;
    	TextMessage message = new TextMessage("");
    	List<String> atMobiles = new ArrayList<>(1);
    	atMobiles.add("");
    	message.setAtMobiles(atMobiles);
		try {
			success = send(WebhookConfig.TEST_URL, message).isSuccess();
		} catch (IOException e) {
			success = false;
		}
		return success;
	}
}


