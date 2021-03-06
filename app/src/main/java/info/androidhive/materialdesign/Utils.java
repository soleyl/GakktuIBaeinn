package info.androidhive.materialdesign;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import java.util.ArrayList;
import java.util.List;

import info.androidhive.materialdesign.model.Article;

/**
 * Created by arnigeirulfarsson on 9.4.2016.
 */
public class Utils {
    public static String url(){
        //This is where we should store our ngrok address for now
        return "http://864bbb48.ngrok.io";
    }

    public static String setKeyValuePair(String key, String value){
        return '\"' + key + '\"' + ": " + '\"' + value + '\"';
    }

    private static String parseDate(String d){
        String fullDate = d;
        return fullDate.substring(0,10);
    }

    public static String finalizedJsonString (String pseudoJson){
        return "{" + pseudoJson + "}";
    }

    public static List<Article> parseArticles(JSONObject jsonBody) throws JSONException {
        JSONArray articleJsonArray = jsonBody.getJSONArray("results");
        List<Article> articles = new ArrayList<Article>();

        for (int i=0; i < articleJsonArray.length(); i++) {
            JSONObject articleJsonObject = articleJsonArray.getJSONObject(i);

            Article article = new Article();
            article.setTitle(articleJsonObject.getString("title"));
            article.setContent(articleJsonObject.getString("content"));
            article.setImage(articleJsonObject.getString("image"));
            article.setAuthor(articleJsonObject.getString("author"));
            article.setOriginalDate(parseDate(articleJsonObject.getString("created_at")));

            articles.add(article);

        }
        return articles;
    }
}