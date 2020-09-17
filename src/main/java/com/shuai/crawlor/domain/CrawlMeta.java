package com.shuai.crawlor.domain;

import lombok.Data;

import java.util.HashSet;
import java.util.Set;

/**
 * 配置实体
 */
@Data
public class CrawlMeta {
    /**
     * 待爬取的网址
     */
    private String url;

    /**
     * 获取指定内容的规则，因为一个网页中，你可能获取多个不同的内容， 所以放在集合中
     */
    private Set<String> selectorRules;

    public Set<String> getSelectorRules() {
        return selectorRules != null ? selectorRules : new HashSet<String>();
    }
}
