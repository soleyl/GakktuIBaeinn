package info.androidhive.materialdesign.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import info.androidhive.materialdesign.R;
import info.androidhive.materialdesign.model.Article;

/**
 * Created by Notandi on 4.4.2016.
 */
public class ArticleAdapter extends ArrayAdapter<Article> {

    //Generic constructor
    public ArticleAdapter(Context context, Article[] articles) {
        super(context, R.layout.article_row, articles);
    }

    public void updateData()

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater articleInflater = LayoutInflater.from(getContext());
        View articleView = articleInflater.inflate(R.layout.article_row, parent, false);

        //Get current iteration of Article
        Article singleArticleItem = getItem(position);

        // --------------   Set up the Profile Pic for Mentor  ---------------------------//
        ImageView articleImage = (ImageView) articleView.findViewById(R.id.article_image);
        String imageURL = singleArticleItem.getURL();
        //articleImage.setImageResource(imageURL);
        //Picasso.with(convertView.getContext()).load(imageURL).into(articleImage);

        String newTitle = singleArticleItem.getTitle();

        //Article articlesItem = getItem(position);
        TextView article_title = (TextView) articleView.findViewById(R.id.article_title);
        //ImageView article_image = (ImageView) articleView.findViewById(R.id.article_image);

        article_title.setText(newTitle);
        //article_image.setImageResource(R.drawable.image1);

        return articleView;
    }

}


