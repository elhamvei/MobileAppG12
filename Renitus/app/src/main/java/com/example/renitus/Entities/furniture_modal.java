package com.example.renitus.Entities;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.NonNull;

public class furniture_modal implements Parcelable {

    private int furniture_id = 0;
    private String furniture_type; // table, chair, sofa;
    private String furniture_material; // plastic, wooden;
    private String furniture_price;

    public furniture_modal() {
    }

    public furniture_modal(int furniture_id, String furniture_type, String furniture_material, String furniture_price) {
        this.furniture_id = furniture_id;
        this.furniture_type = furniture_type;
        this.furniture_material = furniture_material;
        this.furniture_price = furniture_price;
    }

    protected furniture_modal(Parcel in) {
        furniture_id = in.readInt();
        furniture_type = in.readString();
        furniture_material = in.readString();
        furniture_price = in.readString();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(furniture_id);
        dest.writeString(furniture_type);
        dest.writeString(furniture_material);
        dest.writeString(furniture_price);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<furniture_modal> CREATOR = new Creator<furniture_modal>() {
        @Override
        public furniture_modal createFromParcel(Parcel in) {
            return new furniture_modal(in);
        }

        @Override
        public furniture_modal[] newArray(int size) {
            return new furniture_modal[size];
        }
    };

    public int getFurniture_id() {
        return furniture_id;
    }

    public void setFurniture_id(int furniture_id) {
        this.furniture_id = furniture_id;
    }

    public String getFurniture_type() {
        return furniture_type;
    }

    public void setFurniture_type(String furniture_type) {
        this.furniture_type = furniture_type;
    }

    public String getFurniture_material() {
        return furniture_material;
    }

    public void setFurniture_material(String furniture_material) {
        this.furniture_material = furniture_material;
    }

    public String getFurniture_price() {
        return furniture_price;
    }

    public void setFurniture_price(String furniture_price) {
        this.furniture_price = furniture_price;
    }
}
