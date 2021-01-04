package Spiders;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.Objects;
import java.util.Vector;


public class NewsListSpider extends Spider {
    /* Constants */
    final String urlFormatter = "https://www.sit.edu.cn/_t470/12988/list%d.htm";
    final String urlPrefix = "https://www.sit.edu.cn";

    OkHttpClient client = new OkHttpClient();

    protected String genPageUrl(int page) {
        return String.format(this.urlFormatter, page);
    }

    protected String genFullUrl(String url) {
        if (url.startsWith("/")) {
            return urlPrefix + url;
        }
        return url;
    }

    /*
        Parse news list and extract news items.
     */
    protected Vector<NewsItem> parseNewsList(String pageContent) {
        Document pageObj = Jsoup.parse(pageContent);
        Vector<NewsItem> result = new Vector<>();

        Elements newsElements = pageObj.select("span.news_title a");
        for (Element e : newsElements) {
            String title = e.attr("title");
            String url = e.attr("href");

            result.add(new NewsItem(title, genFullUrl(url)));
        }
        return result;
    }

    /*
        Create a new okhttp3.Request object with a given url.
     */
    protected Request newRequest(String url) {
        return new Request.Builder()
                .url(url)
                .method("GET", null)
                .addHeader("User-Agent", super.defaultUserAgent)
                .build();
    }

    /*
        Download the raw news list page by page index.
     */
    protected String downloadNewsList(int pageIndex) throws SpiderException, IOException {
        if (pageIndex < 0) {
            throw new SpiderException("Page index must greater than zero.");
        }

        Request request = newRequest(genPageUrl(pageIndex));
        Response response = client.newCall(request).execute();

        return Objects.requireNonNull(response.body()).string();
    }

    public Vector<NewsItem> getNewsList(int pageIndex) throws SpiderException, IOException {
        String pageContent = downloadNewsList(pageIndex);
        return parseNewsList(pageContent);
    }
}
