package com.example.robert.lab1;

import android.content.ContentValues;
import android.content.Context;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import java.util.ArrayList;

public class ChatWindow extends AppCompatActivity {

    ListView lv1;
    EditText et1;
    String eT1;
    Button bt1;
    ChatAdapter cA;

    Cursor cursor;
    Context ctx = this;
    ContentValues s;
    ChatDatabaseHelper tempDb = new ChatDatabaseHelper(ctx);
    ArrayList<String> chatMessages = new ArrayList<>();

    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;
    boolean exists;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat_window);
        lv1 = (ListView) findViewById(R.id.listView);
        et1 = (EditText) findViewById(R.id.editText);
        bt1 = (Button) findViewById(R.id.button4);
        cA = new ChatAdapter(this);
        lv1.setAdapter(cA);
        cursor = tempDb.getInformation(tempDb);
        sharedPreferences = getSharedPreferences("exists", Context.MODE_PRIVATE);
        s = new ContentValues();
        exists = sharedPreferences.getBoolean("exists", false);
        cursor.moveToFirst();

        if(exists) {
            do {

                chatMessages.add(cursor.getString(0).toString());
                Log.i("ChatWindow ", " Cursor’s  column count = " + cursor.getColumnName(cursor.getColumnIndex(TableData.TableInfo.KEY_MESSAGE)));
                Log.i("ChatWindow ", " Cursor’s  column count = " + cursor.getColumnCount());
                Log.i("ChatWindow ", "SQL MESSAGE:" + cursor.getString(cursor.getColumnIndex(TableData.TableInfo.KEY_MESSAGE)));


            } while (cursor.moveToNext());
        }

        bt1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                chatMessages.add(et1.getText().toString());


                tempDb.onInsert(tempDb, et1.getText().toString());
                Toast.makeText(getBaseContext(), "insert sucessful", Toast.LENGTH_LONG).show();

                editor = sharedPreferences.edit();
                editor.putBoolean("exists", true);
                editor.apply();

                et1.setText("");
                cA.notifyDataSetChanged();


            }
        });




    }
	/* ***********************************************************************************************/
    public class ChatAdapter extends ArrayAdapter<String> {
        Context ctx;

        public ChatAdapter(Context ctx) {
            super(ctx, 0);
            this.ctx = ctx;
        }

        public int getCount() {
            return chatMessages.size();
        }

        public String getItem(int position) {
            return chatMessages.get(position);
        }

        public View getView(int position, View convertView, ViewGroup parent) {
            LayoutInflater inflater = ChatWindow.this.getLayoutInflater();

            View result = null;
            if (position % 2 == 0) {
                result = inflater.inflate(R.layout.chat_row_incoming, null);
            } else {
                result = inflater.inflate(R.layout.chat_row_outgoing, null);
            }

            TextView message = (TextView) result.findViewById(R.id.message_text);
            message.setText(getItem(position));
            return result;
        }
    }
	/* ***********************************************************************************************/
}
