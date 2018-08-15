package com.moolajoo.waferchallenge.model;

import android.os.Parcel;
import android.os.Parcelable;

public class Country implements Parcelable {

    private String name;
    private String currency;
    private String language;

    public Country(String n, String c, String l) {
        name = n;
        currency = c;
        language = l;

    }

    protected Country(Parcel in) {
        name = in.readString();
        currency = in.readString();
        language = in.readString();
    }

    public String getName() {
        return name;
    }

    public String getCurrency() {
        return currency;
    }

    public String getLanguage() {
        return language;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

    public void setLanguage(String language) {
        this.language = language;
    }

    public static final Creator<Country> CREATOR = new Creator<Country>() {
        @Override
        public Country createFromParcel(Parcel in) {
            return new Country(in);
        }

        @Override
        public Country[] newArray(int size) {
            return new Country[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(name);
        parcel.writeString(currency);
        parcel.writeString(language);
    }
}
