package com.example.robert.lab1;

import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
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


import com.example.robert.lab1.dummy.DummyContent;

import java.util.ArrayList;
import java.util.List;

/**
 * An activity representing a list of Messages. This activity
 * has different presentations for handset and tablet-size devices. On
 * handsets, the activity presents a list of items, which when touched,
 * lead to a {@link MessageDetailActivity} representing
 * item details. On tablets, the activity presents the list of items and
 * item details side-by-side using two vertical panes.
 */
public class MessageListActivity extends AppCompatActivity {

    /**
     * Whether or not the activity is in two-pane mode, i.e. running on a tablet
     * device.
     */
    private boolean mTwoPane;
    ListView lv1;
    EditText et1;
    Button bt1;
    ChatAdapter cA;
    TextView message;
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
        setContentView(R.layout.activity_message_list);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        toolbar.setTitle(getTitle());

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






        // if (findViewById(R.id.message_detail_container) != null) {
            // The detail container view will be present only in the
            // large-screen layouts (res/values-w900dp).
            // If this view is present, then the
            // activity should be in two-pane mode.
         //   mTwoPane = true;
       // }
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

        public View getView(final int position, View convertView, ViewGroup parent) {
            LayoutInflater inflater = MessageListActivity.this.getLayoutInflater();

            View result;
            if (position % 2 == 0) {
                result = inflater.inflate(R.layout.chat_row_incoming, null);
            } else {
                result = inflater.inflate(R.layout.chat_row_outgoing, null);
            }
            final String messageText = getItem(position);
            message = (TextView) result.findViewById(R.id.message_text);
            message.setText(messageText);

            result.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    if (mTwoPane) {
                        Bundle arguments = new Bundle();
                        arguments.putString(MessageDetailFragment.ARG_ITEM_ID, messageText);
                        MessageDetailFragment fragment = new MessageDetailFragment();
                        fragment.setArguments(arguments);
                        getSupportFragmentManager().beginTransaction()
                                .replace(R.id.message_detail_container, fragment)
                                .commit();
                    } else {
                        Context context = v.getContext();
                        Intent intent = new Intent(context, MessageDetailActivity.class);
                        intent.putExtra(MessageDetailFragment.ARG_ITEM_ID, messageText);

                        context.startActivity(intent);
                    }
                }
            });
            return result;
        }
    }
	/* ***********************************************************************************************/

    private void setupRecyclerView(@NonNull RecyclerView recyclerView) {
        recyclerView.setAdapter(new SimpleItemRecyclerViewAdapter(DummyContent.ITEMS));
    }

    public class SimpleItemRecyclerViewAdapter
            extends RecyclerView.Adapter<SimpleItemRecyclerViewAdapter.ViewHolder> {

        private final List<DummyContent.DummyItem> mValues;

        public SimpleItemRecyclerViewAdapter(List<DummyContent.DummyItem> items) {
            mValues = items;
        }

        @Override
        public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.message_list_content, parent, false);
            return new ViewHolder(view);
        }

        @Override
        public void onBindViewHolder(final ViewHolder holder, int position) {
            holder.mItem = mValues.get(position);
            holder.mIdView.setText(mValues.get(position).id);
            holder.mContentView.setText(mValues.get(position).content);

            holder.mView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (mTwoPane) {
                        Bundle arguments = new Bundle();
                        arguments.putString(MessageDetailFragment.ARG_ITEM_ID, holder.mItem.id);
                        MessageDetailFragment fragment = new MessageDetailFragment();
                        fragment.setArguments(arguments);
                        getSupportFragmentManager().beginTransaction()
                                .replace(R.id.message_detail_container, fragment)
                                .commit();
                    } else {
                        Context context = v.getContext();
                        Intent intent = new Intent(context, MessageDetailActivity.class);
                        intent.putExtra(MessageDetailFragment.ARG_ITEM_ID, holder.mItem.id);

                        context.startActivity(intent);
                    }
                }
            });
        }

        @Override
        public int getItemCount() {
            return mValues.size();
        }

        public class ViewHolder extends RecyclerView.ViewHolder {
            public final View mView;
            public final TextView mIdView;
            public final TextView mContentView;
            public DummyContent.DummyItem mItem;

            public ViewHolder(View view) {
                super(view);
                mView = view;
                mIdView = (TextView) view.findViewById(R.id.id);
                mContentView = (TextView) view.findViewById(R.id.content);
            }

            @Override
            public String toString() {
                return super.toString() + " '" + mContentView.getText() + "'";
            }
        }
    }
}
