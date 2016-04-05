package info.androidhive.materialdesign.model;

/**
 * Created by troyporter on 4/5/16.
 */
public class User {

    private int mId;
    //ASK ÁRNI: SHOULD THIS BE A URL, NOT STRING?
    private String mUrl;
    private String mUserName;
    private String mEmail;
    //Groups?

    public User() {}

    public void setId(int i) { mId=i;}

    public int getId() {return mId;}

    public void setUserName(String u) { mUserName=u;}

    public String getUserName() {return mUserName;}

    public void setUrl(String u) { mUrl=u;}

    public String getUrl() {return mUrl;}

    public void setEmail(String e) { mEmail=e;}

    public String getEmail() {return mEmail;}

}
