package com.metropolitan.cs330pz.com.metropolitan.cs330pz.gui;

import android.os.Bundle;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.metropolitan.cs330pz.R;

public class RecipeDetails extends AppCompatActivity {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipe_details);

        String savedExtra = getIntent().getStringExtra("animal");
        TextView myText = (TextView)findViewById(R.id.recipe_details_textView);
        myText.setText(savedExtra);
    }
}
