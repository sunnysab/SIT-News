package Spiders;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.nodes.Node;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.Objects;
import java.util.Vector;


public class NewsSpider extends Spider {
    protected String url;
    protected OkHttpClient client = new OkHttpClient();
    // protected Pattern  domain_pattern = Pattern.compile("^|[/]([a-zA-Z0-9]+.sit.edu.cn)");

    public NewsSpider(OkHttpClient  client, String  url) {
        if (client != null) {
            this.client = client;
        }
        this.url = url;
    }

    public NewsSpider(String  url) {
        this(null, url);
    }

    protected static String  domain() {
        return "www.sit.edu.cn";
    }

    private String selectText(Document  content, String selector) {
        return content.selectFirst(selector).text();
    }

    protected Vector<Paragraph> parseNewsContent(Element  mainPage) {
        Elements p_nodes = mainPage.select("p");
        Vector<Paragraph>  results = new Vector<>();

        for (Element  p: p_nodes) {
            Node img_node = p.selectFirst("img");
            if (img_node != null) { // Image node
                String image_url = String.format("https://%s%s", domain(), img_node.attr("src"));
                results.add(Paragraph.image(image_url));
            } else {  // Pure text node
                if (!Objects.equals(p.text(), ""))
                    results.add(Paragraph.text(p.text()));
            }
        }
        return results;
    }

    protected NewsPage parseNewsPage(String  content) {
        Document pageObj = Jsoup.parse(content);

        String title = selectText(pageObj, "h1.arti_title");
        String author = selectText(pageObj, "span.arti_publisher").substring(4); // Ignore prefix "作者:"
        String publishTime = selectText(pageObj, "span.arti_update").substring(5); // Ignore prefix "发布时间:"
        Element content_node = pageObj.selectFirst("div.wp_articlecontent");

        NewsPage result = new NewsPage(title, url);
        result.setAuthor(author);
        result.setDatetime(publishTime);

        Vector<Paragraph>  paragraphs = parseNewsContent(content_node);
        result.setContent(paragraphs);

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
