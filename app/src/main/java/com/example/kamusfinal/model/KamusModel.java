package com.example.kamusfinal.model;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;

public class KamusModel extends ArrayList<KamusModel> implements Parcelable {

    private  int id ;
    private  String kata;
    private  String arti;

    public KamusModel(int id, String kata, String arti) {
        this.id = id;
        this.kata = kata;
        this.arti = arti;
    }

    public KamusModel(String kata, String arti) {
        this.kata = kata;
        this.arti = arti;
    }

    public KamusModel(){

    }


    public KamusModel(Parcel in) {
        id = in.readInt();
        kata = in.readString();
        arti = in.readString();
    }

    public static final Creator<KamusModel> CREATOR = new Creator<KamusModel>() {
        @Override
        public KamusModel createFromParcel(Parcel in) {
            return new KamusModel(in);
        }

        @Override
        public KamusModel[] newArray(int size) {
            return new KamusModel[size];
        }
    };

    public KamusModel(String s) {

    }


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getKata() {
        return kata;
    }

    public void setKata(String kata) {
        this.kata = kata;
    }

    public String getArti() {
        return arti;
    }

    public void setArti(String arti) {
        this.arti = arti;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(id);
        dest.writeString(kata);
        dest.writeString(arti);
    }
}
