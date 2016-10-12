package com.example.robert.lab1;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.provider.MediaStore;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageButton;
import android.widget.Switch;
import android.widget.Toast;

public class ListItemsActivity extends AppCompatActivity {
    static final int REQUEST_IMAGE_CAPTURE = 1;

    ImageButton imageButton;
    Switch switch1;
    CheckBox checkbox;

    CharSequence textOn = "Switch is On";
    CharSequence textOff = "Switch is off";
    Toast toast;
    int duration = Toast.LENGTH_SHORT;

    Context context = this;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_items);

        imageButton = (ImageButton) findViewById(R.id.imageButton);
        switch1 = (Switch) findViewById(R.id.switch1);
        checkbox = (CheckBox) findViewById(R.id.checkBox);

        checkbox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                AlertDialog.Builder builder = new AlertDialog.Builder(ListItemsActivity.this);
                builder.setMessage(R.string.dialog_message).setTitle(R.string.dialog_title)
                        .setPositiveButton(R.string.ok, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                            // ok button
                                Intent resultIntent = new Intent();
                                resultIntent.putExtra("Response", "My info to share");
                                setResult(Activity.RESULT_OK, resultIntent);
                                finish();
                            }
                        }).setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        // cancel
                    }
                }).show();
            }
        });

        switch1.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

                if(isChecked){
                    toast = Toast.makeText(context, textOn, duration);
                    toast.show();
                }else{
                    toast = Toast.makeText(context, textOff, duration);
                    toast.show();
                }
            }
        });

        imageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            dispatchTakePictureIntent();
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

    private void dispatchTakePictureIntent(){
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if(takePictureIntent.resolveActivity(getPackageManager()) != null){
            startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data){
        if(requestCode == REQUEST_IMAGE_CAPTURE && resultCode == RESULT_OK){
            Bundle extras = data.getExtras();
            Bitmap imageBitmap = (Bitmap) extras.get("data");
            imageButton.setImageBitmap(imageBitmap);
        }
    }

}
