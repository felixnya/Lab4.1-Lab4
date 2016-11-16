package com.example.robert.lab1;

import android.provider.BaseColumns;

/**
 * Created by Robert Jackson on 10/14/2016.
 */

public class TableData {

    public TableData(){

    }

    public static abstract class TableInfo implements BaseColumns{
        public static final String DATABASE_NAME = "Chats.db";
        public static final String TABLE_NAME = "chattable";
        public static final String KEY_ID = "id";
        public static final String KEY_MESSAGE = "message";
    }
}
