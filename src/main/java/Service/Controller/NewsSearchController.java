package Service.Controller;

import Service.EsClient;
import Service.SearchEngine;
import org.elasticsearch.client.RestHighLevelClient;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping("/news")
public class NewsSearchController {

    @GetMapping("/suggestion")
    public SearchEngine.SuggestWord suggest(@RequestParam String q) throws IOException {
        RestHighLevelClient client = EsClient.getClient();
        SearchEngine engine = new SearchEngine(client);

        return engine.suggest(q);
    }

    @GetMapping("/recent")
    public SearchEngine.RecentResults get_recent() throws IOException {
        RestHighLevelClient client = EsClient.getClient();
        SearchEngine engine = new SearchEngine(client);

        return engine.get_recent_item();
    }

    @GetMapping("/today_in_history")
    public SearchEngine.RecentResults get_today_in_history() throws IOException {
        RestHighLevelClient client = EsClient.getClient();
        SearchEngine engine = new SearchEngine(client);

        Date todayDate = new Date();
        SimpleDateFormat formatter = new SimpleDateFormat("MM-dd");
        return engine.find_the_day_in_history(formatter.format(todayDate));
    }

    @GetMapping("/{id}")
    public SearchEngine.ResultItem get(@PathVariable String id) throws IOException {
        RestHighLevelClient client = EsClient.getClient();
        SearchEngine engine = new SearchEngine(client);

        return engine.get(id);
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
        } else if (sort.equals("content")) {
            return engine.queryByContent(q, offset, _count);
        } else {
            return engine.queryNormally(q, offset, _count);
        }
    }
}