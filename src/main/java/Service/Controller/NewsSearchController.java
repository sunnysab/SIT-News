package Service.Controller;

import Service.EsClient;
import Service.SearchEngine;
import org.elasticsearch.client.RestHighLevelClient;
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
    public SearchEngine.QueryResults query(@RequestParam String q,
                                           @RequestParam(required = false) String sort,
                                           @RequestParam(required = false) Integer page,
                                           @RequestParam(required = false) Integer count) throws IOException {
        Integer _page = page == null ? 0 : page < 0 ? 0 : page;
        Integer _count = count == null ? 10 : count < 1 ? 10 : count;
        int offset = _page * _count;

        RestHighLevelClient client = EsClient.getClient();
        SearchEngine engine = new SearchEngine(client);

        if (sort == null) {
            return engine.queryNormally(q, offset, _count);
        } else if (sort.equals("author")) {
            return engine.queryByAuthor(q, offset, _count);
        } else if (sort.equals("title")) {
            return engine.queryByTitle(q, offset, _count);
        } else {
            return engine.queryNormally(q, offset, _count);
        }
    }
}