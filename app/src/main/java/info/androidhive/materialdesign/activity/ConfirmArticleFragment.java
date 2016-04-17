package info.androidhive.materialdesign.activity;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
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
import android.widget.ImageView;
import android.widget.TextView;

import info.androidhive.materialdesign.API;
import info.androidhive.materialdesign.R;
import info.androidhive.materialdesign.model.Article;
import android.content.SharedPreferences;
import android.widget.Toast;
import java.io.IOException;
import java.io.InputStream;

/**
 * Created by Ravi on 29/07/15.
 */
public class ConfirmArticleFragment extends Fragment {

    private TextView mArticleToConfirmTitleTextView;
    private TextView mArticleToConfirmBodyTextView;
    private Button mConfirmButton;
    private Button mEditButton;
    private Button mDiscardButton;
    ImageView mConfirmArticleImageView;
    View mConfirmArticleLoadingPanel;

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
                .remove("image")
                .apply();

    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_confirm_article, container, false);

        mConfirmArticleLoadingPanel = rootView.findViewById(R.id.confirm_article_loading_panel);

        // --------------------Display the Locally-Stored Article --------------------------//
        SharedPreferences sharedPref = getActivity().getSharedPreferences(
                getString(R.string.locallyStoredArticle), Context.MODE_APPEND);
        final String title = sharedPref.getString("title", "null");
        final String body = sharedPref.getString("body", "null");
        final String image = sharedPref.getString("image", "");

        //Display the Image for this Article
        mConfirmArticleImageView = (ImageView) rootView.findViewById(R.id.confirm_article_image_view);
        if (!(image.equals(""))){
        new DownloadImageTask(mConfirmArticleImageView)
                .execute(image);}
        else {
            mConfirmArticleImageView.setImageResource(R.drawable.stock_photo);
            mConfirmArticleImageView.setVisibility(View.VISIBLE);
            mConfirmArticleLoadingPanel.setVisibility(View.GONE);
        }

        mArticleToConfirmTitleTextView = (TextView) rootView.findViewById(R.id.article_to_confirm_title);
        mArticleToConfirmTitleTextView.setText(title);
        mArticleToConfirmBodyTextView = (TextView) rootView.findViewById(R.id.article_to_confirm_body);
        mArticleToConfirmBodyTextView.setText(body);



        // ---------------------- CONFIRM BUTTON ------------------------------------------//
        mConfirmButton = (Button) rootView.findViewById(R.id.confirm_article_confirm_button);
        mConfirmButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                //Create New Article
                Article articleForDB = new Article();
                articleForDB.setTitle(title);
                articleForDB.setContent(body);
                articleForDB.setImage(image);
                //Save Article In DB
                AsyncTask paTask = new PostArticleTask(articleForDB);
                paTask.execute();
                Toast.makeText(getActivity(),"Article saved to Database", Toast.LENGTH_SHORT).show();
                clearFromSharedPreferences();
                //----------Return to Home Fragment---------------------//
                //FragmentManager fragmentManager = getFragmentManager();
                //FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                //HomeFragment homeFrag = new HomeFragment();
                //fragmentTransaction.replace(R.id.container_body,homeFrag);
                //fragmentTransaction.commit();

            }
        });


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

            }
        });

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
            }
        });

        // Inflate the layout for this fragment
        return rootView;
    }

    private class PostArticleTask extends AsyncTask<Object, Void, String>{

        //This is the Article given to the Task to be sent to DB
        public Article TasksArticle;

        //Overriding the Constructor so that we can pass along an article
        public PostArticleTask (Article a){
            super();
            TasksArticle = a;
        }

        @Override
        protected String doInBackground(Object... params){
            String s ="";
            try{
                API api = new API();
                s=api.postArticle(TasksArticle,getAuthentication());
            }
            catch(IOException e){
                Log.e("error", "Error in AsyncTask");
            }
            return s;
        }

        private String getAuthentication(){
            SharedPreferences sharedPref = getActivity().getSharedPreferences(
                    getString(R.string.loggedInUser), Context.MODE_APPEND);
            return sharedPref.getString("authentication", "null");
        }

        @Override
        protected void onPostExecute(String s){
            //----------Return to Home Fragment---------------------//
            FragmentManager fragmentManager = getFragmentManager();
            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
            HomeFragment homeFrag = new HomeFragment();
            fragmentTransaction.replace(R.id.container_body,homeFrag);
            fragmentTransaction.commit();
            String x = s;

        }

    }

    private class DownloadImageTask extends AsyncTask<String, Void, Bitmap> {
        ImageView bmImage;

        public DownloadImageTask(ImageView bmImage) {
            this.bmImage = bmImage;
        }

        protected Bitmap doInBackground(String... urls) {
            String urldisplay = urls[0];
            Bitmap mIcon11 = null;
            try {
                InputStream in = new java.net.URL(urldisplay).openStream();
                mIcon11 = BitmapFactory.decodeStream(in);
            } catch (Exception e) {
                e.printStackTrace();
            }
            return mIcon11;
        }
        protected void onPostExecute(Bitmap result) {
            bmImage.setImageBitmap(result);
            bmImage.setVisibility(View.VISIBLE);
            mConfirmArticleLoadingPanel.setVisibility(View.GONE);
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