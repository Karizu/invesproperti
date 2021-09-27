package com.selada.invesproperti.presentation.adapter;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextSwitcher;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.selada.invesproperti.R;
import com.selada.invesproperti.presentation.home.DetailProductActivity;
import com.selada.invesproperti.presentation.verification.VerificationData2Activity;
import com.white.progressview.HorizontalProgressView;

import java.util.List;

public class HomeFeedAdapter extends RecyclerView.Adapter<HomeFeedAdapter.ViewHolder> {
    private List<String> transactionModels;
    private Context context;
    private Activity activity;

    public HomeFeedAdapter(List<String> transactionModels, Context context, Activity activity) {
        this.transactionModels = transactionModels;
        this.context = context;
        this.activity = activity;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.list_item_home, parent, false);
        return new ViewHolder(v);
    }

    @SuppressLint({"SetTextI18n", "ResourceAsColor", "CheckResult"})
    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.progressBar.setProgressInTime(20,2500);
        holder.cvItem.setOnClickListener(view -> {
            Intent intent = new Intent(context, DetailProductActivity.class);
            activity.startActivity(intent);
        });
    }

    @Override
    public int getItemCount() {
        return transactionModels.size();
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        TextView baru_;
        TextView tvTitleItem;
        TextView tvMinPrice;
        TextView tvMaxPrice;
        HorizontalProgressView progressBar;
        FrameLayout frame_4;
        CardView cvItem;

        ViewHolder(View v) {
            super(v);
            baru_ = v.findViewById(R.id.baru_);
            tvTitleItem = v.findViewById(R.id.tvTitleItem);
            tvMinPrice = v.findViewById(R.id.tvMinPrice);
            tvMaxPrice = v.findViewById(R.id.tvMaxPrice);
            progressBar = v.findViewById(R.id.progressBarItem);
            frame_4 = v.findViewById(R.id.frame_4);
            cvItem = v.findViewById(R.id.cvItem);
        }
    }
}
