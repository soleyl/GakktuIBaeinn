package info.androidhive.materialdesign.model;

/**
 * Created by troyporter on 4/5/16.
 */
public class Credential {
    private int mId;
    private String mName;

    public Credential() {}

    public void setId(int i){mId = i;}

    public int getId(){return mId;}

    public void setName(String n){mName = n;}

    public String getName(){
        return mName;
    }
}
