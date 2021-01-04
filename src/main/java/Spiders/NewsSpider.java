package Spiders;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import javax.print.Doc;
import java.io.IOException;
import java.util.Objects;

public class NewsSpider extends Spider {
    protected String url;
    protected OkHttpClient client = new OkHttpClient();

    public NewsSpider(OkHttpClient  client, String  url) {
        if (client != null) {
            this.client = client;
        }
        this.url = url;
    }

    public NewsSpider(String  url) {
        this.url = url;
    }

    private String selectText(Document  content, String selector) {
        return content.selectFirst(selector).text();
    }

    protected NewsPage parseNewsPage(String  content) {
        Document pageObj = Jsoup.parse(content);

        String title = selectText(pageObj, "h1.arti_title");
        String author = selectText(pageObj, "span.arti_publisher");
        String publishTime = selectText(pageObj, "span.arti_update");
        String text = selectText(pageObj, "div.wp_articlecontent");

        NewsPage result = new NewsPage(title, url);
        result.setAuthor(author);
        result.setDatetime(publishTime);
        result.setContent(text);

        return result;
    }

    /*
        Download the raw news page by page index.
     */
    protected String downloadNews(String  url) throws SpiderException, IOException {
        Request  request = new Request.Builder()
                .url(url)
                .method("GET", null)
                .addHeader("User-Agent", super.defaultUserAgent)
                .build();

        Response response = client.newCall(request).execute();

        return Objects.requireNonNull(response.body()).string();
    }

    public NewsPage run() throws SpiderException, IOException {
        String page = downloadNews(url);
        return parseNewsPage(page);
    }
}
