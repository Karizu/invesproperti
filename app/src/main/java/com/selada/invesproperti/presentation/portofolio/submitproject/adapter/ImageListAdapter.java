package com.selada.invesproperti.presentation.portofolio.submitproject.adapter;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.net.Uri;
import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.selada.invesproperti.R;
import com.selada.invesproperti.presentation.portofolio.deposit.DetailDepositActivity;
import com.selada.invesproperti.presentation.portofolio.submitproject.SubmitProject3Activity;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.List;
import java.util.Objects;

import static com.facebook.FacebookSdk.getApplicationContext;

public class ImageListAdapter extends RecyclerView.Adapter<ImageListAdapter.ViewHolder> {
    private List<Uri> transactionModels;
    private Context context;
    private Activity activity;

    public ImageListAdapter(List<Uri> transactionModels, Context context, Activity activity) {
        this.transactionModels = transactionModels;
        this.context = context;
        this.activity = activity;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.list_item_image, parent, false);
        return new ViewHolder(v);
    }

    @SuppressLint({"SetTextI18n", "ResourceAsColor", "CheckResult"})
    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Uri uri = transactionModels.get(position);
        String[] filePathColumn = { MediaStore.Images.Media.DATA };
        Cursor cursor = activity.getContentResolver().query(uri,
                filePathColumn, null, null, null);
        Objects.requireNonNull(cursor).moveToFirst();

        int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
        File file = new File(cursor.getString(columnIndex));

        try {
            Bitmap bitmap = MediaStore.Images.Media.getBitmap(activity.getContentResolver(), uri);
            holder.img_gallery.setImageBitmap(bitmap);
        } catch (IOException e) {
            e.printStackTrace();
        }

        holder.tv_file_name.setText(file.getName());
        holder.btn_remove.setOnClickListener(view -> {
            transactionModels.remove(position);
            if (activity instanceof SubmitProject3Activity) {
                ((SubmitProject3Activity)activity).updateMArrayUri(uri);
                notifyDataSetChanged();
            }
        });
    }

    @Override
    public int getItemCount() {
        return transactionModels.size();
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        TextView tv_file_name;
        ImageView img_gallery, btn_remove;

        ViewHolder(View v) {
            super(v);
            tv_file_name = v.findViewById(R.id.tv_file_name);
            img_gallery = v.findViewById(R.id.img_gallery);
            btn_remove = v.findViewById(R.id.btn_remove);
        }
    }
}
