package info.androidhive.materialdesign.activity;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.app.Activity;
import android.widget.ArrayAdapter;
import android.widget.GridView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import info.androidhive.materialdesign.R;

public class ProfileFragment extends Fragment {

    private TextView mChildrenSlot;
    private TextView mEmployedSlot;
    private TextView mDisabilitySlot;
    private TextView mCitizenshipSlot;

    private String[] profileLabels = new String[] {
            "Children:",
            "Employment:",
            "Disability:",
            "Citizenship:"
    };

    private String[] answerStrings = new String[] {
            "Low", "Neutral", "High"
    };

    private List<String> profileData = new ArrayList();

    public ProfileFragment() {
        // Required empty public constructor
    }

    //Pull User's data from Shared Preference and store in array
    public void gatherProfileData(){
        SharedPreferences sharedPref = getActivity().getSharedPreferences(
                getString(R.string.userProfilePreferences), Context.MODE_APPEND);

        for (int i=0; i<profileLabels.length; i++){
            String key = profileLabels[i];
            int value = sharedPref.getInt(key, 1);
            String valueAsString = answerStrings[value];
            profileData.add(key);
            profileData.add(valueAsString);
        }
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.fragment_profile, container, false);

        mChildrenSlot = (TextView) rootView.findViewById(R.id.children_text_view);
        mEmployedSlot = (TextView) rootView.findViewById(R.id.employed_text_view);
        mDisabilitySlot = (TextView) rootView.findViewById(R.id.disability_text_view);
        mCitizenshipSlot = (TextView) rootView.findViewById(R.id.citizenship_text_view);

        SharedPreferences sharedPref = getActivity().getSharedPreferences(
                getString(R.string.userProfilePreferences), Context.MODE_APPEND);
        int childScore = sharedPref.getInt("Children:", 1);
        int employedScore = sharedPref.getInt("Employed:", 1);
        int disabledScore = sharedPref.getInt("Disability:", 1);
        int citizenshipScore = sharedPref.getInt("Citizenship:", 1);

        updateProfileSlot(mChildrenSlot,childScore, "Children");
        updateProfileSlot(mEmployedSlot,employedScore, "Employment");
        updateProfileSlot(mDisabilitySlot,disabledScore, "Disability");
        updateProfileSlot(mCitizenshipSlot,citizenshipScore, "Citizenship");

        ActionBar actionBar = ((AppCompatActivity)getActivity()).getSupportActionBar();
        actionBar.setTitle("Profile");

        //Inflate the layout for this fragment
        return rootView;
    }

    private void updateProfileSlot(TextView view, int score, String topic) {
        String statement="";
        if (score == 0) {
            statement += topic + " is not a topic of interest for you.";
            view.setBackgroundColor(getResources().getColor(R.color.no));
            view.setText(statement);

        }
        if (score==1){
            statement = "Your interest in "+ topic + " is not known.";
            view.setBackgroundColor(getResources().getColor(R.color.skip));
            view.setText(statement);
        }
        if (score==2){
            statement = topic + " is a topic of interest to you.";
            view.setBackgroundColor(getResources().getColor(R.color.yes));
            view.setText(statement);
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

