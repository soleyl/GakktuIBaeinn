package info.androidhive.materialdesign.activity;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.method.ScrollingMovementMethod;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import android.widget.ImageView;
import android.widget.TextView;

import java.io.InputStream;

import info.androidhive.materialdesign.R;
import info.androidhive.materialdesign.model.Article;

/**
 * Created by Ravi on 29/07/15.
 */
public class FullArticleFragment extends Fragment {


    TextView mSelectedArticleTitleView;
    TextView mSelectedArticleContentView;
    ImageView mSelectedArticleImageView;
    View mSelectedArticleLoadingPanel;

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

        mSelectedArticleLoadingPanel = rootView.findViewById(R.id.loadingPanel);
        //Display the Image for this Article
        String ourImageUrlString = ourArticle.getImage();
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
        String ourTitle = ourArticle.getTitle();
        mSelectedArticleTitleView = (TextView) rootView.findViewById(R.id.full_article_title);
        mSelectedArticleTitleView.setText(ourTitle);
        //Display the Content
        String ourContent = ourArticle.getContent();
        mSelectedArticleContentView = (TextView) rootView.findViewById(R.id.full_article_content);
        mSelectedArticleContentView.setMovementMethod(new ScrollingMovementMethod());
        mSelectedArticleContentView.setText(ourContent);

        // Inflate the layout for this fragment
        return rootView;
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
