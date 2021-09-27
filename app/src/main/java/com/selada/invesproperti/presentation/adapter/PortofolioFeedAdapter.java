package com.selada.invesproperti.presentation.adapter;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.selada.invesproperti.R;
import com.selada.invesproperti.presentation.home.DetailProductActivity;
import com.selada.invesproperti.presentation.portofolio.DetailProductDidanaiActivity;
import com.white.progressview.HorizontalProgressView;

import java.util.List;

public class PortofolioFeedAdapter extends RecyclerView.Adapter<PortofolioFeedAdapter.ViewHolder> {
    private List<String> transactionModels;
    private Context context;
    private Activity activity;

    public PortofolioFeedAdapter(List<String> transactionModels, Context context, Activity activity) {
        this.transactionModels = transactionModels;
        this.context = context;
        this.activity = activity;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.list_item_portofolio, parent, false);
        return new ViewHolder(v);
    }

    @SuppressLint({"SetTextI18n", "ResourceAsColor", "CheckResult"})
    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
//        holder.progressBar.setProgressInTime(20,2500);
        holder.cvItem.setOnClickListener(view -> {
            Intent intent = new Intent(context, DetailProductDidanaiActivity.class);
            activity.startActivity(intent);
            activity.overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
        });
    }

    @Override
    public int getItemCount() {
        return transactionModels.size();
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        TextView tv_type_aset;
        TextView tv_asset_name;
        TextView tv_last_price;
        TextView tv_lot;
        TextView tv_percentage;
        CardView cvItem;

        ViewHolder(View v) {
            super(v);
            tv_type_aset = v.findViewById(R.id.tv_type_aset);
            tv_asset_name = v.findViewById(R.id.tv_asset_name);
            tv_last_price = v.findViewById(R.id.tv_last_price);
            tv_lot = v.findViewById(R.id.tv_lot);
            tv_percentage = v.findViewById(R.id.tv_percentage);
            cvItem = v.findViewById(R.id.cvItem);
        }
    }
}
