package com.example.renitus.Entities;

import android.os.Parcel;
import android.os.Parcelable;

public class shoes_modal implements Parcelable {

    private int shoes_id = 0;
    private String shoes_size; // 6, 7, 8;
    private String shoes_brand; // plastic, wooden;
    private String shoes_price;

    public shoes_modal() {}

    public shoes_modal(int shoes_id, String shoes_size, String shoes_brand, String shoes_price) {
        this.shoes_id = shoes_id;
        this.shoes_size = shoes_size;
        this.shoes_brand = shoes_brand;
        this.shoes_price = shoes_price;
    }


    protected shoes_modal(Parcel in) {
        shoes_id = in.readInt();
        shoes_size = in.readString();
        shoes_brand = in.readString();
        shoes_price = in.readString();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(shoes_id);
        dest.writeString(shoes_size);
        dest.writeString(shoes_brand);
        dest.writeString(shoes_price);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<shoes_modal> CREATOR = new Creator<shoes_modal>() {
        @Override
        public shoes_modal createFromParcel(Parcel in) {
            return new shoes_modal(in);
        }

        @Override
        public shoes_modal[] newArray(int size) {
            return new shoes_modal[size];
        }
    };

    public int getShoes_id() {
        return shoes_id;
    }

    public void setShoes_id(int shoes_id) {
        this.shoes_id = shoes_id;
    }

    public String getShoes_size() {
        return shoes_size;
    }

    public void setShoes_size(String shoes_size) {
        this.shoes_size = shoes_size;
    }

    public String getShoes_brand() {
        return shoes_brand;
    }

    public void setShoes_brand(String shoes_brand) {
        this.shoes_brand = shoes_brand;
    }

    public String getShoes_price() {
        return shoes_price;
    }

    public void setShoes_price(String shoes_price) {
        this.shoes_price = shoes_price;
    }
}
