package info.androidhive.materialdesign.activity;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.StrictMode;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import info.androidhive.materialdesign.API;
import info.androidhive.materialdesign.R;
import info.androidhive.materialdesign.Utils;
import info.androidhive.materialdesign.model.Article;

public class MainActivity extends AppCompatActivity implements FragmentDrawer.FragmentDrawerListener {

    private static String TAG = MainActivity.class.getSimpleName();
    private Toolbar mToolbar;
    private FragmentDrawer drawerFragment;
    private List<Article> mArticles;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Allow for Networking on Main Thread - Will only use for the User/Password Authentication
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);

        AsyncTask task = new FetchArticlesTask();
        task.execute();

        mToolbar = (Toolbar) findViewById(R.id.toolbar);

        setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        drawerFragment = (FragmentDrawer)
                getSupportFragmentManager().findFragmentById(R.id.fragment_navigation_drawer);
        drawerFragment.setUp(R.id.fragment_navigation_drawer, (DrawerLayout) findViewById(R.id.drawer_layout), mToolbar);
        drawerFragment.setDrawerListener(this);

        // display the first navigation drawer view on app launch
        displayView(0);
    }


    public boolean articlesExist() {
        if (mArticles==null) { return false;}
        else return true;
    }

    public int getNumberOfArticles(){
        return mArticles.size();
    }

    public List<Article> getArticles(){
        return mArticles;
    }

    public Article getArticle(int i){
        return mArticles.get(i);
    }

    public String getNewestArticleTitle(){
        int indexOfNewestArticle= mArticles.size()-1;
        Article newestArticle = mArticles.get(indexOfNewestArticle);
        return newestArticle.getTitle();

    }
    private class FetchArticlesTask extends AsyncTask<Object, Void, List<Article>>{

        @Override
        protected List<Article> doInBackground(Object... params){
            JSONObject articlesJSON = new JSONObject();
            try{
                articlesJSON = API.get("articles");
            }
            catch(IOException e){
                Log.e("error", "fetching articles");
            }
            List<Article> articles = new ArrayList<>();
            try{
                articles = Utils.parseArticles(articlesJSON);
            }
            catch(JSONException je){
                Log.e(TAG, "Failed to parse JSON", je);
            }
            return articles;

        }

        @Override
        protected void onPostExecute(List<Article> articles){
            mArticles = articles;
            displayView(0);

        }

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        //Update the Displayed UserName, if User is Logged in
        TextView userNameTextView = (TextView) findViewById(R.id.user_logged_in_status_text_view);
        ImageView userImageView = (ImageView) findViewById(R.id.user_profile_image);
        String userName = getLoggedInUser();
        String userAvatar = getUserAvatar();
        if (userName != "null")
        {
            userNameTextView.setText(userName);
            new DownloadAvatarTask(userImageView)
                    .execute(userAvatar);

        }
        else {
            userNameTextView.setText(R.string.not_logged_in_text);
            userImageView.setImageResource(R.drawable.blueman);
        }

        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    private String getLoggedInUser(){
        SharedPreferences sharedPref = getSharedPreferences(
                getString(R.string.loggedInUser), Context.MODE_APPEND);
        return sharedPref.getString("userName", "null");

    }


    private class DownloadAvatarTask extends AsyncTask<String, Void, Bitmap> {
        ImageView bmImage;

        public DownloadAvatarTask(ImageView bmImage) {
            this.bmImage = bmImage;
        }

        protected Bitmap doInBackground(String... urls) {
            String urldisplay = urls[0];
            Bitmap mIcon11 = null;
            try {
                InputStream in = new java.net.URL(urldisplay).openStream();
                mIcon11 = BitmapFactory.decodeStream(in);
            } catch (Exception e) {
                Log.e("Error", e.getMessage());
                e.printStackTrace();
            }
            return mIcon11;
        }

        protected void onPostExecute(Bitmap result) {
            bmImage.setImageBitmap(result);
            bmImage.setVisibility(View.VISIBLE);
        }
    }

    private String getAuthentication(){
        SharedPreferences sharedPref = getSharedPreferences(
                getString(R.string.loggedInUser), Context.MODE_APPEND);
       return sharedPref.getString("authentication", "null");
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        if(id == R.id.action_search){
            Toast.makeText(getApplicationContext(), "Search action is selected!", Toast.LENGTH_SHORT).show();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onDrawerItemSelected(View view, int position) {
        displayView(position);
    }

    private String getUserAvatar(){
        //return "https://upload.wikimedia.org/wikipedia/commons/6/6d/Round_4_wheel_2012_by_wheelgenius.png";

        SharedPreferences sharedPref = getSharedPreferences(
                getString(R.string.user_profile), Context.MODE_APPEND);
        String userAvatar = sharedPref.getString("avatar", "null");
        return userAvatar;

    }

    private void displayView(int position) {
        Fragment fragment = null;
        String title = getString(R.string.app_name);
        switch (position) {
            case 0:
                fragment = new HomeFragment();
                title = getString(R.string.title_home);
                break;
            case 1:
                fragment = new SurveyFragment();
                title = getString(R.string.title_survey);
                break;
            case 2:
                fragment = new ProfileFragment();
                title = getString(R.string.title_profile);
                break;
            case 3:
                fragment = new ArticleFragment();
                title = getString(R.string.title_articles);
                break;
            case 4:
                fragment = new MentorsFragment();
                title = getString(R.string.title_mentors);
                break;
            case 5:
                fragment = new WriteArticleFragment();
                title = getString(R.string.title_write_article);
                break;
            case 6:
                fragment = new LogInFragment();
                title = getString(R.string.title_login);
                break;
            default:
                break;
        }


        if (fragment != null) {
            FragmentManager fragmentManager = getSupportFragmentManager();
            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
            fragmentTransaction.replace(R.id.container_body, fragment).addToBackStack("tag");
            fragmentTransaction.commit();

            // set the toolbar title
            getSupportActionBar().setTitle(title);
        }
    }
}