package com.shuai.crawlor.service;

public abstract class AbstractJob implements IJob {
    public void beforeRun() {

    }

    public void afterRun() {

    }

    public void run() {
        beforeRun();

        try {
            doFetchPage();
        } catch (Exception e) {
            e.printStackTrace();
        }

        afterRun();
    }

    /**
     * 具体的抓取网页的方法，需要子类来补全实现逻辑
     */
    public abstract void doFetchPage() throws Exception;
}
