package info.androidhive.materialdesign.activity;

/**
 * Created by Ravi on 29/07/15.
 */
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URI;
import java.net.URL;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;

import info.androidhive.materialdesign.GenderFetcher;
import info.androidhive.materialdesign.R;
import info.androidhive.materialdesign.model.Gender;


public class HomeFragment extends Fragment {

    private TextView mannouncement1TextView;
    private TextView mannouncement2TextView;
    private List<Gender> mGenders;
    String TAG= "testing";
    private ProgressDialog progressDialog;
    private String testCategory = "";

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

        if (surveyStatus==getString(R.string.survey_started_status)){
            messageToDisplay = getString(R.string.text_announcement1_started);
        }
        if (surveyStatus==getString(R.string.survey_completed_status)){
            messageToDisplay = getString(R.string.text_announcement1_completed);
        }
        else{messageToDisplay = getString(R.string.text_announcement1_not_started);}
        
        mannouncement1TextView.setText(messageToDisplay);

    }

    private void setAnnouncement2(List<Gender> genders){
        Log.i(TAG, "inside setAnn2");

        final String nameToDisplay= genders.get(0).getName();
        int idToDisplay= genders.get(0).getId();

        mannouncement2TextView.setText(nameToDisplay);
        Log.i(TAG, nameToDisplay);

        // Reload current fragment
        Fragment frg = null;
        frg = getFragmentManager().findFragmentByTag("HomeFragment");
        final FragmentTransaction ft = getFragmentManager().beginTransaction();
        ft.detach(frg);
        ft.attach(frg);
        ft.commit();

        /*

        getActivity().runOnUiThread(new Runnable() {
                                        @Override
                                        public void run() {
                                            Log.i(TAG, "inside RUN");
                                            mannouncement2TextView.setText(nameToDisplay);
                                        }
                                    }
        );
        */

        }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_home, container, false);
        Log.i(TAG, "inside createView");

        //Display first announcement, based on Status of Survey
        mannouncement1TextView = (TextView) rootView.findViewById(R.id.announcement1);
        setAnnouncement1();

        //COMMENTING THIS OUT UNTIL WE FIX THE SERVER CONNECTION

        //Display 2nd announcement.  Just testing a server connection w gender now.
        mannouncement2TextView = (TextView) rootView.findViewById(R.id.announcement2);
        AsyncTask task = new FetchGendersTask();
        task.execute();

        // Inflate the layout for this fragment
        return rootView;
    }

    private class FetchGendersTask extends AsyncTask<Object, Void, List<Gender>>{

        @Override
        protected List<Gender> doInBackground(Object... params){
            Log.i(TAG, "background");
                return new GenderFetcher().fetchGenders();

        }


        @Override
        protected void onPostExecute(List<Gender> genders){
            mGenders = genders;
            Log.i(TAG, "inside execute");
            setAnnouncement2(mGenders);


        }

    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        Log.i(TAG, "onAttach");
    }

    @Override
    public void onDetach() {
        super.onDetach();
    }
}
