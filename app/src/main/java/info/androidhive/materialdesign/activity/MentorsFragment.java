package info.androidhive.materialdesign.activity;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import info.androidhive.materialdesign.adapter.MentorAdapter;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.Toast;

import info.androidhive.materialdesign.R;
import info.androidhive.materialdesign.model.Mentor;

/**
 * Created by Ravi on 29/07/15.
 */
public class MentorsFragment extends Fragment {

    public MentorsFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        //initDataset();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_mentors, container, false);

        //Build our list
        Mentor alice = new Mentor("Alice", "alice@wonderland.com", R.drawable.alice, R.drawable.flag_english, 0, 0);
        Mentor wonderWoman = new Mentor("Wonder Woman", "diana@prince.com", R.drawable.wonderwoman,
                R.drawable.flag_french,R.drawable.flag_german,R.drawable.flag_english);
        Mentor rene = new Mentor("René", "rene@magritte.org", R.drawable.rene,R.drawable.flag_french,0,0);
        Mentor clippy = new Mentor("Clippy", "bill@gates.com", R.drawable.clippy,R.drawable.flag_swedish,R.drawable.flag_english,0);
        Mentor lucy = new Mentor("Lucy", "lucille_ball@tv.com", R.drawable.lucy,R.drawable.flag_german,0,0);
        Mentor[] mentorData = {alice, wonderWoman, rene,clippy,lucy};

        ListAdapter mentorAdapter = new MentorAdapter(getActivity(),mentorData);
        ListView mentorListView = (ListView) rootView.findViewById(R.id.mentor_list_view);
        mentorListView.setAdapter(mentorAdapter);

        //Temporary Listener.  We can add better functionality (send message to Mentor?)
        mentorListView.setOnItemClickListener(
                new AdapterView.OnItemClickListener(){
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id){
                        String mentor = String.valueOf(parent.getItemAtPosition(position));
                        Toast.makeText(getActivity(), mentor, Toast.LENGTH_SHORT).show();
                    }
                }
        );

        // Inflate the layout for this fragment
        return rootView;
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
    }

    @Override
    public void onDetach() {
        super.onDetach();
    }
}