package info.androidhive.materialdesign;

import android.util.Log;

import com.squareup.okhttp.Credentials;

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


    Response run(String url, String authentication) throws IOException {
        Request request = new Request.Builder()
                .url(url)
                .header("Authorization", authentication)
                .build();
        return client.newCall(request).execute();
    }

    String post(String url, String json, String authentication) throws IOException{
        RequestBody body = RequestBody.create(JSON, json);
        Request request = new Request.Builder()
                .url(url)
                .header("Authorization", authentication)
                .post(body)
                .build();
        Response response = client.newCall(request).execute();
        return response.body().string();
    }


    public static Boolean verifyUser(String user, String password) throws IOException{
        API api = new API();
        Response response = api.run(Utils.url() + "/userprofiles", Credentials.basic(user, password));
        int answer = response.code();
        Log.e("code Status", String.valueOf(answer));
        return response.code()==200;
    }


    public static JSONObject get(String objectType, String authentication) throws IOException{
        API api = new API();
        Response response = api.run(Utils.url() +"/" + objectType, authentication);
        JSONObject responseJSON = new JSONObject();
        try{
            responseJSON = new JSONObject(response.body().string());
        }
        catch(JSONException j){
            Log.e("errorInGet", j.toString());
        }

        return responseJSON;
    }


    public String postArticle(Article article, String authentication) throws IOException{
        API api = new API();
        String titleData = article.getTitle();
        String contentData = article.getContent();
        String json = Utils.finalizedJsonString(
                Utils.setKeyValuePair("title", titleData)
                        + "," + Utils.setKeyValuePair("content", contentData));
        return api.post(Utils.url() + "/articles/", json, authentication);
    }
}