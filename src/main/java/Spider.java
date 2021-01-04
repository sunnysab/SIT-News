import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

import java.io.IOException;
import java.util.Objects;

public class Spider {

    public static void main(String[] args) throws IOException {
        OkHttpClient client = new OkHttpClient().newBuilder()
                .build();
        Request request = new Request.Builder()
                .url("https://baidu.com")
                .method("GET", null)
                .addHeader("Requester-ID", "Meow")
                .build();
        Response response = client.newCall(request).execute();

        System.out.println(Objects.requireNonNull(response.body()).string());
    }

}