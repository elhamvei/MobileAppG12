package com.example.renitus.SQliteDB;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.widget.Toast;

import androidx.annotation.Nullable;

import com.example.renitus.Entities.book_modal;
import com.example.renitus.Entities.shoes_modal;


public class Shoes_Database_Helper extends SQLiteOpenHelper {

    private Context context;
    private static final String DATABASE_NAME = "Shoes.db";
    private static final int DATABASE_VERSION = 1;

    private static final String TABLE_NAME_3 = "shoes_database";
    private static final String COLUMN_ID_3 = "_id";
    private static final String COLUMN_SIZE_3 = "shoes_size";
    private static final String COLUMN_BRAND_3 = "shoes_brand";
    private static final String COLUMN_PRICE_3 = "shoes_price";


    public Shoes_Database_Helper(@Nullable Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        this.context = context;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String Shoes_query =
                "CREATE TABLE " + TABLE_NAME_3 +
                        " (" + COLUMN_ID_3 + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                        COLUMN_SIZE_3 + " TEXT, " +
                        COLUMN_BRAND_3 + " TEXT, " +
                        COLUMN_PRICE_3 + " TEXT);";
        db.execSQL(Shoes_query);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i1) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME_3);
        onCreate(db);
    }

    public void insert_shoes(shoes_modal shoe) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();

        cv.put(COLUMN_SIZE_3, shoe.getShoes_size());
        cv.put(COLUMN_BRAND_3, shoe.getShoes_brand());
        cv.put(COLUMN_PRICE_3, shoe.getShoes_price());

        long result = db.insert(TABLE_NAME_3, null, cv);
        if (result == -1) {
            Toast.makeText(context, "Shoe Failed", Toast.LENGTH_SHORT).show();
        } else {
            // Toast.makeText(context, "Added Successfully", Toast.LENGTH_SHORT).show();
        }
    }

    public Cursor getALlShoes() {
        String query = "SELECT * FROM " + TABLE_NAME_3;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = null;
        if (db != null) {
            cursor = db.rawQuery(query, null);
        }
        return cursor;
    }

    public void updateShoe(shoes_modal shoe) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();

        cv.put(COLUMN_SIZE_3, shoe.getShoes_size());
        cv.put(COLUMN_BRAND_3, shoe.getShoes_brand());
        cv.put(COLUMN_PRICE_3, shoe.getShoes_price());

        long result = db.update(TABLE_NAME_3, cv, "_id=?", new String[]{String.valueOf(shoe.getShoes_id())});
        if (result == -1) {
            Toast.makeText(context, "Book Update Failed", Toast.LENGTH_SHORT).show();
        } else {
            //Toast.makeText(context, "Updated Successfully", Toast.LENGTH_SHORT).show();
        }
    }

    public void deleteShoe(shoes_modal shoe) {
        SQLiteDatabase db = this.getWritableDatabase();
        long result = db.delete(TABLE_NAME_3, "_id=?", new String[]{String.valueOf(shoe.getShoes_id())});
        if (result == -1) {
            Toast.makeText(context, "Book Delete Failed", Toast.LENGTH_SHORT).show();
        } else {
            //Toast.makeText(context, "Deleted Successfully", Toast.LENGTH_SHORT).show();
        }
    }


}
