package info.androidhive.materialdesign.activity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import info.androidhive.materialdesign.adapter.MentorAdapter;

import android.widget.EditText;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import info.androidhive.materialdesign.R;
import info.androidhive.materialdesign.model.Article;
import info.androidhive.materialdesign.model.Mentor;

/**
 * Created by Ravi on 29/07/15.
 */
public class FullArticleFragment extends Fragment {


    TextView mSelectedArticleTitleView;
    TextView mSelectedArticleContentView;

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

        SharedPreferences sharedPref = getActivity().getSharedPreferences(
                getString(R.string.fullArticleToView), Context.MODE_APPEND);
        final int articleSelected = sharedPref.getInt("articleSelected", 0);

        Article ourArticle= ((MainActivity) getActivity()).getArticle(articleSelected);
        String ourTitle = ourArticle.getTitle();
        String ourContent = ourArticle.getContent();

        //Display Title
        mSelectedArticleTitleView = (TextView) rootView.findViewById(R.id.full_article_title);
        mSelectedArticleTitleView.setText(ourTitle);
        //Display Content
        mSelectedArticleContentView = (TextView) rootView.findViewById(R.id.full_article_content);
        mSelectedArticleContentView.setText(ourContent);

        // Inflate the layout for this fragment
        return rootView;
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
