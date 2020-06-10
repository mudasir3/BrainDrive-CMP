package com.example.ranashazib.braindrive;

import android.annotation.TargetApi;
import android.content.Context;
import android.os.Build;

import java.util.ArrayList;
import java.util.Random;

/**
 * Created by Rana Shazib on 8/10/2016.
 */
public class question_sec_game {
    private Random generator;


    private ArrayList images;
    private Context c;
//    private int brand=0;
//    private int[] border = {R.drawable.back, R.drawable.bg2, R.drawable.bg_game11, R.drawable.main_bg};

    public question_sec_game(Context c) {
        generator = new Random();

        images = new ArrayList();
        this.c = c;
        doSomething(c);


    }

    public void doSomething(Context ctx) {
//              images.add(R.drawable.sec_game_1);
        images.add(R.drawable.sec_game_2);
        images.add(R.drawable.sec_game_3);
//        images.add(R.drawable.sec_game_4);
//        images.add(R.drawable.sec_game_5);
//        images.add(R.drawable.sec_game_6);
//        images.add(R.drawable.sec_game_7);
//        images.add(R.drawable.sec_game_8);
//        images.add(R.drawable.sec_game_9);
        images.add(R.drawable.sec_game_10);

    }


    public int getrandomnum() {
        return (int) images.get(generator.nextInt(images.size()));

    }

//    public String getborder(){
//        brand=generator.nextInt(4);
//        return String.valueOf(brand);
//    }
}