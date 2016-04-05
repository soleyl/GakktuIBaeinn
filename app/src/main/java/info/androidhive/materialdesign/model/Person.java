package info.androidhive.materialdesign.model;

import java.util.Date;
import java.util.List;

/**
 * Created by troyporter on 4/5/16.
 */
public class Person {
    private int mId;
    private String mName;
    private User mUser;
    private Credential mCredential;
    private Date mBirthdate;
    private Country mCountryOfOrigin;
    private List<Language> mLanguages;
    private Gender mGender;

    public Person() {}

    private void setId(int i){mId = i;}

    private int getId(){return mId;}

    private void setName(String n){mName = n;}

    private String getName(){
        return mName;
    }

    public void setUser(User u) { mUser =u;}

    public User getUser() {return mUser;}

    public void setCredential(Credential c) { mCredential=c;}

    public Credential getCredential() {return mCredential;}

    public void setBirthDate(Date dob) { mBirthdate=dob;}

    public Date getBirthDate() {return mBirthdate;}

    public void setCountryOfOrigin(Country c) { mCountryOfOrigin=c;}

    public Country getCountryOfOrigin() {return mCountryOfOrigin;}

    public void addLanguage(Language l) { mLanguages.add(l);}

    public List<Language> getLanguages() {return mLanguages;}

    public void setGender(Gender g) { mGender=g;}

    public Gender getGender() {return mGender;}

}
