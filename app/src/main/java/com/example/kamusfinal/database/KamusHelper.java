package com.example.kamusfinal.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteStatement;

import com.example.kamusfinal.model.KamusModel;

import java.util.ArrayList;

import static android.provider.BaseColumns._ID;
import static com.example.kamusfinal.database.DatabaseContract.KamusColumns.ARTI;
import static com.example.kamusfinal.database.DatabaseContract.KamusColumns.KATA;
import static com.example.kamusfinal.database.DatabaseContract.TABLE_KATA;
import static com.example.kamusfinal.database.DatabaseContract.TABLE_KATA2;

public class KamusHelper {
    private Context context;
    private DatabaseHelper dataBaseHelper;
    private SQLiteDatabase database;
    private static final String INGGRIS_TABLE = DatabaseContract.TABLE_KATA;
    private static final String INDONESIA_TABLE = DatabaseContract.TABLE_KATA2;

    public KamusHelper(Context context) {
        this.context = context;
    }

    public KamusHelper open() throws SQLException {
        dataBaseHelper = new DatabaseHelper(context);
        database = dataBaseHelper.getWritableDatabase();
        return this;
    }

    public void close() {
        dataBaseHelper.close();
    }

    public ArrayList<KamusModel> getDataByName(String kata) {

            Cursor cursor = database.query(TABLE_KATA, null, KATA + " LIKE ?", new String[]{kata}, null, null, _ID + " ASC", null);
            cursor.moveToFirst();
            ArrayList<KamusModel> arrayList = new ArrayList<>();
            KamusModel kamusModel;
            if (cursor.getCount() > 0) {
                do {
                    kamusModel = new KamusModel();
                    kamusModel.setId(cursor.getInt(cursor.getColumnIndexOrThrow(_ID)));
                    kamusModel.setKata(cursor.getString(cursor.getColumnIndexOrThrow(KATA)));
                    kamusModel.setArti(cursor.getString(cursor.getColumnIndexOrThrow(ARTI)));

                    arrayList.add(kamusModel);
                    cursor.moveToNext();
                } while (!cursor.isAfterLast());
            }
            cursor.close();
            return arrayList;

        }


    public ArrayList<KamusModel> getAllData() {


            Cursor cursor = database.query(TABLE_KATA, null, null, null, null, null, _ID + " ASC", null);
            cursor.moveToFirst();
            ArrayList<KamusModel> arrayList = new ArrayList<>();
            KamusModel kamusModel;
            if (cursor.getCount() > 0) {
                do {
                    kamusModel = new KamusModel();
                   kamusModel.setId(cursor.getInt(cursor.getColumnIndexOrThrow(_ID)));
                    kamusModel.setKata(cursor.getString(cursor.getColumnIndexOrThrow(KATA)));
                    kamusModel.setArti(cursor.getString(cursor.getColumnIndexOrThrow(ARTI)));

                    arrayList.add(kamusModel);
                    cursor.moveToNext();

                } while (!cursor.isAfterLast());
            }
            cursor.close();
            return arrayList;
        }





    public void beginTransaction() {
        database.beginTransaction();
    }

    public void setTransactionSuccess() {
        database.setTransactionSuccessful();
    }

    public void endTransaction() {
        database.endTransaction();
    }

    public void insertTransaction(KamusModel kamusModel, String kode) {

        if (kode.equals("Inggris")) {
            String sql = "INSERT INTO " + TABLE_KATA + " (" + KATA + ", " + ARTI
                    + ") VALUES (?, ?)";
            SQLiteStatement stmt = database.compileStatement(sql);
            stmt.bindString(1, kamusModel.getKata());
            stmt.bindString(2, kamusModel.getArti());
            stmt.execute();
            stmt.clearBindings();
        } else if (kode.equals("Indonesia")) {
            String sql = "INSERT INTO " + TABLE_KATA2 + " (" + KATA + ", " + ARTI
                    + ") VALUES (?, ?)";
            SQLiteStatement stmt = database.compileStatement(sql);
            stmt.bindString(1, kamusModel.getKata());
            stmt.bindString(2, kamusModel.getArti());
            stmt.execute();
            stmt.clearBindings();
        }
    }

    public int update(KamusModel kamusModel, boolean isEngIna) {

        if (isEngIna) {
            TABLE_KATA = INGGRIS_TABLE;
        } else {
            TABLE_KATA2 = INDONESIA_TABLE;
        }

        ContentValues args = new ContentValues();
        args.put(KATA, kamusModel.getKata());
        args.put(ARTI, kamusModel.getArti());
        return database.update(TABLE_KATA, args, _ID + "= '" + kamusModel.getId() + "'", null);
    }


    public int delete(int id, boolean isEngIna) {
        if (isEngIna) {
            TABLE_KATA = INGGRIS_TABLE;
        } else {
            TABLE_KATA2 = INDONESIA_TABLE;
        }


        return database.delete(TABLE_KATA, _ID + " = '" + id + "'", null);
    }


}

