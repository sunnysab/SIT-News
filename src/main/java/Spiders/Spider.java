package Spiders;


/*
    Abstract class of Spider
 */
public abstract class Spider {

    // SpiderException
    public static class SpiderException extends Exception {
        public SpiderException(String message) {
            super(message);
        }
    }

    protected final String  defaultUserAgent = "SIT-Spiders.NewsSpider";
}