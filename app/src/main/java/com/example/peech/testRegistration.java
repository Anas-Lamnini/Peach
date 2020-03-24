package com.example.peech;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.storage.FirebaseStorage;

public class testRegistration extends AppCompatActivity {

    private EditText userName, userPassword, userEmail;
    private Button regButton;
    private TextView userLogin;
    private FirebaseAuth firebaseAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test_registration);
        setupUIViews();

        firebaseAuth = FirebaseAuth.getInstance();

        regButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(Validate()){
                    //UPLOAD data to the Database
                    String user_email = userEmail.getText().toString().trim();
                    String user_password = userPassword.getText().toString().trim();
                    firebaseAuth.createUserWithEmailAndPassword(user_email,user_password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()){
                                Toast.makeText(testRegistration.this,"Registration Successful" , Toast.LENGTH_SHORT).show();
                                startActivity(new Intent(testRegistration.this,TestLog.class));
                            }else{
                                Toast.makeText(testRegistration.this,"Registration Failed" , Toast.LENGTH_SHORT).show();
                            }

                        }
                    });
                }
            }
        });
        userLogin.setOnClickListener(new View.OnClickListener(){
            public void onClick(View view){
                    startActivity(new Intent(testRegistration.this,MainMenu.class));
            }
        });
    }
    private void setupUIViews()
    {

        userName = (EditText)findViewById(R.id.etUserName);
        userEmail = (EditText)findViewById(R.id.etUserEmail);
        userPassword = (EditText)findViewById(R.id.etUserPassword);
        regButton = (Button)findViewById(R.id.btnRegister);
        userLogin = (TextView) findViewById(R.id.tvUserLogin);
    }
    private Boolean Validate(){
        Boolean result = false;

        String name= userName.getText().toString();
        String password= userPassword.getText().toString();
        String email= userEmail.getText().toString();

        if (name.isEmpty() || password.isEmpty() || email.isEmpty()){
            Toast.makeText(testRegistration.this,"Please enter all the details" , Toast.LENGTH_SHORT).show();

        }else {
                result = true;
        }
        return result;
    }
}
