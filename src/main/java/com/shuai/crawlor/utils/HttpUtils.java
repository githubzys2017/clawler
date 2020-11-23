package com.shuai.crawlor.utils;

import com.shuai.crawlor.domain.CrawlMeta;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.ssl.SSLContextBuilder;

import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.util.Map;

public class HttpUtils {
    public static HttpResponse request(CrawlMeta crawlMeta, CrawlHttpConf crawlHttpConf) throws Exception {
        switch (crawlHttpConf.getMethod()) {
            case GET:
                return doGet(crawlMeta, crawlHttpConf);
            case POST:
                return doPost(crawlMeta, crawlHttpConf);
            default:
                return null;
        }
    }

    /**
     * post请求
     * @param crawlMeta
     * @param crawlHttpConf
     * @return
     */
    private static HttpResponse doPost(CrawlMeta crawlMeta, CrawlHttpConf crawlHttpConf) throws Exception {
        SSLContextBuilder builder = new SSLContextBuilder();
        //全部信任，不做身份校验
        builder.loadTrustMaterial(null, (x509Certificates, s) -> true);
        HttpClient httpClient = HttpClientBuilder.create().setSSLContext(builder.build()).build();

        //配置请求参数
        

        return null;
    }

    /**
     * get请求
     * @param crawlMeta
     * @param crawlHttpConf
     * @return
     * @throws Exception
     */
    private static HttpResponse doGet(CrawlMeta crawlMeta, CrawlHttpConf crawlHttpConf) throws Exception {
        SSLContextBuilder builder = new SSLContextBuilder();
        //全部信任，不做身份校验
        builder.loadTrustMaterial(null, (x509Certificates, s) -> true);
        HttpClient httpClient = HttpClientBuilder.create().setSSLContext(builder.build()).build();
        //设置请求参数
        StringBuilder param = new StringBuilder(crawlMeta.getUrl()).append("?");
        for (Map.Entry<String, Object> entry:
             crawlHttpConf.getRequestParams().entrySet()) {
            param.append(entry.getKey()).append("=").append(entry.getValue()).append("&");
        }
        //过滤掉最后一个无效字符
        HttpGet httpGet = new HttpGet(param.substring(0, param.length() - 1));

        //设置请求头
        for (Map.Entry<String, String> entry : crawlHttpConf.getRequestHeaders().entrySet()) {
            httpGet.addHeader(entry.getKey(), entry.getValue());
        }


        return httpClient.execute(httpGet);
    }
}
