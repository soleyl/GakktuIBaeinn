package info.androidhive.materialdesign.model;

/**
 * Created by troyporter on 3/29/16.
 */
public class Mentor {
    private String mName;
    private String mEmail;
    private int mImageURL;
    private int mLanguage1;
    private int mLanguage2;
    private int mLanguage3;


    public Mentor(String n, String e, int i, int lang1, int lang2, int lang3 ){
        mName = n;
        mEmail= e;
        mImageURL = i;
        mLanguage1 = lang1;
        mLanguage2 = lang2;
        mLanguage3 = lang3;
    }

    public String getName(){
        return mName;
    }

    public String getEmail(){
        return mEmail;
    }

    public int getImageURL(){
        return mImageURL;
    }

    public int getLang1URL(){ return mLanguage1;}
    public int getLang2URL(){ return mLanguage2;}
    public int getLang3URL(){ return mLanguage3;}


}
