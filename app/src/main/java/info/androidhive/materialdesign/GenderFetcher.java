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

    public void authenticateWithServer(URL url) throws IOException{
        Log.e(TAG, "in AUTHENTICATE");


        String formData = "username=troy&password=troy&grant_type=password";
        //<client_id>:<client_secret>


        String string = "troy:troy";
        byte[] bytes = string.getBytes("UTF-8");

        //String header = "Basic " + Base64.encodeToString(bytes,Base64.DEFAULT);
        //String header = "Basic " + "PGNsaWVudF9pZD46PGNsaWVudF9zZWNyZXQ+";
        //<username>:<password>
        //String header = "Basic " + "PHVzZXJuYW1lPjo8cGFzc3dvcmQ+";
        final String header = "basic " + Base64.encodeToString("troy:troy".getBytes(), Base64.NO_WRAP);


        //String tokenUrl = "http://55627451.ngrok.io/api-auth/";
        //String tokenUrl = "http://55627451.ngrok.io/api-auth/login/";
        String tokenUrl = "http://55627451.ngrok.io/genders/";

        //Maybe we need the "Secret_Code"?
        //String keyFromCode = "@sg+65@6x$v8062%fwe+zbzf(5e3i_^t7o&jdp#r(!2g%*&(jf";

        HttpURLConnection c
                = (HttpURLConnection) new URL(tokenUrl).openConnection();
        c.setUseCaches(false);
        //c.setDoOutput(true);
        //c.addRequestProperty("Authorization", header);
        c.setRequestProperty("Authorization", header);
        c.addRequestProperty("Content-Type", "application/x-www-form-urlencoded");
        //c.addRequestProperty("SECRET_KEY", keyFromCode);
        c.setRequestMethod("GET");
        //c.setRequestProperty("charset", "utf-8");
        //c.setRequestProperty("Content-Length", Integer.toString(formData.length()));

        OutputStream out = c.getOutputStream();
        out.write(formData.getBytes(StandardCharsets.UTF_8));

        InputStream in = c.getInputStream();
        String myToken = convertStreamToString(in);
        Log.e(TAG,myToken);

        //AccessToken token = new TokenMapper().readValue(in, AccessToken.class);
        //return token;
    }

    static String convertStreamToString(java.io.InputStream is) {
        java.util.Scanner s = new java.util.Scanner(is).useDelimiter("\\A");
        return s.hasNext() ? s.next() : "";
    }

    public byte[] getUrlBytes (String urlSpec) throws IOException {
        URL url = new URL(urlSpec);

        HttpURLConnection conn = (HttpURLConnection)url.openConnection();
        try{
            ByteArrayOutputStream out = new ByteArrayOutputStream();
            InputStream in = conn.getInputStream();
            if (conn.getResponseCode() != HttpURLConnection.HTTP_OK){
                throw new IOException(conn.getResponseMessage() + ":with 11111" +urlSpec);
            }
            Log.e(TAG,"in try");
            int bytesRead =0;
            byte[] buffer = new byte[1024];
            while ((bytesRead = in.read(buffer)) > 0){
                out.write(buffer, 0, bytesRead);
                Log.e(TAG, "in write");
            }
            Log.e(TAG, "after write");
            out.close();
            Log.e(TAG, "closed");
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

   /*
    private String parseApis(JSONObject jsonBody) throws IOException, JSONException {
        JSONObject apisJsonObject = jsonBody.getJSONObject("results");
        JSONArray apisJsonArray = apisJsonObject.getJSONArray("eventDateName");
        JSONObject sampleFromApis = apisJsonArray.getJSONObject(0);
        return sampleFromApis.getString("eventDateName");
    }
    */

    private void parseGenders(List<Gender> genders, JSONObject jsonBody)
        throws IOException, JSONException {
        //JSONObject gendersJsonObject = jsonBody.getJSONObject();
        //JSONArray genderJsonArray = gendersJsonObject.getJSONArray("name");
        JSONArray genderJsonArray = jsonBody.getJSONArray("results");

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
