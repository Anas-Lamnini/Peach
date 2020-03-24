package com.example.peech;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;

public class RegisterActivity extends AppCompatActivity {


    Button but , BackBut, Register, ForgPass;
    EditText Login, Password;
    TextView Info;

    int Counter=5;
    private FirebaseAuth Auth;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);


        but= (Button) findViewById(R.id.Login_Button);
        Login=(EditText) findViewById(R.id.etName);
        Password= (EditText) findViewById(R.id.Password1);
        BackBut= (Button) findViewById(R.id.Transfer);
        Register=findViewById(R.id.Accunt);
        ForgPass= (Button) findViewById(R.id.forgpass2);


        //if the user is already logged in we will directly start the profile activity
        if (SharedPrefManager.getInstance(this).isLoggedIn()) {
            Toast.makeText(this,"This Account already exist",Toast.LENGTH_SHORT).show();
            finish();
            startActivity(new Intent(this, MainMenu.class));
            return;
        }

        but.setOnClickListener(new View.OnClickListener(){

            public void onClick(View v){
                userLogin();
            }
        });





        BackBut.setOnClickListener(new View.OnClickListener() {
            public void onClick(final View v) {
                // Do something in response to button click
                Intent intent1 = new Intent(RegisterActivity.this, MainMenu.class);
                startActivity(intent1);
            }});

        Register.setOnClickListener(new View.OnClickListener() {
            public void onClick(final View v) {
                // Do something in response to button click
                Intent intent1 = new Intent(RegisterActivity.this, Account.class);
                startActivity(intent1);
            }});
        ForgPass.setOnClickListener(new View.OnClickListener() {
            public void onClick(final View v) {
                // Do something in response to button click
                Intent intent1 = new Intent(RegisterActivity.this, ForgottenPassword.class);
                startActivity(intent1);
            }});

    }

    private void userLogin() {
        //first getting the values
        final String login = Login.getText().toString();
        final String password = Password.getText().toString();

        //validating inputs
        if (TextUtils.isEmpty(login)) {
            Login.setError("Please enter your username");
            Login.requestFocus();
            return;
        }

        if (TextUtils.isEmpty(password)) {
            Password.setError("Please enter your password");
            Password.requestFocus();
            return;
        }

        //if everything is fine

        class UserLogin extends AsyncTask<Void, Void, String> {

            ProgressBar progressBar;

            @Override
            protected void onPreExecute() {
                super.onPreExecute();
                progressBar = (ProgressBar) findViewById(R.id.progressBar);
                progressBar.setVisibility(View.VISIBLE);
            }

            @Override
            protected void onPostExecute(String s) {
                super.onPostExecute(s);
                progressBar.setVisibility(View.GONE);


                try {
                    //converting response to json object
                    JSONObject obj = new JSONObject(s);

                    //if no error in response
                    if (!obj.getBoolean("error")) {
                        Toast.makeText(getApplicationContext(), obj.getString("message"), Toast.LENGTH_SHORT).show();

                        //getting the user from the response
                        JSONObject userJson = obj.getJSONObject("user");

                        //creating a new user object
                        User user = new User(
                                userJson.getInt("id"),
                                userJson.getString("FirstNamev"),
                                userJson.getString("LastName"),
                                userJson.getString("Username"),
                                userJson.getString("Email"),
                                userJson.getString("PhoneNumber"),
                                userJson.getString("Password")
                        );

                        //storing the user in shared preferences
                        SharedPrefManager.getInstance(getApplicationContext()).userLogin(user);

                        //starting the profile activity
                        finish();
                        startActivity(new Intent(getApplicationContext(), MainMenu.class));
                    } else {
                        Toast.makeText(getApplicationContext(), "Invalid username or password", Toast.LENGTH_SHORT).show();
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            protected String doInBackground(Void... voids) {
                //creating request handler object
                RequestHandler requestHandler = new RequestHandler();

                //creating request parameters
                HashMap<String, String> params = new HashMap<>();
                params.put("Username", login);
                params.put("Password", password);
                //returing the response
                return requestHandler.sendPostRequest(URLs.URL_LOGIN, params);
            }
        }

        UserLogin ul = new UserLogin();
        ul.execute();
    }

}
