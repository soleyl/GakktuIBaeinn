package info.androidhive.materialdesign;

import android.net.Uri;
import android.util.Base64;
import android.util.Log;

import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.DataInputStream;
import java.io.DataOutput;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.Authenticator;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.PasswordAuthentication;
import java.net.ProtocolException;
import java.net.URL;
import java.net.URLConnection;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import android.util.Base64;

import info.androidhive.materialdesign.model.AccessToken;
import info.androidhive.materialdesign.model.Article;
import info.androidhive.materialdesign.model.Category;
import info.androidhive.materialdesign.model.Gender;



/**
 * Created by troyporter on 4/05/16.
 */
public class ArticleFetcher {

    private static final String TAG = "testing";

    public List<Article> fetchArticles (){
        List<Article> articles = new ArrayList<>();

        try {

            String url = Uri.parse(Utils.url() + "/articles/")
                    .buildUpon()
                    .appendQueryParameter("method", "GET")
                    .appendQueryParameter("format", "json")
                    .appendQueryParameter("extras", "url_s")
                    .build().toString();

            Log.e(TAG, url);

            String jsonString = getUrlString(url);
            JSONObject jsonBody = new JSONObject(jsonString);
            parseArticles(articles, jsonBody);
        } catch(IOException ioe){
            Log.e(TAG, "Failed to fetch items", ioe);
        } catch(JSONException je){
            Log.e(TAG, "Failed to parse JSON", je);
        }
        return articles;
    }


    public String getUrlString(String urlSpec) throws IOException{
        return new String(getUrlBytes(urlSpec));
    }

    public byte[] getUrlBytes (String urlSpec) throws IOException {
        URL url = new URL(urlSpec);

        HttpURLConnection conn = (HttpURLConnection)url.openConnection();
        try{
            ByteArrayOutputStream out = new ByteArrayOutputStream();
            InputStream in = conn.getInputStream();
            if (conn.getResponseCode() != HttpURLConnection.HTTP_OK){
                throw new IOException(conn.getResponseMessage() + ":with" +urlSpec);
            }
            int bytesRead =0;
            byte[] buffer = new byte[1024];
            while ((bytesRead = in.read(buffer)) > 0){
                out.write(buffer, 0, bytesRead);
            }
            out.close();
            return out.toByteArray();
        } finally {conn.disconnect();}

    }

    private void parseArticles(List<Article> articles, JSONObject jsonBody)
            throws IOException, JSONException {
        JSONArray articleJsonArray = jsonBody.getJSONArray("results");

        for (int i=0; i < articleJsonArray.length(); i++) {
            JSONObject genderJsonObject = articleJsonArray.getJSONObject(i);

            String articleTitle = genderJsonObject.getString("title");
            String articleContent = genderJsonObject.getString("content");

            Article article = new Article();
            article.setTitle(articleTitle);
            article.setContent(articleContent);
            String testTitle = article.getTitle();
            Log.i(TAG, "ARTICLE TITLE");
            Log.i(TAG, testTitle);

            articles.add(article);

        }
    }
}

