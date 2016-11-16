package com.example.robert.lab1;

import android.content.DialogInterface;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

public class TestToolbar extends AppCompatActivity {
    View v;
    View vv;
    String message = "number 3 has not been selected yet";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test_toolbar);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);

        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        v = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "You've selected item 1", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });




    }
    public boolean onCreateOptionsMenu(Menu m){
        getMenuInflater().inflate(R.menu.toolbar_menu, m);
        return true;
    }

    public boolean onOptionsItemSelected(MenuItem mi){
        int id = mi.getItemId();

        switch (id){
            case R.id.action_one:

                Snackbar.make(v , message , Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
                Log.d("Toolbar", "Option 1 selected");
                break;
            case R.id.action_two:
                Log.d("Toolbar", "Option 2 selected");
                break;
            case R.id.action_three:

                AlertDialog.Builder builder = new AlertDialog.Builder(this);
                LayoutInflater inflater = this.getLayoutInflater();
                vv = inflater.inflate(R.layout.dialog_box, null);
                builder.setView(vv);
                builder.setPositiveButton(R.string.ok, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        EditText et = (EditText) vv.findViewById(R.id.MessageText);
                        message = et.getText().toString();
                                          }
                });
                builder.setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        EditText et = (EditText) vv.findViewById(R.id.MessageText);

                        message = et.getText().toString();
                    }
                });
                AlertDialog dialog = builder.create();
                dialog.show();
                Log.d("Toolbar", "Option 3 selected");
                break;
            case R.id.about:
                Toast.makeText(this,"About selected",Toast.LENGTH_LONG).show();
        }
        return true;
    }
}
