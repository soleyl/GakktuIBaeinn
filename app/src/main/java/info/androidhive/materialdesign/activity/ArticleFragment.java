package info.androidhive.materialdesign.activity;

import android.app.Activity;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.Toast;

import java.util.List;

import info.androidhive.materialdesign.ArticleFetcher;
import info.androidhive.materialdesign.R;
import info.androidhive.materialdesign.adapter.ArticleAdapter;
import info.androidhive.materialdesign.model.Article;

/**
 * Created by Ravi on 29/07/15.
 */
public class ArticleFragment extends Fragment {

    private List<Article> mArticles;
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
        //String[] articleTitles = {"Health", "Organic", "Apples", "Cars", "Housing", "Taxes", "Jobs", "Schools", "Education"};

        Article testArticle = new Article();
        testArticle.setTitle("Hello Article!");
        testArticle.setContent("Are u here?");
        testArticle.setURL("http://avatarbox.net/avatars/img22/homer_with_hair_avatar_picture_15052.jpg");


        Article[] articles = { testArticle };

        AsyncTask task = new FetchArticlesTask();


        //we need an adapter..
        //ListAdapter articlesAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, articleTitles);
        //ListView articles_list_view = (ListView) findViewById(R.id.articles_list_view);

        ListAdapter articleAdapter = new ArticleAdapter(getActivity(),articles);
        ListView articleListView = (ListView) rootView.findViewById(R.id.articles_list_view);
        articleListView.setAdapter(articleAdapter);

        //Temporary Listener.  For now it just makes a toast that displays the title of the row. We will add better functionality...
        articleListView.setOnItemClickListener(
                new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        String articles = String.valueOf(parent.getItemAtPosition(position));
                        Toast.makeText(getActivity(), articles, Toast.LENGTH_SHORT).show();
                    }
                }
        );

        // Inflate the layout for this fragment
        return rootView;
    }

    private class FetchArticlesTask extends AsyncTask<Object, Void, List<Article>>{

        @Override
        protected List<Article> doInBackground(Object... params){
            return new ArticleFetcher().fetchArticles();

        }

        @Override
        protected void onPostExecute(List<Article> articles){
            mArticles = articles;
            //If we found at least 1 article on the server, let's update our View/Fragment
            //if(mArticles.size() >0) {setAnnouncement2(mArticles);}

            //reload current fragment...
            Fragment frg = null:
            frg = getSupportFragmentManager().findFragmentByTag("")

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
