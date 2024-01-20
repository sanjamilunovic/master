package com.example.ema.adapters;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.ema.ItemDetailActivity;
import com.example.ema.MainActivity;
import com.example.ema.R;
import com.example.ema.viewmodels.ItemViewModel;
import com.google.android.material.button.MaterialButton;

import java.util.ArrayList;

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
        holder.textViewAmount.setText("$" + item.getAmount());
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
        Intent intent = new Intent(context, ItemDetailActivity.class);
        ItemDetailActivity.DataHolder.setData(item);
        context.startActivity(intent);


    }
}
