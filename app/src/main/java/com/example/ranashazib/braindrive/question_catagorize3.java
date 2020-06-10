package com.example.ranashazib.braindrive;

import android.content.Context;

import java.util.Random;

/**
 * Created by Rana Shazib on 8/26/2016.
 */
public class question_catagorize3 {
    private int[] opr1={R.drawable.fruit11,R.drawable.fruit12,R.drawable.fruit13,R.drawable.fruit14,R.drawable.fruit15,R.drawable.fruit16,R.drawable.fruit17,R.drawable.fruit18,R.drawable.fruit19,R.drawable.fruit20};
    private int[] opr2={R.drawable.vege11,R.drawable.vege12,R.drawable.vege13,R.drawable.vege14,R.drawable.vege15,R.drawable.vege16,R.drawable.vege17,R.drawable.vege18,R.drawable.vege19,R.drawable.vege20};
    private int[] opr3={R.drawable.meat11,R.drawable.meat12,R.drawable.meat13,R.drawable.meat14,R.drawable.meat15,R.drawable.meat16,R.drawable.meat17,R.drawable.meat18,R.drawable.meat19,R.drawable.meat20};
    private int[] opr4={R.drawable.swet11,R.drawable.swet12,R.drawable.swet13,R.drawable.swet14,R.drawable.swet15,R.drawable.swet16,R.drawable.swet17,R.drawable.swet18,R.drawable.swet19,R.drawable.swet20};
    Random r;
    private int category;
    private int item;

    public question_catagorize3(Context c) {
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
