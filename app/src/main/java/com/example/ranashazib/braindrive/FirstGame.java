package com.example.ranashazib.braindrive;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.media.MediaPlayer;
import android.os.CountDownTimer;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.ContextMenu;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.ImageButton;

import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ToggleButton;

import java.io.IOException;
import java.sql.Time;
import java.util.ArrayList;
import java.util.Random;
import java.util.concurrent.TimeUnit;

public class FirstGame extends AppCompatActivity {
    TextView textViewtime;
    TextView countertext;
    // ImageView temp;
    public int counter = 0;
    private static final String FORMAT = "%02d";
    TextView text1, text2;
    private int _timer_time_remaining;
    CounterClass timer;
    public Question question;
    Random rand;
    float a, b;
    ImageView correctImg, incorrectImg;
    private float correctAns;
    TextView textAns;
    ArrayList<Float> allOperationsAns;

//    @Override
//
//    protected void onPause() {
//        super.onPause();
//        mysound.release();
//    }

//    MediaPlayer mysound;
    MediaPlayer correctsound, incorrectsound;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_first_game);
        countertext = (TextView) findViewById(R.id.textView12);

        ToggleButton toggle = (ToggleButton) findViewById(R.id.toggleButton2);
        toggle.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            public void onCheckedChanged(CompoundButton buttonView,
                                         boolean isChecked) {
                if (isChecked) {
                    correctsound.stop();
                    incorrectsound.stop();
                } else {
                    correctsound.release();
                    incorrectsound.release();

                }
            }
        });

//correct and incorect image

        //  temp=(ImageView)findViewById(R.id.imageView5);
        correctImg = (ImageView) findViewById(R.id.imageView5);
        incorrectImg = (ImageView) findViewById(R.id.imageView6);

//sound
//        mysound = MediaPlayer.create(this, R.raw.my);
        correctsound = MediaPlayer.create(this, R.raw.cor);
        incorrectsound = MediaPlayer.create(this, R.raw.wro);

//random number
        rand = new Random();
        text1 = (TextView) findViewById(R.id.textView2);
        text2 = (TextView) findViewById(R.id.textView3);
        allOperationsAns = new ArrayList<Float>();

        //timer
        textViewtime = (TextView) findViewById(R.id.textView7);
        textViewtime.setText("01:00");
        timer = new CounterClass(60000, 1000);

        timer.start();

//        mysound.start();
        textAns = (TextView) findViewById(R.id.textView5);

        myFunc();
    }


    public void myFunc() {
//
//       correctImg.setVisibility(View.GONE);
//        incorrectImg.setVisibility(View.GONE);
        question = new Question();
//temp.setImageResource(0);
        String numbr1 = question.getRandomNumber1();
        String numbr2 = question.getRandomNumber2();

        text1.setText(numbr1.split("\\.")[0]);
        textAns.setText(String.valueOf(question.getResult()));

        text2.setText(numbr2.split("\\.")[0]);
        boolean b = textViewtime.getText().toString().equals("Completed");
        if (b) {
            Intent intent = new Intent(FirstGame.this, First_Game_Score.class);
            startActivity(intent);
        }
//                // image1.setVisibility(View.GONE);


        // image1.setVisibility(1);

        //back game

        ImageView im = (ImageView) findViewById(R.id.imageView4);
        im.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(FirstGame.this, GameList.class);
                startActivity(intent);
                timer.cancel();


            }
        });


        //performing addition
        final ImageButton img1 = (ImageButton) findViewById(R.id.imageButton6);
        img1.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {


                {
                    if (question.getOP() == '+') {
//                        Toast.makeText(getApplicationContext(), "correct", Toast.LENGTH_LONG).show();
                        setCounterToPositive();
                        loadCorrect();
                        correctImg.invalidate();

                        myFunc();

                    } else {

                        setCounterToNegative();
                        loadInCorrect();
                        incorrectImg.invalidate();
                        myFunc();
                    }
                }

            }


        });
        ImageButton img2 = (ImageButton) findViewById(R.id.imageButton7);
        img2.setOnClickListener(new View.OnClickListener() {


            @Override
            public void onClick(View v) {

                if (question.getOP() == '-') {
//                    Toast.makeText(getApplicationContext(), "correct", Toast.LENGTH_LONG).show();


                    setCounterToPositive();
                    loadCorrect();
                    correctImg.invalidate();
                    myFunc();
                } else {

//                    Toast.makeText(getApplicationContext(), "Incorrect", Toast.LENGTH_LONG).show();


                    setCounterToNegative();
                    loadInCorrect();
                    incorrectImg.invalidate();
                    myFunc();

                }

            }
        });

        ImageButton img3 = (ImageButton) findViewById(R.id.imageButton8);
        img3.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {


                if (question.getOP() == '*') {
//                    Toast.makeText(getApplicationContext(), "correct", Toast.LENGTH_LONG).show();


                    setCounterToPositive();
                    loadCorrect();
                    correctImg.invalidate();
                    myFunc();
                } else {

//                    Toast.makeText(getApplicationContext(), "Incorrect", Toast.LENGTH_LONG).show();


                    setCounterToNegative();
                    loadInCorrect();
                    incorrectImg.invalidate();
                    myFunc();

                }


            }
        });

        ImageButton img4 = (ImageButton) findViewById(R.id.imageButton9);
        img4.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                if (question.getOP() == '/') {
//                    Toast.makeText(getApplicationContext(), "correct", Toast.LENGTH_LONG).show();

                    loadCorrect();
                    setCounterToPositive();
                    myFunc();
                    correctImg.invalidate();

                } else {

//                    Toast.makeText(getApplicationContext(), "Incorrect", Toast.LENGTH_LONG).show();


                    setCounterToNegative();
                    loadInCorrect();
                    incorrectImg.invalidate();
                    myFunc();


                }


            }
        });



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
        CounterClasss c = new CounterClasss(100, 50, correctImg, R.drawable.tick);
        c.start();

        correctsound.start();
        correctsound.setVolume(20, 20);

    }
    public void loadInCorrect() {
        CounterClasss c=new CounterClasss(100,50,incorrectImg,R.drawable.cros);
        c.start();

//      incorrectImg.invalidate();
        incorrectsound.start();
        incorrectsound.setVolume(20, 20);

    }


    public boolean validateAns(char operation, float result) {
        if (operation == '+' && result == correctAns) {
            return true;
        }
        if (operation == '/' && result == correctAns) {
            return true;
        }
        if (operation == '-' && result == correctAns) {
            return true;
        }
        if (operation == '*' && result == correctAns) {
            return true;
        } else {
            return false;
        }
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
//                    TimeUnit.MILLISECONDS.toHours(millisUntilFinished),
//                    TimeUnit.MILLISECONDS.toMinutes(millisUntilFinished) - TimeUnit.HOURS.toMinutes(
//                            TimeUnit.MILLISECONDS.toHours(millisUntilFinished)),
                    TimeUnit.MILLISECONDS.toSeconds(millisUntilFinished) - TimeUnit.MINUTES.toSeconds(
                            TimeUnit.MILLISECONDS.toMinutes(millisUntilFinished))));


        }

        @Override
        public void onFinish() {
            textViewtime.setText("Completed");
            textViewtime.setTextColor(Color.CYAN);
            FirstGame.this.finish();
//            mysound.stop();
            Intent intent=new Intent(FirstGame.this, First_Game_Score.class);
            String s=countertext.getText().toString();
            intent.putExtra("Score",s);
            startActivity(intent);
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
        FirstGame.this.finish();
        startActivity(new Intent(FirstGame.this,GameList.class));
        // / optional depending on your needs
    }


    }










