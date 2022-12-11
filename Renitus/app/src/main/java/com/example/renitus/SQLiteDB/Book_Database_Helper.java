package com.example.renitus.SQliteDB;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.widget.Toast;

import androidx.annotation.Nullable;

import com.example.renitus.Entities.book_modal;


public class Book_Database_Helper extends SQLiteOpenHelper {

    private Context context;
    private static final String DATABASE_NAME = "Book.db";
    private static final int DATABASE_VERSION = 1;

    private static final String TABLE_NAME_1 = "book_database";
    private static final String COLUMN_ID_1 = "_id";
    private static final String COLUMN_NAME_1 = "book_name";
    private static final String COLUMN_AUTHOR_1 = "book_author";
    private static final String COLUMN_PRICE_1 = "book_price";


    public Book_Database_Helper(@Nullable Context context) {
        super(context, DATABASE_NAME, null,DATABASE_VERSION);
        this.context = context;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String Book_query =
                "CREATE TABLE "+ TABLE_NAME_1 +
                        " (" + COLUMN_ID_1 + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                        COLUMN_NAME_1 + " TEXT, " +
                        COLUMN_AUTHOR_1 + " TEXT, " +
                        COLUMN_PRICE_1 +" TEXT);";

        db.execSQL(Book_query);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i1) {
        db.execSQL("DROP TABLE IF EXISTS "+ TABLE_NAME_1);
        onCreate(db);
    }

    public void insert_book(book_modal book){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();

        cv.put(COLUMN_NAME_1, book.getBook_name());
        cv.put(COLUMN_AUTHOR_1, book.getBook_author());
        cv.put(COLUMN_PRICE_1, book.getBook_price());

        long result = db.insert(TABLE_NAME_1,null, cv);
        if(result==-1){
            Toast.makeText(context, "Book Failed", Toast.LENGTH_SHORT).show();
        }
        else{
            //Toast.makeText(context, "Added Successfully", Toast.LENGTH_SHORT).show();
        }
    }
    public Cursor getALlBooks(){
        String query = "SELECT * FROM "+ TABLE_NAME_1;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = null;
        if(db!=null){
           cursor =  db.rawQuery(query, null);
        }
        return cursor;
    }

    public void updateBook(book_modal book){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();

        cv.put(COLUMN_NAME_1, book.getBook_name());
        cv.put(COLUMN_AUTHOR_1, book.getBook_author());
        cv.put(COLUMN_PRICE_1, book.getBook_price());

        long result = db.update(TABLE_NAME_1, cv, "_id=?", new String[]{String.valueOf(book.getBook_id())});
        if(result==-1){
            Toast.makeText(context, "Book Update Failed", Toast.LENGTH_SHORT).show();
        }
        else{
            //Toast.makeText(context, "Updated Successfully", Toast.LENGTH_SHORT).show();
        }
    }


    public void deleteBook( book_modal book){
        SQLiteDatabase db = this.getWritableDatabase();
        long result = db.delete(TABLE_NAME_1, "_id=?", new String[]{String.valueOf(book.getBook_id())});
        if(result==-1){
            Toast.makeText(context, "Book Delete Failed", Toast.LENGTH_SHORT).show();
        }
        else{
            //Toast.makeText(context, "Deleted Successfully", Toast.LENGTH_SHORT).show();
        }
    }

}
