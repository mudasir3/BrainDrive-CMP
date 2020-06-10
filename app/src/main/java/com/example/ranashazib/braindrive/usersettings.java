package com.example.ranashazib.braindrive;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.Toast;
import android.widget.ToggleButton;

public class usersettings extends AppCompatActivity {
    EditText name;
    DataBase1 db;
    public FirstGame firstGame;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_usersettings);
        name = (EditText) findViewById(R.id.username);
//        if( name.getText().toString().length() == 0 )
//            name.setError( "name is required!" );

        firstGame=new FirstGame();

        Button userbutton = (Button)findViewById(R.id.SaveInfo);

        ToggleButton toggle = (ToggleButton) findViewById(R.id.toggleButton);
        toggle.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {

                } else {
                    // The toggle is disabled
                }
            }
        });


        userbutton.setOnClickListener(

                new View.OnClickListener() {

                    public void onClick(View v) {
                        if (name.getText().toString().trim().length() <= 0) {
                            name.setError( "name is required!" );
//                            Toast.makeText(usersettings.this, "It's empty", Toast.LENGTH_SHORT).show();
                        }

                        else {
                            SharedPreferences sharedpref = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
                            SharedPreferences.Editor editor = sharedpref.edit();

                            editor.putString("UserName", name.getText().toString());
                            editor.commit();
                            Intent intent = new Intent(usersettings.this, MainActivity.class);
                            startActivity(intent);
                        }

                    }
                });

    }
    public void onBackPressed() {

        usersettings.this.finish();
        super.onBackPressed();
        // Clear your session, remove preferences, etc.
        Intent intent = new Intent(getBaseContext(), MainActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);

    }
}