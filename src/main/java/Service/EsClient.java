package Service;

import org.apache.http.HttpHost;
import org.elasticsearch.client.RestClient;
import org.elasticsearch.client.RestHighLevelClient;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import java.io.IOException;

@Component
public class EsClient {
    private static RestHighLevelClient client;

    public static RestHighLevelClient getClient() {
        return client;
    }

    @PostConstruct
    public void init() {
        client = new RestHighLevelClient(
                RestClient.builder(
                        new HttpHost("localhost", 9200, "http")));
    }

    @PreDestroy
    public void destroy() throws IOException {
        if (client != null) {
            client.close();
        }
    }
}
