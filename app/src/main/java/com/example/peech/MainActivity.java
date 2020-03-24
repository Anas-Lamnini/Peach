package com.example.peech;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import java.util.Timer;
import java.util.TimerTask;

public class MainActivity extends AppCompatActivity {

    Timer Timer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        Timer = new Timer();
        Timer.schedule(new TimerTask() {
            @Override
            public void run() {
                Log.i("Redirection ", "step1");
                startActivity(new Intent(getApplicationContext(),MainMenu.class));
                Log.i("Redirection ", "step2");

            }
        },3000);

    }
}



