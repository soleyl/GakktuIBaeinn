package info.androidhive.materialdesign.model;

import java.util.Date;


/**
 * Created by troyporter on 4/2/16.
 */
public class Article {
    private String id;
    private String title;
    private Category category;
    private Language language;
    private String content;
    private Person author;
    private Date originalDate;
    private int rating;
    private int numberOfRatings;

    public Article(){

    }

    public void setTitle(String t){
        title =t;
    }

    public String getTitle(){
        return title;
    }

    public void setContent(String c){
        content=c;
    }

    public String getContent(){
        return content;
    }
}
