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
import com.selada.invesproperti.model.response.ResponseProjects;
import com.selada.invesproperti.presentation.portofolio.DetailProductDidanaiActivity;
import com.selada.invesproperti.util.MethodUtil;

import java.util.List;

public class PortofolioFeedPOAdapter extends RecyclerView.Adapter<PortofolioFeedPOAdapter.ViewHolder> {
    private List<ResponseProjects> transactionModels;
    private Context context;
    private Activity activity;

    public PortofolioFeedPOAdapter(List<ResponseProjects> transactionModels, Context context, Activity activity) {
        this.transactionModels = transactionModels;
        this.context = context;
        this.activity = activity;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.list_item_portofolio_po, parent, false);
        return new ViewHolder(v);
    }

    @SuppressLint({"SetTextI18n", "ResourceAsColor", "CheckResult"})
    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        ResponseProjects responseProjects = transactionModels.get(position);

        holder.tv_asset_name.setText(responseProjects.getName());
        holder.tv_terdanai.setText("Rp " + MethodUtil.toCurrencyFormat(String.valueOf(responseProjects.getFundingAmount())));
        holder.tv_target.setText("Rp " + MethodUtil.toCurrencyFormat(String.valueOf(responseProjects.getRequestedAmount())));
        holder.tv_lot_terjual.setText(responseProjects.getTotalLot() + " Lot");
//        holder.tv_didanai_oleh.setText("");
        holder.cvItem.setOnClickListener(view -> {
            Intent intent = new Intent(context, DetailProductDidanaiActivity.class);
            intent.putExtra("id", responseProjects.getId());
            intent.putExtra("name", responseProjects.getName());
            activity.startActivity(intent);
            activity.overridePendingTransition(R.anim.slide_in, R.anim.slide_out);
        });
    }

    @Override
    public int getItemCount() {
        return transactionModels.size();
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        TextView tv_type_aset;
        TextView tv_asset_name;
        TextView tv_terdanai;
        TextView tv_target;
        TextView tv_lot_terjual;
        TextView tv_didanai_oleh;
        CardView cvItem;

        ViewHolder(View v) {
            super(v);
            tv_type_aset = v.findViewById(R.id.tv_type_aset);
            tv_asset_name = v.findViewById(R.id.tv_asset_name);
            tv_terdanai = v.findViewById(R.id.tv_terdanai);
            tv_target = v.findViewById(R.id.tv_target);
            tv_lot_terjual = v.findViewById(R.id.tv_lot_terjual);
            tv_didanai_oleh = v.findViewById(R.id.tv_didanai_oleh);
            cvItem = v.findViewById(R.id.cvItem);
        }
    }
}
