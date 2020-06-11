package com.example.kamusfinal;

import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.widget.TextView;

import com.example.kamusfinal.adapter.KamusAdapter;
import com.example.kamusfinal.database.KamusHelperIN;
import com.example.kamusfinal.model.KamusModel;

import java.util.ArrayList;

public class inggrisAktivity extends AppCompatActivity {

    RecyclerView recyclerView;
    KamusAdapter kamusAdapter;
    KamusHelperIN kamusHelper;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        TextView english = findViewById(R.id.english);
        TextView indonesia = findViewById(R.id.indonesia);
        CardView change = findViewById(R.id.cardView);

        english.setText(R.string.inggris);
        indonesia.setText(R.string.indonesia);

        change.setOnClickListener(new CardView.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                startActivity(intent);
                finish();
            }
        });


        kamusHelper = new KamusHelperIN(getApplicationContext());
        recyclerView = findViewById(R.id.recyclerview);
        kamusAdapter = new KamusAdapter(this);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(kamusAdapter);
        recyclerView.setHasFixedSize(true);


        kamusHelper.open();
        ArrayList<KamusModel> kamusModels = kamusHelper.getAllData();
        kamusHelper.close();
        kamusAdapter.addKamus(kamusModels);


    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.main, menu);

        SearchManager searchManager = (SearchManager) getSystemService(Context.SEARCH_SERVICE);

        if (searchManager != null) {
            final SearchView searchView = (SearchView) (menu.findItem(R.id.search)).getActionView();
            searchView.setSearchableInfo(searchManager.getSearchableInfo(getComponentName()));
            searchView.setQueryHint(getResources().getString(R.string.action_search));
            searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {

                @Override
                public boolean onQueryTextSubmit(String query) {
                    recyclerView = findViewById(R.id.recyclerview);
                    recyclerView.setAdapter(kamusAdapter);
                    kamusHelper.open();
                    ArrayList<KamusModel> kamusModels = kamusHelper.getDataByName(query);
                    kamusHelper.close();
                    kamusAdapter.addKamus(kamusModels);

                    return true;

                }

                @Override
                public boolean onQueryTextChange(String newText) {
                    return false;
                }
            });
        }
        return true;
    }


}
