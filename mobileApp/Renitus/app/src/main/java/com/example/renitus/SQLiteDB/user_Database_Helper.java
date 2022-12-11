package com.example.renitus.SQliteDB;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.widget.Toast;

import androidx.annotation.Nullable;

import com.example.renitus.Entities.user_modal;


public class user_Database_Helper extends SQLiteOpenHelper {

    private final Context context;
    private static final String DATABASE_NAME = "user.db";
    private static final int DATABASE_VERSION = 1;

    private static final String TABLE_NAME_1 = "user_database";
    private static final String COLUMN_EMAIL_1 = "user_email";
    private static final String COLUMN_PASSWORD_1 = "user_password";
    private static final String COLUMN_TYPE_1 = "user_type";


    public user_Database_Helper(@Nullable Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        this.context = context;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String user_query =
                "CREATE TABLE " + TABLE_NAME_1 +
                        " (" + COLUMN_EMAIL_1 + " TEXT PRIMARY KEY, " +
                        COLUMN_PASSWORD_1 + " TEXT, "+
                        COLUMN_TYPE_1 + " TEXT);";

        db.execSQL(user_query);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i1) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME_1);
        onCreate(db);
    }

    public void insert_renter(user_modal user) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();

        cv.put(COLUMN_EMAIL_1, user.getUser_email());
        cv.put(COLUMN_PASSWORD_1, user.getUser_pass());
        cv.put(COLUMN_TYPE_1, user.getUser_type());


        long result = db.insert(TABLE_NAME_1, null, cv);
        if (result == -1) {
            Toast.makeText(context, "User Failed", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(context, "Added Successfully", Toast.LENGTH_SHORT).show();
        }
    }

    public boolean checkRenter(user_modal user) {
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(
                "SELECT * FROM " + TABLE_NAME_1 + " WHERE " +
                        COLUMN_EMAIL_1 + " = ? AND " +
                        COLUMN_PASSWORD_1+ " = ? AND "+
                        COLUMN_TYPE_1+ " = ?",
                new String[]{user.getUser_email(), user.getUser_pass(), user.getUser_type()});

        if(cursor.getCount()>0){
            cursor.close();
            return true;
        }
        return false;
    }

}
