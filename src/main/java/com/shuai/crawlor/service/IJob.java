package com.shuai.crawlor.service;

/**
 * 定义接口，定义一些辅助方法，可以打印日志，记录耗时等
 */
public interface IJob extends Runnable {

    /**
     * 在job执行之前回调的方法
     */
    void beforeRun();

    /**
     * 在job执行之后回调的方法
     */
    void afterRun();
}
