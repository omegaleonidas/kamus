package com.example.kamusfinal;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

public class MainActivityDetail extends AppCompatActivity {
    public static final String NAMA = "kata";
    public static final String TERJEMAHAN = "arti";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_detail);


        TextView nama = findViewById(R.id.detail_nama_text);
        TextView terjemahan = findViewById(R.id.detail_terjemahan_text);

        nama.setText(getIntent().getStringExtra("kata"));
        terjemahan.setText(getIntent().getStringExtra("arti"));


    }
}
