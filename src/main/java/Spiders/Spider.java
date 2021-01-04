package Spiders;


public class Spider {
    public static class SpiderException extends Exception {
        public SpiderException(String message) {
            super(message);
        }
    }

    protected final String  defaultUserAgent = "SIT-Spiders.NewsSpider";
}