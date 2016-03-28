package info.androidhive.materialdesign.activity;

/**
 * Created by Ravi on 29/07/15.
 */
import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import info.androidhive.materialdesign.R;


public class HomeFragment extends Fragment {

    private TextView mannouncement1TextView;
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
        Log.i(TAG, surveyStatus);
        String messageToDisplay="";
        if (surveyStatus==getString(R.string.survey_not_started_status)){
            messageToDisplay = getString(R.string.text_announcement1_not_started);
        }
        if (surveyStatus==getString(R.string.survey_started_status)){
            messageToDisplay = getString(R.string.text_announcement1_started);
        }
        if (surveyStatus==getString(R.string.survey_completed_status)){
            messageToDisplay = getString(R.string.text_announcement1_completed);
        }
        
        mannouncement1TextView.setText(messageToDisplay);

    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_home, container, false);
        mannouncement1TextView = (TextView) rootView.findViewById(R.id.announcement1);
        setAnnouncement1();
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
