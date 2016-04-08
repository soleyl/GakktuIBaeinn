package info.androidhive.materialdesign.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import info.androidhive.materialdesign.R;

/**
 * Created by Notandi on 4.4.2016.
 */
public class ArticleAdapter extends ArrayAdapter<String> {

    //Generic constructor
    public ArticleAdapter(Context context, String[] articleTitles) {
        super(context, R.layout.article_row, articleTitles);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater articleInflater = LayoutInflater.from(getContext());
        View articleView = articleInflater.inflate(R.layout.article_row, parent, false);

        String articleTitleItem = getItem(position);
        TextView article_title = (TextView) articleView.findViewById(R.id.article_title);
        ImageView article_image = (ImageView) articleView.findViewById(R.id.article_image);

        article_title.setText(articleTitleItem);
        article_image.setImageResource(R.drawable.image1);

        return articleView;
    }

}


