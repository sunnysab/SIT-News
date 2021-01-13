package Service;

import org.apache.http.HttpHost;
import org.elasticsearch.client.RestClient;
import org.elasticsearch.client.RestHighLevelClient;

import java.io.IOException;

public class EsClient {
    final static String  defaultHost = "localhost";
    final static int     defaultPort = 9200;

    String  host;
    int     port;
    RestHighLevelClient client;

    public EsClient(String  host, int port) {
        this.host = host;
        this.port = port;
    }

    public EsClient() {
        this(defaultHost, defaultPort);
    }

    public void connect() {
        client = new RestHighLevelClient(
                RestClient.builder(
                        new HttpHost(this.host, this.port, "http")
                )
        );
    }

    public RestHighLevelClient getClient() {
        return this.client;
    }

    public void close() throws IOException {
        if (null != client) {
            client.close();
        }
    }
}
