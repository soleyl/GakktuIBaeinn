package info.androidhive.materialdesign.model;

/**
 * Created by troyporter on 4/5/16.
 */
public class Category {
    private int mId;
    private String mName;

    public Category() {}

    private void setId(int i){mId = i;}

    private int getId(){return mId;}

    private void setName(String n){mName = n;}

    private String getName(){
        return mName;
    }
}
