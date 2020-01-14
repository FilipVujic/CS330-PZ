package com.metropolitan.cs330pz.com.metropolitan.cs330pz.gui;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.RequiresApi;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.metropolitan.cs330pz.R;

import java.util.ArrayList;

public class CustomAdapter extends ArrayAdapter<Recipe> {

    //private ArrayList<Recipe> dataSet;
    //Context mContext;


    // View lookup cache
    private static class ViewHolder {
        TextView title;
        TextView synopsis;
        ImageView image;
    }

    public CustomAdapter(ArrayList<Recipe> recipes, Context context) {
        super(context, R.layout.recipe_list_row_item, recipes);
        //this.mContext = context;
    }

    private int lastPosition = -1;

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        // Get the data item for this position
        Recipe dataModel = getItem(position);

        // Check if an existing view is being reused, otherwise inflate the view
        ViewHolder viewHolder; // view lookup cache stored in tag

        if (convertView == null) {

            viewHolder = new ViewHolder();
            LayoutInflater inflater = LayoutInflater.from(getContext());
            convertView = inflater.inflate(R.layout.recipe_list_row_item, parent, false);
            viewHolder.title = (TextView) convertView.findViewById(R.id.recipeTitle);
            viewHolder.synopsis = (TextView) convertView.findViewById(R.id.recipeSynopsis);
            viewHolder.image = (ImageView) convertView.findViewById(R.id.imageView1ID);


            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }

        Animation animation = AnimationUtils.loadAnimation(getContext(), (position > lastPosition) ? R.anim.up_from_bottom : R.anim.down_from_top);
        convertView.startAnimation(animation);
        lastPosition = position;

        RequestOptions options = new RequestOptions()
                .centerCrop()
                .placeholder(R.mipmap.ic_launcher_round)
                .error(R.mipmap.ic_launcher_round);


        Glide.with(getContext()).load(dataModel.getImage_url()).apply(options).into(viewHolder.image);


        viewHolder.title.setText(dataModel.getTitle());
        viewHolder.synopsis.setText(dataModel.getSynopsis());


        // Return the completed view to render on screen
        return convertView;
    }
}
