package info.androidhive.materialdesign;

import android.util.Log;
import org.json.JSONException;
import org.json.JSONObject;
import java.io.IOException;
import info.androidhive.materialdesign.model.Article;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;


public class API {
    OkHttpClient client = new OkHttpClient();
    public static final MediaType JSON
            = MediaType.parse("application/json; charset=utf-8");

    String run(String url) throws IOException {
        Request request = new Request.Builder()
                .url(url)
                .header("Authorization", Utils.authentication())
                .build();
        Response response = client.newCall(request).execute();
        return response.body().string();
    }

    String post(String url, String json) throws IOException{
        RequestBody body = RequestBody.create(JSON, json);
        Request request = new Request.Builder()
                .url(url)
                .header("Authorization", Utils.authentication())
                .post(body)
                .build();
        Response response = client.newCall(request).execute();
        return response.body().string();
    }

    public static JSONObject get(String objectType) throws IOException{
        API api = new API();
        String response = api.run(Utils.url() +"/" + objectType);
        JSONObject responseJSON = new JSONObject();
        try{
            responseJSON = new JSONObject(response);
        }
        catch(JSONException j){
            Log.e("error", j.toString());
        }
        return responseJSON;
    }

    public String postArticle(Article article) throws IOException{
        API api = new API();
        String titleData = article.getTitle();
        String contentData = article.getContent();
        String json = Utils.finalizedJsonString(
                Utils.setKeyValuePair("title", titleData)
                        + "," + Utils.setKeyValuePair("content", contentData));
        return api.post(Utils.url() + "/articles/", json);
    }
}
