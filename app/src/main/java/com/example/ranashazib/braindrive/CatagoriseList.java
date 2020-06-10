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

public class CatagoriseList extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_catagorise_list);
        ImageView img = (ImageView) findViewById(R.id.imageView11);
        Animation animation2 = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.blink);
        img.startAnimation(animation2);

        ImageView img1 = (ImageView) findViewById(R.id.imageView12);
        Animation animation1 = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.blink);
        img1.startAnimation(animation1);

        ImageView img22 = (ImageView) findViewById(R.id.imageView13);
        Animation animation22 = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.blink);
        img22.startAnimation(animation22);

        ImageView img21 = (ImageView) findViewById(R.id.imageView15);
        Animation animation21 = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.blink);
        img21.startAnimation(animation21);

        ImageButton image1 = (ImageButton) findViewById(R.id.imageButton2);
        image1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(CatagoriseList.this, Third_Game_Instruction.class);
                startActivity(intent);
            }


        });

        ImageButton image2 = (ImageButton) findViewById(R.id.imageButton11);
        image2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(CatagoriseList.this, EdibleInstruction.class);
                startActivity(intent);
            }


        });
        ImageButton image3 = (ImageButton) findViewById(R.id.imageButton4);
        image3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(CatagoriseList.this, HumanInstruction.class);
                startActivity(intent);
            }


        });
        ImageButton imageButton = (ImageButton) findViewById(R.id.imageButton5);
        imageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(CatagoriseList.this, ConsumerInstruction.class);
                startActivity(intent);
            }


        });
    }
    public void onBackPressed() {

        CatagoriseList.this.finish();
        super.onBackPressed();
        // Clear your session, remove preferences, etc.
        Intent intent = new Intent(getBaseContext(), GameList.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);

    }
}