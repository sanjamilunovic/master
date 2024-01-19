package com.example.ema.adapters;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.recyclerview.widget.RecyclerView;
import com.example.ema.R;
import com.example.ema.viewmodels.ItemViewModel;
import com.google.android.material.button.MaterialButton;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ItemAdapter extends RecyclerView.Adapter<ItemAdapter.ItemViewHolder>{
    private ArrayList<ItemViewModel> lstItems;
    private int lastPositon;


    public ItemAdapter(ArrayList<ItemViewModel>lstItems){
        this.lstItems = lstItems;

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
        lastPositon = position+1;
        holder.btnItem.setText("Item " + lastPositon);

        holder.btnItem.setOnClickListener(v -> onClick(item));



    }

    static class ItemViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.btnItem)
        MaterialButton btnItem;


        public ItemViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }

    public void onClick(ItemViewModel item) {

    }
}
