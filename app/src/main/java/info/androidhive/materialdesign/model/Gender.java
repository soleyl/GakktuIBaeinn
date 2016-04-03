package info.androidhive.materialdesign.model;

/**
 * Created by troyporter on 3/31/16.
 */
public class Gender {
    private String mName;
    private int mId;

    public Gender(String n, int i){
        mName = n;
        mId = i;
    }

    public String getName(){return mName;}
    public int getId(){return mId;}

    @Override
    public String toString(){return mName;}

}
