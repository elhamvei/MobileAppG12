package com.example.renitus.SQliteDB;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.widget.Toast;

import androidx.annotation.Nullable;

import com.example.renitus.Entities.book_modal;
import com.example.renitus.Entities.furniture_modal;


public class Furniture_Database_Helper extends SQLiteOpenHelper {

    private Context context;
    private static final String DATABASE_NAME = "Furniture.db";
    private static final int DATABASE_VERSION = 1;

    private static final String TABLE_NAME_2 = "furniture_database";
    private static final String COLUMN_ID_2 = "_id";
    private static final String COLUMN_TYPE_2 = "furniture_type";
    private static final String COLUMN_MATERIAL_2 = "furniture_material";
    private static final String COLUMN_PRICE_2 = "furniture_price";


    public Furniture_Database_Helper(@Nullable Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        this.context = context;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String Furniture_query =
                "CREATE TABLE " + TABLE_NAME_2 +
                        " (" + COLUMN_ID_2 + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                        COLUMN_TYPE_2 + " TEXT, " +
                        COLUMN_MATERIAL_2 + " TEXT, " +
                        COLUMN_PRICE_2 + " TEXT);";
        db.execSQL(Furniture_query);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i1) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME_2);
        onCreate(db);
    }

    public void insert_furniture(furniture_modal furniture) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();

        cv.put(COLUMN_TYPE_2, furniture.getFurniture_type());
        cv.put(COLUMN_MATERIAL_2, furniture.getFurniture_material());
        cv.put(COLUMN_PRICE_2, furniture.getFurniture_price());

        long result = db.insert(TABLE_NAME_2, null, cv);
        if (result == -1) {
            Toast.makeText(context, "Furniture Failed", Toast.LENGTH_SHORT).show();
        } else {
            //Toast.makeText(context, "Added Successfully", Toast.LENGTH_SHORT).show();
        }
    }

    public Cursor getALlFurniture() {
        String query = "SELECT * FROM " + TABLE_NAME_2;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = null;
        if (db != null) {
            cursor = db.rawQuery(query, null);
        }
        return cursor;
    }

    public void updateFurniture(furniture_modal furniture) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();

        cv.put(COLUMN_TYPE_2, furniture.getFurniture_type());
        cv.put(COLUMN_MATERIAL_2, furniture.getFurniture_material());
        cv.put(COLUMN_PRICE_2, furniture.getFurniture_price());

        long result = db.update(TABLE_NAME_2, cv, "_id=?", new String[]{String.valueOf(furniture.getFurniture_id())});
        if (result == -1) {
            Toast.makeText(context, "Book Update Failed", Toast.LENGTH_SHORT).show();
        } else {
            //Toast.makeText(context, "Updated Successfully", Toast.LENGTH_SHORT).show();
        }
    }


    public void deleteFurniture(furniture_modal furniture) {
        SQLiteDatabase db = this.getWritableDatabase();
        long result = db.delete(TABLE_NAME_2, "_id=?", new String[]{String.valueOf(furniture.getFurniture_id())});
        if (result == -1) {
            Toast.makeText(context, "Book Delete Failed", Toast.LENGTH_SHORT).show();
        } else {
            //Toast.makeText(context, "Deleted Successfully", Toast.LENGTH_SHORT).show();
        }
    }


}
