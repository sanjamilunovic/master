package com.example.ema.adapters;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.LinearLayout;
import android.widget.TextView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.ema.ItemDetailActivity;
import com.example.ema.MainActivity;
import com.example.ema.R;
import com.example.ema.viewmodels.ItemViewModel;
import com.google.android.material.button.MaterialButton;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Locale;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ItemAdapter extends RecyclerView.Adapter<ItemAdapter.ItemViewHolder>{
    private ArrayList<ItemViewModel> lstItems;
    private Context context;


    public ItemAdapter(ArrayList<ItemViewModel>lstItems, Context context){
        this.lstItems = lstItems;
        this.context = context;

    }

    @Override
    public int getItemCount() {
        return lstItems.size();
    }

    @Override
    public ItemAdapter.ItemViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item, parent, false);
        ItemAdapter.ItemViewHolder viewHolder = new ItemAdapter.ItemViewHolder(v);


        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ItemAdapter.ItemViewHolder holder, int position) {
        ItemViewModel item = lstItems.get(position);
        holder.textViewDescription.setText(item.getDescription());
        holder.textViewAmount.setText("$" + String.valueOf(item.getAmount()));

        holder.contMain.setOnClickListener(v -> onClick(item));

    }

    static class ItemViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.textViewDescription)
        TextView textViewDescription;
        @BindView(R.id.textViewAmount)
        TextView textViewAmount;
        @BindView(R.id.contMain)
        LinearLayout contMain;


        public ItemViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }

    public void onClick(ItemViewModel item) {
        Dialog dialog = new Dialog(context);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.bottom_sheet_dialog_layout);

        TextView tvPurchaseDate = dialog.findViewById(R.id.tvPurchaseDate);
        TextView tvInvoice = dialog.findViewById(R.id.tvInvoice);
        TextView tvCategory = dialog.findViewById(R.id.tvCategory);
        TextView tvType = dialog.findViewById(R.id.tvType);
        TextView tvAmount = dialog.findViewById(R.id.tvAmount);
        TextView tvDescription = dialog.findViewById(R.id.tvDescription);
        TextView tvVendor = dialog.findViewById(R.id.tvVendor);
        TextView tvEducationalBenefit= dialog.findViewById(R.id.tvEducationalBenefits);

        SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy", Locale.getDefault());
        tvPurchaseDate.setText(sdf.format(item.getPurchaseDate()));
        tvInvoice.setText(String.valueOf(item.getInvoice()));
        tvCategory.setText(String.valueOf(item.getCategory()));
        tvType.setText(String.valueOf(item.getType()));
        tvAmount.setText("$" + item.getAmount());
        tvDescription.setText(String.valueOf(item.getDescription()));
        tvVendor.setText(String.valueOf(item.getVendor()));
        tvEducationalBenefit.setText(String.valueOf(item.getEducationalBenefit()));

        dialog.show();
        dialog.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT,ViewGroup.LayoutParams.WRAP_CONTENT);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.getWindow().setGravity(Gravity.BOTTOM);


    }
}
