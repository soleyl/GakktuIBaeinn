package info.androidhive.materialdesign.activity;

/**
 * Created by Ravi on 29/07/15.
 */
import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import info.androidhive.materialdesign.ArticleFetcher;
import info.androidhive.materialdesign.GenderFetcher;
import info.androidhive.materialdesign.R;
import info.androidhive.materialdesign.model.Article;
import info.androidhive.materialdesign.model.Gender;


public class HomeFragment extends Fragment {

    private TextView mannouncement1TextView;
    private TextView mannouncement2TextView;
    private List<Gender> mGenders;
    private List<Article> mArticles;
    String TAG= "testing";



    public HomeFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    private void setAnnouncement1(){
        SharedPreferences sharedPref = getActivity().getSharedPreferences(
                getString(R.string.userProfilePreferences), Context.MODE_APPEND);
        String surveyStatus =  sharedPref.getString(getString(R.string.key_sp_survey_status), getString(R.string.survey_not_started_status));
        String messageToDisplay="";
        messageToDisplay = getString(R.string.text_announcement1_not_started);

        if (surveyStatus==getString(R.string.survey_started_status)){
            messageToDisplay = getString(R.string.text_announcement1_started);
        }
        if (surveyStatus==getString(R.string.survey_completed_status)){
            messageToDisplay = getString(R.string.text_announcement1_completed);
        }

        mannouncement1TextView.setText(messageToDisplay);

    }


    private void setAnnouncement2(List<Article> articles){
        int indexOfNewestArticle= articles.size()-1;
        Article newestArticle = articles.get(indexOfNewestArticle);
        String newestArticleTitle = newestArticle.getTitle();
        mannouncement2TextView.setText(newestArticleTitle);

        Log.i(TAG, "refreshedData in Home Fragment");

        }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_home, container, false);
        Log.i(TAG, "inside createView");

        //Display first announcement, based on Status of Survey
        mannouncement1TextView = (TextView) rootView.findViewById(R.id.announcement1);
        setAnnouncement1();

        //Display 2nd announcement.
        mannouncement2TextView = (TextView) rootView.findViewById(R.id.announcement2);
        AsyncTask task = new FetchArticlesTask();
        //AsyncTask task = new FetchGendersTask();
        task.execute();

        //AsyncTask task2 = new PostArticleTask();
        //task2.execute();

        // Inflate the layout for this fragment
        return rootView;
    }

    private class FetchArticlesTask extends AsyncTask<Object, Void, List<Article>>{

        @Override
        protected List<Article> doInBackground(Object... params){
            return new ArticleFetcher().fetchArticles();

        }

        @Override
        protected void onPostExecute(List<Article> articles){
            mArticles = articles;
            Log.i(TAG, "inside Postexecute");
            //If we found at least 1 article on the server, let's update our View/Fragment
            if(mArticles.size() >0) {setAnnouncement2(mArticles);}

        }

    }

    private class FetchGendersTask extends AsyncTask<Object, Void, List<Gender>>{

        @Override
        protected List<Gender> doInBackground(Object... params){
            Log.e(TAG, "in do In Background FetchGenders");
            List<Gender> returnList = new ArrayList<Gender>();
                try{
                     returnList = new GenderFetcher().get();
                }
                catch(IOException e){
                    Log.e("error", "error TROY");
                }
            return returnList;
        }

        @Override
        protected void onPostExecute(List<Gender> genders){
            mGenders = genders;
            Log.i(TAG, "inside execute");
            //setAnnouncement2(mGenders);
        }

    }
/*
    private class PostArticleTask extends AsyncTask<Object, Void, String>{

        @Override
        protected String doInBackground(Object... params){
            Log.e(TAG, "in do In Background PostArticleTask");
            List<Gender> returnList = new ArrayList<Gender>();
            String s ="";
            try{
                PostArticle pa = new PostArticle();
                s = pa.postarticle();
            }
            catch(IOException e){
                Log.e("error", "error PostArticleRask");
            }
            return s;
        }

        @Override
        protected void onPostExecute(String s){
            String x = s;
            Log.i(TAG, "inside execute");
            //setAnnouncement2(mGenders);

        }

    }
*/


    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        Log.i(TAG, "onAttach");
    }

    @Override
    public void onDetach() {
        super.onDetach();
    }
}
