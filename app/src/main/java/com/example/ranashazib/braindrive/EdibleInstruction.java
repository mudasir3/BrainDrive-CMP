package com.example.ranashazib.braindrive;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageButton;
import android.widget.ImageView;

public class EdibleInstruction extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edible_instruction);
        ImageView img = (ImageView) findViewById(R.id.imageView11);
        Animation animation2 = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.blink);
        img.startAnimation(animation2);
        ImageButton image2 = (ImageButton) findViewById(R.id.imageButton4);

        image2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(EdibleInstruction.this, EdibleCategoryGame.class);
                startActivity(intent);


            }
        });
    }
    public void onBackPressed() {

        EdibleInstruction.this.finish();
        startActivity(new Intent(EdibleInstruction.this, CatagoriseList.class));
    }
}