package com.example.ranashazib.braindrive;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.media.MediaPlayer;
import android.os.CountDownTimer;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Random;
import java.util.concurrent.TimeUnit;

public class Third_Game extends AppCompatActivity {

    TextView textViewtime;
    TextView countertext;
    ImageView temp, holder;
    public int counter = 0;
    private static final String FORMAT = "%02d:%02d:%02d";
    TextView text1, text2;
    public question_third_game ques;
    CounterClass timer;
    Random rand;
    ImageView correctImg, incorrectImg;
    ArrayList<Float> allOperationsAns;
    private int cat;
    private int item;

//    @Override
//    protected void onPause() {
//        super.onPause();
//        mysound.release();


//    }

    //    MediaPlayer mysound;

    MediaPlayer correctsound, incorrectsound;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_third__game);
        countertext = (TextView) findViewById(R.id.textView12);
        // countertext.setText(counter);
        temp = (ImageView) findViewById(R.id.imageView5);
        correctImg = (ImageView) findViewById(R.id.imageView5);
        incorrectImg = (ImageView) findViewById(R.id.imageView6);
        holder = (ImageView) findViewById(R.id.imageView10);
        Animation animation1 = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.fady);
        holder.startAnimation(animation1);

        //sound
//                mysound = MediaPlayer.create(this, R.raw.my);
        correctsound = MediaPlayer.create(this, R.raw.cor);
        incorrectsound = MediaPlayer.create(this, R.raw.wro);


        rand = new Random();
        text1 = (TextView) findViewById(R.id.textView2);
        text2 = (TextView) findViewById(R.id.textView3);

        allOperationsAns = new ArrayList<Float>();
        textViewtime = (TextView) findViewById(R.id.textView7);
        textViewtime.setText("01:00");
        timer = new CounterClass(60000, 1000);

        timer.start();

