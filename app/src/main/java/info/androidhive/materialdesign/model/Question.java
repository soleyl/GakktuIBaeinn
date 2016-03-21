package info.androidhive.materialdesign.model;

/**
 * Created by troyporter on 3/21/16.
 */
public class Question {
    private int mQuestionTextResID;
    private int mAnswerGiven;

    public Question (int questionTextResID){
        mQuestionTextResID = questionTextResID;
        mAnswerGiven=0;
    }

    public int getQuestionTextResID(){
        return mQuestionTextResID;
    }

    public void setmQuestionTextResID(int questionTextResID){
        mQuestionTextResID=questionTextResID;
    }

    public int getAnswerGiven(){
        return mAnswerGiven;
    }

    public void setAnswerGiven(int answerGiven){
        mAnswerGiven=answerGiven;
    }
}
