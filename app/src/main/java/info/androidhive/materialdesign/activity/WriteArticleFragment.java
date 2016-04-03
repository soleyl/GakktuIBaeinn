package info.androidhive.materialdesign.activity;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import info.androidhive.materialdesign.R;
import android.content.SharedPreferences;
import android.widget.Toast;

/**
 * Created by Ravi on 29/07/15.
 */
public class WriteArticleFragment extends Fragment {

    private TextView mWriteArticleInstructions;
    private EditText mWriteArticleEditText;
    private Button mSaveButton;
    private Button mCancelButton;
    private int mCurrentPhase=0;

    private static final String TAG = "writeArticle";

    String[] writingPhases = {"Title", "Body"};

    public WriteArticleFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    public void saveTitle(){

        //Get Title from User's input
        String articleTitle = mWriteArticleEditText.getText().toString();

        //Save Title locally in SharedPreferences
        SharedPreferences prefs = getActivity().getSharedPreferences(
                getString(R.string.locallyStoredArticle), Context.MODE_APPEND);
        String key = "title";
        SharedPreferences.Editor editor = prefs.edit();
        editor.putString(key, articleTitle)
                .apply();

        //Update to next View
        mWriteArticleInstructions.setText(R.string.write_article_instruction_body_text);
        mWriteArticleEditText.setText("");

        //If User is returning to Edit a locally stored article, fetch the data
        SharedPreferences sharedPref = getActivity().getSharedPreferences(
                getString(R.string.locallyStoredArticle), Context.MODE_APPEND);
        String localStoredArticleBody = sharedPref.getString("body", null);
        if (localStoredArticleBody != null){ mWriteArticleEditText.setText(localStoredArticleBody);}
    }

    public void saveBody(){

        //Get Body from User's input
        String articleBody = mWriteArticleEditText.getText().toString();

        //Save Body locally in SharedPreferences
        SharedPreferences prefs = getActivity().getSharedPreferences(
                getString(R.string.locallyStoredArticle), Context.MODE_APPEND);
        String key = "body";
        SharedPreferences.Editor editor = prefs.edit();
        editor.putString(key, articleBody)
                .apply();

        //Move to a ConfirmArticle Fragment
        FragmentManager fragmentManager = getFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        ConfirmArticleFragment confirmFrag = new ConfirmArticleFragment();
        fragmentTransaction.replace(R.id.container_body,confirmFrag);
        fragmentTransaction.commit();

    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_write_article, container, false);

        mWriteArticleInstructions = (TextView) rootView.findViewById(R.id.write_article_instructions);
        mWriteArticleEditText = (EditText) rootView.findViewById(R.id.write_article_input_field);

        //If User is returning to Edit a locally-stored article, fetch the article data
        SharedPreferences sharedPref = getActivity().getSharedPreferences(
                getString(R.string.locallyStoredArticle), Context.MODE_APPEND);
        String localStoredArticleTitle = sharedPref.getString("title", null);
        //Display the old Title so User can edit it.
        if (localStoredArticleTitle != null){ mWriteArticleEditText.setText(localStoredArticleTitle);}

        // ---------------------- SAVE BUTTON ------------------------------------------//
        mSaveButton = (Button) rootView.findViewById(R.id.write_article_save_button);
        mSaveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String phase = writingPhases[mCurrentPhase];
                String t = phase + " Saved Locally";
                Toast.makeText(getActivity(),t, Toast.LENGTH_SHORT).show();
                if (phase=="Title") { saveTitle();}
                if (phase=="Body") { saveBody();}

                //Increment to next phase
                mCurrentPhase+=1;

            }
        });
        // -----------------------------------------------------------------------------//
        // ---------------------- CANCEL BUTTON ------------------------------------------//
        mCancelButton = (Button) rootView.findViewById(R.id.write_article_cancel_button);
        mCancelButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                //Stop the Article-Writing process. Return to Home Fragment
                FragmentManager fragmentManager = getFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                HomeFragment homeFrag = new HomeFragment();
                fragmentTransaction.replace(R.id.container_body,homeFrag);
                fragmentTransaction.commit();
            }
        });
        // -----------------------------------------------------------------------------//

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