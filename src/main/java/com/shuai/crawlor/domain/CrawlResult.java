package com.shuai.crawlor.domain;

import lombok.*;
import org.jsoup.nodes.Document;

import java.util.List;
import java.util.Map;

/**
 * 爬虫抓取结果
 */
@Data
public class CrawlResult {

    /**
     * 爬取的网址
     */
    private String url;

    /**
     * 待爬取的网址对应的doc结构
     */
    private Document htmlDoc;

    /**
     * 返回状态
     */
    private Status status;

    /**
     * 选择的结果，key为选择规则，value为根据规则匹配的结果
     */
    private Map<String, List<String>> result;

    public void setStatus(int code, String msg) {
        status = new Status(code, msg);
    }

    @Getter
    @Setter
    @ToString
    @AllArgsConstructor
    static class Status {
        private int code;

        private String msg;
    }
}
