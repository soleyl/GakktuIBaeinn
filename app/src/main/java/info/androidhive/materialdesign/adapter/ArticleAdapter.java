package info.androidhive.materialdesign.adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.io.InputStream;
import java.net.URL;
import java.util.List;

import info.androidhive.materialdesign.R;
import info.androidhive.materialdesign.model.Article;

/**
 * Created by Notandi on 4.4.2016.
 */
public class ArticleAdapter extends ArrayAdapter<Article> {

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

        //Load title into View
        TextView article_title = (TextView) articleView.findViewById(R.id.article_title);
        article_title.setText(articleTitleItem);

        //Load intro into View
        TextView article_intro_view = (TextView) articleView.findViewById(R.id.article_intro_view);
        article_intro_view.setText(articleIntroItem);

        //Load Image into View
        ImageView article_image_view = (ImageView) articleView.findViewById(R.id.article_image);
        new DownloadImageTask(article_image_view)
                .execute(imageUrlString);

        return articleView;
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
                Log.e("Error", e.getMessage());
                e.printStackTrace();
            }
            return mIcon11;
        }

        protected void onPostExecute(Bitmap result) {
            bmImage.setImageBitmap(result);
        }
    }

}


