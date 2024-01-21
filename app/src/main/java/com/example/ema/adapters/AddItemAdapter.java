package com.example.ema.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import androidx.recyclerview.widget.RecyclerView;
import com.example.ema.R;
import com.example.ema.viewmodels.ItemViewModel;
import java.util.ArrayList;
import butterknife.BindView;
import butterknife.ButterKnife;

public class AddItemAdapter  extends RecyclerView.Adapter<AddItemAdapter.AddItemViewHolder>{
    private ArrayList<ItemViewModel> lstItems;
    private Context context;
    AddItemAdapter.AddItemViewHolder currentViewHolder;


    public AddItemAdapter(ArrayList<ItemViewModel>lstItems, Context context){
        this.lstItems = lstItems;
        this.context = context;

    }

    @Override
    public int getItemCount() {
        return lstItems.size();
    }

    @Override
    public AddItemAdapter.AddItemViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_add_item, parent, false);
        AddItemAdapter.AddItemViewHolder viewHolder = new AddItemAdapter.AddItemViewHolder(v);


        return viewHolder;
    }

    @Override
    public void onBindViewHolder(AddItemAdapter.AddItemViewHolder holder, int position) {
        ItemViewModel item = lstItems.get(position);
        if(lstItems.size()==1){
            currentViewHolder = holder;
        }



        holder.contMain.setOnClickListener(v -> onClick(holder));

    }

    static class AddItemViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.contMain)
        LinearLayout contMain;

        public AddItemViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }

    private void onClick(AddItemAdapter.AddItemViewHolder holder){
        currentViewHolder = holder;
    }
}
