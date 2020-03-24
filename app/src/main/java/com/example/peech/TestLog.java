package com.example.peech;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class TestLog extends AppCompatActivity {

    private EditText Name, Password;
    private TextView Info,userRegistration;
    private Button Login;
    private int Counter=5;
    private FirebaseAuth firebaseAuth;
    private ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test_log);

        Name = (EditText)findViewById(R.id.etName);
        Password = (EditText)findViewById(R.id.etPassword);
        Info = (TextView) findViewById(R.id.tvInfo);
        Login = (Button) findViewById(R.id.btnLogin);
        userRegistration = (TextView) findViewById(R.id.tvRegister);

         Info.setText("NO attempt of Remaining : 5");
         firebaseAuth = FirebaseAuth.getInstance();
         progressDialog = new ProgressDialog(this);
        FirebaseUser user = firebaseAuth.getCurrentUser();

        if (user != null){
            finish();
            startActivity(new Intent(TestLog.this,testRegistration.class));

        }

        Login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Validat(Name.getText().toString(), Password.getText().toString());
            }
        });

        userRegistration.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(TestLog.this,testRegistration.class));

            }
        });

    }

    private void Validat(String Username, String UserPassword){

        progressDialog.setMessage("You can subscribe to our App until you are verfied ");
        progressDialog.show();

        firebaseAuth.signInWithEmailAndPassword(Username, UserPassword).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()){
                    progressDialog.dismiss();
                    Toast.makeText(TestLog.this,"Login Successful", Toast.LENGTH_SHORT).show();

                }else {
                    Toast.makeText(TestLog.this,"Login Failed", Toast.LENGTH_SHORT).show();
                    Counter--;
                    Info.setText("Number of remaining time: "+ Counter);
                    progressDialog.dismiss();
                    if (Counter==0){
                        Login.setEnabled(false);
                    }
                }
            }
        });
    }
}
