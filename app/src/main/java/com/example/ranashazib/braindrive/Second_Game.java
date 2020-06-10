package com.example.ranashazib.braindrive;

import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.ColorFilter;
import android.graphics.drawable.Drawable;
import android.media.MediaPlayer;
import android.os.Build;
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
import java.util.logging.Handler;
import java.util.logging.LogRecord;


public class Second_Game extends AppCompatActivity {

    public question_sec_game ques;
    TextView textViewtime;
    TextView countertext;
    int previousImage;
    int currentImage;
//    ImageView temp;
    public int counter=0;
    private static final String FORMAT = "%02d:%02d:%02d";
    TextView text1,text2;
    CounterClass timer;
    Random rand;
    ImageView correctImg,incorrectImg,holder;

    ArrayList<Float> allOperationsAns;
//    @Override
//    protected void onPause() {
//        super.onPause();
//        mysound.release();
//    }

//    MediaPlayer mysound;
    MediaPlayer correctsound,incorrectsound;

    @Override
    public void onBackPressed()
    {

        timer.cancel();
//       timer=null;
//         // code here to show dialog
       Second_Game.this.finish();
        startActivity(new Intent(Second_Game.this,GameList.class));
        // / optional depending on your needs
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second__game);
        countertext=(TextView)findViewById(R.id.textView12);


        // countertext.setText(counter);
      ques=new question_sec_game(getApplication());
        currentImage=ques.getrandomnum();
        holder=(ImageView)findViewById(R.id.imageView9);
        holder.setImageResource(currentImage);
        Animation animation1 = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.fade1);
            holder.startAnimation(animation1);

//        final String border=ques.getborder();


//        temp=(ImageView)findViewById(R.id.imageView5);
        correctImg=(ImageView)findViewById(R.id.imageView5);
        incorrectImg=(ImageView)findViewById(R.id.imageView6);

//        mysound = MediaPlayer.create(this, R.raw.my);
        correctsound = MediaPlayer.create(this, R.raw.cor);
        incorrectsound = MediaPlayer.create(this, R.raw.wro);
        rand = new Random();
        text1=(TextView)findViewById(R.id.textView2);
        text2=(TextView) findViewById(R.id.textView3);

        allOperationsAns=new ArrayList<Float>();
        //  image1 = (ImageButton) findViewById(R.id.imageButton5);
        textViewtime = (TextView) findViewById(R.id.textView7);
        textViewtime.setText("01:00");
        timer = new CounterClass(60000, 1000);

        timer.start();
//        mysound.start();


        //back game

        ImageView im = (ImageView) findViewById(R.id.imageView4);
        im.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Second_Game.this, GameList.class);
                startActivity(intent);
                timer.cancel();

            }
        });



        ImageButton img1 = (ImageButton) findViewById(R.id.imageButton6);
        img1.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {


                {
if(previousImage==currentImage){
//                    Toast.makeText(getApplication(),"correct",Toast.LENGTH_SHORT).show();
    previousImage=currentImage;
    currentImage=ques.getrandomnum();
    holder.setImageResource(currentImage);


    holder.invalidate();
    setCounterToPositive();
    loadCorrect();
//    ImageView image = (ImageView) findViewById(R.id.imageView);
    Animation animation1 = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.fade);
    holder.startAnimation(animation1);
}
                    else{
//                    Toast.makeText(getApplication(),"in correct",Toast.LENGTH_SHORT).show();
    previousImage=currentImage;
    currentImage=ques.getrandomnum();
    holder.setImageResource(currentImage);
    holder.invalidate();
    Animation animation1 = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.fade);
    holder.startAnimation(animation1);
  setCounterToNegative();
    loadInCorrect();
}
                }

            }



        });
        ImageButton img2 = (ImageButton) findViewById(R.id.imageButton9);
        img2.setOnClickListener(new View.OnClickListener() {


            @Override
            public void onClick(View v) {
                if(previousImage!=currentImage){
//                    Toast.makeText(getApplication(),"correct",Toast.LENGTH_SHORT).show();
                    previousImage=currentImage;
                    currentImage=ques.getrandomnum();
                    holder.setImageResource(currentImage);
                    holder.invalidate();
                    Animation animation1 = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.fade);
                    holder.startAnimation(animation1);
                    setCounterToPositive();
                    loadCorrect();
                }
                else{
//                    Toast.makeText(getApplication(),"incorrect",Toast.LENGTH_SHORT).show();
                    previousImage=currentImage;
                    currentImage=ques.getrandomnum();
                    holder.setImageResource(currentImage);
                    holder.invalidate();
                    Animation animation1 = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.fade);
                    holder.startAnimation(animation1);
                    setCounterToNegative();
                    loadInCorrect();
                        }
                    }
                });

CounterClasss s=new CounterClasss(2000,1000);
        s.start();
    }


    public void setCounterToPositive(){
        counter++;
        countertext.setText(String.valueOf(counter));
    }


    public void setCounterToNegative(){
        counter--;
        countertext.setText(String.valueOf(counter));
    }
    public void loadCorrect() {
        CounterClass3 c=new CounterClass3(200,100,correctImg,R.drawable.tick);
        c.start();

//        holder.setBackgroundColor(Color.GREEN);
        correctsound.start();
    }

    public void loadInCorrect() {

        CounterClass3 c=new CounterClass3(100,50,incorrectImg,R.drawable.cros);
//        holder.setBackgroundColor(Color.RED);
        c.start();

//      incorrectImg.invalidate();
        incorrectsound.start();
    }

    public void savinfo(View view){
        SharedPreferences sharedpref=getSharedPreferences("user info", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor=sharedpref.edit();
        TextView txt=(TextView)findViewById(R.id.textView12);
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
            this.cancel();
            Intent intent=new Intent(Second_Game.this, Second_game_scor.class);
            String s=countertext.getText().toString();
            intent.putExtra("Score",s);
            startActivity(intent);
            Second_Game.this.finish();

        }
    }

    public class CounterClasss extends CountDownTimer {
        public CounterClasss(long millisInFuture, long countDownInterval) {
            super(millisInFuture, countDownInterval);
        }

        @Override
        public void onTick(long millisUntilFinished) {


        }


        @Override
        public void onFinish() {
            previousImage=currentImage;
            currentImage=ques.getrandomnum();

                holder.setImageResource(currentImage);
            holder.invalidate();

        }
    }




    public class CounterClass3 extends CountDownTimer {
        ImageView i;
        int id;
        public CounterClass3(long millisInFuture, long countDownInterval,final ImageView i,int id) {
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


}

