package info.androidhive.materialdesign.model;

import java.util.Date;


/**
 * Created by troyporter on 4/2/16.
 */
public class Article {
    private String id;
    private String title;
    private String image;
    private String intro; //The first few words of the longer Content string.
    private Category category;
    private Language language;
    private String content;
    private String author;
    private String originalDate;
    private int rating;
    private int numberOfRatings;

    public Article() {

    }

    public void setTitle(String t) {
        title = t;
    }

    public String getTitle() {
        return title;
    }

    public void setContent(String c) {
        content = c;
        setIntro(c);
    }

    public String getContent() {
        return content;
    }

    public void setAuthor(String a) {
        author=a;
    }

    public String getAuthor() {
        return author;
    }

    public void setOriginalDate(String d) {
        originalDate=d;
    }

    public String getOriginalDate() {
        return originalDate;
    }

    public void setImage(String i) {
        image = i;
    }

    public String getImage() {
        return image;
    }

    public String getIntro() {
        return intro;
    }

    private void setIntro(String c) {
        String subString;
        if (c.length() > 50)
        {
            subString = c.substring(0, 49);
            subString=subString + "...";
        }
        else {subString = c;}
        intro = subString;
    }
}
