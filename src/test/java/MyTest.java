import com.shuai.crawlor.domain.CrawlMeta;
import com.shuai.crawlor.domain.CrawlResult;
import com.shuai.crawlor.job.SimpleCrawlJob;
import org.junit.Test;

import java.util.HashSet;
import java.util.Set;

public class MyTest {

    @Test
    public void testFetch() throws InterruptedException {
        String url = "https://my.oschina.net/u/566591/blog/1047233";
        Set<String> selectRule = new HashSet<String>();
        selectRule.add("div[class=article-box__header] h1"); // 博客标题
//        selectRule.add("div[class=content]"); // 博客正文

        CrawlMeta crawlMeta = new CrawlMeta();
        crawlMeta.setUrl(url);
        crawlMeta.setSelectorRules(selectRule);

        SimpleCrawlJob crawlJob = new SimpleCrawlJob();
        crawlJob.setCrawlMeta(crawlMeta);

        Thread thread = new Thread(crawlJob, "test-crawlJob");
        thread.start();

        //确保线程执行完毕
        thread.join();

        CrawlResult crawlResult = crawlJob.getCrawlResult();
        System.out.println(crawlResult.getResult().get("div[class=article-box__header] h1"));

    }
}
