package com.example.robert.lab1;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;

public class ChatWindow extends AppCompatActivity {

    ListView lv1;
    EditText et1;
    Button bt1;
    ChatAdapter cA;

    ArrayList<String> chatMessages = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat_window);
        lv1 = (ListView) findViewById(R.id.listView);
        et1 = (EditText) findViewById(R.id.editText);
        bt1 = (Button) findViewById(R.id.button4);
        cA = new ChatAdapter(this);
        lv1.setAdapter(cA);


    bt1.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            chatMessages.add(et1.getText().toString());
            et1.setText("");
            cA.notifyDataSetChanged();

        }
    });

    }

    public class ChatAdapter extends ArrayAdapter<String>{
        public ChatAdapter(Context ctx){
            super(ctx,0);
        }

        public int getCount(){
        return chatMessages.size();
        }
        public String getItem(int position){
        return chatMessages.get(position);
        }
        public View getView(int position, View convertView, ViewGroup parent){
            LayoutInflater inflater = ChatWindow.this.getLayoutInflater();

            View result = null ;
            if(position%2 == 0)
                result = inflater.inflate(R.layout.chat_row_incoming, null);
            else
                result = inflater.inflate(R.layout.chat_row_outgoing, null);

            TextView message = (TextView)result.findViewById(R.id.message_text);
            message.setText(getItem(position));
            return result;
        }
    }
}
