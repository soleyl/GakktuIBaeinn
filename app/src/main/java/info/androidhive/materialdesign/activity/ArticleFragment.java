package info.androidhive.materialdesign.activity;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListAdapter;
import android.widget.ListView;

import info.androidhive.materialdesign.R;
import info.androidhive.materialdesign.adapter.MentorAdapter;

/**
 * Created by Ravi on 29/07/15.
 */
public class ArticleFragment extends Fragment {

    public ArticleFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_articles, container, false);

        //create our list of articles....
        //a list of strings...
        String[] articleTitles = {"Health", "Organic", "Apples", "Cars", "Housing", "Taxes", "Jobs", "Schools", "Education"};

        //we need an adapter..
        //ListAdapter articlesAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, articleTitles);
        //ListView articles_list_view = (ListView) findViewById(R.id.articles_list_view);

        ListAdapter articleAdapter = new ArticleAdapter(getActivity(),articleTitles);
        ListView articleListView = (ListView) rootView.findViewById(R.id.articles_list_view);
        articleListView.setAdapter(articleAdapter);


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
