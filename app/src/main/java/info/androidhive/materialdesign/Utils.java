package info.androidhive.materialdesign;

/**
 * Created by arnigeirulfarsson on 9.4.2016.
 */
public class Utils {
    public static String url(){
        //This is where we should store our ngrok address for now
        return "https://98ccea39.ngrok.io";
    }

    public static String setKeyValuePair(String key, String value){
        return '\"' + key + '\"' + ": " + '\"' + value + '\"';
    }

    public static String finalizedJsonString (String pseudoJson){
        return "{" + pseudoJson + "}";
    }
}
