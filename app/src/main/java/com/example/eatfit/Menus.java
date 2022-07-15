package com.example.eatfit;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.List;

public class Menus implements Parcelable {

    private List<Restaurant> menus;

    protected Menus(Parcel in) {
        menus = in.createTypedArrayList(Restaurant.CREATOR);
    }

    public static final Creator<Menus> CREATOR = new Creator<Menus>() {
        @Override
        public Menus createFromParcel(Parcel in) {
            return new Menus(in);
        }

        @Override
        public Menus[] newArray(int size) {
            return new Menus[size];
        }
    };

    public Menus() {

    }

    public List<Restaurant> getMenus() {
        return menus;
    }

    public void setMenus(List<Restaurant> menus) {
        this.menus = menus;
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeTypedList(menus);
    }
}
