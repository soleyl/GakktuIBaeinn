package info.androidhive.materialdesign;

import android.util.Log;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import info.androidhive.materialdesign.model.Article;
import info.androidhive.materialdesign.model.Gender;
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
        String TAG= "postarticle";
        Log.e(TAG,url);
        Log.e(TAG,json);
        Response response = client.newCall(request).execute();
        return response.body().string();
    }

    String setasKeyValuePair (String key, String value){
        //return "{" + '\"' + "name" + '\"' + ": " + '\"' + name + '\"' + "}";
        return '\"' + key + '\"' + ": " + '\"' + value + '\"';
    }

    String finalizedJsonString (String pseudoJson){
        return "{" + pseudoJson + "}";
    }

    public String postarticle(Article article) throws IOException{
        PostArticle postArticle = new PostArticle();

        String titleData = article.getTitle();
        String contentData = article.getContent();
        String json = finalizedJsonString(setasKeyValuePair("title", titleData) + "," + setasKeyValuePair("content", contentData));
        String response = postArticle.post("http://ee6ce353.ngrok.io/articles/", json);
        //String json = genderJson("magical");
        //String response = postArticle.post("http://e260395e.ngrok.io/genders/", json);
        //String TAG= "postarticle";
        //Log.e(TAG,response);
        return response;
    }

}
