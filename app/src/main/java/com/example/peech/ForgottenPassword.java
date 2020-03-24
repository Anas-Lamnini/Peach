package com.example.peech;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class ForgottenPassword extends AppCompatActivity {

    Button BackBut;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgotten_password);

        BackBut= findViewById(R.id.Transfer);

        BackBut.setOnClickListener(new View.OnClickListener() {
            public void onClick(final View v) {
                // Do something in response to button click
                Intent intent1 = new Intent(ForgottenPassword.this, RegisterActivity.class);
                startActivity(intent1);
            }});
    }
}
