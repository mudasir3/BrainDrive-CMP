package com.example.ranashazib.braindrive;

import android.content.Context;

import java.util.ArrayList;
import java.util.Random;

/**
 * Created by Rana Shazib on 8/13/2016.
 */
public class question_third_game {

    private int[] opr1={R.drawable.person_2,R.drawable.person_3,R.drawable.person_4,R.drawable.person_6,R.drawable.person_8,R.drawable.person_9};
    private int[] opr2={R.drawable.car_1,R.drawable.car_4,R.drawable.car_5,R.drawable.car_6,R.drawable.car_7,R.drawable.car_9};
    private int[] opr3={R.drawable.card_4,R.drawable.card_5,R.drawable.card_6,R.drawable.card_7,R.drawable.card_9,R.drawable.card_10};
    private int[] opr4={R.drawable.dice_2,R.drawable.dice_3,R.drawable.dice_4,R.drawable.dice_5,R.drawable.dice_8,R.drawable.dice_10};
    Random r;
    private int category;
    private int item;

    public question_third_game(Context c) {
        r=new Random();
        createquestion();
    }
    public void createquestion(){
        category=r.nextInt(4);
        item=r.nextInt(6);
    }
public int getResource(){
    if(category==0)
        return opr1[item];
    else if(category==1)
        return opr2[item];
    else if(category==2)
        return opr3[item];
    else if(category==3)
        return opr4[item];
    else{
        return 0;
    }

}

    public int getCategory() {
        return category;
    }

    public int getItem() {
        return item;
    }
}