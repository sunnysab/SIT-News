import Spiders.*;

import java.io.IOException;
import java.util.Vector;


public class SpiderExecutor {
    public static void main(String[]  args) throws Spider.SpiderException, IOException, InterruptedException {
        int  lastPage = 1;
        NewsListSpider  s = new NewsListSpider();
        Vector<NewsItem>  lastList;

        while (!(lastList = s.getNewsList(lastPage)).isEmpty()) {
            lastList = s.getNewsList(lastPage);
            for (NewsItem  item: lastList) {
                NewsSpider  newsSpider = new NewsSpider(item.getUrl());
                NewsPage    page = newsSpider.run();

                System.out.println(page);
                Thread.sleep(1000);
            }
            lastPage++;
        }
    }
}
