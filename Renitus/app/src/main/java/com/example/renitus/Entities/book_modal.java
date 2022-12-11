package com.example.renitus.Entities;

import android.os.Parcel;
import android.os.Parcelable;

public class book_modal implements Parcelable {

    private int book_id = 0;
    private String book_name;
    private String book_author;
    private String book_price;

    public book_modal(int book_id, String book_name, String book_author, String book_price) {
        this.book_id = book_id;
        this.book_name = book_name;
        this.book_author = book_author;
        this.book_price = book_price;
    }

    public book_modal() {
    }

    protected book_modal(Parcel in) {
        book_id = in.readInt();
        book_name = in.readString();
        book_author = in.readString();
        book_price = in.readString();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(book_id);
        dest.writeString(book_name);
        dest.writeString(book_author);
        dest.writeString(book_price);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<book_modal> CREATOR = new Creator<book_modal>() {
        @Override
        public book_modal createFromParcel(Parcel in) {
            return new book_modal(in);
        }

        @Override
        public book_modal[] newArray(int size) {
            return new book_modal[size];
        }
    };

    public int getBook_id() {
        return book_id;
    }

    public void setBook_id(int book_id) {
        this.book_id = book_id;
    }

    public String getBook_name() {
        return book_name;
    }

    public void setBook_name(String book_name) {
        this.book_name = book_name;
    }

    public String getBook_author() {
        return book_author;
    }

    public void setBook_author(String book_author) {
        this.book_author = book_author;
    }

    public String getBook_price() {
        return book_price;
    }

    public void setBook_price(String book_price) {
        this.book_price = book_price;
    }
}
