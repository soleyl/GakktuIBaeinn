package info.androidhive.materialdesign.activity;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import info.androidhive.materialdesign.R;
import info.androidhive.materialdesign.model.Question;
import android.content.SharedPreferences;
/**
 * Created by Ravi on 29/07/15.
 */
public class SurveyFragment extends Fragment {

    private TextView mSurveyQuestionTextView;
    private Button mYesButton;
    private Button mNoButton;
    private Button mSkipButton;
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
            "children", "employed", "disabled", "citizenship"
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

    }

    //Iterate to the next question
    private void updateQuestion() {
        int question = mQuestionBank[mCurrentIndex].getQuestionTextResID();
        mSurveyQuestionTextView.setText(question);
    }

    public SurveyFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mSurveyQuestionTextView = (TextView) getActivity().findViewById(R.id.survey_question_text_view);

        /* We will use a 3-point scale for storing User's Preference/Interest in a topic:
                0: User is NOT interested in this category
                1: Unknown/User has not expressed an opinion about this category
                2: User IS interested in this category.
        */

        // ---------------------- YES BUTTON ------------------------------------------//
        mYesButton = (Button) getActivity().findViewById(R.id.survey_yes_button);
        mYesButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //YES, User is interested. Store value of 2
                recordSurveyAnswer(mCurrentIndex,2);
                //Increment to next question
                mCurrentIndex = (mCurrentIndex + 1) % mQuestionBank.length;
                updateQuestion();
            }
        });
        // -----------------------------------------------------------------------------//
        // ---------------------- NO BUTTON ------------------------------------------//
        mNoButton = (Button) getActivity().findViewById(R.id.survey_no_button);
        mNoButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //NO, User is not interested. Store value of 0
                recordSurveyAnswer(mCurrentIndex,0);
                //Increment to next question
                mCurrentIndex = (mCurrentIndex + 1) % mQuestionBank.length;
                updateQuestion();
            }
        });
        // -----------------------------------------------------------------------------//

        // ---------------------- SKIP BUTTON ------------------------------------------//
        mSkipButton = (Button) getActivity().findViewById(R.id.survey_skip_button);
        mSkipButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ////User is ambivalent. Store value of 1
                recordSurveyAnswer(mCurrentIndex,1);
                //Increment to next question
                mCurrentIndex = (mCurrentIndex + 1) % mQuestionBank.length;
                updateQuestion();
            }
        });
        // -----------------------------------------------------------------------------//


        //After setup, display the first question
        updateQuestion();


    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_survey, container, false);

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