package com.example.kamusfinal;

import android.content.Intent;
import android.content.res.Resources;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.ProgressBar;

import com.example.kamusfinal.database.KamusHelper;
import com.example.kamusfinal.model.KamusModel;
import com.example.kamusfinal.prefs.AppPreference;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;

public class SplashActivity extends AppCompatActivity {
    KamusHelper kamusHelper;
    AppPreference appPreference;
    ArrayList<KamusModel> kamusModels = new ArrayList<KamusModel>();
    ProgressBar progressBar;
    ActionBarDrawerToggle toggle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main1);

        progressBar = (ProgressBar) findViewById(R.id.progress_bar);

        new loadData().execute();
    }

    public class loadData extends AsyncTask<Void, Integer, Void> {
        final String TAG = loadData.class.getCanonicalName();
        KamusHelper kamusHelper;

        double progress;
        double maxprogress = 100;

        protected void onPreExecute() {
            kamusHelper = new KamusHelper(SplashActivity.this);
            appPreference = new AppPreference(SplashActivity.this);
        }

        @Override
        protected Void doInBackground(Void... voids) {
            Boolean firstRun = appPreference.getFirstRun();
            if (firstRun) {
                ArrayList<KamusModel>kamusModels = preLoadRaw();
                ArrayList<KamusModel>kamusModels1 = preLoadRaw2();

                kamusHelper.open();

                progress = 30;
                publishProgress((int) progress);
                Double progressMaxInsert = 80.0;
                Double progressDiff = (progressMaxInsert - progress) / (kamusModels.size()+kamusModels1.size());
                kamusHelper.beginTransaction();
                try {
                    for (KamusModel model : kamusModels) {
                        Log.d("berhasilload", "masuk");
                        kamusHelper.insertTransaction(model, "Inggris");
                        progress += progressDiff;
                        publishProgress((int) progress);
                    }
                } catch (Exception e) {
                    Log.e("errorload", e.getMessage());
                }

                try {
                    for (KamusModel model : kamusModels1) {
                        Log.d("berhasilload", "masuk");
                        kamusHelper.insertTransaction(model, "Indonesia");
                        progress += progressDiff;
                        publishProgress((int) progress);
                    }
                } catch (Exception e) {
                    Log.e("errorload", e.getMessage());
                }


                kamusHelper.setTransactionSuccess();
                kamusHelper.endTransaction();

                kamusHelper.close();

                appPreference.setFirstRun(false);

                publishProgress((int) maxprogress);

                kamusHelper.close();
                appPreference.setFirstRun(false);
                publishProgress((int) maxprogress);

            } else {
                try {
                    synchronized (this) {
                        this.wait(2000);

                        publishProgress(50);

                        this.wait(2000);
                        publishProgress((int) maxprogress);
                    }
                } catch (Exception e) {
                    Log.d("loadData", e.getMessage());
                }
            }
            return null;
        }


        @Override
        protected void onProgressUpdate(Integer... values) {
            progressBar.setProgress(values[0]);
        }

        @Override
        protected void onPostExecute(Void result) {
            Intent i = new Intent(SplashActivity.this, MainActivity.class);
            startActivity(i);
            finish();
//
        }
    }

    public ArrayList<KamusModel> preLoadRaw() {
        ArrayList<KamusModel> kamusModels = new ArrayList<>();
        String line;
        BufferedReader reader;
        try {
            Resources res = getResources();
            InputStream raw_dict = res.openRawResource(R.raw.english_indonesia);

            reader = new BufferedReader(new InputStreamReader(raw_dict));
            int count = 0;
            do {
                line = reader.readLine();
                String[] splitstr = line.split("\t");

                KamusModel kamusModel;

                kamusModel = new KamusModel(splitstr[0], splitstr[1]);
                kamusModels.add(kamusModel);
                count++;
            } while (line != null);
        } catch (Exception e) {
            Log.d("preLoadRawa", e.getLocalizedMessage());
        }
        return kamusModels;
    }

    public ArrayList<KamusModel> preLoadRaw2() {
        ArrayList<KamusModel> kamusModels = new ArrayList<>();
        String line;
        BufferedReader reader;
        try {
            Resources res = getResources();
            InputStream raw_dict = res.openRawResource(R.raw.indonesia_english);

            reader = new BufferedReader(new InputStreamReader(raw_dict));
            int count = 0;
            do {
                line = reader.readLine();
                String[] splitstr = line.split("\t");

                KamusModel kamusModel;

                kamusModel = new KamusModel(splitstr[0], splitstr[1]);
                kamusModels.add(kamusModel);
                count++;
            } while (line != null);
        } catch (Exception e) {
            Log.d("preLoadRawa", e.getLocalizedMessage());
        }
        return kamusModels;
    }


}
