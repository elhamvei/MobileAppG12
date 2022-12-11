package com.example.renitus.SQliteDB;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.Nullable;

import com.example.renitus.Entities.item_modal;


public class Item_Database_Helper extends SQLiteOpenHelper {

    private Context context;

    private static final String DATABASE_NAME = "Item.db";
    private static final int DATABASE_VERSION = 1;

    private static final String TABLE_NAME_1 = "owner_item_database";
    private static final String COLUMN_ID_1 = "_id";
    private static final String COLUMN_USER_EMAIL = "owner_email";
    private static final String COLUMN_NAME_1 = "item_name";
    private static final String COLUMN_CATEGORY_1 = "item_category";
    private static final String COLUMN_PRICE_1 = "item_price";
    private static final String COLUMN_DESCRIPTION_1 = "item_description";
    private static final String COLUMN_LAT = "item_lat";
    private static final String COLUMN_LONG = "item_long";


    public Item_Database_Helper(@Nullable Context context) {
        super(context, DATABASE_NAME, null,DATABASE_VERSION);
        this.context = context;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String item_query =
                "CREATE TABLE "+ TABLE_NAME_1 +
                        " (" + COLUMN_ID_1 + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                        COLUMN_USER_EMAIL +" TEXT, "+
                        COLUMN_NAME_1 + " TEXT, " +
                        COLUMN_CATEGORY_1 + " TEXT, " +
                        COLUMN_DESCRIPTION_1 + " TEXT, " +
                        COLUMN_PRICE_1 +" INTEGER, " +
                        COLUMN_LAT+" REAL, "+
                        COLUMN_LONG+" REAL);";
        db.execSQL(item_query);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i1) {
        db.execSQL("DROP TABLE IF EXISTS "+ TABLE_NAME_1);
        onCreate(db);
    }

    public void insert_item(item_modal item, String owner_email){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();

        cv.put(COLUMN_USER_EMAIL, owner_email); // i = 1
        cv.put(COLUMN_NAME_1, item.getItem_name()); // i = 2
        cv.put(COLUMN_CATEGORY_1, item.getItem_category());
        cv.put(COLUMN_DESCRIPTION_1, item.getItem_description());
        cv.put(COLUMN_PRICE_1, Integer.parseInt(item.getItem_price()));
        cv.put(COLUMN_LAT, item.getItem_lat());
        cv.put(COLUMN_LONG, item.getItem_long());
        Log.d("inside DB", ""+item.getItem_price());
        long result = db.insert(TABLE_NAME_1,null, cv);
        if(result==-1){
            Toast.makeText(context, "item Failed", Toast.LENGTH_SHORT).show();
        }
        else{
            //Toast.makeText(context, "Added Successfully", Toast.LENGTH_SHORT).show();
        }
    }
    public Cursor getAllItems(){
        String query = "SELECT * FROM "+ TABLE_NAME_1;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = null;
        if(db!=null){
           cursor =  db.rawQuery(query, null);
        }
        return cursor;
    }

    public void updateItem(item_modal item){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();

        cv.put(COLUMN_NAME_1, item.getItem_name());
        cv.put(COLUMN_CATEGORY_1, item.getItem_category());
        cv.put(COLUMN_DESCRIPTION_1, item.getItem_description());
        cv.put(COLUMN_PRICE_1, Integer.parseInt(item.getItem_price()));
        cv.put(COLUMN_LAT, item.getItem_lat());
        cv.put(COLUMN_LONG, item.getItem_long());

//        long result = db.update(TABLE_NAME_1, cv, "_id = ? AND " + COLUMN_USER_EMAIL+" = ?",
//                new String[]{String.valueOf(item.getItem_id()), owner_email});
        long result = db.update(TABLE_NAME_1, cv, "_id = ?",
                new String[]{String.valueOf(item.getItem_id())});
        if(result==-1){
            Toast.makeText(context, "item Update Failed", Toast.LENGTH_SHORT).show();
        }
        else{
            //Toast.makeText(context, "Updated Successfully", Toast.LENGTH_SHORT).show();
        }
    }


    public void deleteItem( item_modal item){
        SQLiteDatabase db = this.getWritableDatabase();
        long result = db.delete(TABLE_NAME_1, "_id = ?",
                new String[]{String.valueOf(item.getItem_id()) });
        if(result==-1){
            Toast.makeText(context, "item Delete Failed", Toast.LENGTH_SHORT).show();
        }
        else{
            //Toast.makeText(context, "Deleted Successfully", Toast.LENGTH_SHORT).show();
        }
    }

}
