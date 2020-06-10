package com.example.ranashazib.braindrive;

/**
 * Created by Rana Shazib on 8/22/2016.
 */
public class UserData {
    //private variables
    String _name;
    String _score;


    // Empty constructor
    public UserData(){

    }
    // constructor
    public UserData(String name, String score){
        this._name = name;
        this._score = score;
    }


    public String getName(){
        return this._name;
    }
    public void setName(String name){
        this._name= name ;
    }

    public String getScore(){
        return this._score;
    }
    public void setScore(String score){
        this._score = score;
    }

}
