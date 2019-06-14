package com.tseward.webspider.utils;

import java.util.*;

/**
 * Description：关键词过滤
 *
 * @author tseward
 * @date 2018/10/23
 */
public class KeyWordFilter {

    /**
     * 关键字库
     */
    private static HashMap keyWordMap;

    //最小匹配规则
    public static int minMatchTYpe = 1;

    //最大匹配规则
    public static int maxMatchType = 2;

    /**
     * 获取文本中包含的关键词
     *
     * @param txt
     * @param matchType
     * @return
     */
    public static Set<String> getKeyWord(String txt, List<String> keyWordList, int matchType) {
        initKeyWord(keyWordList);
        Set<String> keyWordSet = new HashSet<String>();
        for (int i = 0; i < txt.length(); i++) {
            //判断是否包含关键字字符
            int length = checkKeyWord(txt, i, matchType);
            //存在,加入list中
            if (length > 0) {
                keyWordSet.add(txt.substring(i, i + length));
                //减1的原因，是因为for会自增
                i = i + length - 1;
            }
        }

        return keyWordSet;
    }

    /**
     * 初始化关键词库
     *
     * @return
     */
    @SuppressWarnings("rawtypes")
    private static Map initKeyWord(List<String> keyWordList) {
        try {
            Set<String> keyWordSet = getKeyWordSet(keyWordList);
            //将关键词库加入到HashMap中
            addKeyWordToHashMap(keyWordSet);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return keyWordMap;
    }

    /**
     * 获取关键词库
     *
     * @return
     */
    private static Set<String> getKeyWordSet(List<String> keyWordList) {
        Set<String> set = new HashSet<>();
        for (String keyword : keyWordList) {
            if (keyword.contains(",")) {
                Collections.addAll(set, keyword.split(","));
            } else {
                set.add(keyword);
            }
        }
        return set;
    }

    @SuppressWarnings({"rawtypes", "unchecked"})
    private static void addKeyWordToHashMap(Set<String> keyWordSet) {
        //初始化关键词容器，减少扩容操作
        keyWordMap = new HashMap(keyWordSet.size());
        String key = null;
        Map nowMap = null;
        Map<String, String> newWorMap = null;
        //迭代keyWordSet
        Iterator<String> iterator = keyWordSet.iterator();
        while (iterator.hasNext()) {
            //关键字
            key = iterator.next();
            nowMap = keyWordMap;
            for (int i = 0; i < key.length(); i++) {
                //转换成char型
                char keyChar = key.charAt(i);
                //获取
                Object wordMap = nowMap.get(keyChar);
                //如果存在该key，直接赋值
                if (wordMap != null) {
                    nowMap = (Map) wordMap;
                    //不存在则，则构建一个map，同时将isEnd设置为0，因为它不是最后一个
                } else {
                    newWorMap = new HashMap<String, String>();
                    //不是最后一个
                    newWorMap.put("isEnd", "0");
                    nowMap.put(keyChar, newWorMap);
                    nowMap = newWorMap;
                }

                if (i == key.length() - 1) {
                    //最后一个
                    nowMap.put("isEnd", "1");
                }
            }
        }
    }

    /**
     * 检查文字中是否包含关键字符，检查规则如下
     *
     * @param txt
     * @param beginIndex
     * @param matchType
     * @return， 如果存在，则返回关键词字符的长度，不存在返回0
     */
    @SuppressWarnings({"rawtypes"})
    private static int checkKeyWord(String txt, int beginIndex, int matchType) {
        //关键词结束标识位：用于关键词只有1位的情况
        boolean flag = false;
        //匹配标识数默认为0
        int matchFlag = 0;
        char word;
        Map nowMap = keyWordMap;
        for (int i = beginIndex; i < txt.length(); i++) {
            word = txt.charAt(i);
            //获取指定key
            nowMap = (Map) nowMap.get(word);
            //存在，则判断是否为最后一个
            if (nowMap != null) {
                //找到相应key，匹配标识+1
                matchFlag++;
                //如果为最后一个匹配规则,结束循环，返回匹配标识数
                if ("1".equals(nowMap.get("isEnd"))) {
                    //结束标志位为true
                    flag = true;
                    //最小规则，直接返回,最大规则还需继续查找
                    if (KeyWordFilter.minMatchTYpe == matchType) {
                        break;
                    }
                }
            } else {     //不存在，直接返回
                break;
            }
        }
        //长度必须大于等于1，为词
        if (matchFlag < 2 || !flag) {
            matchFlag = 0;
        }
        return matchFlag;
    }
}
