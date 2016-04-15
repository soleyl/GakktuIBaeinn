package info.androidhive.materialdesign.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import info.androidhive.materialdesign.model.Mentor;

import info.androidhive.materialdesign.R;

/**
 * Created by troyporter on 3/29/16.
 */
public class MentorAdapter extends ArrayAdapter<Mentor> {

    //Generic constructor
    public MentorAdapter(Context context, Mentor[] mentorData){
        super(context, R.layout.mentor_row,mentorData);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent){
        LayoutInflater mentorInflater = LayoutInflater.from(getContext());
        View mentorView = mentorInflater.inflate(R.layout.mentor_row, parent, false);

        //Get current iteration of Mentor
        Mentor singleMentorItem = getItem(position);

        // --------------   Set up the Profile Pic for Mentor  ---------------------------//
        ImageView mentorImage = (ImageView) mentorView.findViewById(R.id.mentor_image_View);
        int imageURL = singleMentorItem.getImageURL();
        mentorImage.setImageResource(imageURL);

        // --------------   Set up the Name for Mentor  ---------------------------//
        TextView mentorNameView = (TextView) mentorView.findViewById(R.id.mentor_name_View);
        mentorNameView.setText(singleMentorItem.getName());

        // --------------   Set up the Email for Mentor  ---------------------------//
        TextView mentorEmailView = (TextView) mentorView.findViewById(R.id.mentor_email_view);
        mentorEmailView.setText(singleMentorItem.getEmail());

        // --------------   Set up the Language Flags for Mentor  ------------------//
        ImageView mentorLang1 = (ImageView) mentorView.findViewById(R.id.flag_image_View_1);
        ImageView mentorLang2 = (ImageView) mentorView.findViewById(R.id.flag_image_View_2);
        ImageView mentorLang3 = (ImageView) mentorView.findViewById(R.id.flag_image_View_3);
        int lang1 = singleMentorItem.getLang1URL();
        int lang2 = singleMentorItem.getLang2URL();
        int lang3 = singleMentorItem.getLang3URL();
        if (lang1 != 0){mentorLang1.setImageResource(lang1);}
        if (lang2 != 0){mentorLang2.setImageResource(lang2);}
        if (lang3 != 0){mentorLang3.setImageResource(lang3);}

        return mentorView;
    }


}
