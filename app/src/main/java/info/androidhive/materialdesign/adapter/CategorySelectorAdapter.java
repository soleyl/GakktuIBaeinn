package info.androidhive.materialdesign.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import info.androidhive.materialdesign.model.Category;
import info.androidhive.materialdesign.model.Mentor;

import info.androidhive.materialdesign.R;

/**
 * Created by troyporter on 4/13/16.
 */
public class CategorySelectorAdapter extends ArrayAdapter<Category> {

    //Generic constructor
    public CategorySelectorAdapter(Context context, Category[] categories){
        super(context, R.layout.mentor_row,categories);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent){
        LayoutInflater mentorInflater = LayoutInflater.from(getContext());
        View categorySelectorView = mentorInflater.inflate(R.layout.category_selector_row, parent, false);

        //Get current iteration of Category
        Category singleCategoryItem = getItem(position);
        String singleCategoryTitleItem = singleCategoryItem.getName();

        // --------------   Set up the Name for Category  ---------------------------//
        TextView categoryNameView = (TextView) categorySelectorView.findViewById(R.id.category_selector_name);
        categoryNameView.setText(singleCategoryTitleItem);

        return categorySelectorView;
    }


}
