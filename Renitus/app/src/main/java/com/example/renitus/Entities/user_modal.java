package com.example.renitus.Entities;

import android.os.Parcel;
import android.os.Parcelable;

public class user_modal implements Parcelable {

    private String user_email;
    private String user_pass;
    private String user_type; // renter or owner

    public user_modal() {
    }

    public user_modal(String user_email, String user_pass, String user_type) {
        this.user_email = user_email;
        this.user_pass = user_pass;
        this.user_type = user_type;
    }

    protected user_modal(Parcel in) {
        user_email = in.readString();
        user_pass = in.readString();
        user_type = in.readString();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(user_email);
        dest.writeString(user_pass);
        dest.writeString(user_type);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<user_modal> CREATOR = new Creator<user_modal>() {
        @Override
        public user_modal createFromParcel(Parcel in) {
            return new user_modal(in);
        }

        @Override
        public user_modal[] newArray(int size) {
            return new user_modal[size];
        }
    };

    public String getUser_email() {
        return user_email;
    }

    public void setUser_email(String user_email) {
        this.user_email = user_email;
    }

    public String getUser_pass() {
        return user_pass;
    }

    public void setUser_pass(String user_pass) {
        this.user_pass = user_pass;
    }

    public String getUser_type() {
        return user_type;
    }

    public void setUser_type(String user_type) {
        this.user_type = user_type;
    }
}
