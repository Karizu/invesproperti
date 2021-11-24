package com.selada.invesproperti.presentation.adapter;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.selada.invesproperti.R;
import com.selada.invesproperti.model.response.Bank;
import com.selada.invesproperti.presentation.portofolio.deposit.DetailDepositActivity;
import com.selada.invesproperti.presentation.profile.bank.DetailBankActivity;

import java.util.List;

public class DepositBankAdapter extends RecyclerView.Adapter<DepositBankAdapter.ViewHolder> {
    private List<Bank> transactionModels;
    private Context context;
    private Activity activity;

    public DepositBankAdapter(List<Bank> transactionModels, Context context, Activity activity) {
        this.transactionModels = transactionModels;
        this.context = context;
        this.activity = activity;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.list_item_isi_saldo, parent, false);
        return new ViewHolder(v);
    }

    @SuppressLint({"SetTextI18n", "ResourceAsColor", "CheckResult"})
    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Bank bank = transactionModels.get(position);
        holder.tv_bank_name.setText(bank.getName() + " - ATM");
//        holder.progressBar.setProgressInTime(20,2500);
        if(bank.getName().toLowerCase().contains("bca")){
            holder.img_logo_bank.setImageDrawable(activity.getResources().getDrawable(R.drawable.ic_bca));
        } else if (bank.getName().toLowerCase().contains("mandiri")){
            holder.img_logo_bank.setImageDrawable(activity.getResources().getDrawable(R.drawable.ic_mandiri));
        } else if (bank.getName().toLowerCase().contains("permata")){
            holder.img_logo_bank.setImageDrawable(activity.getResources().getDrawable(R.drawable.ic_permata));
        }
        holder.cvItem.setOnClickListener(view -> {
            Intent intent = new Intent(activity, DetailDepositActivity.class);
            intent.putExtra("bank_name", holder.tv_bank_name.getText().toString());
            activity.startActivity(intent);
            activity.overridePendingTransition(R.anim.slide_in, R.anim.slide_out);
        });
    }

    @Override
    public int getItemCount() {
        return transactionModels.size();
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        TextView tv_bank_name;
        ImageView img_logo_bank;
        CardView cvItem;

        ViewHolder(View v) {
            super(v);
            tv_bank_name = v.findViewById(R.id.tv_bank_name);
            img_logo_bank = v.findViewById(R.id.img_logo_bank);
            cvItem = v.findViewById(R.id.cv_item);
        }
    }
}
