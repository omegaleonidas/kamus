package com.example.kamusfinal.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.kamusfinal.MainActivityDetail;
import com.example.kamusfinal.R;
import com.example.kamusfinal.model.KamusModel;

import java.util.ArrayList;

public class KamusAdapter extends RecyclerView.Adapter<KamusAdapter.KamusHolder> {
    private ArrayList<KamusModel> kamusModels = new ArrayList<>();

    public KamusAdapter(Context context) {
        this.context = context;
    }

    private Context context;

    public void addKamus(ArrayList<KamusModel> kamusModels) {
        this.kamusModels = kamusModels;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public KamusHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_kamus, viewGroup, false);
        return new KamusHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull KamusHolder kamusHolder, int i) {
        kamusHolder.kata.setText(kamusModels.get(i).getKata());
    }

    @Override
    public int getItemCount() {
        return kamusModels.size();
    }

    public class KamusHolder extends RecyclerView.ViewHolder {
        private TextView arti;
        private TextView kata;

        public KamusHolder(View itemView) {
            super(itemView);
            kata = (TextView) itemView.findViewById(R.id.txt_kata1);


            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int position = getAdapterPosition();


                    Intent intent = new Intent(context, MainActivityDetail.class);

                    intent.putExtra("kata", kamusModels.get(position).getKata());
                    intent.putExtra("arti", kamusModels.get(position).getArti());
                    context.startActivity(intent);
                }
            });
        }
    }
}
