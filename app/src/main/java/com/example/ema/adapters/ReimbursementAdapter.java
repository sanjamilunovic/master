package com.example.ema.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.recyclerview.widget.RecyclerView;
import com.example.ema.R;
import com.example.ema.viewmodels.ReimbursementViewModel;
import java.util.ArrayList;
import butterknife.ButterKnife;

public class ReimbursementAdapter extends RecyclerView.Adapter<ReimbursementAdapter.ReimbursementViewHolder>{
    private ArrayList<ReimbursementViewModel> lstReimbursement;
    private Context context;
    private ReimbursementViewModel reimbursementViewModel;

    public ReimbursementAdapter(ArrayList<ReimbursementViewModel>lstReimbursement, Context context){
        this.lstReimbursement = lstReimbursement;
        this.context = context;

    }

    @Override
    public int getItemCount() {
        return lstReimbursement.size();
    }

    @Override
    public ReimbursementAdapter.ReimbursementViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item_reimbursement, parent, false);
        ReimbursementAdapter.ReimbursementViewHolder viewHolder = new ReimbursementAdapter.ReimbursementViewHolder(v);


        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ReimbursementAdapter.ReimbursementViewHolder holder, int position) {
        ReimbursementViewModel reimbursementViewModel = lstReimbursement.get(position);



    }

    static class ReimbursementViewHolder extends RecyclerView.ViewHolder {


        public ReimbursementViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
