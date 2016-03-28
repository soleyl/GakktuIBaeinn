package info.androidhive.materialdesign.activity;

import android.content.Context;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.app.Activity;
import android.widget.ArrayAdapter;
import android.widget.GridView;

import java.util.ArrayList;
import java.util.List;

import info.androidhive.materialdesign.R;

public class ProfileFragment extends Fragment {

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

        GridView profileGrid;

        profileGrid = (GridView) rootView.findViewById(R.id.profile_grid);

        gatherProfileData();
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_list_item_1, profileData);

        profileGrid.setAdapter(adapter);
        //Inflate the layout for this fragment
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

