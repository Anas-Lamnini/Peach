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
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class Account extends AppCompatActivity {

    //Vars Declaration
    private EditText FirstName, LastName, Username, Email, PhoneNumber, Password1, Password2;
    private Button But, BacckBut;
    private ProgressBar Pbar;
    private TextView Sign;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_account);

        //Finding The vars in The activity
        setupUIViews();
        But = findViewById(R.id.ValidationButt);
        Pbar = findViewById(R.id.Loading);
        BacckBut = findViewById(R.id.Transfer);
        Sign = findViewById(R.id.Login);

        //if the user is already logged in we will directly start the profile activity
        if (SharedPrefManager.getInstance(this).isLoggedIn()) {
            Toast.makeText(this,"This Account already exist",Toast.LENGTH_SHORT).show();
            finish();
            startActivity(new Intent(this, MainMenu.class));
            return;
        }


        //Set the onClick function
        Sign.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                   registerUser();
                   //Toast.makeText(this,"This Account already exist",Toast.LENGTH_SHORT).show();
                   Intent intent1 = new Intent(Account.this, RegisterActivity.class);
                   startActivity(intent1);           }


        });

        BacckBut.setOnClickListener(new View.OnClickListener() {
            public void onClick(final View v) {
                Intent intent1 = new Intent(Account.this, MainMenu.class);
                startActivity(intent1);
            }});
    }

    //Get the elements from the activity
    private void setupUIViews(){
        FirstName =(EditText) findViewById(R.id.FirstName);
        LastName = (EditText) findViewById(R.id.LastName);
        Username = (EditText) findViewById(R.id.Username);
        Email = (EditText) findViewById(R.id.Email);
        PhoneNumber =(EditText)  findViewById(R.id.PhoneNumber);
        Password1 = (EditText) findViewById(R.id.Pasword1);
        Password2 = (EditText) findViewById(R.id.Pasword2);


    }


    private void registerUser() {

        final String Firstname = this.FirstName.getText().toString().trim();
        final String Lastname = this.LastName.getText().toString().trim();
        final String username = this.Username.getText().toString().trim();
        final String email = this.Email.getText().toString().trim();
        final String Phonenumber = this.PhoneNumber.getText().toString().trim();;
        final String Password = this.Password1.getText().toString().trim();
        //final String Password2 = this.Password2.getText().toString().trim();

        //first we will do the validations

        if (TextUtils.isEmpty(Firstname)) {
            FirstName.setError("Please enter username");
            FirstName.requestFocus();
            return;
        }
        if (TextUtils.isEmpty(Lastname)) {
            LastName.setError("Please enter username");
            LastName.requestFocus();
            return;
        }
        if (TextUtils.isEmpty(username)) {
            Username.setError("Please enter username");
            Username.requestFocus();
            return;
        }
        if (TextUtils.isEmpty(Phonenumber)) {
            PhoneNumber.setError("Please enter username");
            PhoneNumber.requestFocus();
            return;
        }

        if (TextUtils.isEmpty(email)) {
            Email.setError("Please enter your email");
            Email.requestFocus();
            return;
        }

        if (!android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            Email.setError("Enter a valid email");
            Email.requestFocus();
            return;
        }

        if (TextUtils.isEmpty(Password)) {
            Password1.setError("Enter a password");
            Password1.requestFocus();
            return;
        }

        //if it passes all the validations

        class RegisterUser extends AsyncTask<Void, Void, String> {

            private ProgressBar progressBar;

            @Override
            protected String doInBackground(Void... voids) {
                //creating request handler object
                RequestHandler requestHandler = new RequestHandler();

                //creating request parameters
                HashMap<String, String> params = new HashMap<>();
                params.put("FirstName", Firstname);
                params.put("Email", email);
                params.put("LastName", Lastname);
                params.put("Password", Password);
                params.put("PhoneNumber", Phonenumber);
                params.put("Username", username);

                //returing the response
                return requestHandler.sendPostRequest(URLs.URL_REGISTER, params);
            }

            @Override
            protected void onPreExecute() {
                super.onPreExecute();
                //displaying the progress bar while user registers on the server
                progressBar = (ProgressBar) findViewById(R.id.Loading);
                progressBar.setVisibility(View.VISIBLE);
            }

            @Override
            protected void onPostExecute(String s) {
                super.onPostExecute(s);
                //hiding the progressbar after completion
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
                        Toast.makeText(getApplicationContext(), "Some error occurred", Toast.LENGTH_SHORT).show();
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }

        //executing the async task
        RegisterUser ru = new RegisterUser();
        ru.execute();
    }


}
