package info.androidhive.materialdesign.adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.io.InputStream;
import java.util.List;

import info.androidhive.materialdesign.R;
import info.androidhive.materialdesign.model.Article;

/**
 * Created by Notandi on 4.4.2016.
 */
public class ArticleAdapter extends ArrayAdapter<Article> {

    View mArticleLoadingPanel;

    //Generic constructor
    public ArticleAdapter(Context context, List<Article> articles) {
        super(context, R.layout.article_row, articles);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater articleInflater = LayoutInflater.from(getContext());
        View articleView = articleInflater.inflate(R.layout.article_row, parent, false);

        //Get Data from the current Article
        Article articleItem = getItem(position);
        String articleTitleItem = articleItem.getTitle();
        String imageUrlString = articleItem.getImage();
        String articleIntroItem = articleItem.getIntro();

        //Display a Loading Panel while Image is uploaded
        mArticleLoadingPanel = articleView.findViewById(R.id.article_row_loading_panel);

        //Load title into View
        TextView article_title = (TextView) articleView.findViewById(R.id.article_title);
        article_title.setText(articleTitleItem);

        //Load intro into View
        TextView article_intro_view = (TextView) articleView.findViewById(R.id.article_intro_view);
        article_intro_view.setText(articleIntroItem);

        //Load Image into View
        ImageView article_image_view = (ImageView) articleView.findViewById(R.id.article_image);
        if (!(imageUrlString.equals(""))){
            mArticleLoadingPanel.setVisibility(View.VISIBLE);
            new DownloadImageTask(article_image_view, mArticleLoadingPanel)
                .execute(imageUrlString);}
        else {
            article_image_view.setImageResource(R.drawable.stock_photo);
            article_image_view.setVisibility(View.VISIBLE);
        }

        return articleView;
    }
    /*
    //This override allows us to display articles in reverse chronological order
    @Override
    public Article getItem(int position) {
        return super.getItem(super.getCount() - position - 1);
    }
    */

    private class DownloadImageTask extends AsyncTask<String, Void, Bitmap> {
        ImageView bmImage;
        View loadingPanel;

        public DownloadImageTask(ImageView bmImage, View loadingPanel) {
            this.bmImage = bmImage;
            this.loadingPanel = loadingPanel;
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
            loadingPanel.setVisibility(View.GONE);
        }
    }

}


