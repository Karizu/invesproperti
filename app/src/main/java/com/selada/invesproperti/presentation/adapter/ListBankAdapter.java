package com.selada.invesproperti.presentation.adapter;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.selada.invesproperti.R;
import com.selada.invesproperti.presentation.portofolio.DetailProductDidanaiActivity;
import com.selada.invesproperti.presentation.profile.bank.AddBankActivity;
import com.selada.invesproperti.presentation.profile.bank.AkunBankActivity;
import com.selada.invesproperti.presentation.profile.bank.DetailBankActivity;
import com.selada.invesproperti.util.Constant;

import java.util.List;

public class ListBankAdapter extends RecyclerView.Adapter<ListBankAdapter.ViewHolder> {
    private List<String> transactionModels;
    private Context context;
    private Activity activity;

    public ListBankAdapter(List<String> transactionModels, Context context, Activity activity) {
        this.transactionModels = transactionModels;
        this.context = context;
        this.activity = activity;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.list_item_bank, parent, false);
        return new ViewHolder(v);
    }

    @SuppressLint({"SetTextI18n", "ResourceAsColor", "CheckResult"})
    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
//        holder.progressBar.setProgressInTime(20,2500);
        holder.cvItem.setOnClickListener(view -> {
            Intent intent = new Intent(activity, DetailBankActivity.class);
            activity.startActivity(intent);
            activity.overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
        });
    }

    @Override
    public int getItemCount() {
        return transactionModels.size();
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        TextView tv_bank_name;
        TextView tv_name;
        TextView tv_account_no;
        CardView cvItem;

        ViewHolder(View v) {
            super(v);
            tv_bank_name = v.findViewById(R.id.tv_bank_name);
            tv_name = v.findViewById(R.id.tv_name);
            tv_account_no = v.findViewById(R.id.tv_account_no);
            cvItem = v.findViewById(R.id.cv_item);
        }
    }
}
