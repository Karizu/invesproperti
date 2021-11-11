package com.selada.invesproperti.presentation.adapter;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
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
import com.selada.invesproperti.model.response.ResponseProjects;
import com.selada.invesproperti.presentation.home.DetailProductActivity;
import com.selada.invesproperti.presentation.verification.VerificationData2Activity;
import com.selada.invesproperti.util.Constant;
import com.selada.invesproperti.util.MethodUtil;
import com.selada.invesproperti.util.PreferenceManager;
import com.skydoves.transformationlayout.TransformationCompat;
import com.skydoves.transformationlayout.TransformationLayout;
import com.white.progressview.HorizontalProgressView;

import java.util.List;

public class HomeFeedAdapter extends RecyclerView.Adapter<HomeFeedAdapter.ViewHolder> {
    private List<ResponseProjects> transactionModels;
    private Context context;
    private Activity activity;

    public HomeFeedAdapter(List<ResponseProjects> transactionModels, Context context, Activity activity) {
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
        ResponseProjects responseProjects = transactionModels.get(position);
        String id = responseProjects.getId();
        String name = responseProjects.getName();
        int minPrice = responseProjects.getFundingAmount();
        int maxPrice = responseProjects.getRequestedAmount();
        int pricePerLot = responseProjects.getPricePerLot();
        int percentage = (minPrice/maxPrice) * 100;
        int totalLot = responseProjects.getTotalLot();
        String dividenPeriod = responseProjects.getDividenPeriod();
        String interestRate = responseProjects.getInterestRate();

        holder.tvTitleItem.setText(name);
        holder.tvMinPrice.setText("Rp " + MethodUtil.toCurrencyFormat(String.valueOf(minPrice)));
        holder.tvMaxPrice.setText("Rp " + MethodUtil.toCurrencyFormat(String.valueOf(maxPrice)));
        holder.tvPricePerLot.setText("Rp " + MethodUtil.toCurrencyFormat(String.valueOf(pricePerLot)) + "/lot");
        holder.tvTotalLot.setText(totalLot + " lot");
        holder.tvDividenPeriod.setText(dividenPeriod);
        holder.tvInterestRate.setText(interestRate);
        holder.progressBar.setProgressInTime(percentage,2500);
        holder.cvItem.setOnClickListener(view -> {
            Intent intent = new Intent(context, DetailProductActivity.class);
            intent.putExtra("id", id);
            intent.putExtra("name", name);
            TransformationCompat.startActivity(holder.transformationLayout, intent);
        });
        //kondisi
        holder.baru_.setText("Baru");
        holder.frame_4.setBackground(activity.getResources().getDrawable(R.drawable.bg_round_home_item_orange));
    }

    @Override
    public int getItemCount() {
        return transactionModels.size();
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        TextView baru_, tvInterestRate;
        TextView tvTitleItem, tvTotalLot;
        TextView tvMinPrice, tvDividenPeriod;
        TextView tvMaxPrice, tvPricePerLot;
        HorizontalProgressView progressBar;
        FrameLayout frame_4;
        TransformationLayout transformationLayout;
        CardView cvItem;

        ViewHolder(View v) {
            super(v);
            baru_ = v.findViewById(R.id.baru_);
            tvTitleItem = v.findViewById(R.id.tvTitleItem);
            tvMinPrice = v.findViewById(R.id.tvMinPrice);
            tvMaxPrice = v.findViewById(R.id.tvMaxPrice);
            tvPricePerLot = v.findViewById(R.id.rp_1_350_000_lot);
            tvInterestRate = v.findViewById(R.id.tv_interest_rate);
            tvDividenPeriod = v.findViewById(R.id.tv_dividen_period);
            tvTotalLot = v.findViewById(R.id.tv_total_lot);
            progressBar = v.findViewById(R.id.progressBarItem);
            frame_4 = v.findViewById(R.id.frame_4);
            cvItem = v.findViewById(R.id.cvItem);
            transformationLayout = v.findViewById(R.id.transformationLayout);
        }
    }
}
