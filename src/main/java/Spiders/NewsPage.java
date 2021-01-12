package Spiders;

import java.util.Vector;

/*
    Spiders.NewsItem class.
 */
public class NewsPage {
    // News title. From <a>..</a> or the title of the news' page.
    private String title;
    // Complete url string, starting with a protocol.
    private String url;
    // Published by
    private String author;
    // Publishing time
    private String datetime;
    // Main content
    private Vector<Paragraph> content;

    public NewsPage(String  title, String  url) {
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

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getDatetime() {
        return datetime;
    }

    public void setDatetime(String datetime) {
        this.datetime = datetime;
    }

    public Vector<Paragraph> getContent() {
        return content;
    }

    public void setContent(Vector<Paragraph> content) {
        this.content = content;
    }

    @Override
    public String toString() {
        return String.format("Spiders.NewsItem (%s): %s, by %s, on %s", title, url, author, datetime);
    }
}
