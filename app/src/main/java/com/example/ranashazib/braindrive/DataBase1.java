package com.example.ranashazib.braindrive;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Rana Shazib on 8/22/2016.
 */
public class DataBase1 extends SQLiteOpenHelper{
    private static final String DATABASE_NAME="mindgame";
    private static final int DATABASE_VERSION=1;
    private static final String TABLE_NAME="playerscore";
    private static final String KEY_NAME="name";
    private static final String KEY_SCORE="score";
    public DataBase1(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        Log.d("Construction", "called....");
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
     db.execSQL("CREATE TABLE playerscore(name TEXT, score TEXT)");
        Log.d("OnCreate", "called....");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXIST playerscore");
    }
    // adding message in database...
    public void addScore(UserData w) {
        SQLiteDatabase db = getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(KEY_NAME, w.getName());
        values.put(KEY_SCORE, w.getScore());
        db.insert(TABLE_NAME, null, values);
        db.close();
    }

    public List<UserData> getScore() {

        List<UserData> wordsList = new ArrayList<UserData>();
        // Select All Query
        String selectQuery = "SELECT  * FROM " + TABLE_NAME;

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                UserData w = new UserData();
                w.setName(cursor.getString(0));
                w.setScore(cursor.getString(1));
                // Adding contact to list
                wordsList.add(w);
            } while (cursor.moveToNext());
        }
        // return contact list
        return wordsList;

    }

    public int updateScore(long rowId, String name,String score) {

        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(KEY_NAME, name);
        values.put(KEY_SCORE, score);

        // updating row
        return db.update(TABLE_NAME, values, "1 = " + rowId , null);

    }
}
