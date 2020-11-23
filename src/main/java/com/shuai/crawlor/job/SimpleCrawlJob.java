package com.shuai.crawlor.job;

import com.shuai.crawlor.domain.CrawlMeta;
import com.shuai.crawlor.domain.CrawlResult;
import com.shuai.crawlor.service.AbstractJob;
import lombok.Data;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 最简单的爬虫任务
 */
@Data
public class SimpleCrawlJob extends AbstractJob {

    /**
     * 配置项信息
     */
    private CrawlMeta crawlMeta;

    /**
     * 存储爬取的结果
     */
    private CrawlResult crawlResult;

    /**
     * 执行爬取网页
     * @throws Exception
     */
    public void doFetchPage() throws Exception {
        HttpClient httpClient = HttpClients.createDefault();
        HttpGet get = new HttpGet(crawlMeta.getUrl());

        get.addHeader("accept", "text/html,application/xhtml+xml,application/xml;q=0.9,image/webp,*/*;q=0.8");
        get.addHeader("connection", "Keep-Alive");
        get.addHeader("user-agent", "Mozilla/5.0 (Macintosh; Intel Mac OS X 10_12_1) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/58.0.3029.110 Safari/537.36");

        HttpResponse response = httpClient.execute(get);

        String res = EntityUtils.toString(response.getEntity());
        if (response.getStatusLine().getStatusCode() == 200) {
            doParse(res);
        } else {
            crawlResult = new CrawlResult();
            crawlResult.setStatus(response.getStatusLine().getStatusCode(), response.getStatusLine().getReasonPhrase());
        }


//        URL url = new URL(crawlMeta.getUrl());
//        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
//
//        BufferedReader in = null;
//
//        StringBuilder result = new StringBuilder();
//
//        try {
//            // 设置通用的请求属性
//            connection.setRequestProperty("accept", "*/*");
//            connection.setRequestProperty("connection", "Keep-Alive");
//            connection.setRequestProperty("user-agent", "Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.1;SV1)");
//
//            //建立实际链接
//            connection.connect();
//
//            Map<String, List<String>> map = connection.getHeaderFields();
//
//            //遍历所有的响应头字段
//            for (String key : map.keySet()) {
//                System.out.println(key + "--->" + map.get(key));
//            }
//
//            //定义 BufferedReader输入流来读取URL的响应
//            in = new BufferedReader(new InputStreamReader(connection.getInputStream()));
//
//            String line;
//
//            while ((line = in.readLine()) != null) {
//                result.append(line);
//            }
//        } finally {
//            //关闭输入流
//            try {
//                if (in != null) {
//                    in.close();
//                }
//            } catch (IOException e) {
//                e.getStackTrace();
//            }
//        }
//
//        doParse(result.toString());
    }

    private void doParse(String html) {
        Document doc = Jsoup.parse(html);

        Map<String, List<String>> map = new HashMap<String, List<String>>(crawlMeta.getSelectorRules().size());
        for(String rule : crawlMeta.getSelectorRules()) {
            List<String> list = new ArrayList<String>();
            for (Element element : doc.select(rule)) {
                list.add(element.text());
            }
            map.put(rule, list);
        }
        crawlResult = new CrawlResult();
        crawlResult.setUrl(crawlMeta.getUrl());
        crawlResult.setHtmlDoc(doc);
        crawlResult.setResult(map);
    }

}
