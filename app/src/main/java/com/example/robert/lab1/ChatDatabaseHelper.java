package com.example.robert.lab1;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

/**
 * Created by Robert Jackson on 10/14/2016.
 */

public class ChatDatabaseHelper extends SQLiteOpenHelper {
    public static final int VERSION_NUM = 201;
    public String CREATE_QUERY = "CREATE TABLE "+ TableData.TableInfo.TABLE_NAME + "(" + TableData.TableInfo.KEY_ID +" INTEGER PRIMARY KEY AUTOINCREMENT, "
            + TableData.TableInfo.KEY_MESSAGE + " TEXT );";

    public ChatDatabaseHelper(Context ctx) {
        super(ctx, TableData.TableInfo.DATABASE_NAME, null, VERSION_NUM);
        Log.d("Database Operations", "Database Created");
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_QUERY);
        Log.d("Database Operations", "Table is Created");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TableData.TableInfo.TABLE_NAME + ";");
        Log.d("Database Operations", "Table dropped");
        onCreate(db);
    }

    public void onInsert(ChatDatabaseHelper cDh, String message ){
        SQLiteDatabase  SQL = cDh.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put(TableData.TableInfo.KEY_MESSAGE, message);
        long k = SQL.insert(TableData.TableInfo.TABLE_NAME, null, cv);

        Log.d("Database Operations", "One raw inserted");

    }

    public Cursor getInformation(ChatDatabaseHelper cdh){
        SQLiteDatabase SQ = cdh.getReadableDatabase();
        String[] message = {TableData.TableInfo.KEY_MESSAGE};
        Cursor CR = SQ.query(TableData.TableInfo.TABLE_NAME, message, null, null, null, null, null);
        return CR;
    }

}