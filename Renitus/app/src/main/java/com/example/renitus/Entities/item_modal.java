package com.example.renitus.Entities;

import android.os.Parcel;
import android.os.Parcelable;

public class item_modal implements Parcelable {

    private int item_id = 0;
    private String item_name;
    private String item_category;
    private String item_price;
    private String item_description;
    private double item_lat;
    private double item_long;


    public item_modal() {
    }

    public item_modal( String item_name, String item_category, String item_price, String item_description, double item_lat, double item_long) {
        this.item_name = item_name;
        this.item_category = item_category; // furniture, book, shoes
        this.item_price = item_price;
        this.item_description = item_description;
        this.item_lat = item_lat;
        this.item_long = item_long;
    }

    protected item_modal(Parcel in) {
        item_id = in.readInt();
        item_name = in.readString();
        item_category = in.readString();
        item_price = in.readString();
        item_description = in.readString();
        item_lat = in.readDouble();
        item_long = in.readDouble();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(item_id);
        dest.writeString(item_name);
        dest.writeString(item_category);
        dest.writeString(item_price);
        dest.writeString(item_description);
        dest.writeDouble(item_lat);
        dest.writeDouble(item_long);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<item_modal> CREATOR = new Creator<item_modal>() {
        @Override
        public item_modal createFromParcel(Parcel in) {
            return new item_modal(in);
        }

        @Override
        public item_modal[] newArray(int size) {
            return new item_modal[size];
        }
    };

    public int getItem_id() {
        return item_id;
    }

    public void setItem_id(int item_id) {
        this.item_id = item_id;
    }

    public String getItem_name() {
        return item_name;
    }

    public void setItem_name(String item_name) {
        this.item_name = item_name;
    }

    public String getItem_category() {
        return item_category;
    }

    public void setItem_category(String item_category) {
        this.item_category = item_category;
    }

    public String getItem_price() {
        return item_price;
    }

    public void setItem_price(String item_price) {
        this.item_price = item_price;
    }

    public String getItem_description() {
        return item_description;
    }

    public void setItem_description(String item_description) {
        this.item_description = item_description;
    }

    public double getItem_lat() {
        return item_lat;
    }

    public void setItem_lat(double item_lat) {
        this.item_lat = item_lat;
    }

    public double getItem_long() {
        return item_long;
    }

    public void setItem_long(double item_long) {
        this.item_long = item_long;
    }

}
