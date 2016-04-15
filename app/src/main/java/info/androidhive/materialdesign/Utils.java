package info.androidhive.materialdesign;

import com.squareup.okhttp.Credentials;

import info.androidhive.materialdesign.model.Credential;

/**
 * Created by arnigeirulfarsson on 9.4.2016.
 */
public class Utils {
    public static String url(){
        //This is where we should store our ngrok address for now
        return "http://8fd4be66.ngrok.io";
    }

    public static String authentication(){
        return Credentials.basic("book", "book");
    }

    public static String setKeyValuePair(String key, String value){
        return '\"' + key + '\"' + ": " + '\"' + value + '\"';
    }

    public static String finalizedJsonString (String pseudoJson){
        return "{" + pseudoJson + "}";
    }
}
