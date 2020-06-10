package com.example.ranashazib.braindrive;

import android.content.Context;

import java.util.Random;

/**
 * Created by Rana Shazib on 8/26/2016.
 */
public class question_catagorize2 {
    private int[] opr1={R.drawable.herb11,R.drawable.herb12,R.drawable.herb13,R.drawable.herb14,R.drawable.herb15,R.drawable.herb16,R.drawable.herb17,R.drawable.herb18,R.drawable.herb19,R.drawable.herb20};
    private int[] opr2={R.drawable.carn11,R.drawable.carn12,R.drawable.carn13,R.drawable.carn14,R.drawable.carn15,R.drawable.carn16,R.drawable.carn17,R.drawable.carn18,R.drawable.carn19,R.drawable.carn20};
    private int[] opr3={R.drawable.omni11,R.drawable.omni12,R.drawable.omni13,R.drawable.animal19,R.drawable.omni14,R.drawable.omni15,R.drawable.omni16,R.drawable.omni18,R.drawable.person_6,R.drawable.person_9};
    Random r;
    private int category;
    private int item;

    public question_catagorize2(Context c) {
        r=new Random();
        createquestion();
    }
    public void createquestion(){
        category=r.nextInt(3);
        item=r.nextInt(10);
    }
    public int getResource(){
        if(category==0)
            return opr1[item];
        else if(category==1)
            return opr2[item];
        else if(category==2)
            return opr3[item];

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
