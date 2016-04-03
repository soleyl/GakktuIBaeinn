package info.androidhive.materialdesign.model;

/**
 * Created by troyporter on 4/2/16.
 */
public class Article {
    private String title;
    private String body;
    //private User author;

    private Article(){

    }

    private void setTitle(String t){
        title =t;
    }

    private void setBody(String b){
        body=b;
    }

    private String getTitle(){
        return title;
    }

    private String getBody(){
        return body;
    }
}
