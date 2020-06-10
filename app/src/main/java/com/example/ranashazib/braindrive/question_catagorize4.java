package com.example.ranashazib.braindrive;

import android.content.Context;

import java.util.Random;

/**
 * Created by Rana Shazib on 8/26/2016.
 */
public class question_catagorize4 {

    private int[] opr1={R.drawable.animal11,R.drawable.animal12,R.drawable.animal13,R.drawable.animal14,R.drawable.animal15,R.drawable.animal16,R.drawable.animal17,R.drawable.animal18,R.drawable.animal19,R.drawable.animal20};
    private int[] opr2={R.drawable.flower11,R.drawable.flower12,R.drawable.flower13,R.drawable.flower14,R.drawable.flower15,R.drawable.flower16,R.drawable.flower17,R.drawable.flower18,R.drawable.flower19,R.drawable.flower20};
    private int[] opr3={R.drawable.bird11,R.drawable.bird12,R.drawable.bird13,R.drawable.bird14,R.drawable.bird15,R.drawable.bird16,R.drawable.bird18,R.drawable.bird19,R.drawable.bird20,R.drawable.bird15};
    private int[] opr4={R.drawable.insect11,R.drawable.insect12,R.drawable.insect13,R.drawable.insect14,R.drawable.insect15,R.drawable.insect16,R.drawable.insect17,R.drawable.insect18,R.drawable.insect19,R.drawable.insect20};
    Random r;
    private int category;
    private int item;

    public question_catagorize4(Context c) {
        r=new Random();
        createquestion();
    }
    public void createquestion(){
        category=r.nextInt(4);
        item=r.nextInt(10);
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
