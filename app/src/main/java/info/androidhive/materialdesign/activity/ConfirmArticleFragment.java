package info.androidhive.materialdesign.activity;

import android.app.Activity;
import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import info.androidhive.materialdesign.PostArticle;
import info.androidhive.materialdesign.R;
import info.androidhive.materialdesign.model.Article;
import android.content.SharedPreferences;
import android.widget.Toast;
import java.io.IOException;

/**
 * Created by Ravi on 29/07/15.
 */
public class ConfirmArticleFragment extends Fragment {

    private TextView mArticleToConfirmTitleTextView;
    private TextView mArticleToConfirmBodyTextView;
    private Button mConfirmButton;
    private Button mEditButton;
    private Button mDiscardButton;
    private String TAG = "confirm article";

    public ConfirmArticleFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    private void clearFromSharedPreferences(){
        SharedPreferences sharedPref = getActivity().getSharedPreferences(
                getString(R.string.locallyStoredArticle), Context.MODE_APPEND);
        SharedPreferences.Editor editor = sharedPref.edit();
        editor.remove("title")
                .remove("body")
                .apply();

    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_confirm_article, container, false);

        // --------------------Display the Locally-Stored Article --------------------------//
        SharedPreferences sharedPref = getActivity().getSharedPreferences(
                getString(R.string.locallyStoredArticle), Context.MODE_APPEND);
        final String title = sharedPref.getString("title", "null");
        final String body = sharedPref.getString("body", "null");
        mArticleToConfirmTitleTextView = (TextView) rootView.findViewById(R.id.article_to_confirm_title);
        mArticleToConfirmTitleTextView.setText(title);
        mArticleToConfirmBodyTextView = (TextView) rootView.findViewById(R.id.article_to_confirm_body);
        mArticleToConfirmBodyTextView.setText(body);
        // --------------------===================================--------------------------//


        // ---------------------- CONFIRM BUTTON ------------------------------------------//
        mConfirmButton = (Button) rootView.findViewById(R.id.confirm_article_confirm_button);
        mConfirmButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                /* ------------  TO DO     -----------------
                Create new Article Object
                Connect to Server
                Save to DB
                 */
                Article articleForDB = new Article();
                articleForDB.setTitle(title);
                articleForDB.setContent(body);
                AsyncTask paTask = new PostArticleTask(articleForDB);
                paTask.execute();
                Toast.makeText(getActivity(),"Article saved to Database", Toast.LENGTH_SHORT).show();
                clearFromSharedPreferences();
                //----------Return to Home Fragment---------------------//
                FragmentManager fragmentManager = getFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                HomeFragment homeFrag = new HomeFragment();
                fragmentTransaction.replace(R.id.container_body,homeFrag);
                fragmentTransaction.commit();
                //-------------------------------------------------------//
            }
        });
        // -----------------------------------------------------------------------------//


        // ---------------------- EDIT BUTTON ------------------------------------------//
        mEditButton = (Button) rootView.findViewById(R.id.confirm_article_edit_button);
        mEditButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                //----------Return to WriteArticleFragment---------------------//
                FragmentManager fragmentManager = getFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                WriteArticleFragment writeFrag = new WriteArticleFragment();
                fragmentTransaction.replace(R.id.container_body,writeFrag);
                fragmentTransaction.commit();
                //-------------------------------------------------------//

            }
        });
        // -----------------------------------------------------------------------------//


        // ---------------------- DISCARD BUTTON ------------------------------------------//
        mDiscardButton = (Button) rootView.findViewById(R.id.confirm_article_discard_button);
        mDiscardButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                clearFromSharedPreferences();
                //----------Return to Home Fragment---------------------//
                FragmentManager fragmentManager = getFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                HomeFragment homeFrag = new HomeFragment();
                fragmentTransaction.replace(R.id.container_body,homeFrag);
                fragmentTransaction.commit();
                //-------------------------------------------------------//

            }
        });
        // -----------------------------------------------------------------------------//

        // Inflate the layout for this fragment
        return rootView;
    }

    private class PostArticleTask extends AsyncTask<Object, Void, String>{

        public Article TasksArticle;

        //Overriding the Constructor so that we can pass along an article
        public PostArticleTask (Article a){
            super();
            TasksArticle = a;
        }

        @Override
        protected String doInBackground(Object... params){
            Log.e(TAG, "in do In Background PostArticleTask");
            String s ="";
            try{
                PostArticle pa = new PostArticle();
                s = pa.postarticle(TasksArticle);
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

    @Override
    public void onAttach(Activity activity) {

        super.onAttach(activity);

    }

    @Override
    public void onDetach() {
        super.onDetach();
    }
}