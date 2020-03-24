package com.example.peech;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;

public class MainMenu extends AppCompatActivity {


    //@SuppressLint("WrongViewCast")
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // Get the view from activity_main.xml
        setContentView(R.layout.activity_main_menu);

        //Button button = null;

         Button Menu = (Button) findViewById(R.id.Menu);
         Button Tutoriel = (Button) findViewById(R.id.Help);
         Button Registe = (Button) findViewById(R.id.Register);

//            System.out.println(" welcom to the menu" + Menu.getId());


            Menu.setOnClickListener(new View.OnClickListener() {
                public void onClick(final View v) {
                    // Do something in response to button click
                    Intent intent1 = new Intent(MainMenu.this, MenuActivity.class);
                    startActivity(intent1);
                }});

            Tutoriel.setOnClickListener(new View.OnClickListener() {
                public void onClick(View v) {
                     // Do something in response to button click
                      Intent intent2=new Intent(MainMenu.this,TutorielActivity.class);
                      startActivity(intent2);

                }});

             Registe.setOnClickListener(new View.OnClickListener() {
                    public void onClick(View v) {
                        // Do something in response to button click
                        Intent intent3=new Intent(MainMenu.this,RegisterActivity.class);
                        startActivity(intent3);

                    }});



    }
    }