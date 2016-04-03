package info.androidhive.materialdesign;

import android.net.Uri;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import info.androidhive.materialdesign.model.Gender;

/**
 * Created by troyporter on 3/30/16.
 */
public class GenderFetcher {

    private static final String TAG = "testing";

    public byte[] getUrlBytes (String urlSpec) throws IOException {
        URL url = new URL(urlSpec);
        HttpURLConnection conn = (HttpURLConnection)url.openConnection();
        try{
            ByteArrayOutputStream out = new ByteArrayOutputStream();
            InputStream in = conn.getInputStream();
            if (conn.getResponseCode() != HttpURLConnection.HTTP_OK){
                throw new IOException(conn.getResponseMessage() + ":with 11111" +urlSpec);
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

    public String getUrlString(String urlSpec) throws IOException{
        return new String(getUrlBytes(urlSpec));
    }

    public List<Gender> fetchGenders (){
        List<Gender> genders = new ArrayList<>();

        try {

            //String url = Uri.parse("http://localhost:8000/genders/")
            //String url = Uri.parse("http://192.168.1.41:8000/genders/")
            String url = Uri.parse("http://10.0.2.2:8000/genders/")
                    .buildUpon()
                    .appendQueryParameter("method", "GET")
                    .appendQueryParameter("username","troy")
                    .appendQueryParameter("password","troy")
                    .appendQueryParameter("format", "json")
                    .appendQueryParameter("nojsoncallback", "1")
                    .appendQueryParameter("extras","url_s")
                    .build().toString();

             /*  USED for testing....
            String url = Uri.parse("http://apis.is/concerts")
                    .buildUpon()
                    .appendQueryParameter("type", "GET")
                    //.appendQueryParameter("username","troy")
                    //.appendQueryParameter("password","troy")
                    .appendQueryParameter("dataType", "json")
                    //.appendQueryParameter("nojsoncallback", "1")
                    .appendQueryParameter("extras","url_s")
                    .build().toString();
            */

            Log.e(TAG, url);
            String jsonString = getUrlString(url);
            JSONObject jsonBody = new JSONObject(jsonString);
            //String x = parseApis(jsonBody);
            //Log.e(TAG, x);
            parseGenders(genders, jsonBody);
        } catch(IOException ioe){
            Log.e(TAG, "Failed to fetch items 2222", ioe);
        } catch(JSONException je){
            Log.e(TAG, "Failed to parse JSON", je);
        }
        return genders;
    }

    /* USED THIS FOR TESTING -- PROBABLY DELETE SOON
    private String parseApis(JSONObject jsonBody) throws IOException, JSONException {
        JSONObject apisJsonObject = jsonBody.getJSONObject("results");
        JSONArray apisJsonArray = apisJsonObject.getJSONArray("eventDateName");
        JSONObject sampleFromApis = apisJsonArray.getJSONObject(0);
        return sampleFromApis.getString("eventDateName");
    }
    */

    private void parseGenders(List<Gender> genders, JSONObject jsonBody)
        throws IOException, JSONException {
        JSONObject gendersJsonObject = jsonBody.getJSONObject("genders");
        JSONArray genderJsonArray = gendersJsonObject.getJSONArray("gender");

        //int L = genderJsonArray.length();

        Log.i(TAG, "parseGender");
        //Log.i(TAG, L);

        for (int i=0; i < genderJsonArray.length(); i++) {
            JSONObject genderJsonObject = genderJsonArray.getJSONObject(i);

            String genderName = genderJsonObject.getString("name");
            Log.i(TAG, genderName);
            int genderId = genderJsonObject.getInt("id");

            Gender gender = new Gender(genderName, genderId);
            genders.add(gender);

        }
    }
}
