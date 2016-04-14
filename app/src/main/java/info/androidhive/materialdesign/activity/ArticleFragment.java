package info.androidhive.materialdesign.activity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.Toast;

import java.util.List;

import info.androidhive.materialdesign.R;
import info.androidhive.materialdesign.adapter.ArticleAdapter;
import info.androidhive.materialdesign.model.Article;

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
        //First, make sure MainActivities asyncTask has completed....
        if (((MainActivity)getActivity()).articlesExist()==true) {
            //....if so, then populate the articles
            List<Article> articles = ((MainActivity) getActivity()).getArticles();
            ListAdapter articleAdapter = new ArticleAdapter(getActivity(), articles);
            ListView articleListView = (ListView) rootView.findViewById(R.id.articles_list_view);
            articleListView.setAdapter(articleAdapter);

        //Temporary Listener.  For now it just makes a toast that displays the title of the row. We will add better functionality...
        articleListView.setOnItemClickListener(
                new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                            //Article ourArticle = ((MainActivity) getActivity()).getArticle(position);
                            storeArticleSelected(position);
                            showFullArticle();
                    }
                });
        }

        // Inflate the layout for this fragment
        return rootView;
    }

    private void storeArticleSelected(int i){
        SharedPreferences prefs = getActivity().getSharedPreferences(
                getString(R.string.fullArticleToView), Context.MODE_APPEND);
        String key = "articleSelected";
        SharedPreferences.Editor editor = prefs.edit();
        editor.putInt(key, i)
                .apply();

    }

    private void showFullArticle(){

        Fragment fragment = new FullArticleFragment();
        FragmentManager fragmentManager = getFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.container_body, fragment).addToBackStack("tag");
        fragmentTransaction.commit();
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
