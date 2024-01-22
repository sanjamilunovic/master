package com.example.ema.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.example.ema.R;
import com.example.ema.viewmodels.ReimbursementViewModel;
import com.google.android.material.button.MaterialButton;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;
import butterknife.BindView;
import butterknife.ButterKnife;

public class ReimbursementAdapter extends RecyclerView.Adapter<ReimbursementAdapter.ReimbursementViewHolder>{
    private ArrayList<ReimbursementViewModel> lstReimbursement;
    private Context context;
    private boolean isExpanded = false;


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
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_reimbursement, parent, false);
        ReimbursementAdapter.ReimbursementViewHolder viewHolder = new ReimbursementAdapter.ReimbursementViewHolder(v);


        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ReimbursementAdapter.ReimbursementViewHolder holder, int position) {
        ReimbursementViewModel reimbursementViewModel = lstReimbursement.get(position);
        String[] stringArray = reimbursementViewModel.getStudent().split("[:]");
        holder.txtStudent.setText(stringArray[0]);
        holder.txtProgram.setText(stringArray[1]);

        Date date = new Date();
        SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy", Locale.getDefault());
        holder.txtDate.setText(sdf.format(date));
        holder.txtTotalAmount.setText("$" + reimbursementViewModel.getAmount());

//        LinearLayoutManager layoutManager = new LinearLayoutManager(context);
//        layoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
//        holder.recyclerViewItems.setLayoutManager(layoutManager);
//        holder.recyclerViewItems.setHasFixedSize(true);
//        ItemAdapter itemAdapter = new ItemAdapter(reimbursementViewModel.getItems(),context);
//        holder.recyclerViewItems.setAdapter(itemAdapter);

        holder.imageViewExpandCollapse.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                toggleExpandCollapse(holder,reimbursementViewModel);
            }
        });

    }

    static class ReimbursementViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.txtStudent)
        TextView txtStudent;
        @BindView(R.id.txtProgram)
        TextView txtProgram;
        @BindView(R.id.txtDate)
        TextView txtDate;
        @BindView(R.id.txtTotalAmount)
        TextView txtTotalAmount;
        @BindView(R.id.recyclerViewItems)
        RecyclerView recyclerViewItems;
        @BindView(R.id.imageViewExpandCollapse)
        ImageView imageViewExpandCollapse;
        @BindView(R.id.buttonViewReceipt)
        MaterialButton buttonViewReceipt;

        public ReimbursementViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }

    private void toggleExpandCollapse(ReimbursementAdapter.ReimbursementViewHolder holder,ReimbursementViewModel reimbursementViewModel) {
        isExpanded = !isExpanded;
        if (isExpanded) {
            holder.recyclerViewItems.setVisibility(View.VISIBLE);
            holder.imageViewExpandCollapse.setImageResource(R.drawable.ic_expand_less);
        } else {
            holder.recyclerViewItems.setVisibility(View.GONE);
            holder.imageViewExpandCollapse.setImageResource(R.drawable.ic_expand_more);
        }

        LinearLayoutManager layoutManager = new LinearLayoutManager(context);
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        holder.recyclerViewItems.setLayoutManager(layoutManager);
        holder.recyclerViewItems.setHasFixedSize(true);
        ItemAdapter itemAdapter = new ItemAdapter(reimbursementViewModel.getItems(),context);
        holder.recyclerViewItems.setAdapter(itemAdapter);
    }
}
