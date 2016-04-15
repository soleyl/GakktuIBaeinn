package info.androidhive.materialdesign;

import android.util.Log;
import java.io.IOException;
import info.androidhive.materialdesign.model.Article;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

/**
 * Created by troyporter on 4/8/16.
 */
public class PostArticle {
    public static final MediaType JSON
            = MediaType.parse("application/json; charset=utf-8");

    OkHttpClient client = new OkHttpClient();

    String post(String url, String json) throws IOException{
        RequestBody body = RequestBody.create(JSON, json);
        Request request = new Request.Builder()
                .url(url)
                .post(body)
                .build();
        Response response = client.newCall(request).execute();
        return response.body().string();
    }

    public String postarticle(Article article) throws IOException{
        PostArticle postArticle = new PostArticle();
        String titleData = article.getTitle();
        String contentData = article.getContent();
        String json = Utils.finalizedJsonString(
                Utils.setKeyValuePair("title", titleData)
                        + "," + Utils.setKeyValuePair("content", contentData));
        return postArticle.post(Utils.url() + "/articles/", json);
    }

}
