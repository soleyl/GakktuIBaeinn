package info.androidhive.materialdesign.activity;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import info.androidhive.materialdesign.R;
import info.androidhive.materialdesign.model.Question;
import android.content.SharedPreferences;
import android.widget.Toast;

/**
 * Created by Ravi on 29/07/15.
 */
public class SurveyFragment extends Fragment {

    private TextView mSurveyQuestionTextView;
    private Button mYesButton;
    private Button mNoButton;
    private Button mSkipButton;
    private Button mEndOfSurveyButton;
    private int mCurrentIndex = 0; //Variable to track which question User is currently viewing

    //Array of the questions we will ask the User
    private Question[] mQuestionBank = new Question[]{
            new Question(R.string.survey_question_children),
            new Question(R.string.survey_question_employed),
            new Question(R.string.survey_question_disabled),
            new Question(R.string.survey_question_citizenship)
    };

    //Array of keys for our Shared Preferences (topics of interest)
    private String[] preferenceNames= new String[]{
            "Children:",
            "Employed:",
            "Disability:",
            "Citizenship:"
    };

    //Function to update User's SharedPref, based on their answers
    //Eventually, these preferences will be used in an algorithm to pre-sort all articles/information
    //and present content to the User that is of most appropriate/interesting to him/her
    private void recordSurveyAnswer(int questionNumber, int answer)
    {

        SharedPreferences prefs = getActivity().getSharedPreferences(
                getString(R.string.userProfilePreferences), Context.MODE_APPEND);
        String key = preferenceNames[questionNumber];
        SharedPreferences.Editor editor = prefs.edit();
        editor.putInt(key, answer)
                .apply();
        if (mCurrentIndex==(mQuestionBank.length-1)){
            updateSurveyStatus(getString(R.string.survey_completed_status));
        }
    }

    //Iterate to the next question
    private void updateQuestion() {
        if (mCurrentIndex==1){
            updateSurveyStatus(getString(R.string.survey_started_status));
        }
        int question = mQuestionBank[mCurrentIndex].getQuestionTextResID();
        mSurveyQuestionTextView.setText(question);
    }

    //Keep a record of Survey Status (Started? Completed?)
    private void updateSurveyStatus(String status){
        SharedPreferences prefs = getActivity().getSharedPreferences(
                getString(R.string.userProfilePreferences), Context.MODE_APPEND);
        String key = getString(R.string.key_sp_survey_status);
        SharedPreferences.Editor editor = prefs.edit();
        editor.putString(key, status)
                .apply();

    }

    private void concludeSurvey(){
        //Hide Yes, No, Skip Buttons
        mYesButton.setVisibility(View.GONE);
        mNoButton.setVisibility(View.GONE);
        mSkipButton.setVisibility(View.GONE);
        //Display conclusion text
        mSurveyQuestionTextView.setText(R.string.end_of_survey_message);
        //Display button to go to Profile
        mEndOfSurveyButton.setVisibility(View.VISIBLE);
    }

    public SurveyFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_survey, container, false);

        mSurveyQuestionTextView = (TextView) rootView.findViewById(R.id.survey_question_text_view);
        mEndOfSurveyButton = (Button) rootView.findViewById(R.id.end_of_survey_nav);

        /* We will use a 3-point scale for storing User's Preference/Interest in a topic:
                0: User is NOT interested in this category
                1: Unknown/User has not expressed an opinion about this category
                2: User IS interested in this category.
        */

        // ---------------------- YES BUTTON ------------------------------------------//
        mYesButton = (Button) rootView.findViewById(R.id.survey_yes_button);
        mYesButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(getActivity(),"Profile Updated", Toast.LENGTH_SHORT).show();
                //YES, User is interested. Store value of 2
                recordSurveyAnswer(mCurrentIndex,2);
                //Increment to next question
                mCurrentIndex = (mCurrentIndex + 1);
                if (mCurrentIndex < mQuestionBank.length) {updateQuestion();}
                else concludeSurvey();
            }
        });

        // ---------------------- NO BUTTON ------------------------------------------//
        mNoButton = (Button) rootView.findViewById(R.id.survey_no_button);
        mNoButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(getActivity(),"Profile Updated", Toast.LENGTH_SHORT).show();
                //NO, User is not interested. Store value of 0
                recordSurveyAnswer(mCurrentIndex,0);
                //Increment to next question
                mCurrentIndex = (mCurrentIndex + 1);
                Toast.makeText(getActivity(),"Profile Updated", Toast.LENGTH_SHORT).show();
                if (mCurrentIndex < mQuestionBank.length) {updateQuestion();}
                else concludeSurvey();
            }
        });


        // ---------------------- SKIP BUTTON ------------------------------------------//
        mSkipButton = (Button) rootView.findViewById(R.id.survey_skip_button);
        mSkipButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ////User is ambivalent. Store value of 1
                recordSurveyAnswer(mCurrentIndex,1);
                //Increment to next question
                mCurrentIndex = (mCurrentIndex + 1);
                if (mCurrentIndex < mQuestionBank.length) {updateQuestion();}
                else concludeSurvey();
            }
        });


        // ---------------------- GO TO PROFILE BUTTON ------------------------------------------//
        mEndOfSurveyButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FragmentManager fragmentManager = getFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                ProfileFragment profileFrag = new ProfileFragment();

                fragmentTransaction.replace(R.id.container_body,profileFrag);
                fragmentTransaction.commit();

            }
        });


        //After setup, display the first question
        updateQuestion();

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