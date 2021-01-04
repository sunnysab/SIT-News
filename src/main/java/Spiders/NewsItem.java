package Spiders;

/*
    Spiders.NewsItem class.
 */
public class NewsItem {
    // News title. From <a>..</a> or the title of the news' page.
    private String title;
    // Complete url string, starting with a protocol.
    private String url;

    public NewsItem(String  title, String  url) {
        this.title = title;
        this.url = url;
    }

    /* Default getter and setters. */
    public String getTitle() {
        return title;
    }

    public String getUrl() {
        return url;
    }

    @Override
    public String toString() {
        return String.format("Spiders.NewsItem (%s): %s", this.title, this.url);
    }
}