package com.example.renitus.Entities;

import android.os.Parcel;
import android.os.Parcelable;

public class cart_modal implements Parcelable {

    private String item_name;
    private String item_category;
    private String item_price;
    private int quantity;

    public cart_modal() {
    }

    public cart_modal(String item_name, String item_category, String item_price, int quantity) {
        this.item_name = item_name;
        this.item_category = item_category;
        this.item_price = item_price;
        this.quantity = quantity;
    }

    protected cart_modal(Parcel in) {
        item_name = in.readString();
        item_category = in.readString();
        item_price = in.readString();
        quantity = in.readInt();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(item_name);
        dest.writeString(item_category);
        dest.writeString(item_price);
        dest.writeInt(quantity);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<cart_modal> CREATOR = new Creator<cart_modal>() {
        @Override
        public cart_modal createFromParcel(Parcel in) {
            return new cart_modal(in);
        }

        @Override
        public cart_modal[] newArray(int size) {
            return new cart_modal[size];
        }
    };

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

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }
}
