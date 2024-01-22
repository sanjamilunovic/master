package com.example.ema.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;
import com.example.ema.R;
import com.example.ema.viewmodels.ItemViewModel;
import com.google.android.material.appbar.MaterialToolbar;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;

import butterknife.BindView;
import butterknife.ButterKnife;

public class DetailItemAdapter extends RecyclerView.Adapter<DetailItemAdapter.DetailItemAViewHolder>{
    private ArrayList<ItemViewModel> lstItems;
    private Context context;


    public DetailItemAdapter(ArrayList<ItemViewModel>lstItems, Context context){
        this.lstItems = lstItems;
        this.context = context;

    }

    @Override
    public int getItemCount() {
        return lstItems.size();
    }

    @Override
    public DetailItemAdapter.DetailItemAViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item_detail, parent, false);
        DetailItemAdapter.DetailItemAViewHolder viewHolder = new DetailItemAdapter.DetailItemAViewHolder(v);


        return viewHolder;
    }

    @Override
    public void onBindViewHolder(DetailItemAdapter.DetailItemAViewHolder holder, int position) {
        ItemViewModel item = lstItems.get(position);
        holder.txtPurchaseLabel.setText("Purchase" + " " + (position+1));
        if(position==0)
        holder.txtDescription.setText("Desktop Computer");
        else
            holder.txtDescription.setText("MacBook Pro");


        SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy", Locale.getDefault());
        holder.tvPurchaseDate.setText(sdf.format(new Date()));
        holder.tvInvoice.setText(String.valueOf(item.getInvoice()));
        holder. tvCategory.setText(String.valueOf(item.getCategory()));
        holder.tvType.setText(String.valueOf(item.getType()));
        holder.tvAmount.setText("$" + item.getAmount());
        holder. tvDescription.setText(String.valueOf(item.getDescription()));
        holder.tvVendor.setText(String.valueOf(item.getVendor()));
        holder.tvEducationalBenefits.setText(String.valueOf(item.getEducationalBenefit()));


    }

    static class DetailItemAViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.txtDescription)
        TextView txtDescription;
        @BindView(R.id.txtPurchaseLabel)
        TextView txtPurchaseLabel;
        @BindView(R.id.tvPurchaseDate)
        TextView tvPurchaseDate;
        @BindView(R.id.tvInvoice)
        TextView tvInvoice;
        @BindView(R.id.tvCategory)
        TextView tvCategory;
        @BindView(R.id.tvType)
        TextView tvType;
        @BindView(R.id.tvAmount)
        TextView tvAmount;
        @BindView(R.id.tvDescription)
        TextView tvDescription;
        @BindView(R.id.tvVendor)
        TextView tvVendor;
        @BindView(R.id.tvEducationalBenefits)
        TextView tvEducationalBenefits;

        public DetailItemAViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
