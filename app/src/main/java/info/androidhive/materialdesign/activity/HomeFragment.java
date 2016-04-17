package info.androidhive.materialdesign.activity;

/**
 * Created by Ravi on 29/07/15.
 */
import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import info.androidhive.materialdesign.R;

public class HomeFragment extends Fragment {

    private TextView mAnnouncement1TextView;
    private TextView mAnnouncement2TextView;
    private ImageView mSurveyIconImage;
    private ImageView mNewsIconImage;
    private TextView mStaticAnnounce2;
    String TAG= "testing";

    public HomeFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    private void setAnnouncement1(){
        SharedPreferences sharedPref = getActivity().getSharedPreferences(
                getString(R.string.userProfilePreferences), Context.MODE_APPEND);
        String surveyStatus =  sharedPref.getString(getString(R.string.key_sp_survey_status), getString(R.string.survey_not_started_status));
        String messageToDisplay="";
        messageToDisplay = getString(R.string.text_announcement1_not_started);

        if (surveyStatus==getString(R.string.survey_started_status)){
            messageToDisplay = getString(R.string.text_announcement1_started);
        }
        if (surveyStatus==getString(R.string.survey_completed_status)){
            messageToDisplay = getString(R.string.text_announcement1_completed);
        }

        mAnnouncement1TextView.setText(messageToDisplay);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_home, container, false);

        //Display 1st announcement, based on Status of Survey
        mAnnouncement1TextView = (TextView) rootView.findViewById(R.id.announcement1);
        setAnnouncement1();

        //Display 2nd announcement, based on newest Article found in DB.
        mAnnouncement2TextView = (TextView) rootView.findViewById(R.id.announcement2);
        //If there is at least 1 article found, display its Title
        if (((MainActivity)getActivity()).articlesExist()==true){
            int length = ((MainActivity)getActivity()).getNumberOfArticles();
            Log.e("#0fArts", String.valueOf(length));
            String newestArticleTitle = ((MainActivity)getActivity()).getNewestArticleTitle();
            mAnnouncement2TextView.setText(newestArticleTitle);
        }

        //Add Listener to Survey Icon Image
        mSurveyIconImage = (ImageView) rootView.findViewById(R.id.survey_icon_image);
        mSurveyIconImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FragmentManager fragmentManager = getFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                SurveyFragment surveyFrag = new SurveyFragment();
                fragmentTransaction.replace(R.id.container_body, surveyFrag).addToBackStack("tag");
                fragmentTransaction.commit();
                ActionBar actionBar = ((AppCompatActivity)getActivity()).getSupportActionBar();
                actionBar.setTitle("Survey");

            }
        });

        //Add Listener to Announcement 1
        mAnnouncement1TextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FragmentManager fragmentManager = getFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                SurveyFragment surveyFrag = new SurveyFragment();
                fragmentTransaction.replace(R.id.container_body, surveyFrag).addToBackStack("tag");
                fragmentTransaction.commit();
                ActionBar actionBar = ((AppCompatActivity)getActivity()).getSupportActionBar();
                actionBar.setTitle("Survey");
            }
        });

        //Add Listener to News Icon
        mNewsIconImage=(ImageView) rootView.findViewById(R.id.news_icon_image);
        mNewsIconImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FragmentManager fragmentManager = getFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                ArticleFragment articleFrag = new ArticleFragment();
                fragmentTransaction.replace(R.id.container_body,articleFrag).addToBackStack("tag");
                fragmentTransaction.commit();
                ActionBar actionBar = ((AppCompatActivity)getActivity()).getSupportActionBar();
                actionBar.setTitle("Articles");
            }
        });

        //Add Listener to Announcement 2
        mAnnouncement2TextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FragmentManager fragmentManager = getFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                ArticleFragment articleFrag = new ArticleFragment();
                fragmentTransaction.replace(R.id.container_body,articleFrag).addToBackStack("tag");
                fragmentTransaction.commit();
                ActionBar actionBar = ((AppCompatActivity)getActivity()).getSupportActionBar();
                actionBar.setTitle("Articles");
            }
        });


        //Add Listener to Static Portion of Announcement 2
        mStaticAnnounce2=(TextView) rootView.findViewById(R.id.static_announcement_2);
        mStaticAnnounce2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FragmentManager fragmentManager = getFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                ArticleFragment articleFrag = new ArticleFragment();
                fragmentTransaction.replace(R.id.container_body,articleFrag).addToBackStack("tag");
                fragmentTransaction.commit();
                ActionBar actionBar = ((AppCompatActivity)getActivity()).getSupportActionBar();
                actionBar.setTitle("Articles");
            }
        });


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
