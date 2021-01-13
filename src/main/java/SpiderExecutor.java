import Spiders.*;
import com.google.gson.Gson;
import org.apache.http.HttpHost;
import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.action.index.IndexResponse;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestClient;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.common.xcontent.XContentType;

import java.io.IOException;
import java.util.UUID;
import java.util.Vector;

class SpiderInstance {
    protected RestHighLevelClient client;

    private void saveNewsPage(NewsPage  newsPage) throws IOException {
        Gson gson = new Gson();
        String serializedNews = gson.toJson(newsPage);

        System.out.println(serializedNews);
        IndexRequest request = new IndexRequest("news");
        request.id(UUID.randomUUID().toString());
        request.source(serializedNews, XContentType.JSON);
        IndexResponse indexResponse = client.index(request, RequestOptions.DEFAULT);
        System.out.println(indexResponse);
    }

    public SpiderInstance() {
        client = new RestHighLevelClient(
                RestClient.builder(
                        new HttpHost("localhost", 9200, "http")));
    }

    public void run() throws IOException, Spiders.Spider.SpiderException {
        /* Init spider */
        int  lastPage = 1;
        NewsListSpider  s = new NewsListSpider();
        Vector<NewsItem>  lastList;

        while (!(lastList = s.getNewsList(lastPage)).isEmpty()) {
            for (NewsItem  item: lastList) {
                NewsSpider  newsSpider = new NewsSpider(item.getUrl());
                NewsPage    page = newsSpider.run();

                saveNewsPage(page);
            }
            lastPage++;
        }
    }
}


public class SpiderExecutor {
    public static void main(String[] args) throws IOException, Spiders.Spider.SpiderException {
        SpiderInstance spider = new SpiderInstance();

        spider.run();
    }
}
