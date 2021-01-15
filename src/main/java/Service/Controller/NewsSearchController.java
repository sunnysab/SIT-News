package Service.Controller;

import Service.EsClient;
import Service.SearchEngine;
import org.elasticsearch.client.RestHighLevelClient;
import org.jetbrains.annotations.NotNull;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;

@RestController
@RequestMapping("/news")
public class NewsSearchController {

    @GetMapping("/suggestion")
    public SearchEngine.SuggestWord suggest(@RequestParam String q) throws IOException {
        RestHighLevelClient client = EsClient.getClient();
        SearchEngine engine = new SearchEngine(client);

        return engine.suggest(q);
    }

    @GetMapping("/{id}")
    public String get(@PathVariable String id) {
        return id;
    }

    @GetMapping("/")
    public SearchEngine.QueryResults query(@NotNull @RequestParam String q, @RequestParam int page,
                                           @RequestParam int count) throws IOException {
        int _page = page < 0 ? 0 : page;
        int _count = count < 1 ? 20 : count;
        RestHighLevelClient client = EsClient.getClient();
        SearchEngine engine = new SearchEngine(client);

        return engine.queryNormally(q, _page * _count, _count);
    }
}