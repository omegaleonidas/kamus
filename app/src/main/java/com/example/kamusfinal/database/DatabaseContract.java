package com.example.kamusfinal.database;

import android.provider.BaseColumns;


public class DatabaseContract {


    static String TABLE_KATA = "table_kamus";
    static String TABLE_KATA2 ="table_kamus1";

    static final class KamusColumns implements BaseColumns {
        static String KATA = "kata";
        static String ARTI = "arti";

    }


}
