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
import android.widget.TextView;

import java.util.List;

public class Third_game_scor extends AppCompatActivity {

    String dbbscore,score1,j;
    TextView textView,textView2;
    DataBase1 db;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_third_game_scor);



        db = new DataBase1(this);
        textView= (TextView) findViewById(R.id.textView17);
        Animation animation = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.myanimation);
        textView.startAnimation(animation);
        textView2= (TextView)findViewById(R.id.textView18);
        Intent it=getIntent();
        Bundle b =it.getExtras();
        if (b!=null)
        {
            j=(String) b.get("Score");
            textView.setText(j);
        }

        List<UserData> attentionwords = db.getScore();

        for (UserData w : attentionwords) {
            score1 = w.getScore();
            // Writing Contacts to log
            //Log.d("Name: ", log);

        }

        int dbScore = Integer.parseInt(score1);
        int currentScore = Integer.parseInt(j);
        if (currentScore > dbScore){
            db.updateScore(1,"Shazib",j);
        }
        List<UserData> score = db.getScore();
        for (UserData w : score) {
            dbbscore = w.getScore();
            // Writing Contacts to log
            //Log.d("Name: ", log);

        }

        textView2.setText("Highest Score="+dbbscore);

        ImageButton imageButton = (ImageButton) findViewById(R.id.imageButton10);
        imageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(Third_game_scor.this, CatagoriseList.class);
                startActivity(intent);
                Third_game_scor.this.finish();

            }
        });


    }

    public void onBackPressed() {

        Third_game_scor.this.finish();
        startActivity(new Intent(Third_game_scor.this, CatagoriseList.class));
    }
}

