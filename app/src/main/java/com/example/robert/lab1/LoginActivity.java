package com.example.robert.lab1;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class LoginActivity extends AppCompatActivity {

    public static final String defaultEmail = "DefaultEmail";

    Button loginButton;
    EditText eT1;
    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        sharedPreferences = getSharedPreferences(defaultEmail, Context.MODE_PRIVATE);


        loginButton = (Button) findViewById(R.id.button3);
        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                eT1 = (EditText) findViewById(R.id.editTextLogin);

                editor = sharedPreferences.edit();
                editor.putString(defaultEmail, eT1.getText().toString());
                editor.apply();

                Intent intent = new Intent(getApplicationContext(), StartActivity.class );
                startActivity(intent);
            }
        });
    }

    protected void onStart(){
        super.onStart();
        eT1
                = (EditText) findViewById(R.id.editTextLogin);

        eT1.setHint(sharedPreferences.getString(defaultEmail, "email@domain.com"));


       String ACTIVITY_NAME = "LoginActivity";
        Log.i(ACTIVITY_NAME,"In onStart");
    }

    protected void onRestart(){
        super.onRestart();
        String ACTIVITY_NAME = "LoginActivity";
        Log.i(ACTIVITY_NAME,"In onRestart");
    }
    protected void onResume(){
        super.onResume();
        String ACTIVITY_NAME = "LoginActivity";
        Log.i(ACTIVITY_NAME,"In onResume");
    }

    protected void onPause(){
        super.onPause();
        String ACTIVITY_NAME = "LoginActivity";
        Log.i(ACTIVITY_NAME,"In onPause");
    }

    protected void onStop(){
        super.onStop();
        String ACTIVITY_NAME = "LoginActivity";
        Log.i(ACTIVITY_NAME,"In onStop");
    }

    protected void onDestroy(){
        super.onDestroy();
        String ACTIVITY_NAME = "LoginActivity";
        Log.i(ACTIVITY_NAME,"In onDestroy");
    }
}
