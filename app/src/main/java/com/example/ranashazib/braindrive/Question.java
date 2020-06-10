package com.example.ranashazib.braindrive;

import java.util.Random;
/**
 * Created by Rana Shazib on 7/30/2016.
 */
public class Question
{
    private Random generator;
    private int rand1;
    private int rand2;
    private char[] op={'/','+','-','*'};
    private char resultOP;
    private float result;

    public Question(){
        generator=new Random();
        rand1= generator.nextInt(100);

        if (rand1==0){

            rand1= rand1+1;
        }
        else if(rand1==2){
            rand1=rand1+1;
        }
        else{
            System.out.println(rand1);
        }
        rand2=generator.nextInt(100);
        if (rand2==0){

            rand2= rand2+1;
        }
        else if(rand1==1){
            rand1=rand1+2;
        }
        else{
            System.out.println(rand2);
        }

    }
    public float getResult(){
        int number=generator.nextInt(4);
        switch (number){
            case 0:
                result=rand1/rand2;
                resultOP='/';
                break;
            case 1:
                result=rand1*rand2;
                resultOP='*';
                break;
            case 2:
                result=rand1+rand2;
                resultOP='+';
                break;
            case 3:
                result=rand1-rand2;
                resultOP='-';
                break;
        }
        return  result;
    }

    public char getOP(){
        return resultOP;
    }
    public String getRandomNumber1(){
        return String.valueOf(rand1);
    }
    public String getRandomNumber2(){
        return String.valueOf(rand2);
    }
}