//        mysound.start();
        generateQuestion();
        ImageView im = (ImageView) findViewById(R.id.imageView4);
        im.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Third_Game.this, GameList.class);
                startActivity(intent);
                timer.cancel();

            }
        });

        ImageButton img1 = (ImageButton) findViewById(R.id.person);

        img1.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                if (cat == 0) {

//                    Toast.makeText(getApplicationContext(), "done", Toast.LENGTH_LONG).show();
                    generateQuestion();
                    setCounterToPositive();
                    loadCorrect();
                    correctImg.invalidate();
                    Animation animation1 = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.fady);
                    holder.startAnimation(animation1);
                } else {

//                    Toast.makeText(getApplicationContext(), "bk done", Toast.LENGTH_LONG).show();
                    generateQuestion();
                    setCounterToNegative();
                    loadInCorrect();
                    incorrectImg.invalidate();
                    Animation animation1 = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.fady);
                    holder.startAnimation(animation1);
                }
            }


        });
        ImageButton img2 = (ImageButton) findViewById(R.id.cars);
        img2.setOnClickListener(new View.OnClickListener() {


            @Override
            public void onClick(View v) {

                if (cat == 1) {
//                    Toast.makeText(getApplicationContext(), "done", Toast.LENGTH_LONG).show();
                    generateQuestion();
                    setCounterToPositive();
                    loadCorrect();
                    correctImg.invalidate();
                    Animation animation1 = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.fade);
                    holder.startAnimation(animation1);
                } else {

//                    Toast.makeText(getApplicationContext(), "bk done", Toast.LENGTH_LONG).show();
                    generateQuestion();
                    setCounterToNegative();
                    loadInCorrect();
                    incorrectImg.invalidate();
                    Animation animation1 = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.fade);
                    holder.startAnimation(animation1);
                }
            }
        });

        ImageButton img3 = (ImageButton) findViewById(R.id.cards);
        img3.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {


                if (cat == 2) {
//                    Toast.makeText(getApplicationContext(), "done", Toast.LENGTH_LONG).show();
                    generateQuestion();
                    setCounterToPositive();
                    loadCorrect();
                    correctImg.invalidate();
                    Animation animation1 = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.fady);
                    holder.startAnimation(animation1);
                } else {

//                    Toast.makeText(getApplicationContext(), "bk done", Toast.LENGTH_LONG).show();
                    generateQuestion();
                    setCounterToNegative();
                    loadInCorrect();
                    incorrectImg.invalidate();
                    Animation animation1 = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.fady);
                    holder.startAnimation(animation1);
                }
            }
        });

        ImageButton img4 = (ImageButton) findViewById(R.id.dice);
        img4.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {


                if (cat == 3) {
//                    Toast.makeText(getApplicationContext(), "done", Toast.LENGTH_LONG).show();
                    generateQuestion();
                    setCounterToPositive();
                    loadCorrect();
                    correctImg.invalidate();
                    Animation animation1 = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.fady);
                    holder.startAnimation(animation1);
                } else {

//                    Toast.makeText(getApplicationContext(), "bk done", Toast.LENGTH_LONG).show();

                    generateQuestion();
                    setCounterToNegative();
                    loadInCorrect();
                    incorrectImg.invalidate();
                    Animation animation1 = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.fady);
                    holder.startAnimation(animation1);
                }
            }
        });

    }

    public void generateQuestion() {
        question_third_game q = new question_third_game(Third_Game.this);
        cat = q.getCategory();
        item = q.getItem();
        holder.setImageResource(q.getResource());
        holder.invalidate();
    }


    public void setCounterToPositive() {
        counter++;
        countertext.setText(String.valueOf(counter));
    }


    public void setCounterToNegative() {
        counter--;
        countertext.setText(String.valueOf(counter));
    }

    public void loadCorrect() {
        CounterClasss c=new CounterClasss(100,50,correctImg,R.drawable.tick);
        c.start();

        correctsound.start();
        correctsound.setVolume(20,0);
    }

    public void loadInCorrect() {
        CounterClasss c=new CounterClasss(100,50,incorrectImg,R.drawable.cros);
        c.start();

//      incorrectImg.invalidate();
        incorrectsound.start();
        incorrectsound.setVolume(20,0);
    }

    public void savinfo(View view) {
        SharedPreferences sharedpref = getSharedPreferences("user info", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedpref.edit();
        TextView txt = (TextView) findViewById(R.id.textView12);
        editor.putString("ScoreFirstGame", txt.getText().toString());
        editor.apply();

    }

    public class CounterClass extends CountDownTimer {
        public CounterClass(long millisInFuture, long countDownInterval) {
            super(millisInFuture, countDownInterval);
        }

        @Override
        public void onTick(long millisUntilFinished) {

            textViewtime.setText("" + String.format(FORMAT,
                    TimeUnit.MILLISECONDS.toHours(millisUntilFinished),
                    TimeUnit.MILLISECONDS.toMinutes(millisUntilFinished) - TimeUnit.HOURS.toMinutes(
                            TimeUnit.MILLISECONDS.toHours(millisUntilFinished)),
                    TimeUnit.MILLISECONDS.toSeconds(millisUntilFinished) - TimeUnit.MINUTES.toSeconds(
                            TimeUnit.MILLISECONDS.toMinutes(millisUntilFinished))));

        }

        @Override
        public void onFinish() {
            textViewtime.setText("Completed");
            textViewtime.setTextColor(Color.CYAN);

//            mysound.stop();
            Intent intent = new Intent(Third_Game.this, Third_game_scor.class);
            String s = countertext.getText().toString();
            intent.putExtra("Score", s);
            startActivity(intent);
            Third_Game.this.finish();

        }
    }


    public class CounterClasss extends CountDownTimer {
        ImageView i;
        int id;
        public CounterClasss(long millisInFuture, long countDownInterval,final ImageView i,int id) {
            super(millisInFuture, countDownInterval);
            this.i=i;
            this.id=id;
        }

        @Override
        public void onTick(long millisUntilFinished) {
            i.setImageResource(id);
            i.invalidate();
        }


        @Override
        public void onFinish() {

            //thred
            i.setImageResource(0);
            i.invalidate();
        }

    }
    @Override
    public void onBackPressed()
    {

        timer.cancel();
//       timer=null;
//         // code here to show dialog
        Third_Game.this.finish();
        startActivity(new Intent(Third_Game.this,CatagoriseList.class));
        // / optional depending on your needs
    }

}
