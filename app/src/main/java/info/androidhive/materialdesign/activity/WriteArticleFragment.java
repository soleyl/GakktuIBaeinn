package info.androidhive.materialdesign.activity;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import info.androidhive.materialdesign.R;
import info.androidhive.materialdesign.adapter.CategorySelectorAdapter;
import info.androidhive.materialdesign.model.Category;

import android.content.SharedPreferences;
import android.widget.ListAdapter;
import android.widget.Toast;

/**
 * Created by Ravi on 29/07/15.
 */
public class WriteArticleFragment extends Fragment {

    private EditText mWriteArticleEditTitle;
    private EditText mWriteArticleEditBody;
    private EditText mWriteArticleEditImage;
    private Button mSaveButton;
    private Button mCancelButton;

    private static final String TAG = "writeArticle";

    public WriteArticleFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    //This function saves the article to Shared Preferences.
    public void saveArticleLocally(){
        //Get Title from User's input
        String articleTitle = mWriteArticleEditTitle.getText().toString();
        //Get Body from User's input
        String articleBody = mWriteArticleEditBody.getText().toString();
        //Get Image from User's input.
        String articleImage = mWriteArticleEditImage.getText().toString();



        //Save data locally in SharedPreferences
        SharedPreferences prefs = getActivity().getSharedPreferences(
                getString(R.string.locallyStoredArticle), Context.MODE_APPEND);
        SharedPreferences.Editor editor = prefs.edit();
        editor.putString("title", articleTitle)
                .putString("body",articleBody)
                .putString("image",articleImage)
                .apply();

        //Move to a ConfirmArticle Fragment
        FragmentManager fragmentManager = getFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        ConfirmArticleFragment confirmFrag = new ConfirmArticleFragment();
        fragmentTransaction.replace(R.id.container_body,confirmFrag).addToBackStack("tag");
        fragmentTransaction.commit();

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_write_article, container, false);

        mWriteArticleEditTitle = (EditText) rootView.findViewById(R.id.write_article_input_field_title);
        mWriteArticleEditBody = (EditText) rootView.findViewById(R.id.write_article_input_field_body);
        mWriteArticleEditImage = (EditText) rootView.findViewById(R.id.write_article_input_field_image);

        //If User is returning to Edit a locally-stored article, fetch the article data
        SharedPreferences sharedPref = getActivity().getSharedPreferences(
                getString(R.string.locallyStoredArticle), Context.MODE_APPEND);
        String localStoredArticleTitle = sharedPref.getString("title", null);
        String localStoredArticleBody = sharedPref.getString("body", null);
        String localStoredArticleImage = sharedPref.getString("image", null);

        //Display the old Article data so User can edit it.
        if (localStoredArticleTitle != null){ mWriteArticleEditTitle.setText(localStoredArticleTitle);}
        if (localStoredArticleBody != null){ mWriteArticleEditBody.setText(localStoredArticleBody);}
        if (localStoredArticleImage != null){ mWriteArticleEditImage.setText(localStoredArticleImage);}

        // --------------------- CATEGORY SELECTOR LIST VIEW ---------------------------//

        Category children = new Category();
        children.setName("Children");
        Category employment = new Category();
        employment.setName("Employment");
        Category disability = new Category();
        disability.setName("Disability");
        Category citizenship = new Category();
        citizenship.setName("Citizenship");

        Category[] categories = {children, employment, disability, citizenship};
        ListAdapter categorySelectorAdapter = new CategorySelectorAdapter(getActivity(),categories);
        //ListView categorySelectorListView = (ListView) rootView.findViewById(R.id.category_selector_list_view);
        //categorySelectorListView.setAdapter(categorySelectorAdapter);


        // ---------------------- SAVE BUTTON ------------------------------------------//
        mSaveButton = (Button) rootView.findViewById(R.id.write_article_save_button);
        mSaveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                saveArticleLocally();
                String t = "Article Saved Locally";
                Toast.makeText(getActivity(),t, Toast.LENGTH_SHORT).show();
            }
        });

        // ---------------------- CANCEL BUTTON ------------------------------------------//
        mCancelButton = (Button) rootView.findViewById(R.id.write_article_cancel_button);
        mCancelButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                //Stop the Article-Writing process. Return to Home Fragment
                FragmentManager fragmentManager = getFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                HomeFragment homeFrag = new HomeFragment();
                fragmentTransaction.replace(R.id.container_body,homeFrag).addToBackStack("tag");
                fragmentTransaction.commit();
                ActionBar actionBar = ((AppCompatActivity)getActivity()).getSupportActionBar();
                actionBar.setTitle("Home");
            }
        });

        ActionBar actionBar = ((AppCompatActivity)getActivity()).getSupportActionBar();
        actionBar.setTitle("Write An Article");

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