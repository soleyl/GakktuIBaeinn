package info.androidhive.materialdesign;

import android.util.Log;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import info.androidhive.materialdesign.model.Gender;


public class GenderFetcher {
    OkHttpClient client = new OkHttpClient();

    String run(String url) throws IOException {
        Request request = new Request.Builder().url(url).build();
        Response response = client.newCall(request).execute();
        return response.body().string();
    }

    public List<Gender> get() throws IOException{
        GenderFetcher genderFetcher = new GenderFetcher();
        String response = genderFetcher.run(Utils.url() +"/genders");
        String TAG = "testingServer";
        Log.e(TAG, response);
        List<Gender>  l = new ArrayList<Gender>();
        return l;
    }
}