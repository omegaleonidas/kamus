package com.example.kamusfinal.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import static android.provider.BaseColumns._ID;
import static com.example.kamusfinal.database.DatabaseContract.KamusColumns.ARTI;
import static com.example.kamusfinal.database.DatabaseContract.KamusColumns.KATA;
import static com.example.kamusfinal.database.DatabaseContract.TABLE_KATA;
import static com.example.kamusfinal.database.DatabaseContract.TABLE_KATA2;

public class DatabaseHelper extends SQLiteOpenHelper{
   
 private static  String DATABASE_NAME = "kamus";

 private  static  final  int DATABASE_VERSION = 1;

    public static String CREATE_TABLE_KATA = "create table " +TABLE_KATA+
            " (" + _ID + " integer primary key autoincrement, " +
            KATA + " text not null, " +
            ARTI + " text not null);";




    public static String CREATE_TABLE_KATA1 = "create table "+TABLE_KATA2+
            " ("+_ID+" integer primary key autoincrement, " +
            KATA+" text not null, " +
            ARTI+" text not null);";

   public DatabaseHelper(Context context){
      super(context,DATABASE_NAME,null,DATABASE_VERSION);

   }

   @Override
    public void onCreate(SQLiteDatabase db) {
    db.execSQL(CREATE_TABLE_KATA);
    db.execSQL(CREATE_TABLE_KATA1);
        
    }



    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

       db.execSQL("DROP TABLE IF EXISTS " + TABLE_KATA);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_KATA2);
       onCreate(db);
    }
}

