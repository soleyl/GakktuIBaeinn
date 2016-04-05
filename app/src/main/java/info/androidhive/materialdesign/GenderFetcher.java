package info.androidhive.materialdesign;

import android.net.Uri;
import android.util.Base64;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Authenticator;
import java.net.HttpURLConnection;
import java.net.PasswordAuthentication;
import java.net.ProtocolException;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import android.util.Base64;

import info.androidhive.materialdesign.model.AccessToken;
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

            String url = Uri.parse("http://55627451.ngrok.io/genders")
                    .buildUpon()
                    .appendQueryParameter("type", "GET")
                    .appendQueryParameter("dataType", "json")
                    .appendQueryParameter("extras", "url_s")
                    .build().toString();

            Log.e(TAG, url);
            String jsonString = getUrlString(url);
            JSONObject jsonBody = new JSONObject(jsonString);
            parseGenders(genders, jsonBody);
        } catch(IOException ioe){
            Log.e(TAG, "Failed to fetch items 2222", ioe);
        } catch(JSONException je){
            Log.e(TAG, "Failed to parse JSON", je);
        }
        return genders;
    }

    private void parseGenders(List<Gender> genders, JSONObject jsonBody)
        throws IOException, JSONException {
        JSONArray genderJsonArray = jsonBody.getJSONArray("results");

        Log.i(TAG, "parseGender");

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
