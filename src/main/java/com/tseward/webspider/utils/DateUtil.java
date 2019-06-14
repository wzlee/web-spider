package com.tseward.webspider.utils;

import org.apache.commons.lang3.StringUtils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Description：
 *
 * @author tseward
 * @date 05, 16 2019
 */
public class DateUtil {
    /**
     * 日期转化
     *
     * @param time
     * @return
     */
    public static Date parseToDate(String time) {
        if (StringUtils.isEmpty(time)) {
            return null;
        }
        String afterDeleteBrackets;
        //保监会日期截取
        afterDeleteBrackets = StringUtils.substringBetween(time, "(", ")");
        if (afterDeleteBrackets != null) {
            afterDeleteBrackets = "20" + afterDeleteBrackets;
        }
        //税务局日期截取
        afterDeleteBrackets = StringUtils.substringBetween(time, "[", "]");
        if (afterDeleteBrackets != null) {
            afterDeleteBrackets = "2019-" + afterDeleteBrackets;
        }
        //日期转换
        SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd");
        Date date = new Date();
        try {
            date = sf.parse(afterDeleteBrackets);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return date;
    }
}
