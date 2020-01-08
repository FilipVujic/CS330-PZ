package com.metropolitan.cs330pz.com.metropolitan.cs330pz.gui;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;


import com.google.android.material.snackbar.Snackbar;
import com.metropolitan.cs330pz.R;

import org.w3c.dom.Text;

import java.util.ArrayList;

public class CustomAdapter extends ArrayAdapter<Recipe> implements View.OnClickListener {

    private ArrayList<Recipe> dataSet;
    Context mContext;


    // View lookup cache
    private static class ViewHolder {
        TextView title;
        TextView synopsis;
        ImageView image;
    }

    public CustomAdapter(ArrayList<Recipe> recipes, Context context) {
        super(context, R.layout.recipe_list_row_item);
        this.dataSet = recipes;
        this.mContext = context;
    }

    @Override
    public void onClick(View v) {

        int position = (Integer) v.getTag();
        Object object = getItem(position);
        Recipe dataModel = (Recipe) object;

        switch (v.getId())
        {
            case R.id.imageView1ID:
                Snackbar.make(v, "Release date " + dataModel.getUsername(), Snackbar.LENGTH_LONG)
                        .setAction("No action", null).show();
                break;
        }
    }

    private int lastPosition = -1;

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // Get the data item for this position
        Recipe dataModel = getItem(position);
        // Check if an existing view is being reused, otherwise inflate the view
        ViewHolder viewHolder; // view lookup cache stored in tag

        final View result;

        if (convertView == null) {

            viewHolder = new ViewHolder();
            LayoutInflater inflater = LayoutInflater.from(getContext());
            convertView = inflater.inflate(R.layout.recipe_list_row_item, parent, false);
            viewHolder.title = (TextView) convertView.findViewById(R.id.recipeTitle);
            viewHolder.synopsis = (TextView) convertView.findViewById(R.id.recipeSynopsis);
            viewHolder.image = (ImageView) convertView.findViewById(R.id.imageView1ID);


            result = convertView;

            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
            result=convertView;
        }

        Animation animation = AnimationUtils.loadAnimation(mContext, (position > lastPosition) ? R.anim.up_from_bottom : R.anim.down_from_top);
        result.startAnimation(animation);
        lastPosition = position;

        viewHolder.title.setText(dataModel.getTitle());
        viewHolder.synopsis.setText(dataModel.getSynopsis());
        viewHolder.image.setImageDrawable(null);

        //Log.e("CustomAdapter", convertView.toString());

/*        //dodato START

        View v = View.inflate(mContext, R.layout.list_view, null);
        TextView title = (TextView)v.findViewById(R.id.recipeTitle);
        TextView synopsis = (TextView)v.findViewById(R.id.recipeSynopsis);
        ImageView image = null;

        title.setText(dataSet.get(position).getTitle());
        synopsis.setText(dataSet.get(position).getSynopsis());
        image.setImageDrawable(null);

        convertView = v;
        //dodato END*/



        // Return the completed view to render on screen
        return convertView;
    }
}
