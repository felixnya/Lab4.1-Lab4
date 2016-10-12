package com.example.robert.lab1;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class StartActivity extends AppCompatActivity {

    public static final int REQUEST_CODE = 5;
    Button b1;
    Button b2;

    Context context = this;
    Toast toast;
    int duration = Toast.LENGTH_SHORT;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start);

        Button next = (Button) findViewById(R.id.button);

        Button startChat = (Button) findViewById(R.id.button2);

        startChat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.i("Start Activity", "User clicked start chat");
               Intent ffs = new Intent(getApplicationContext(),ChatWindow.class);
               startActivity(ffs);
            }
        });

        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent ffs = new Intent(getApplicationContext(),ListItemsActivity.class);
                startActivityForResult(ffs, REQUEST_CODE);
            }
        });
    }

    protected void onStart(){
        super.onStart();
        String ACTIVITY_NAME = "StartActivity";
        Log.i(ACTIVITY_NAME,"In onStart");
    }

    protected void onRestart(){
        super.onRestart();
        String ACTIVITY_NAME = "StartActivity";
        Log.i(ACTIVITY_NAME,"In onRestart");
    }

    protected void onResume(){
        super.onResume();
        String ACTIVITY_NAME = "StartActivity";
        Log.i(ACTIVITY_NAME,"In onResume");
    }

    protected void onPause(){
        super.onPause();
        String ACTIVITY_NAME = "StartActivity";
        Log.i(ACTIVITY_NAME,"In onPause");
    }

    protected void onStop(){
        super.onStop();
        String ACTIVITY_NAME = "StartActivity";
        Log.i(ACTIVITY_NAME,"In onStop");
    }

    protected void onDestroy(){
        super.onDestroy();
        String ACTIVITY_NAME = "StartActivity";
        Log.i(ACTIVITY_NAME,"In onDestroy");
    }

    protected void onActivityResult(int requestCode, int resultCode, Intent data){
        if(requestCode == REQUEST_CODE){
            if(resultCode == Activity.RESULT_OK){
                String ACTIVITY_NAME = "StartActivity";

                String message_passed = data.getStringExtra("Response");
                toast = Toast.makeText(context, message_passed, duration);
                toast.show();
                Log.i(ACTIVITY_NAME, "Returned to StartActivity.onActivityResult");
            }
        }
    }
}
