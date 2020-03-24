package com.example.peech;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.MediaController;
import android.widget.VideoView;

public class TutorielActivity extends AppCompatActivity {

    VideoView vid;
    MediaController media;
    Button but;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tutoriel);
        vid = findViewById(R.id.video);
        media = new MediaController(this);
        but = findViewById(R.id.Transfer);

        but.setOnClickListener(new View.OnClickListener() {
            public void onClick(final View v) {
                // Do something in response to button click
                Intent intent1 = new Intent(TutorielActivity.this, MenuActivity.class);
                startActivity(intent1);
            }});

    }
    public void playVideo(View v) {
        MediaController m = new MediaController(this);
        vid.setMediaController(m);

        String path = "android.resource://com.aasemjs.videoplaydemo/"+R.raw.video;

        Uri u = Uri.parse(path);

        vid.setVideoURI(u);

        vid.start();

    }


}
