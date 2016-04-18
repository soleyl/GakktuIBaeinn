package info.androidhive.materialdesign.activity;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.text.method.ScrollingMovementMethod;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import android.widget.ImageView;
import android.widget.TextView;

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

/**
 * Created by Ravi on 29/07/15.
 */
public class FullArticleFragment extends Fragment {


    TextView mSelectedArticleTitleView;
    TextView mSelectedArticleContentView;
    TextView mSelectedArticleAuthorView;
    TextView mSelectedArticleDate;
    ImageView mSelectedArticleImageView;
    View mSelectedArticleLoadingPanel;
    String mAuthorNameString;
    String mArticlePublishDate;

    public FullArticleFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_full_article, container, false);

        //Use SharedPreferences to get the correct Article selected by the User
        SharedPreferences sharedPref = getActivity().getSharedPreferences(
                getString(R.string.fullArticleToView), Context.MODE_APPEND);
        final int articleSelected = sharedPref.getInt("articleSelected", 0);
        Article ourArticle= ((MainActivity) getActivity()).getArticle(articleSelected);

        //Parse Data from the Article
        String ourAuthor = ourArticle.getAuthor();
        String ourImageUrlString = ourArticle.getImage();
        String ourTitle = ourArticle.getTitle();
        String ourContent = ourArticle.getContent();
        mArticlePublishDate = ourArticle.getOriginalDate();

        //Make server call to get String Value/Name of Author
        AsyncTask task = new GetAuthorNameTask(ourAuthor);
        task.execute();

        //Display the Image for this Article
        mSelectedArticleDate = (TextView) rootView.findViewById(R.id.full_article_date);
        mSelectedArticleLoadingPanel = rootView.findViewById(R.id.loadingPanel);
        mSelectedArticleImageView = (ImageView) rootView.findViewById(R.id.full_article_image_view);
        if (!(ourImageUrlString.equals(""))){
        new DownloadImageTask(mSelectedArticleImageView)
                .execute(ourImageUrlString);}
        else{
            mSelectedArticleImageView.setImageResource(R.drawable.stock_photo);
            mSelectedArticleImageView.setVisibility(View.VISIBLE);
            mSelectedArticleLoadingPanel.setVisibility(View.GONE);
        }

        //Display the Title
        mSelectedArticleTitleView = (TextView) rootView.findViewById(R.id.full_article_title);
        mSelectedArticleTitleView.setText(ourTitle);
        //Display the Content
        mSelectedArticleContentView = (TextView) rootView.findViewById(R.id.full_article_content);
        mSelectedArticleContentView.setMovementMethod(new ScrollingMovementMethod());
        mSelectedArticleContentView.setText(ourContent);

        ActionBar actionBar = ((AppCompatActivity)getActivity()).getSupportActionBar();
        actionBar.setTitle("Articles");

        // Inflate the layout for this fragment
        return rootView;
    }

    private class GetAuthorNameTask extends AsyncTask<Object, Void, String>{
        private String authorInt;

        public GetAuthorNameTask(String authorInt) {
            this.authorInt=authorInt;
        }

        @Override
        protected String doInBackground(Object... params){
            String authorNameString="horseShit";
            JSONObject authorJSON = new JSONObject();
            String url="users/"+ authorInt;
            try{
                authorJSON = API.get(url);
            }
            catch(IOException e){
                Log.e("error", "fetching articles");
            }

            try{
                authorNameString=authorJSON.getString("username");
            }
            catch(JSONException je){
                Log.e("getAuthorName", "Failed to parse JSON", je);
            }
            mAuthorNameString=authorNameString;
            return authorNameString;

        }

        @Override
        protected void onPostExecute(String authorNameString){
            String fullString = "Published on " + mArticlePublishDate + " by " + mAuthorNameString;
            mSelectedArticleDate.setText(fullString);
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
            mSelectedArticleLoadingPanel.setVisibility(View.GONE);
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
