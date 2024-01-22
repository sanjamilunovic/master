package com.example.ema.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import androidx.recyclerview.widget.RecyclerView;
import com.example.ema.R;
import com.example.ema.viewmodels.ItemViewModel;
import com.example.ema.viewmodels.ReimbursementViewModel;

import java.util.ArrayList;
import butterknife.BindView;
import butterknife.ButterKnife;

public class ListItemReimbursementAdapter extends RecyclerView.Adapter<ListItemReimbursementAdapter.ListItemReimbursementViewHolder>{
    private ArrayList<ItemViewModel>lstItems;
    private Context context;
    private int totalAmount;
    private ReimbursementViewModel reimbursementViewModel;

    public ListItemReimbursementAdapter(ArrayList<ItemViewModel>lstItems, Context context, ReimbursementViewModel reimbursementViewModel){
        this.lstItems = lstItems;
        this.context = context;
        this.reimbursementViewModel = reimbursementViewModel;

    }

    @Override
    public int getItemCount() {
        return lstItems.size();
    }

    @Override
    public ListItemReimbursementViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item_reimbursement, parent, false);
        ListItemReimbursementViewHolder viewHolder = new ListItemReimbursementViewHolder(v);


        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ListItemReimbursementViewHolder holder, int position) {
        ItemViewModel item = lstItems.get(position);
        holder.txtItem.setText(item.getDescription());
        holder.txtAmount.setText("$" + " " + item.getAmount());
        totalAmount+=item.getAmount();

        if(position==lstItems.size()-1){
            holder.totalLayout.setVisibility(View.VISIBLE);
            holder.txtTotalAmount.setText("$" + " " + totalAmount);
            holder.view.setVisibility(View.VISIBLE);
            reimbursementViewModel.setAmount(totalAmount);
        }


    }

    static class ListItemReimbursementViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.txtItem)
        TextView txtItem;

        @BindView(R.id.txtAmount)
        TextView txtAmount;

        @BindView(R.id.totalLayout)
        LinearLayout totalLayout;

        @BindView(R.id.txtTotalAmount)
        TextView txtTotalAmount;

        @BindView(R.id.view)
        View view;

        public ListItemReimbursementViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
