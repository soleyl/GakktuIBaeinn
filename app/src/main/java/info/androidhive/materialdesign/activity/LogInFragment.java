package info.androidhive.materialdesign.activity;

/**
 * Created by Ravi on 29/07/15.
 */
import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.CardView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.okhttp.Credentials;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.List;

import info.androidhive.materialdesign.API;
import info.androidhive.materialdesign.R;

public class LogInFragment extends Fragment {

    EditText mUserNameEditText;
    EditText mPasswordEditText;
    Button mLogInButton;
    Button mLogOutButton;
    CardView mLogInSuccessCard;
    TextView mLogInSuccessText;
    View mLogInLoadingPanel;


    String TAG= "login";

    public LogInFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_log_in, container, false);

        mUserNameEditText = (EditText) rootView.findViewById(R.id.user_name_edit_text);
        mPasswordEditText = (EditText) rootView.findViewById(R.id.password_edit_text);
        mLogInButton = (Button) rootView.findViewById(R.id.log_in_button);
        mLogOutButton = (Button) rootView.findViewById(R.id.log_out_button);
        mLogInSuccessCard = (CardView) rootView.findViewById(R.id.log_in_success_confirmation_card_view);
        mLogInSuccessText = (TextView) rootView.findViewById(R.id.log_in_success_confirmation_text);
        //Display a Loading Panel while Credentials are Verified
        mLogInLoadingPanel = rootView.findViewById(R.id.log_in_loading_panel);

        String loggedInUser = getLoggedInUser();

        if (loggedInUser != "null"){switchFragmentToLogOut(loggedInUser);}

        mLogInButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mLogInLoadingPanel.setVisibility(View.VISIBLE);
                String attemptedUserName = mUserNameEditText.getText().toString();
                String attemptedPassword = mPasswordEditText.getText().toString();
                Boolean userAuthenticated = authenticateUser(attemptedUserName, attemptedPassword);
                if (userAuthenticated) {
                    //Store the successful logIn Credentials
                    setLoggedInUser(attemptedUserName, attemptedPassword);
                    JSONObject userProfile = getUserProfile();
                    saveUserProfileToSharedPreferences(userProfile);
                    switchFragmentToLogOut(attemptedUserName);
                } else {
                    //Toast the User with Error Message for failed Log In
                    mLogInLoadingPanel.setVisibility(View.GONE);
                    Toast toast = Toast.makeText(getActivity(), "UserName or Password Incorrect", Toast.LENGTH_LONG);
                    toast.show();
                }

            }
        });

        mLogOutButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Remove UserName from SharedPreferences
                removeLoggedInUser();
                //Hide Confirmation Message
                mLogInSuccessCard.setVisibility(view.GONE);
                //Hide LogOut Button
                mLogOutButton.setVisibility(view.GONE);
                //Display LogIn Elements
                mUserNameEditText.setVisibility(view.VISIBLE);
                mPasswordEditText.setVisibility(view.VISIBLE);
                mLogInButton.setVisibility(view.VISIBLE);
            }
        });


        // Inflate the layout for this fragment
        return rootView;
    }

    private void saveUserProfileToSharedPreferences(JSONObject profile){
        String avatar="";
        //String languages = new List<String>;
        JSONArray results=new JSONArray();
        try {
            results = profile.getJSONArray("results");
        }
        catch (JSONException j){
            Log.e("first JSON", "error");
        }

        try{
            JSONObject profileObject=results.getJSONObject(0);
            avatar = profileObject.getString("avatar");}
        catch(JSONException j){
            Log.e("getting Avatar", "error");
        }
        Log.e("profile", profile.toString());
        Log.e("results", results.toString());
        Log.e("varat", avatar);
        SharedPreferences prefs = getActivity().getSharedPreferences(
                getString(R.string.user_profile), Context.MODE_APPEND);
        SharedPreferences.Editor editor = prefs.edit();
        editor.putString("avatar", avatar)
                .apply();
    }

    private void switchFragmentToLogOut(String userName){
        //Hide the LogIn Elements
        mUserNameEditText.setVisibility(View.GONE);
        mPasswordEditText.setVisibility(View.GONE);
        mLogInButton.setVisibility(View.GONE);
        //Display a LogIn Confirmation and LogOut Button
        String successMessage = "Welcome " + userName + "! You are successfully logged in.";
        mLogInSuccessText.setText(successMessage);
        mLogInSuccessCard.setVisibility(View.VISIBLE);
        mLogOutButton.setVisibility(View.VISIBLE);
    }
    private JSONObject getUserProfile(){
        API api = new API();
        try {
            return api.getVerified("userprofiles", getAuthentication());
        }
        catch (IOException e){
            Log.e("getting User Profile", "error");
        }
        return new JSONObject();
    }

    private String getAuthentication(){
        SharedPreferences sharedPref = getActivity().getSharedPreferences(
                getString(R.string.loggedInUser), Context.MODE_APPEND);
        return sharedPref.getString("authentication", "null");
    }

    private boolean authenticateUser(String userName, String password){

        boolean authenticationStatus=false;

        try {
            authenticationStatus = API.verifyUser(userName, password);
        }
        catch(IOException e){
            Log.e(TAG,"error with Profile");
        }

        //Log.e(TAG,profile.toString());
        String as = String.valueOf(authenticationStatus);
        mLogInLoadingPanel.setVisibility(View.GONE);
        return authenticationStatus;
    }

    private void setLoggedInUser(String user, String password){
        SharedPreferences prefs = getActivity().getSharedPreferences(
                getString(R.string.loggedInUser), Context.MODE_APPEND);
        String authentication = Credentials.basic(user, password);
        SharedPreferences.Editor editor = prefs.edit();
        editor.putString("userName", user)
                .putString("authentication",authentication)
                .apply();
    }

    private void removeLoggedInUser(){
        SharedPreferences prefs = getActivity().getSharedPreferences(
                getString(R.string.loggedInUser), Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = prefs.edit();
        editor.clear()
                .apply();
    }

    private String getLoggedInUser(){
        SharedPreferences sharedPref = getActivity().getSharedPreferences(
                getString(R.string.loggedInUser), Context.MODE_APPEND);
        return sharedPref.getString("userName", "null");
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
